<template>
  <PageWrapper title="周计划管理">
    <template #headerContent>
      <div class="flex justify-between items-center">
        <span class="text-lg">我的周计划</span>
        <Button type="primary" @click="handleAdd">新增计划</Button>
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
    message,
  } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import {
    getWeekPlans,
    createWeekPlan,
    updateWeekPlan,
    deleteWeekPlan,
    PlanWeek,
    PlanStatusText,
  } from '/@/api/plan/weekPlan';
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

  function getStatusColor(status: number) {
    const colors = ['', 'blue', 'green', 'default'];
    return colors[status] || '';
  }
</script>
