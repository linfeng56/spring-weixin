<template>
  <PageWrapper title="关联总结">
    <Card title="选择周计划">
      <Table
        :columns="columns"
        :data-source="plans"
        :row-selection="{ selectedRowKeys, onChange: onSelectChange }"
        :pagination="false"
        :loading="loading"
      >
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <Tag :color="getStatusColor(record.status)">
              {{ getStatusText(record.status) }}
            </Tag>
          </template>
        </template>
      </Table>
      <div class="mt-4">
        <Button type="primary" :disabled="selectedRowKeys.length === 0" @click="generateSummary">
          生成关联总结
        </Button>
      </div>
    </Card>

    <Card title="关联总结报告" class="mt-4" v-if="summaryResult">
      <pre class="summary-content">{{ summaryResult }}</pre>
    </Card>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { ref, onMounted } from 'vue';
  import { Card, Table, Tag, Button } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { getWeekPlans, PlanWeek } from '/@/api/plan/weekPlan';
  import { defHttp } from '/@/utils/http/axios';

  const columns = [
    { title: 'ID', dataIndex: 'weekId', key: 'weekId' },
    { title: '标题', dataIndex: 'title', key: 'title' },
    { title: '开始日期', dataIndex: 'beginDate', key: 'beginDate' },
    { title: '结束日期', dataIndex: 'endDate', key: 'endDate' },
    { title: '状态', dataIndex: 'status', key: 'status' },
  ];

  const loading = ref(false);
  const plans = ref<PlanWeek[]>([]);
  const selectedRowKeys = ref<number[]>([]);
  const summaryResult = ref('');

  onMounted(async () => {
    await loadPlans();
  });

  async function loadPlans() {
    loading.value = true;
    try {
      plans.value = await getWeekPlans();
    } finally {
      loading.value = false;
    }
  }

  function onSelectChange(keys: number[]) {
    selectedRowKeys.value = keys;
  }

  async function generateSummary() {
    try {
      const result = await defHttp.post<{ summary: string }>({
        url: '/api/week-plans/linked-summary',
        data: { weekIds: selectedRowKeys.value },
      });
      summaryResult.value = result.summary;
    } catch (e) {
      console.error(e);
    }
  }

  function getStatusColor(status: number) {
    const colors = ['', 'blue', 'green', 'default'];
    return colors[status] || '';
  }

  function getStatusText(status: number) {
    const texts = ['待提交', '进行中', '已完成', '已归档'];
    return texts[status] || '未知';
  }
</script>

<style scoped>
.mt-4 {
  margin-top: 16px;
}
.summary-content {
  white-space: pre-wrap;
  font-family: monospace;
  background: #f5f5f5;
  padding: 16px;
  border-radius: 4px;
}
</style>
