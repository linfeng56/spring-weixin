# spring-weixin
Spring implements Weixin's basic functions

## 数据表字段

### 用户信息(prefix_users)

| 列名                | 数据类型               | 是否为空 | 默认值 | 主键 | 备注                     |
| ------------------- | ---------------------- | -------- | ------ | ---- | ------------------------ |
| uid                 | int(10) unsigned       | NO       |        | PRI  | 用户编号                 |
| user_name           | varchar(50)            | NO       |        |      | 用户名                   |
| nick_name           | varchar(50)            | NO       |        |      | 昵称                     |
| pwd                 | varchar(64)            | NO       |        |      | 密码                     |
| scores              | decimal(18,2) unsigned | NO       | 0.00   |      | 积分                     |
| openid              | varchar(255)           | NO       |        |      | 微信OpenId               |
| access_token        | varchar(255)           | NO       |        |      | 微信Access Token         |
| access_token_expire | int(255) unsigned      | NO       | 0      |      | 微信Access Token过期时间 |
| refresh_token       | varchar(255)           | NO       |        |      | 微信Refresh Token        |
| wechat              | varchar(50)            | NO       |        |      | 微信号                   |
| phone               | varchar(15)            | NO       |        |      | 手机号                   |
| birthday            | int(255) unsigned      | NO       | 0      |      | 出生日期(年月日)         |
| sex                 | int(2) unsigned        | NO       | 0      |      | 性别(1男2女)             |
| stature             | int(255) unsigned      | NO       | 0      |      | 身高(CM)                 |
| weight              | int(255) unsigned      | NO       | 0      |      | 体重(KG)                 |
| domicile            | varchar(500)           | NO       |        |      | 现居                     |
| native_place        | varchar(50)            | NO       |        |      | 籍贯                     |
| marital_status      | int(2) unsigned        | NO       | 0      |      | 婚姻状况(1未2已3离4丧)   |
| education           | varchar(50)            | NO       |        |      | 学历                     |
| profession          | varchar(50)            | NO       |        |      | 职业                     |
| narrate             | varchar(1000)          | NO       |        |      | 自我简述                 |
| family              | varchar(1000)          | NO       |        |      | 家庭背景                 |
| interest            | varchar(1000)          | NO       |        |      | 兴趣爱好                 |
| want_style          | varchar(1000)          | NO       |        |      | 理想另一半               |
| why_single          | varchar(1000)          | NO       |        |      | 为何单着                 |
| yearning_life       | varchar(1000)          | NO       |        |      | 理想生活                 |



### 标签(prefix_tags)

| 列名        | 数据类型         | 是否为空 | 默认值 | 主键 | 备注     |
| ----------- | ---------------- | -------- | ------ | ---- | -------- |
| tag_id      | int(10) unsigned | NO       |        | PRI  | 标签编号 |
| tag_name    | varchar(50)      | NO       |        |      | 标签     |
| rel_num     | int(10) unsigned | NO       | 0      |      | 引用次数 |
| create_time | int(11) unsigned | NO       | 0      |      | 创建时间 |



### 用户标签(prefix_user_tag)

| 列名      | 数据类型         | 是否为空 | 默认值 | 主键 | 备注     |
| --------- | ---------------- | -------- | ------ | ---- | -------- |
| uid       | int(10) unsigned | NO       |        | PRI  | 用户编号 |
| tag_id    | int(10) unsigned | NO       |        | PRI  | 标签编号 |
| tag_name  | varchar(50)      | NO       |        |      | 标签     |
| bind_time | int(11) unsigned | NO       | 0      |      | 贴标时间 |


