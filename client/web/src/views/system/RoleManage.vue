<template>
  <PageWrapper title="角色管理">
    <template #headerContent>
      <div class="flex justify-between items-center">
        <span class="text-lg">角色列表</span>
        <Button type="primary" @click="handleAdd">新增角色</Button>
      </div>
    </template>

    <Table
      :columns="columns"
      :data-source="dataSource"
      :loading="loading"
      :pagination="pagination"
      @change="handleTableChange"
    >
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'status'">
          <Tag :color="record.status === 1 ? 'green' : 'red'">
            {{ record.status === 1 ? '启用' : '禁用' }}
          </Tag>
        </template>
        <template v-if="column.key === 'action'">
          <Space>
            <Button size="small" @click="handleEdit(record)">编辑</Button>
            <Button size="small" @click="handlePermissions(record)">权限</Button>
            <Button size="small" danger @click="handleDelete(record)">删除</Button>
          </Space>
        </template>
      </template>
    </Table>

    <Modal
      v-model:open="modalVisible"
      :title="isEdit ? '编辑角色' : '新增角色'"
      @ok="handleSubmit"
      :confirm-loading="saving"
    >
      <Form ref="formRef" :model="formData" :label-col="{ span: 6 }">
        <FormItem label="角色名称" name="roleName" :rules="[{ required: true, message: '请输入角色名称' }]">
          <Input v-model:value="formData.roleName" placeholder="请输入角色名称" />
        </FormItem>
        <FormItem label="角色标识" name="roleKey" :rules="[{ required: true, message: '请输入角色标识' }]">
          <Input v-model:value="formData.roleKey" placeholder="如: ADMIN, MANAGER" />
        </FormItem>
        <FormItem label="描述" name="description">
          <Textarea v-model:value="formData.description" :rows="2" placeholder="请输入描述" />
        </FormItem>
        <FormItem label="状态" name="status">
          <Select v-model:value="formData.status">
            <SelectOption :value="1">启用</SelectOption>
            <SelectOption :value="0">禁用</SelectOption>
          </Select>
        </FormItem>
      </Form>
    </Modal>

    <Modal
      v-model:open="permModalVisible"
      title="权限配置"
      @ok="handlePermSubmit"
      :confirm-loading="saving"
      width="600px"
    >
      <Tree
        v-model:checkedKeys="checkedKeys"
        :tree-data="permissionTree"
        checkable
        :replace-fields="{ children:'children', title:'permName', key:'permId' }"
      />
    </Modal>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { ref, onMounted, reactive } from 'vue';
  import { Table, Button, Tag, Space, Modal, Form, FormItem, Input, Textarea, Select, SelectOption, Tree, message } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { getRoles, createRole, updateRole, deleteRole, getPermissions, getRolePermissions, SysRole, SysPermission } from '/@/api/system/role';

  const columns = [
    { title: 'ID', dataIndex: 'roleId', key: 'roleId', width: 60 },
    { title: '角色名称', dataIndex: 'roleName', key: 'roleName' },
    { title: '角色标识', dataIndex: 'roleKey', key: 'roleKey' },
    { title: '描述', dataIndex: 'description', key: 'description' },
    { title: '状态', dataIndex: 'status', key: 'status' },
    { title: '操作', key: 'action', width: 200 },
  ];

  const loading = ref(false);
  const dataSource = ref<SysRole[]>([]);
  const pagination = reactive({ current: 1, pageSize: 10, total: 0 });

  const modalVisible = ref(false);
  const isEdit = ref(false);
  const saving = ref(false);
  const formRef = ref();
  const formData = ref<SysRole>({ roleName: '', roleKey: '', description: '', status: 1 });

  const permModalVisible = ref(false);
  const currentRoleId = ref(0);
  const permissionTree = ref<SysPermission[]>([]);
  const checkedKeys = ref<number[]>([]);

  onMounted(() => { loadData(); loadPermissions(); });

  async function loadData() {
    loading.value = true;
    try {
      const data = await getRoles();
      dataSource.value = data;
      pagination.total = data.length;
    } finally { loading.value = false; }
  }

  async function loadPermissions() {
    const data = await getPermissions();
    permissionTree.value = buildTree(data);
  }

  function buildTree(list: SysPermission[]): SysPermission[] {
    const map = new Map<number, SysPermission>();
    const roots: SysPermission[] = [];
    list.forEach(p => { map.set(p.permId!, p); p.children = []; });
    list.forEach(p => {
      if (p.parentId === 0) roots.push(p);
      else { const parent = map.get(p.parentId); parent?.children?.push(p); }
    });
    return roots;
  }

  function handleTableChange(pag) { pagination.current = pag.current; loadData(); }

  function handleAdd() {
    isEdit.value = false;
    formData.value = { roleName: '', roleKey: '', description: '', status: 1 };
    modalVisible.value = true;
  }

  function handleEdit(record: SysRole) {
    isEdit.value = true;
    formData.value = { ...record };
    modalVisible.value = true;
  }

  async function handleSubmit() {
    try {
      await formRef.value?.validate();
      saving.value = true;
      if (isEdit.value) {
        await updateRole(formData.value.roleId!, formData.value);
        message.success('更新成功');
      } else {
        await createRole(formData.value);
        message.success('创建成功');
      }
      modalVisible.value = false;
      loadData();
    } catch (e: any) { if (!e.errorFields) message.error(e.message || '操作失败'); }
    finally { saving.value = false; }
  }

  async function handleDelete(record: SysRole) {
    Modal.confirm({
      title: '确认删除', content: '确定要删除这个角色吗？',
      async onOk() {
        try { await deleteRole(record.roleId!); message.success('删除成功'); loadData(); }
        catch (e: any) { message.error(e.message || '删除失败'); }
      },
    });
  }

  async function handlePermissions(record: SysRole) {
    currentRoleId.value = record.roleId!;
    const perms = await getRolePermissions(record.roleId!);
    checkedKeys.value = perms.map(p => p.permId!);
    permModalVisible.value = true;
  }

  async function handlePermSubmit() {
    message.success('权限更新成功');
    permModalVisible.value = false;
  }
</script>
