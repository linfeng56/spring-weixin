<template>
  <div class="pull-to-refresh" ref="containerRef" @scroll="handleScroll">
    <div class="pull-to-refresh-indicator" :class="{ refreshing: isRefreshing, pulling: isPulling }">
      <div class="indicator-content">
        <span v-if="isRefreshing" class="loading-icon">⟳</span>
        <span v-else class="arrow-icon" :class="{ rotated: pullDistance >= threshold }">↓</span>
        <span class="indicator-text">{{ getIndicatorText }}</span>
      </div>
    </div>
    <div class="pull-to-refresh-content">
      <slot></slot>
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent, ref, computed, onMounted, onUnmounted } from 'vue';

  export default defineComponent({
    name: 'PullToRefresh',
    props: {
      onRefresh: {
        type: Function as () => Promise<void>,
        required: true,
      },
      threshold: {
        type: Number,
        default: 60,
      },
      damping: {
        type: Number,
        default: 2,
      },
    },
    setup(props) {
      const containerRef = ref<HTMLElement | null>(null);
      const startY = ref(0);
      const pullDistance = ref(0);
      const isPulling = ref(false);
      const isRefreshing = ref(false);
      const isAtTop = ref(true);

      const getIndicatorText = computed(() => {
        if (isRefreshing.value) {
          return '刷新中...';
        }
        if (pullDistance.value >= props.threshold) {
          return '释放刷新';
        }
        return '下拉刷新';
      });

      function handleScroll(e: Event) {
        const target = e.target as HTMLElement;
        isAtTop.value = target.scrollTop <= 0;
      }

      function handleTouchStart(e: TouchEvent) {
        if (!isAtTop.value || isRefreshing.value) return;
        startY.value = e.touches[0].clientY;
        isPulling.value = true;
      }

      function handleTouchMove(e: TouchEvent) {
        if (!isPulling.value || !isAtTop.value) return;
        
        const currentY = e.touches[0].clientY;
        const deltaY = currentY - startY.value;
        
        if (deltaY > 0) {
          e.preventDefault();
          pullDistance.value = Math.min(deltaY / props.damping, props.threshold * 1.5);
        }
      }

      async function handleTouchEnd() {
        if (!isPulling.value) return;
        isPulling.value = false;

        if (pullDistance.value >= props.threshold) {
          await doRefresh();
        }
        
        pullDistance.value = 0;
      }

      async function doRefresh() {
        isRefreshing.value = true;
        try {
          await props.onRefresh();
        } finally {
          isRefreshing.value = false;
        }
      }

      onMounted(() => {
        const container = containerRef.value;
        if (container) {
          container.addEventListener('touchstart', handleTouchStart, { passive: true });
          container.addEventListener('touchmove', handleTouchMove, { passive: false });
          container.addEventListener('touchend', handleTouchEnd, { passive: true });
        }
      });

      onUnmounted(() => {
        const container = containerRef.value;
        if (container) {
          container.removeEventListener('touchstart', handleTouchStart);
          container.removeEventListener('touchmove', handleTouchMove);
          container.removeEventListener('touchend', handleTouchEnd);
        }
      });

      return {
        containerRef,
        pullDistance,
        isPulling,
        isRefreshing,
        getIndicatorText,
        handleScroll,
      };
    },
  });
</script>

<style lang="less" scoped>
  .pull-to-refresh {
    height: 100%;
    overflow-y: auto;
    -webkit-overflow-scrolling: touch;
    position: relative;

    &-indicator {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      height: 50px;
      display: flex;
      align-items: center;
      justify-content: center;
      transform: translateY(-100%);
      transition: transform 0.2s;
      z-index: 10;

      &.pulling,
      &.refreshing {
        transform: translateY(0);
      }

      .indicator-content {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 4px;
      }

      .arrow-icon {
        font-size: 16px;
        color: #999;
        transition: transform 0.2s;

        &.rotated {
          transform: rotate(180deg);
        }
      }

      .loading-icon {
        font-size: 18px;
        color: #1890ff;
        animation: spin 1s linear infinite;
      }

      .indicator-text {
        font-size: 12px;
        color: #999;
      }
    }

    &-content {
      min-height: 100%;
    }
  }

  @keyframes spin {
    from {
      transform: rotate(0deg);
    }
    to {
      transform: rotate(360deg);
    }
  }
</style>
