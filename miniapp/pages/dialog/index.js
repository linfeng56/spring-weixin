// pages/dialog/index.js
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
    wx.showNavigationBarLoading()
    wx.setNavigationBarTitle({
      title: '弹窗测试'
    })
    wx.setNavigationBarColor({
      frontColor: '#ffffff',
      backgroundColor: '#0000ff',
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
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
  showMsg01:function(){
    wx.showToast({
      title: '成功',
      icon: 'success',
      duration: 2000,
      mask:true
    })
  },
  showMsg02:function(){
    wx.showModal({
      title: '提示',
      content: '这是一个模态弹窗',
      success (res) {
        if (res.confirm) {
          console.log('用户点击确定')
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  showMsg03:function(){
    wx.showLoading({
      title: '加载中...',
    })
    setTimeout(function () {
      wx.hideLoading()
    }, 2000)
  },
  showMsg04:function(){
    wx.showActionSheet({
      alertText:"选择城市",
      itemList: ['北京', '上海', '深圳'],
      success (res) {
        console.log(res.tapIndex)
      },
      fail (res) {
        console.log(res.errMsg)
      }
    })
  },
  hideLoading:function(){
    wx.hideNavigationBarLoading()
  }
})