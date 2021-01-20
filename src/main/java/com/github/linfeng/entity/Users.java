package com.github.linfeng.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户信息.
 *
 * @author 黄麟峰
 */
@TableName("spwx_users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 积分
     */
    private BigDecimal scores;

    /**
     * 微信OpenId
     */
    private String openid;

    /**
     * 微信Access Token
     */
    private String accessToken;

    /**
     * 微信Access Token过期时间
     */
    private Integer accessTokenExpire;

    /**
     * 微信Refresh Token
     */
    private String refreshToken;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 出生日期(年月日)
     */
    private Integer birthday;

    /**
     * 性别(1男2女)
     */
    private Integer sex;

    /**
     * 身高(CM)
     */
    private Integer stature;

    /**
     * 体重(KG)
     */
    private Integer weight;

    /**
     * 现居
     */
    private String domicile;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 婚姻状况(1未2已3离4丧)
     */
    private Integer maritalStatus;

    /**
     * 学历
     */
    private String education;

    /**
     * 职业
     */
    private String profession;

    /**
     * 自我简述
     */
    private String narrate;

    /**
     * 家庭背景
     */
    private String family;

    /**
     * 兴趣爱好
     */
    private String interest;

    /**
     * 理想另一半
     */
    private String wantStyle;

    /**
     * 为何单着
     */
    private String whySingle;

    /**
     * 理想生活
     */
    private String yearningLife;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public BigDecimal getScores() {
        return scores;
    }

    public void setScores(BigDecimal scores) {
        this.scores = scores;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getAccessTokenExpire() {
        return accessTokenExpire;
    }

    public void setAccessTokenExpire(Integer accessTokenExpire) {
        this.accessTokenExpire = accessTokenExpire;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStature() {
        return stature;
    }

    public void setStature(Integer stature) {
        this.stature = stature;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNarrate() {
        return narrate;
    }

    public void setNarrate(String narrate) {
        this.narrate = narrate;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getWantStyle() {
        return wantStyle;
    }

    public void setWantStyle(String wantStyle) {
        this.wantStyle = wantStyle;
    }

    public String getWhySingle() {
        return whySingle;
    }

    public void setWhySingle(String whySingle) {
        this.whySingle = whySingle;
    }

    public String getYearningLife() {
        return yearningLife;
    }

    public void setYearningLife(String yearningLife) {
        this.yearningLife = yearningLife;
    }

    @Override
    public String toString() {
        return "Users{" +
            "uid=" + uid +
            ", userName=" + userName +
            ", nickName=" + nickName +
            ", pwd=" + pwd +
            ", scores=" + scores +
            ", openid=" + openid +
            ", accessToken=" + accessToken +
            ", accessTokenExpire=" + accessTokenExpire +
            ", refreshToken=" + refreshToken +
            ", wechat=" + wechat +
            ", phone=" + phone +
            ", birthday=" + birthday +
            ", sex=" + sex +
            ", stature=" + stature +
            ", weight=" + weight +
            ", domicile=" + domicile +
            ", nativePlace=" + nativePlace +
            ", maritalStatus=" + maritalStatus +
            ", education=" + education +
            ", profession=" + profession +
            ", narrate=" + narrate +
            ", family=" + family +
            ", interest=" + interest +
            ", wantStyle=" + wantStyle +
            ", whySingle=" + whySingle +
            ", yearningLife=" + yearningLife +
            "}";
    }
}
