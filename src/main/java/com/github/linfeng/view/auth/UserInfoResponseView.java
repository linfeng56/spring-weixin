package com.github.linfeng.view.auth;

import com.github.linfeng.view.base.BaseResponseView;

/**
 * @author 黄麟峰
 * @date 2020-12-03
 */
public class UserInfoResponseView extends BaseResponseView {

    /**
     * 用户的唯一标识
     */
    private String openId = "";
    /**
     * 用户昵称
     */
    private String nickName = "";
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    private String sex = "";
    /**
     * 用户个人资料填写的省份
     */
    private String province = "";
    /**
     * 普通用户个人资料填写的城市
     */
    private String city = "";
    /**
     * 国家，如中国为CN
     */
    private String country = "";
    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     */
    private String headImgUrl = "";
    /**
     * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
     */
    private String[] privilege = null;
    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionid = "";

    public UserInfoResponseView(String json) {
        /*
         * 正确时返回的JSON数据包如下：
         *
         * {
         *   "openid":" OPENID",
         *   "nickname": NICKNAME,
         *   "sex":"1",
         *   "province":"PROVINCE",
         *   "city":"CITY",
         *   "country":"COUNTRY",
         *   "headimgurl":"https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
         *   "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
         *   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
         * }
         *
         */
        super(json);

        if ("".equals(this.getErrCode())) {
            openId = this.json.getString("openid");
            nickName = this.json.getString("nickname");
            sex = this.json.getString("sex");
            province = this.json.getString("province");
            city = this.json.getString("city");
            country = this.json.getString("country");
            headImgUrl = this.json.getString("headimgurl");
            privilege = this.json.getJSONArray("privilege").toArray(privilege);
            unionid = this.json.getString("unionid");
        }
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
