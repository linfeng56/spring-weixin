<template>
  <div class="swipe-action" ref="containerRef" @touchstart="handleTouchStart" @touchmove="handleTouchMove" @touchend="handleTouchEnd">
    <div class="swipe-action-content" :style="contentStyle">
      <slot></slot>
    </div>
    <div class="swipe-action-buttons" :class="direction">
      <div v-if="leftActions.length" class="swipe-action-left">
        <div
          v-for="(action, index) in leftActions"
          :key="'left-' + index"
          class="swipe-action-button"
          :style="{ backgroundColor: action.color || '#ff4d4f' }"
          @click="handleActionClick(action)"
        >
          <span>{{ action.text }}</span>
        </div>
      </div>
      <div v-if="rightActions.length" class="swipe-action-right">
        <div
          v-for="(action, index) in rightActions"
          :key="'right-' + index"
          class="swipe-action-button"
          :style="{ backgroundColor: action.color || '#52c41a' }"
          @click="handleActionClick(action)"
        >
          <span>{{ action.text }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent, ref, computed, PropType } from 'vue';

  export interface SwipeActionButton {
    text: string;
    color?: string;
    onClick?: () => void;
  }

  export default defineComponent({
    name: 'SwipeAction',
    props: {
      leftActions: {
        type: Array as PropType<SwipeActionButton[]>,
        default: () => [],
      },
      rightActions: {
        type: Array as PropType<SwipeActionButton[]>,
        default: () => [],
      },
      disabled: {
        type: Boolean,
        default: false,
      },
      threshold: {
        type: Number,
        default: 60,
      },
    },
    emits: ['action'],
    setup(props, { emit }) {
      const containerRef = ref<HTMLElement | null>(null);
      const startX = ref(0);
      const startY = ref(0);
      const currentX = ref(0);
      const isMoving = ref(false);
      const direction = ref<'left' | 'right' | ''>('');

      const contentStyle = computed(() => {
        const offset = currentX.value;
        return {
          transform: `translateX(${offset}px)`,
          transition: isMoving.value ? 'none' : 'transform 0.3s ease',
        };
      });

      function handleTouchStart(e: TouchEvent) {
        if (props.disabled) return;
        const touch = e.touches[0];
        startX.value = touch.clientX;
        startY.value = touch.clientY;
        isMoving.value = true;
      }

      function handleTouchMove(e: TouchEvent) {
        if (props.disabled || !isMoving.value) return;
        const touch = e.touches[0];
        const deltaX = touch.clientX - startX.value;
        const deltaY = touch.clientY - startY.value;

        if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > 10) {
          e.preventDefault();
          
          if (deltaX > 0 && props.leftActions.length) {
            direction.value = 'left';
            currentX.value = Math.min(deltaX, 120);
          } else if (deltaX < 0 && props.rightActions.length) {
            direction.value = 'right';
            currentX.value = Math.max(deltaX, -120);
          }
        }
      }

      function handleTouchEnd() {
        if (props.disabled || !isMoving.value) return;
        isMoving.value = false;

        if (currentX.value !== 0) {
          const shouldOpen = Math.abs(currentX.value) > props.threshold;
          currentX.value = shouldOpen ? (direction.value === 'left' ? 120 : -120) : 0;
        }
      }

      function handleActionClick(action: SwipeActionButton) {
        if (action.onClick) {
          action.onClick();
        }
        emit('action', action);
        currentX.value = 0;
      }

      return {
        containerRef,
        contentStyle,
        direction,
        handleTouchStart,
        handleTouchMove,
        handleTouchEnd,
        handleActionClick,
      };
    },
  });
</script>

<style lang="less" scoped>
  .swipe-action {
    position: relative;
    overflow: hidden;

    &-content {
      position: relative;
      z-index: 1;
      background: #fff;
    }

    &-buttons {
      position: absolute;
      top: 0;
      bottom: 0;
      display: flex;
      z-index: 0;

      &.left {
        left: 0;
        flex-direction: row;
      }

      &.right {
        right: 0;
        flex-direction: row-reverse;
      }
    }

    &-button {
      display: flex;
      align-items: center;
      justify-content: center;
      width: 80px;
      height: 100%;
      color: #fff;
      font-size: 14px;
      cursor: pointer;

      span {
        padding: 0 8px;
        text-align: center;
      }
    }
  }
</style>
