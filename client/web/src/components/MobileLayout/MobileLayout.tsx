<template>
  <div class="mobile-layout">
    <div v-if="header" class="mobile-layout-header">
      <slot name="header">
        <div class="default-header">
          <span class="header-title">{{ title }}</span>
        </div>
      </slot>
    </div>
    <div class="mobile-layout-content" :class="{ 'with-footer': footer }">
      <PullToRefresh v-if="refreshable" :on-refresh="handleRefresh">
        <slot></slot>
      </PullToRefresh>
      <slot v-else></slot>
    </div>
    <div v-if="footer" class="mobile-layout-footer">
      <slot name="footer">
        <BottomNav :items="defaultNavItems" :active-key="activeNav" @change="handleNavChange" />
      </slot>
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent, PropType } from 'vue';
  import PullToRefresh from '/@/components/PullToRefresh';
  import BottomNav, { NavItem } from '/@/components/BottomNav';

  export default defineComponent({
    name: 'MobileLayout',
    components: {
      PullToRefresh,
      BottomNav,
    },
    props: {
      title: {
        type: String,
        default: '',
      },
      header: {
        type: Boolean,
        default: true,
      },
      footer: {
        type: Boolean,
        default: true,
      },
      refreshable: {
        type: Boolean,
        default: false,
      },
      navItems: {
        type: Array as PropType<NavItem[]>,
        default: () => [],
      },
      activeNav: {
        type: String,
        default: '',
      },
    },
    emits: ['refresh', 'nav-change'],
    setup(props, { emit }) {
      const defaultNavItems: NavItem[] = [
        { key: 'home', label: '首页', icon: '🏠', path: '/' },
        { key: 'plans', label: '计划', icon: '📋', path: '/plan/week' },
        { key: 'profile', label: '我的', icon: '👤', path: '/profile' },
      ];

      const finalNavItems = props.navItems.length ? props.navItems : defaultNavItems;

      async function handleRefresh() {
        emit('refresh');
      }

      function handleNavChange(key: string) {
        emit('nav-change', key);
      }

      return {
        defaultNavItems: finalNavItems,
        handleRefresh,
        handleNavChange,
      };
    },
  });
</script>

<style lang="less" scoped>
  .mobile-layout {
    display: flex;
    flex-direction: column;
    height: 100vh;
    background: #f5f5f5;

    &-header {
      flex-shrink: 0;
      background: #fff;
      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
      z-index: 100;

      .default-header {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 44px;
        padding: 0 16px;

        .header-title {
          font-size: 16px;
          font-weight: 500;
          color: #333;
        }
      }
    }

    &-content {
      flex: 1;
      overflow: hidden;

      &.with-footer {
        padding-bottom: 50px;
      }
    }

    &-footer {
      flex-shrink: 0;
      z-index: 100;
    }
  }
</style>
