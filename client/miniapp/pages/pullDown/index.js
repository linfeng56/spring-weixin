// pages/pullDown/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[{
      color:"#00CED1",
      text:"内容一"
    },{
      color:"#4169E1",
      text:"内容二"
    },{
      color:"#CD5C5C",
      text:"内容三"
    },{
      color:"#D2691E",
      text:"内容四"
    },{
      color:"#CD2626",
      text:"内容五"
    },{
      color:"#8B3626",
      text:"内容六"
    },]
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
  startPull: function(){
    // 在开发者工具里没有效果,真机有效果
    wx.startPullDownRefresh({
      success: (res) => {console.log(res)},
    })
  },
  stopPull: function(){
    wx.stopPullDownRefresh({
      success: (res) => {console.log(res)},
    })
  },
  testScrollTo01:function(){
    wx.pageScrollTo({
      scrollTop:68,
      duration: 300,
    })
  },
  testScrollTo02:function(){
    wx.pageScrollTo({
      duration: 300,
      selector: ".p_1"
    })
  },
  testTopBar:function(){
    // 只有当前小程序被置顶时能生效，如果当前小程序没有被置顶，也能调用成功，但是不会立即生效
    wx.setTopBarText({
      text: '小程序测试示例!'
    })
  }
})