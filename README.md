# spring-weixin
Spring implements Weixin's basic functions

## 数据表字段

### 个人信息(prefix_users)

| 字段                | 类型          | 备类型 | 名称                     | 说明         |
| ------------------- | ------------- | ------ | ------------------------ | ------------ |
| uid                 | int           |        | 用户编号                 |              |
| user_name           | varchar(50)   |        | 用户名                   |              |
| nick_name           | varchar(50)   |        | 昵称                     |              |
| pwd                 | varchar(50)   |        | 密码                     |              |
| openId              | varchar(255)  |        | 微信Open Id              |              |
| access_token        | varchar(255)  |        | 微信Access Token         |              |
| refresh_token       | varchar(255)  |        | 微信Refresh Token        |              |
| access_token_expire | datetime      |        | 微信Access Token过期时间 |              |
| wechat              | varchar(50)   |        | 微信号                   |              |
| phone               | varchar(15)   |        | 手机号                   |              |
| birthday            | datetime      |        | 出生日期                 | 年月日       |
| sex                 | int           |        | 性别                     | 1男2女       |
| stature             | int           |        | 身高                     | CM           |
| weight              | int           |        | 体重                     | KG           |
| domicile            | varchar(500)  |        | 现居                     |              |
| native_place        | varchar(50)   |        | 籍贯                     |              |
| marital_status      | int           |        | 婚姻状况                 | 1未2已3离4丧 |
| education           | varchar(50)   |        | 学历                     |              |
| profession          | varchar(50)   |        | 职业                     |              |
| narrate             | varchar(1000) |        | 自述                     |              |
| family              | varchar(1000) |        | 家庭背景                 |              |
| interest            | varchar(1000) |        | 兴趣爱好                 |              |
| want_style          | varchar(1000) |        | 理想另一半               |              |
| why_single          | varchar(1000) |        | 为何单身                 |              |
| yearning_life       | varchar(1000) |        | 遇到对的人后期待的生活   |              |
|                     |               |        |                          |              |

### 标签(prefix_tags)

| 字段        | 类型        | 备类型 | 名称     | 说明 |
| ----------- | ----------- | ------ | -------- | ---- |
| tag_id      | int         |        | 标签编号 |      |
| tag_name    | varchar(50) |        | 标签     |      |
| rel_num     | int         |        | 引用次数 |      |
| create_time | int(11)     |        | 创建时间 |      |
|             |             |        |          |      |



### 用户标签

| 字段      | 类型        | 备类型 | 名称     | 说明 |
| --------- | ----------- | ------ | -------- | ---- |
| uid       | int         |        | 用户编号 |      |
| tag_id    | int         |        | 标签编号 |      |
| tag_name  | varchar(50) |        | 标签     |      |
| bind_time | int(11)     |        | 贴标时间 |      |
|           |             |        |          |      |

