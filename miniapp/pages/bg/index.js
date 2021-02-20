// pages/bg/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  bgTextStyle:function(){
    console.log('dark')
    wx.setBackgroundTextStyle({
      textStyle: 'dark' // 下拉背景字体、loading 图的样式为dark
    })
  },
  bgColor:function(){
    console.log('#ffff00')
    wx.setBackgroundColor({
      backgroundColor: '#ffff00', // 窗口的背景色为白色
    })
    
    wx.setBackgroundColor({
      backgroundColorTop: '#ffff00', // 顶部窗口的背景色为白色
      backgroundColorBottom: '#ffff00', // 底部窗口的背景色为白色
    })
  }
})