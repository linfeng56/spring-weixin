//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    // TODO:目前每次都会请求登录
if(this.globalData.login.isLogin==false){
  console.info("请求登录");
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        console.info("wx.login");
        console.info(res);
        if(res.code){
          wx.request({
            url: 'http://localhost:8080/api/mini/login/wx-login',
            data:{
              code:res.code
            },
            success: wxInfo => {
              console.info(wxInfo);
              if(wxInfo.data){
                if ("success"==wxInfo.data.status
                && "true"==wxInfo.data.login){
                  this.globalData.login.isLogin=true;
                  this.globalData.login.loginKey=wxInfo.data.loginKey;
                }else{
                  console.info("登录失败");
                }
              }
            }
          })
        }else{
          console.info('登录失败'+res.errMsg);
        }
      }
    })
  }else{
    console.info("已经登录");
  }
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框

          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo
              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }else{
          console.info(res);
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    login:{
      isLogin:false,
      loginKey:""
    }
  }
})