<template>
  <PageWrapper title="个人信息">
    <template #headerContent>
      <div class="flex justify-between items-center">
        <span class="text-lg">管理我的账户</span>
      </div>
    </template>

    <div class="flex flex-col lg:flex-row gap-6">
      <div class="w-full lg:w-1/3">
        <Card title="头像" class="h-full">
          <div class="flex flex-col items-center">
            <div class="avatar-container mb-4">
              <Avatar :size="120" :src="avatarUrl" @click="triggerUpload">
                <template #icon>
                  <UserOutlined v-if="!userInfo.avatar" />
                </template>
              </Avatar>
              <div class="avatar-overlay" @click="triggerUpload">
                <CameraOutlined />
              </div>
            </div>
            <input
              ref="fileInput"
              type="file"
              accept="image/jpeg,image/png,image/gif"
              class="hidden"
              @change="handleFileChange"
            />
            <p class="text-gray-500 text-sm">点击上传头像 (JPG, PNG, GIF, 最大1MB)</p>
          </div>
        </Card>
      </div>

      <div class="w-full lg:w-2/3">
        <Card title="基本信息">
          <Form
            ref="formRef"
            :model="formData"
            :label-col="{ span: 6 }"
            :wrapper-col="{ span: 14 }"
            @finish="handleSubmit"
          >
            <FormItem label="用户名" name="userName">
              <Input v-model:value="formData.userName" disabled />
            </FormItem>

            <FormItem label="昵称" name="nickName" :rules="[{ required: true, message: '请输入昵称' }]">
              <Input v-model:value="formData.nickName" placeholder="请输入昵称" />
            </FormItem>

            <FormItem label="手机号" name="phone">
              <Input v-model:value="formData.phone" placeholder="请输入手机号" />
            </FormItem>

            <FormItem label="邮箱" name="email" :rules="[{ type: 'email', message: '请输入正确的邮箱格式' }]">
              <Input v-model:value="formData.email" placeholder="请输入邮箱" />
            </FormItem>

            <FormItem label="性别" name="sex">
              <RadioGroup v-model:value="formData.sex">
                <Radio :value="1">男</Radio>
                <Radio :value="2">女</Radio>
                <Radio :value="0">未知</Radio>
              </RadioGroup>
            </FormItem>

            <FormItem label="现居" name="domicile">
              <Input v-model:value="formData.domicile" placeholder="请输入现居地址" />
            </FormItem>

            <FormItem label="籍贯" name="nativePlace">
              <Input v-model:value="formData.nativePlace" placeholder="请输入籍贯" />
            </FormItem>

            <FormItem label="学历" name="education">
              <Input v-model:value="formData.education" placeholder="请输入学历" />
            </FormItem>

            <FormItem label="职业" name="profession">
              <Input v-model:value="formData.profession" placeholder="请输入职业" />
            </FormItem>

            <FormItem :wrapper-col="{ offset: 6, span: 14 }">
              <Space>
                <Button type="primary" html-type="submit" :loading="saving">
                  保存修改
                </Button>
                <Button @click="showPasswordModal = true">
                  修改密码
                </Button>
              </Space>
            </FormItem>
          </Form>
        </Card>
      </div>
    </div>

    <Modal
      v-model:open="showPasswordModal"
      title="修改密码"
      @ok="handlePasswordChange"
      :confirm-loading="passwordLoading"
    >
      <Form ref="passwordFormRef" :model="passwordData" :label-col="{ span: 6 }">
        <FormItem label="旧密码" name="oldPassword" :rules="[{ required: true, message: '请输入旧密码' }]">
          <InputPassword v-model:value="passwordData.oldPassword" placeholder="请输入旧密码" />
        </FormItem>
        <FormItem label="新密码" name="newPassword" :rules="[{ required: true, message: '请输入新密码' }, { min: 6, message: '密码至少6位' }]">
          <InputPassword v-model:value="passwordData.newPassword" placeholder="请输入新密码" />
        </FormItem>
        <FormItem label="确认密码" name="confirmPassword" :rules="[{ required: true, message: '请确认密码' }, { validator: validateConfirmPassword }]">
          <InputPassword v-model:value="passwordData.confirmPassword" placeholder="请确认新密码" />
        </FormItem>
      </Form>
    </Modal>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { ref, computed, onMounted } from 'vue';
  import {
    Card,
    Avatar,
    Form,
    FormItem,
    Input,
    InputPassword,
    Button,
    Radio,
    RadioGroup,
    Space,
    Modal,
    message,
  } from 'ant-design-vue';
  import { UserOutlined, CameraOutlined } from '@ant-design/icons-vue';
  import { PageWrapper } from '/@/components/Page';
  import { getUserProfile, updateUserProfile, changePassword, uploadAvatar, UserInfoModel } from '/@/api/sys/user';

  const formRef = ref();
  const passwordFormRef = ref();
  const userInfo = ref<Partial<UserInfoModel>>({});
  const saving = ref(false);
  const passwordLoading = ref(false);
  const showPasswordModal = ref(false);
  const fileInput = ref<HTMLInputElement>();

  const formData = ref({
    userName: '',
    nickName: '',
    phone: '',
    email: '',
    sex: 0,
    domicile: '',
    nativePlace: '',
    education: '',
    profession: '',
  });

  const passwordData = ref({
    oldPassword: '',
    newPassword: '',
    confirmPassword: '',
  });

  const avatarUrl = computed(() => {
    const avatar = userInfo.value.avatar;
    if (avatar) {
      return avatar.startsWith('http') ? avatar : avatar;
    }
    return undefined;
  });

  onMounted(async () => {
    await loadUserInfo();
  });

  async function loadUserInfo() {
    try {
      const data = await getUserProfile();
      userInfo.value = data;
      formData.value = {
        userName: data.userName || '',
        nickName: data.nickName || '',
        phone: data.phone || '',
        email: data.email || '',
        sex: data.sex || 0,
        domicile: data.domicile || '',
        nativePlace: data.nativePlace || '',
        education: data.education || '',
        profession: data.profession || '',
      };
    } catch (e) {
      message.error('加载用户信息失败');
    }
  }

  async function handleSubmit() {
    try {
      saving.value = true;
      await updateUserProfile(formData.value);
      message.success('保存成功');
      await loadUserInfo();
    } catch (e: any) {
      message.error(e.message || '保存失败');
    } finally {
      saving.value = false;
    }
  }

  function triggerUpload() {
    fileInput.value?.click();
  }

  async function handleFileChange(e: Event) {
    const target = e.target as HTMLInputElement;
    const file = target.files?.[0];
    if (!file) return;

    if (file.size > 1024 * 1024) {
      message.error('文件大小不能超过1MB');
      return;
    }

    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
    if (!allowedTypes.includes(file.type)) {
      message.error('只能上传 JPG、PNG、GIF 格式');
      return;
    }

    try {
      const result = await uploadAvatar(file);
      userInfo.value.avatar = result.url;
      await updateUserProfile({ avatar: result.url });
      message.success('头像上传成功');
    } catch (e: any) {
      message.error(e.message || '上传失败');
    }

    target.value = '';
  }

  function validateConfirmPassword() {
    return new Promise((resolve) => {
      if (passwordData.value.newPassword !== passwordData.value.confirmPassword) {
        resolve('两次密码输入不一致');
      } else {
        resolve('');
      }
    });
  }

  async function handlePasswordChange() {
    try {
      await passwordFormRef.value?.validate();
      passwordLoading.value = true;
      await changePassword(passwordData.value.oldPassword, passwordData.value.newPassword);
      message.success('密码修改成功，请重新登录');
      showPasswordModal.value = false;
      passwordData.value = { oldPassword: '', newPassword: '', confirmPassword: '' };
    } catch (e: any) {
      if (e.errorFields) return;
      message.error(e.message || '修改密码失败');
    } finally {
      passwordLoading.value = false;
    }
  }
</script>

<style scoped>
  .avatar-container {
    position: relative;
    cursor: pointer;
  }

  .avatar-overlay {
    position: absolute;
    top: 0;
    left: 0;
    width: 120px;
    height: 120px;
    border-radius: 50%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-size: 24px;
    opacity: 0;
    transition: opacity 0.3s;
  }

  .avatar-container:hover .avatar-overlay {
    opacity: 1;
  }

  .hidden {
    display: none;
  }
</style>
