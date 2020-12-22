# spring-weixin
Spring implements Weixin's basic functions

## 数据表字段

### 个人信息

| 字段              | 类型          | 备类型 | 名称                     | 说明         |
| ----------------- | ------------- | ------ | ------------------------ | ------------ |
| uid               | int           |        | 用户编号                 |              |
| userName          | varchar(50)   |        | 用户名                   |              |
| nickName          | varchar(50)   |        | 昵称                     |              |
| pwd               | varchar(50)   |        | 密码                     |              |
| openId            | varchar(255)  |        | 微信Open Id              |              |
| accessToken       | varchar(255)  |        | 微信Access Token         |              |
| refreshToken      | varchar(255)  |        | 微信Refresh Token        |              |
| accessTokenExpire | datetime      |        | 微信Access Token过期时间 |              |
| wechat            | varchar(50)   |        | 微信号                   |              |
| phone             | varchar(15)   |        | 手机号                   |              |
| birthday          | datetime      |        | 出生日期                 | 年月日       |
| sex               | int           |        | 性别                     | 1男2女       |
| stature           | int           |        | 身高                     | CM           |
| weight            | int           |        | 体重                     | KG           |
| domicile          | varchar(500)  |        | 现居                     |              |
| nativePlace       | varchar(50)   |        | 籍贯                     |              |
| maritalStatus     | int           |        | 婚姻状况                 | 1未2已3离4丧 |
| education         | varchar(50)   |        | 学历                     |              |
| profession        | varchar(50)   |        | 职业                     |              |
| narrate           | varchar(1000) |        | 自述                     |              |
| family            | varchar(1000) |        | 家庭背景                 |              |
| interest          | varchar(1000) |        | 兴趣爱好                 |              |
| wantStyle         | varchar(1000) |        | 理想另一半               |              |
| whySingle         | varchar(1000) |        | 为何单身                 |              |
| yearningLife      | varchar(1000) |        | 遇到对的人后期待的生活   |              |
| tags              | varchar(10)   |        | 标签                     |              |

