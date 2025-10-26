<template>
  <PageWrapper title="通知设置">
    <Row :gutter="16">
      <Col :span="12">
        <Card title="通知渠道设置">
          <Form :label-col="{ span: 6 }">
            <FormItem label="邮箱">
              <Input v-model:value="userSettings.email" placeholder="请输入邮箱地址" />
            </FormItem>
            <FormItem label="钉钉Webhook">
              <Input v-model:value="userSettings.dingtalkWebhook" placeholder="请输入钉钉机器人Webhook地址" />
            </FormItem>
            <FormItem label="邮件提醒">
              <Switch v-model:checked="emailEnabled" />
            </FormItem>
            <FormItem label="钉钉提醒">
              <Switch v-model:checked="dingtalkEnabled" />
            </FormItem>
            <FormItem>
              <Button type="primary" @click="saveUserSettings">保存设置</Button>
            </FormItem>
          </Form>
        </Card>
      </Col>
      <Col :span="12">
        <Card title="提醒规则说明">
          <Descriptions :column="1" bordered>
            <DescriptionsItem label="截止前1天">
              计划到期前1天发送提醒（每天9:00执行）
            </DescriptionsItem>
            <DescriptionsItem label="截止当天">
              计划到期当天发送提醒（每天9:00执行）
            </DescriptionsItem>
            <DescriptionsItem label="提醒频率">
              每个计划每个提醒类型仅发送一次
            </DescriptionsItem>
          </Descriptions>
        </Card>
      </Col>
    </Row>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { ref, onMounted, computed } from 'vue';
  import { Row, Col, Card, Form, FormItem, Input, Switch, Button, Descriptions, DescriptionsItem, message } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { getUserNotificationSettings, saveUserNotificationSettings, UserNotificationSettings } from '/@/api/notification';

  const userSettings = ref<UserNotificationSettings>({
    email: '',
    dingtalkWebhook: '',
    enableEmail: 1,
    enableDingtalk: 1,
  });

  const emailEnabled = computed({
    get: () => userSettings.value.enableEmail === 1,
    set: (val) => { userSettings.value.enableEmail = val ? 1 : 0; },
  });

  const dingtalkEnabled = computed({
    get: () => userSettings.value.enableDingtalk === 1,
    set: (val) => { userSettings.value.enableDingtalk = val ? 1 : 0; },
  });

  onMounted(async () => {
    const data = await getUserNotificationSettings();
    userSettings.value = data;
  });

  async function saveUserSettings() {
    try {
      await saveUserNotificationSettings(userSettings.value);
      message.success('保存成功');
    } catch (e: any) {
      message.error(e.message || '保存失败');
    }
  }
</script>
