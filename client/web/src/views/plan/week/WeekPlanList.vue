<template>
  <PageWrapper title="周计划管理">
    <template #headerContent>
      <div class="flex justify-between items-center">
        <span class="text-lg">我的周计划</span>
        <Space>
          <Button type="primary" @click="handleAdd">新增计划</Button>
          <Button @click="handleExport">导出</Button>
        </Space>
      </div>
    </template>

    <div class="mb-4">
      <RadioGroup v-model:value="statusFilter" button-style="solid" @change="loadData">
        <RadioButton :value="undefined">全部</RadioButton>
        <RadioButton :value="0">待提交</RadioButton>
        <RadioButton :value="1">进行中</RadioButton>
        <RadioButton :value="2">已完成</RadioButton>
        <RadioButton :value="3">已归档</RadioButton>
      </RadioGroup>
    </div>

    <Table
      :columns="columns"
      :data-source="dataSource"
      :loading="loading"
      :pagination="pagination"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          <Tag :color="getStatusColor(record.status)">
            {{ PlanStatusText[record.status] }}
          </Tag>
        </template>
        <template v-if="column.key === 'beginDate'">
          {{ formatDate(record.beginDate) }}
        </template>
        <template v-if="column.key === 'endDate'">
          {{ formatDate(record.endDate) }}
        </template>
        <template v-if="column.key === 'action'">
          <Space>
            <Button size="small" @click="handleEdit(record)">编辑</Button>
            <Button size="small" @click="handleSummary(record)">总结</Button>
            <Button size="small" @click="handleSubtasks(record)">子任务</Button>
            <Button size="small" v-if="record.status !== 3" @click="handleArchive(record)">归档</Button>
            <Button size="small" @click="handleHistory(record)">历史</Button>
            <Button size="small" danger @click="handleDelete(record)">删除</Button>
          </Space>
        </template>
      </template>
    </Table>

    <Modal
      v-model:open="modalVisible"
      :title="isEdit ? '编辑周计划' : '新增周计划'"
      @ok="handleSubmit"
      :confirm-loading="saving"
    >
      <Form ref="formRef" :model="formData" :label-col="{ span: 6 }">
        <FormItem label="标题" name="title" :rules="[{ required: true, message: '请输入标题' }]">
          <Input v-model:value="formData.title" placeholder="请输入计划标题" />
        </FormItem>
        <FormItem label="开始日期" name="beginDate" :rules="[{ required: true, message: '请选择开始日期' }]">
          <DatePicker v-model:value="formData.beginDate" style="width: 100%" />
        </FormItem>
        <FormItem label="结束日期" name="endDate" :rules="[{ required: true, message: '请选择结束日期' }]">
          <DatePicker v-model:value="formData.endDate" style="width: 100%" />
        </FormItem>
        <FormItem label="备注" name="remarks">
          <Textarea v-model:value="formData.remarks" :rows="3" placeholder="请输入备注" />
        </FormItem>
        <FormItem label="状态" name="status" v-if="isEdit">
          <Select v-model:value="formData.status">
            <SelectOption :value="0">待提交</SelectOption>
            <SelectOption :value="1">进行中</SelectOption>
            <SelectOption :value="2">已完成</SelectOption>
            <SelectOption :value="3">已归档</SelectOption>
          </Select>
        </FormItem>
      </Form>
    </Modal>

    <Modal
      v-model:open="summaryModalVisible"
      title="周计划总结"
      @ok="handleSummarySubmit"
      :confirm-loading="saving"
    >
      <Form ref="summaryFormRef" :model="summaryData">
        <FormItem label="总结内容" name="summary">
          <Textarea v-model:value="summaryData.summary" :rows="6" placeholder="请输入周计划总结" />
        </FormItem>
      </Form>
    </Modal>

    <Modal
      v-model:open="subtaskModalVisible"
      title="子任务管理"
      width="800px"
      :footer="null"
    >
      <div class="mb-4">
        <Button type="primary" size="small" @click="handleAddSubtask">新增子任务</Button>
      </div>
      <Table :columns="subtaskColumns" :data-source="subtasks" :pagination="false" size="small">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'status'">
            <Select
              size="small"
              :value="record.status"
              @change="(val) => handleSubtaskStatusChange(record, val)"
            >
              <SelectOption :value="0">待完成</SelectOption>
              <SelectOption :value="1">进行中</SelectOption>
              <SelectOption :value="2">已完成</SelectOption>
            </Select>
          </template>
          <template v-if="column.key === 'progress'">
            <Progress :percent="record.progress" size="small" />
          </template>
          <template v-if="column.key === 'action'">
            <Button size="small" danger @click="handleDeleteSubtask(record)">删除</Button>
          </template>
        </template>
      </Table>
    </Modal>

    <Modal
      v-model:open="historyModalVisible"
      title="变更历史"
      width="700px"
      :footer="null"
    >
      <Table :columns="historyColumns" :data-source="changeHistory" :pagination="false" size="small">
        <template #bodyCell="{ column, record }">
          <template v-if="column.key === 'createDate'">
            {{ formatDate(record.createDate) }}
          </template>
        </template>
      </Table>
    </Modal>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { ref, onMounted, reactive } from 'vue';
  import {
    Table,
    Button,
    Radio,
    RadioGroup,
    RadioButton,
    Tag,
    Space,
    Modal,
    Form,
    FormItem,
    Input,
    Textarea,
    Select,
    SelectOption,
    DatePicker,
    Progress,
    message,
  } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import {
    getWeekPlans,
    createWeekPlan,
    updateWeekPlan,
    deleteWeekPlan,
    updateWeekPlanSummary,
    exportWeekPlans,
    getChangeHistory,
    PlanWeek,
    PlanStatusText,
    PlanChangeHistory,
  } from '/@/api/plan/weekPlan';
  import { getSubtasks, createSubtask, updateSubtask, deleteSubtask, PlanSubtask } from '/@/api/plan/subtask';
  import { dayjs } from '/@/utils/dateUtil';

  const columns = [
    { title: '标题', dataIndex: 'title', key: 'title' },
    { title: '开始日期', dataIndex: 'beginDate', key: 'beginDate' },
    { title: '结束日期', dataIndex: 'endDate', key: 'endDate' },
    { title: '状态', dataIndex: 'status', key: 'status' },
    { title: '操作', key: 'action', width: 150 },
  ];

  const loading = ref(false);
  const dataSource = ref<PlanWeek[]>([]);
  const statusFilter = ref<number | undefined>(undefined);
  const pagination = reactive({
    current: 1,
    pageSize: 10,
    total: 0,
  });

  const modalVisible = ref(false);
  const isEdit = ref(false);
  const saving = ref(false);
  const formRef = ref();
  const summaryModalVisible = ref(false);
  const summaryFormRef = ref();
  const summaryData = ref({
    weekId: 0,
    summary: '',
  });

  const subtaskModalVisible = ref(false);
  const currentWeekId = ref(0);
  const subtasks = ref<PlanSubtask[]>([]);
  const subtaskColumns = [
    { title: '标题', dataIndex: 'title', key: 'title' },
    { title: '描述', dataIndex: 'description', key: 'description' },
    { title: '状态', dataIndex: 'status', key: 'status' },
    { title: '进度', dataIndex: 'progress', key: 'progress' },
    { title: '操作', key: 'action', width: 80 },
  ];

  const historyModalVisible = ref(false);
  const changeHistory = ref<PlanChangeHistory[]>([]);
  const historyColumns = [
    { title: '字段', dataIndex: 'fieldName', key: 'fieldName' },
    { title: '旧值', dataIndex: 'oldValue', key: 'oldValue' },
    { title: '新值', dataIndex: 'newValue', key: 'newValue' },
    { title: '变更原因', dataIndex: 'changeReason', key: 'changeReason' },
    { title: '时间', dataIndex: 'createDate', key: 'createDate' },
  ];

  const formData = ref({
    title: '',
    beginDate: null,
    endDate: null,
    remarks: '',
    status: 0,
  });

  onMounted(() => {
    loadData();
  });

  async function loadData() {
    loading.value = true;
    try {
      const data = await getWeekPlans(statusFilter.value);
      dataSource.value = data;
      pagination.total = data.length;
    } catch (e) {
      message.error('加载失败');
    } finally {
      loading.value = false;
    }
  }

  function handleTableChange(pag) {
    pagination.current = pag.current;
    loadData();
  }

  function handleAdd() {
    isEdit.value = false;
    formData.value = {
      title: '',
      beginDate: null,
      endDate: null,
      remarks: '',
      status: 0,
    };
    modalVisible.value = true;
  }

  function handleEdit(record: PlanWeek) {
    isEdit.value = true;
    formData.value = {
      ...record,
      beginDate: record.beginDate ? dayjs(record.beginDate) : null,
      endDate: record.endDate ? dayjs(record.endDate) : null,
    };
    modalVisible.value = true;
  }

  function handleSummary(record: PlanWeek) {
    summaryData.value = {
      weekId: record.weekId!,
      summary: record.summary || '',
    };
    summaryModalVisible.value = true;
  }

  async function handleSummarySubmit() {
    try {
      saving.value = true;
      await updateWeekPlanSummary(summaryData.value.weekId, summaryData.value.summary);
      message.success('总结更新成功');
      summaryModalVisible.value = false;
      loadData();
    } catch (e: any) {
      message.error(e.message || '更新失败');
    } finally {
      saving.value = false;
    }
  }

  async function handleSubmit() {
    try {
      await formRef.value?.validate();
      saving.value = true;

      const data = {
        ...formData.value,
        beginDate: formData.value.beginDate?.valueOf(),
        endDate: formData.value.endDate?.valueOf(),
      };

      if (isEdit.value) {
        await updateWeekPlan(formData.value.weekId!, data);
        message.success('更新成功');
      } else {
        await createWeekPlan(data);
        message.success('创建成功');
      }
      modalVisible.value = false;
      loadData();
    } catch (e: any) {
      if (e.errorFields) return;
      message.error(e.message || '操作失败');
    } finally {
      saving.value = false;
    }
  }

  async function handleArchive(record: PlanWeek) {
    Modal.confirm({
      title: '确认归档',
      content: '确定要将此计划归档吗？归档后将不可编辑。',
      async onOk() {
        try {
          await updateWeekPlan(record.weekId!, { ...record, status: 3 });
          message.success('归档成功');
          loadData();
        } catch (e: any) {
          message.error(e.message || '归档失败');
        }
      },
    });
  }

  async function handleHistory(record: PlanWeek) {
    historyModalVisible.value = true;
    const data = await getChangeHistory(record.weekId!);
    changeHistory.value = data;
  }

  async function handleDelete(record: PlanWeek) {
    Modal.confirm({
      title: '确认删除',
      content: '确定要删除这个周计划吗？',
      async onOk() {
        try {
          await deleteWeekPlan(record.weekId!);
          message.success('删除成功');
          loadData();
        } catch (e: any) {
          message.error(e.message || '删除失败');
        }
      },
    });
  }

  function formatDate(timestamp: number) {
    return timestamp ? dayjs(timestamp).format('YYYY-MM-DD') : '-';
  }

  async function handleSubtasks(record: PlanWeek) {
    currentWeekId.value = record.weekId!;
    subtaskModalVisible.value = true;
    await loadSubtasks();
  }

  async function loadSubtasks() {
    const data = await getSubtasks(currentWeekId.value);
    subtasks.value = data;
  }

  async function handleAddSubtask() {
    const title = prompt('请输入子任务标题');
    if (!title) return;
    await createSubtask(currentWeekId.value, { title, status: 0, progress: 0 });
    message.success('子任务添加成功');
    await loadSubtasks();
  }

  async function handleSubtaskStatusChange(record: PlanSubtask, status: number) {
    const progress = status === 2 ? 100 : (status === 1 ? 50 : 0);
    await updateSubtask(currentWeekId.value, record.subtaskId!, { status, progress });
    message.success('状态更新成功');
    await loadSubtasks();
  }

  async function handleDeleteSubtask(record: PlanSubtask) {
    await deleteSubtask(currentWeekId.value, record.subtaskId!);
    message.success('子任务删除成功');
    await loadSubtasks();
  }

  function getStatusColor(status: number) {
    const colors = ['', 'blue', 'green', 'default'];
    return colors[status] || '';
  }

  async function handleExport() {
    try {
      const data = await exportWeekPlans({});
      const blob = new Blob([data as any], { type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `week-plans-${dayjs().format('YYYYMMDD')}.docx`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
      message.success('导出成功');
    } catch (e: any) {
      message.error(e.message || '导出失败');
    }
  }
</script>
