Component({
  data: {
    selected: 0,
    color: "#7A7E83",
    selectedColor: "#3cc51f",
    list: [{
      pagePath: "/pages/index/index",
      iconPath: "/image/icon_component.png",
      selectedIconPath: "/image/icon_component_HL.png",
      text: "首页"
    }, {
      pagePath: "/pages/pullDown/index",
      iconPath: "/image/icon_API.png",
      selectedIconPath: "/image/icon_API_HL.png",
      text: "下拉刷新"
    },{
      pagePath: "/pages/dialog/index",
      iconPath: "/image/icon_API.png",
      selectedIconPath: "/image/icon_API_HL.png",
      text: "弹框测试"
    }, {
      pagePath: "/pages/bg/index",
      iconPath: "/image/icon_API.png",
      selectedIconPath: "/image/icon_API_HL.png",
      text: "背景测试"
    }, {
      pagePath: "/pages/navTo/index",
      iconPath: "/image/icon_API.png",
      selectedIconPath: "/image/icon_API_HL.png",
      text: "跳转测试"
    }]
  },
  attached() {
  },
  methods: {
    switchTab(e) {
      const data = e.currentTarget.dataset
      const url = data.path
      wx.switchTab({url})
      this.setData({
        selected: data.index
      })
    }
  }
})