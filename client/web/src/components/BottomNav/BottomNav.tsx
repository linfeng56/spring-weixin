<template>
  <div class="bottom-nav">
    <div
      v-for="item in items"
      :key="item.key"
      class="bottom-nav-item"
      :class="{ active: activeKey === item.key }"
      @click="handleClick(item)"
    >
      <div class="bottom-nav-icon">
        <slot name="icon" :item="item">
          <span class="default-icon">{{ item.icon }}</span>
        </slot>
      </div>
      <div class="bottom-nav-label">{{ item.label }}</div>
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent, PropType } from 'vue';

  export interface NavItem {
    key: string;
    label: string;
    icon?: string;
    path?: string;
  }

  export default defineComponent({
    name: 'BottomNav',
    props: {
      items: {
        type: Array as PropType<NavItem[]>,
        required: true,
      },
      activeKey: {
        type: String,
        default: '',
      },
    },
    emits: ['change'],
    setup(props, { emit }) {
      function handleClick(item: NavItem) {
        emit('change', item.key);
      }

      return {
        handleClick,
      };
    },
  });
</script>

<style lang="less" scoped>
  .bottom-nav {
    display: none;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    height: 50px;
    background: #fff;
    box-shadow: 0 -1px 4px rgba(0, 0, 0, 0.1);
    z-index: 1000;

    @media (max-width: 768px) {
      display: flex;
    }

    &-item {
      flex: 1;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      transition: background-color 0.2s;

      &:active {
        background-color: #f5f5f5;
      }

      &.active {
        color: #1890ff;

        .bottom-nav-icon {
          color: #1890ff;
        }

        .bottom-nav-label {
          color: #1890ff;
        }
      }
    }

    &-icon {
      font-size: 20px;
      color: #666;

      .default-icon {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 24px;
        height: 24px;
        font-size: 16px;
      }
    }

    &-label {
      font-size: 12px;
      color: #666;
      margin-top: 2px;
    }
  }
</style>
