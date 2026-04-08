<template>
  <div class="offline-page">
    <div class="offline-content">
      <div class="offline-icon">📡</div>
      <h2 class="offline-title">网络不可用</h2>
      <p class="offline-desc">您当前处于离线状态，部分功能可能不可用</p>
      <div v-if="cachedData.length" class="cached-data">
        <h3>已缓存的数据</h3>
        <ul>
          <li v-for="(item, index) in cachedData" :key="index">
            {{ item.title }}
          </li>
        </ul>
      </div>
      <div class="offline-actions">
        <Button type="primary" @click="handleRefresh">重新加载</Button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent, ref, onMounted } from 'vue';
  import { Button } from 'ant-design-vue';

  interface CachedItem {
    title: string;
    type: string;
  }

  export default defineComponent({
    name: 'OfflinePage',
    components: {
      Button,
    },
    setup() {
      const cachedData = ref<CachedItem[]>([]);

      onMounted(async () => {
        if ('caches' in window) {
          try {
            const cacheNames = await caches.keys();
            const allData: CachedItem[] = [];
            
            for (const name of cacheNames) {
              if (name.includes('api-cache')) {
                const cache = await caches.open(name);
                const requests = await cache.keys();
                
                for (const request of requests) {
                  try {
                    const response = await cache.match(request);
                    if (response) {
                      const data = await response.json();
                      if (Array.isArray(data)) {
                        allData.push(...data.map((item: any) => ({
                          title: item.title || item.name || 'Unknown',
                          type: 'plan'
                        })));
                      }
                    }
                  } catch (e) {
                    console.error('Error reading cache:', e);
                  }
                }
              }
            }
            
            cachedData.value = allData.slice(0, 10);
          } catch (e) {
            console.error('Error accessing caches:', e);
          }
        }
      });

      function handleRefresh() {
        window.location.reload();
      }

      return {
        cachedData,
        handleRefresh,
      };
    },
  });
</script>

<style lang="less" scoped>
  .offline-page {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
    padding: 24px;
    background: #f5f5f5;
  }

  .offline-content {
    text-align: center;
    max-width: 400px;
  }

  .offline-icon {
    font-size: 64px;
    margin-bottom: 16px;
  }

  .offline-title {
    font-size: 24px;
    font-weight: 500;
    color: #333;
    margin-bottom: 8px;
  }

  .offline-desc {
    font-size: 14px;
    color: #666;
    margin-bottom: 24px;
  }

  .cached-data {
    text-align: left;
    padding: 16px;
    background: #fff;
    border-radius: 8px;
    margin-bottom: 24px;

    h3 {
      font-size: 14px;
      font-weight: 500;
      color: #333;
      margin-bottom: 12px;
    }

    ul {
      list-style: none;
      padding: 0;
      margin: 0;

      li {
        padding: 8px 0;
        font-size: 14px;
        color: #666;
        border-bottom: 1px solid #f0f0f0;

        &:last-child {
          border-bottom: none;
        }
      }
    }
  }

  .offline-actions {
    .ant-btn {
      min-width: 120px;
    }
  }
</style>
