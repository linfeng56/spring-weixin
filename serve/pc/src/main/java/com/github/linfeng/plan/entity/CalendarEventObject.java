package com.github.linfeng.plan.entity;

import java.io.Serializable;

public class CalendarEventObject implements Serializable {

    public CalendarEventObject() {
    }

    public CalendarEventObject(String title, Long start) {
        this(title, start, null);
    }

    public CalendarEventObject(String title, Long start, Long end) {
        this(title, start, end, false);
    }

    public CalendarEventObject(String title, Long start, Long end, Boolean allDay) {
        this(title, start, end, null, null, false);
    }

    public CalendarEventObject(String title, Long start, String backgroundColor, String borderColor, Boolean allDay) {
        this(title, start, null, backgroundColor, borderColor, allDay);
    }

    public CalendarEventObject(String title, Long start, Long end, String backgroundColor, String borderColor,
        Boolean allDay) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.allDay = allDay;
    }


    /**
     * String/Integer. Optional Uniquely identifies the given event. Different instances of repeating events should all have the same `id`.
     */
    private Integer id;
    /**
     * String. *Required*. The text on an event's element
     */
    private String title;
    /**
     * `true` or `false`. Optional. Whether an event occurs at a specific time-of-day. This property affects whether an event's time is shown. Also, in the agenda views, determines if it is displayed in the "all-day" section. If this value is not explicitly specified, [allDayDefault](https://fullcalendar.io/docs/v3/allDayDefault) will be used if it is defined. If all else fails, FullCalendar will try to guess. If either the `start` or `end` value has a `"T"` as part of the ISO8601 date string, `allDay` will become `false`. Otherwise, it will be `true`. **Don't include quotes** around your `true`/`false`. This value is a boolean, not a string!
     */
    private Boolean allDay;
    /**
     * The date/time an event begins. *Required*. A [Moment](https://fullcalendar.io/docs/v3/moment)-ish input, like an [ISO8601 string](http://en.wikipedia.org/wiki/ISO_8601). Throughout the API this will become a real Moment object.
     */
    private Long start;
    /**
     * The **exclusive** date/time an event ends. Optional. A [Moment](https://fullcalendar.io/docs/v3/moment)-ish input, like an [ISO8601 string](http://en.wikipedia.org/wiki/ISO_8601). Throughout the API this will become a real Moment object. It is the moment immediately *after* the event has ended. For example, if the last full day of an event is **Thursday**, the exclusive end of the event will be 00:00:00 on **Friday**!
     */
    private Long end;
    /**
     * String. Optional. A URL that will be visited when this event is clicked by the user. For more information on controlling this behavior, see the [eventClick](https://fullcalendar.io/docs/v3/eventClick) callback.
     */
    private String url;
    /**
     * String/Array. Optional. A CSS class (or array of classes) that will be attached to this event's element.
     */
    private String[] className;
    /**
     * `true` or `false`. Optional. Overrides the master [editable](https://fullcalendar.io/docs/v3/editable) option for this single event.
     */
    private Boolean editable;
    /**
     * `true` or `false`. Optional. Overrides the master [eventStartEditable](https://fullcalendar.io/docs/v3/eventStartEditable) option for this single event.
     */
    private Boolean startEditable;
    /**
     * `true` or `false`. Optional. Overrides the master [eventDurationEditable](https://fullcalendar.io/docs/v3/eventDurationEditable) option for this single event.
     */
    private Boolean durationEditable;
    /**
     * `true` or `false`. Optional. Overrides the master [eventResourceEditable](https://fullcalendar.io/docs/v3/eventResourceEditable) option for this single event.
     */
    private Boolean resourceEditable;
    /**
     * Allows alternate rendering of the event, like [background events](https://fullcalendar.io/docs/v3/background-events).
     * Can be empty, `"background"`, or `"inverse-background"`
     */
    private String rendering;
    /**
     * `true` or `false`. Optional. Overrides the master [eventOverlap](https://fullcalendar.io/docs/v3/eventOverlap) option for this single event. If `false`, prevents this event from being dragged/resized over other events. Also prevents other events from being dragged/resized over this event.
     */
    private Boolean overlap;
    /**
     * an event ID, `"businessHours"`, object. Optional. Overrides the master [eventConstraint](https://fullcalendar.io/docs/v3/eventConstraint) option for this single event.
     */
    private String constraint;
    /**
     * [Event Source Object](https://fullcalendar.io/docs/v3/event-source-object). Automatically populated. A reference to the event source that this event came from.
     */
    private String source;
    /**
     * Sets an event's background *and* border color just like the calendar-wide [eventColor](https://fullcalendar.io/docs/v3/eventColor) option.
     */
    private String color;
    /**
     * Sets an event's background color just like the calendar-wide [eventBackgroundColor](https://fullcalendar.io/docs/v3/eventBackgroundColor) option.
     */
    private String backgroundColor;
    /**
     * Sets an event's border color just like the the calendar-wide [eventBorderColor](https://fullcalendar.io/docs/v3/eventBorderColor) option.
     */
    private String borderColor;
    /**
     * Sets an event's text color just like the calendar-wide [eventTextColor](https://fullcalendar.io/docs/v3/eventTextColor) option.
     */
    private String textColor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(Boolean allDay) {
        this.allDay = allDay;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getClassName() {
        return className;
    }

    public void setClassName(String[] className) {
        this.className = className;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getStartEditable() {
        return startEditable;
    }

    public void setStartEditable(Boolean startEditable) {
        this.startEditable = startEditable;
    }

    public Boolean getDurationEditable() {
        return durationEditable;
    }

    public void setDurationEditable(Boolean durationEditable) {
        this.durationEditable = durationEditable;
    }

    public Boolean getResourceEditable() {
        return resourceEditable;
    }

    public void setResourceEditable(Boolean resourceEditable) {
        this.resourceEditable = resourceEditable;
    }

    public String getRendering() {
        return rendering;
    }

    public void setRendering(String rendering) {
        this.rendering = rendering;
    }

    public Boolean getOverlap() {
        return overlap;
    }

    public void setOverlap(Boolean overlap) {
        this.overlap = overlap;
    }

    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
