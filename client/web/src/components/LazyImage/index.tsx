<template>
  <div class="lazy-image" ref="containerRef">
    <img
      v-if="isVisible"
      :src="src"
      :alt="alt"
      :class="imageClass"
      @load="handleLoad"
      @error="handleError"
    />
    <div v-else class="lazy-image-placeholder" :class="placeholderClass">
      <slot name="placeholder">
        <div class="default-placeholder"></div>
      </slot>
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent, ref, onMounted, onUnmounted, PropType } from 'vue';

  export default defineComponent({
    name: 'LazyImage',
    props: {
      src: {
        type: String,
        required: true,
      },
      alt: {
        type: String,
        default: '',
      },
      placeholder: {
        type: String,
        default: '',
      },
      threshold: {
        type: Number,
        default: 0.1,
      },
      rootMargin: {
        type: String,
        default: '50px',
      },
      imageClass: {
        type: [String, Object, Array] as PropType<string | Record<string, boolean> | string[]>,
        default: '',
      },
      placeholderClass: {
        type: [String, Object, Array] as PropType<string | Record<string, boolean> | string[]>,
        default: '',
      },
    },
    emits: ['load', 'error'],
    setup(props, { emit }) {
      const containerRef = ref<HTMLElement | null>(null);
      const isVisible = ref(false);
      let observer: IntersectionObserver | null = null;

      onMounted(() => {
        if ('IntersectionObserver' in window) {
          observer = new IntersectionObserver(
            (entries) => {
              entries.forEach((entry) => {
                if (entry.isIntersecting) {
                  isVisible.value = true;
                  observer?.disconnect();
                }
              });
            },
            {
              threshold: props.threshold,
              rootMargin: props.rootMargin,
            }
          );

          if (containerRef.value) {
            observer.observe(containerRef.value);
          }
        } else {
          isVisible.value = true;
        }
      });

      onUnmounted(() => {
        observer?.disconnect();
      });

      function handleLoad(e: Event) {
        emit('load', e);
      }

      function handleError(e: Event) {
        emit('error', e);
      }

      return {
        containerRef,
        isVisible,
        handleLoad,
        handleError,
      };
    },
  });
</script>

<style lang="less" scoped>
  .lazy-image {
    display: inline-block;
    overflow: hidden;

    img {
      display: block;
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: opacity 0.3s ease;
    }
  }

  .lazy-image-placeholder {
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #f5f5f5;
    min-height: 100px;

    .default-placeholder {
      width: 100%;
      height: 100%;
      min-height: 100px;
      background: linear-gradient(90deg, #f0f0f0 25%, #e6e6e6 50%, #f0f0f0 75%);
      background-size: 200% 100%;
      animation: loading 1.5s infinite;
    }
  }

  @keyframes loading {
    0% {
      background-position: 200% 0;
    }
    100% {
      background-position: -200% 0;
    }
  }
</style>
