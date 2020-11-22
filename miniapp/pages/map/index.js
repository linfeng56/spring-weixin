// pages/map/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    latitude:22.833885560686763,
    longitude:108.31012974775308,
    markers:[{
      id:1,
      latitude:22.833885560686763,
      longitude:108.31012974775308,
      name:'十八中'
    }],
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
    // 创建地图的上下文 MapContext对象
    this.mapCtx  = wx.createMapContext('testMap')
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
  getCenterLocation:function(){
    this.mapCtx.getCenterLocation({
      success:function(res){
        console.log(res.latitude)
        console.log(res.longitude)
      }
    })
  },
  moveToLocation:function(){
    this.mapCtx.moveToLocation()
  },
  translateMarker:function(){
    this.mapCtx.translateMarker({
      markerId:1,
      autoRotate:true,
      duration:1000,
      destination:{
        latitude:22.83393,
        longitude:108.31342999999993,
      },
      animationEnd(){
        console.log('动画完成')
      }
    })
  },
  includePoints:function(){
    this.mapCtx.includePoints({
      padding:[10],
      points:[{
        latitude:22.8344830212654,
        longitude:108.30764384323788,
      },{
        latitude:22.83393,
        longitude:108.31342999999993,
      },]
    })
  },
  scaleClick:function(){
    this.mapCtx.getScale({
      success:function(res){
        console.log(res.scale)
      }
    })
  },
  getRegionClick:function(){
    this.mapCtx.getRegion({
      success:function(res){
        console.log(res.southwest)
        console.log(res.northeast)
      }
    })
  }
})