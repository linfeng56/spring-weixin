<template>
  <PageWrapper title="周计划汇总">
    <Row :gutter="16" class="mb-4">
      <Col :span="6">
        <Card>
          <Statistic title="总计划数" :value="summary.total" />
        </Card>
      </Col>
      <Col :span="6">
        <Card>
          <Statistic title="本周新增" :value="summary.thisWeek" />
        </Card>
      </Col>
      <Col :span="6">
        <Card>
          <Statistic title="本月新增" :value="summary.thisMonth" />
        </Card>
      </Col>
      <Col :span="6">
        <Card>
          <Statistic title="已完成" :value="summary.statusCount?.completed || 0" />
        </Card>
      </Col>
    </Row>

    <Row :gutter="16">
      <Col :span="12">
        <Card title="状态分布">
          <div ref="pieChartRef" style="height: 300px"></div>
        </Card>
      </Col>
      <Col :span="12">
        <Card title="月度趋势">
          <div ref="lineChartRef" style="height: 300px"></div>
        </Card>
      </Col>
    </Row>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { ref, onMounted } from 'vue';
  import { Row, Col, Card, Statistic } from 'ant-design-vue';
  import * as echarts from 'echarts';
  import { PageWrapper } from '/@/components/Page';
  import { getPlanSummary, PlanSummary } from '/@/api/plan/weekPlan';

  const pieChartRef = ref<HTMLElement>();
  const lineChartRef = ref<HTMLElement>();
  const summary = ref<Partial<PlanSummary>>({});

  onMounted(async () => {
    await loadSummary();
    initCharts();
  });

  async function loadSummary() {
    const data = await getPlanSummary();
    summary.value = data;
  }

  function initCharts() {
    if (pieChartRef.value) {
      const pieChart = echarts.init(pieChartRef.value);
      pieChart.setOption({
        tooltip: { trigger: 'item' },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [
              { value: summary.value.statusCount?.pending || 0, name: '待提交' },
              { value: summary.value.statusCount?.inProgress || 0, name: '进行中' },
              { value: summary.value.statusCount?.completed || 0, name: '已完成' },
            ],
          },
        ],
      });
    }

    if (lineChartRef.value) {
      const lineChart = echarts.init(lineChartRef.value);
      const months = Object.keys(summary.value.byMonth || {}).sort();
      const values = months.map((m) => summary.value.byMonth?.[m] || 0);
      lineChart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: months },
        yAxis: { type: 'value' },
        series: [
          {
            type: 'line',
            data: values,
            smooth: true,
          },
        ],
      });
    }
  }
</script>
