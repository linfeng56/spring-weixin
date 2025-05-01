<template>
  <PageWrapper title="计划日历">
    <div class="calendar-container">
      <div class="calendar-header">
        <Button @click="prevMonth">&lt;</Button>
        <span class="calendar-title">{{ currentYear }}年{{ currentMonth + 1 }}月</span>
        <Button @click="nextMonth">&gt;</Button>
        <Button @click="goToToday" class="ml-4">今天</Button>
      </div>
      <div class="calendar-grid">
        <div class="calendar-weekday" v-for="day in weekdays" :key="day">{{ day }}</div>
        <div
          v-for="(day, index) in calendarDays"
          :key="index"
          class="calendar-day"
          :class="{ 'other-month': day.otherMonth, 'today': day.isToday, 'has-event': day.events.length > 0 }"
        >
          <div class="day-number">{{ day.date }}</div>
          <div class="day-events">
            <div
              v-for="event in day.events"
              :key="event.id"
              class="event-item"
              :style="{ backgroundColor: event.color }"
              @click="handleEventClick(event)"
            >
              {{ event.title }}
            </div>
          </div>
        </div>
      </div>
    </div>

    <Modal v-model:open="detailVisible" :title="selectedEvent?.title" width="600px">
      <Descriptions :column="1" bordered v-if="selectedEvent">
        <DescriptionsItem label="计划ID">{{ selectedEvent.id }}</DescriptionsItem>
        <DescriptionsItem label="标题">{{ selectedEvent.title }}</DescriptionsItem>
        <DescriptionsItem label="状态">{{ getStatusText(selectedEvent.status) }}</DescriptionsItem>
        <DescriptionsItem label="开始日期">{{ formatDate(selectedEvent.start) }}</DescriptionsItem>
        <DescriptionsItem label="结束日期">{{ formatDate(selectedEvent.end) }}</DescriptionsItem>
      </Descriptions>
    </Modal>
  </PageWrapper>
</template>

<script lang="ts" setup>
  import { ref, computed, onMounted } from 'vue';
  import { Button, Modal, Descriptions, DescriptionsItem } from 'ant-design-vue';
  import { PageWrapper } from '/@/components/Page';
  import { defHttp } from '/@/utils/http/axios';

  interface CalendarEvent {
    id: number;
    title: string;
    start: number;
    end: number;
    status: number;
    color: string;
  }

  const weekdays = ['日', '一', '二', '三', '四', '五', '六'];
  const currentYear = ref(new Date().getFullYear());
  const currentMonth = ref(new Date().getMonth());
  const events = ref<CalendarEvent[]>([]);
  const detailVisible = ref(false);
  const selectedEvent = ref<CalendarEvent | null>(null);

  const calendarDays = computed(() => {
    const year = currentYear.value;
    const month = currentMonth.value;
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const startDay = firstDay.getDay();
    const daysInMonth = lastDay.getDate();

    const days = [];
    const today = new Date();

    for (let i = 0; i < startDay; i++) {
      const date = new Date(year, month, -startDay + i + 1);
      days.push({
        date: date.getDate(),
        otherMonth: true,
        isToday: false,
        events: getEventsForDate(date.getTime()),
      });
    }

    for (let i = 1; i <= daysInMonth; i++) {
      const date = new Date(year, month, i);
      const dateTime = date.getTime();
      days.push({
        date: i,
        otherMonth: false,
        isToday: date.getFullYear() === today.getFullYear() && 
                 date.getMonth() === today.getMonth() && 
                 date.getDate() === today.getDate(),
        events: getEventsForDate(dateTime),
      });
    }

    const remaining = 42 - days.length;
    for (let i = 1; i <= remaining; i++) {
      const date = new Date(year, month + 1, i);
      days.push({
        date: i,
        otherMonth: true,
        isToday: false,
        events: getEventsForDate(date.getTime()),
      });
    }

    return days;
  });

  function getEventsForDate(date: number): CalendarEvent[] {
    const dayStart = new Date(date).setHours(0, 0, 0, 0);
    const dayEnd = new Date(date).setHours(23, 59, 59, 999);
    return events.value.filter(e => e.start <= dayEnd && e.end >= dayStart);
  }

  onMounted(async () => {
    await loadEvents();
  });

  async function loadEvents() {
    const data = await defHttp.get<CalendarEvent[]>({ url: '/api/calendar/events' });
    events.value = data;
  }

  function prevMonth() {
    if (currentMonth.value === 0) {
      currentMonth.value = 11;
      currentYear.value--;
    } else {
      currentMonth.value--;
    }
  }

  function nextMonth() {
    if (currentMonth.value === 11) {
      currentMonth.value = 0;
      currentYear.value++;
    } else {
      currentMonth.value++;
    }
  }

  function goToToday() {
    const today = new Date();
    currentYear.value = today.getFullYear();
    currentMonth.value = today.getMonth();
  }

  function handleEventClick(event: CalendarEvent) {
    selectedEvent.value = event;
    detailVisible.value = true;
  }

  function formatDate(timestamp: number) {
    const date = new Date(timestamp);
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
  }

  function getStatusText(status: number) {
    const texts = ['', '进行中', '已完成', '已归档'];
    return texts[status] || '待提交';
  }
</script>

<style scoped>
.calendar-container {
  background: #fff;
  padding: 16px;
  border-radius: 8px;
}

.calendar-header {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
}

.calendar-title {
  margin: 0 16px;
  font-size: 18px;
  font-weight: bold;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 1px;
  background: #f0f0f0;
  border: 1px solid #f0f0f0;
}

.calendar-weekday {
  background: #fafafa;
  padding: 8px;
  text-align: center;
  font-weight: bold;
}

.calendar-day {
  background: #fff;
  min-height: 100px;
  padding: 4px;
}

.calendar-day.other-month {
  background: #fafafa;
  color: #999;
}

.calendar-day.today {
  background: #e6f7ff;
}

.day-number {
  font-weight: bold;
  margin-bottom: 4px;
}

.day-events {
  font-size: 12px;
}

.event-item {
  color: #fff;
  padding: 2px 4px;
  border-radius: 2px;
  margin-bottom: 2px;
  cursor: pointer;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.event-item:hover {
  opacity: 0.8;
}

.ml-4 {
  margin-left: 16px;
}
</style>
