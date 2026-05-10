<template>
  <div class="template-list">
    <div class="template-header">
      <InputSearch
        v-model:value="keyword"
        placeholder="搜索模板"
        style="width: 260px"
        @search="handleSearch"
      />
      <Select
        v-model:value="category"
        placeholder="选择分类"
        style="width: 120px; margin-left: 8px"
        allowClear
        @change="handleCategoryChange"
      >
        <Select.Option v-for="cat in categories" :key="cat.code" :value="cat.code">
          {{ cat.name }}
        </Select.Option>
      </Select>
    </div>

    <Spin :spinning="loading">
      <Row :gutter="[16, 16]" class="template-grid">
        <Col
          v-for="template in templates"
          :key="template.id"
          :xs="24"
          :sm="12"
          :md="8"
          :lg="6"
        >
          <Card
            class="template-card"
            hoverable
            @click="handleSelect(template)"
          >
            <template #cover>
              <div class="template-cover" :class="`category-${template.category.toLowerCase()}`">
                <Tag v-if="template.isSystem" color="blue">系统</Tag>
                <Tag v-if="template.isPublic" color="green">公开</Tag>
              </div>
            </template>
            <Card.Meta
              :title="template.name"
              :description="template.description"
            />
            <div class="template-footer">
              <span class="template-category">
                {{ getCategoryText(template.category) }}
              </span>
              <span class="template-count">
                使用 {{ template.useCount || 0 }} 次
              </span>
            </div>
            <div class="template-actions">
              <Button type="link" size="small" @click.stop="handlePreview(template)">
                预览
              </Button>
              <Button type="link" size="small" @click.stop="handleApply(template)">
                使用
              </Button>
              <Button
                v-if="!template.isSystem && template.userId === currentUserId"
                type="link"
                size="small"
                danger
                @click.stop="handleDelete(template)"
              >
                删除
              </Button>
            </div>
          </Card>
        </Col>
      </Row>

      <Empty v-if="!loading && templates.length === 0" description="暂无模板" />
    </Spin>

    <Modal
      v-model:open="previewVisible"
      :title="currentTemplate?.name"
      width="600px"
      :footer="null"
    >
      <pre class="template-content">{{ currentTemplate?.content }}</pre>
    </Modal>

    <Modal
      v-model:open="applyVisible"
      title="使用模板"
      @ok="handleApplyConfirm"
      :confirm-loading="applying"
    >
      <Form :model="applyForm" layout="vertical">
        <FormItem label="模板名称">
          <Input v-model:value="applyForm.title" placeholder="请输入计划标题" />
        </FormItem>
        <FormItem label="开始日期">
          <DatePicker v-model:value="applyForm.beginDate" style="width: 100%" />
        </FormItem>
        <FormItem label="结束日期">
          <DatePicker v-model:value="applyForm.endDate" style="width: 100%" />
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>

<script lang="ts">
  import { defineComponent, ref, onMounted } from 'vue';
  import {
    Card,
    Tag,
    Input,
    Select,
    Button,
    Row,
    Col,
    Spin,
    Empty,
    Modal,
    Form,
    DatePicker,
    message,
  } from 'ant-design-vue';
  import {
    getTemplates,
    applyTemplate,
    deleteTemplate,
    PlanTemplate,
    TemplateCategory,
    TemplateCategoryText,
  } from '/@/api/plan/template';
  import { dateUtil as dayjs } from '/@/utils/dateUtil';

  export default defineComponent({
    name: 'TemplateList',
    components: {
      Card,
      Tag,
      Input: Input.Search,
      Select,
      Button,
      Row,
      Col,
      Spin,
      Empty,
      Modal,
      Form,
      FormItem: Form.Item,
      DatePicker,
    },
    emits: ['select', 'apply'],
    setup(props, { emit }) {
      const loading = ref(false);
      const applying = ref(false);
      const templates = ref<PlanTemplate[]>([]);
      const keyword = ref('');
      const category = ref<string | undefined>(undefined);
      const previewVisible = ref(false);
      const applyVisible = ref(false);
      const currentTemplate = ref<PlanTemplate | null>(null);
      const currentUserId = ref(1);

      const applyForm = ref({
        title: '',
        beginDate: null as any,
        endDate: null as any,
      });

      const categories = [
        { code: TemplateCategory.WORK, name: TemplateCategoryText.WORK },
        { code: TemplateCategory.STUDY, name: TemplateCategoryText.STUDY },
        { code: TemplateCategory.LIFE, name: TemplateCategoryText.LIFE },
        { code: TemplateCategory.OTHER, name: TemplateCategoryText.OTHER },
      ];

      async function fetchTemplates() {
        loading.value = true;
        try {
          const params: any = {};
          if (keyword.value) {
            params.keyword = keyword.value;
          }
          if (category.value) {
            params.category = category.value;
          }
          templates.value = await getTemplates(params);
        } catch (e) {
          message.error('加载模板失败');
        } finally {
          loading.value = false;
        }
      }

      function handleSearch() {
        fetchTemplates();
      }

      function handleCategoryChange() {
        fetchTemplates();
      }

      function getCategoryText(cat: string) {
        return TemplateCategoryText[cat] || cat;
      }

      function handleSelect(template: PlanTemplate) {
        emit('select', template);
      }

      function handlePreview(template: PlanTemplate) {
        currentTemplate.value = template;
        previewVisible.value = true;
      }

      function handleApply(template: PlanTemplate) {
        currentTemplate.value = template;
        applyForm.value = {
          title: template.name,
          beginDate: dayjs(),
          endDate: dayjs().add(7, 'day'),
        };
        applyVisible.value = true;
      }

      async function handleApplyConfirm() {
        if (!currentTemplate.value?.id) return;
        
        applying.value = true;
        try {
          const result = await applyTemplate(currentTemplate.value.id, {
            variables: {
              title: applyForm.value.title,
              beginDate: applyForm.value.beginDate?.format('YYYY-MM-DD'),
              endDate: applyForm.value.endDate?.format('YYYY-MM-DD'),
            },
          });
          message.success('模板应用成功');
          applyVisible.value = false;
          emit('apply', result);
        } catch (e) {
          message.error('模板应用失败');
        } finally {
          applying.value = false;
        }
      }

      async function handleDelete(template: PlanTemplate) {
        Modal.confirm({
          title: '确认删除',
          content: `确定要删除模板"${template.name}"吗？`,
          async onOk() {
            try {
              await deleteTemplate(template.id!);
              message.success('删除成功');
              fetchTemplates();
            } catch (e) {
              message.error('删除失败');
            }
          },
        });
      }

      onMounted(() => {
        fetchTemplates();
      });

      return {
        loading,
        applying,
        templates,
        keyword,
        category,
        categories,
        previewVisible,
        applyVisible,
        currentTemplate,
        currentUserId,
        applyForm,
        handleSearch,
        handleCategoryChange,
        getCategoryText,
        handleSelect,
        handlePreview,
        handleApply,
        handleApplyConfirm,
        handleDelete,
      };
    },
  });
</script>

<style lang="less" scoped>
  .template-list {
    padding: 16px;
  }

  .template-header {
    display: flex;
    margin-bottom: 16px;
  }

  .template-grid {
    margin-top: 16px;
  }

  .template-card {
    height: 100%;
    display: flex;
    flex-direction: column;

    :deep(.ant-card-body) {
      flex: 1;
      display: flex;
      flex-direction: column;
    }

    .template-cover {
      height: 80px;
      display: flex;
      align-items: flex-start;
      justify-content: flex-end;
      padding: 8px;
      background: #f5f5f5;

      &.category-work {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      }

      &.category-study {
        background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
      }

      &.category-life {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
      }

      &.category-other {
        background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
      }
    }

    .template-footer {
      display: flex;
      justify-content: space-between;
      margin-top: 12px;
      font-size: 12px;
      color: #999;
    }

    .template-actions {
      display: flex;
      gap: 8px;
      margin-top: 8px;
      border-top: 1px solid #f0f0f0;
      padding-top: 8px;
    }
  }

  .template-content {
    white-space: pre-wrap;
    word-wrap: break-word;
    background: #f5f5f5;
    padding: 16px;
    border-radius: 4px;
    max-height: 400px;
    overflow-y: auto;
  }
</style>
