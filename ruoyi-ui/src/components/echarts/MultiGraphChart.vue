<template>
  <div ref="MultiGraphChart" :style="styles" />
</template>

<script>
export default {
  name: "MultiGraphChart",
  props: {
    data: {
      type: Array,
      default: () => [
        { name: '一工区', data: [0.97,0.96,1.17,1.2,1.23,1.26,1.29,1.33,1.36,1.4] },
        { name: '二工区', data: [0.97,0.96,1.17,1.2,1.23,1.26,1.29,1.33,1.36,1.4] },
        { name: '三工区', data: [0.97,0.96,1.17,1.2,1.23,1.26,1.29,1.33,1.36,1.4] },
      ]
    },
    width: {
      type: String,
      default: '100%'
    },
    groupHeight: {
      type: String,
      default: '600px'
    },
    barWidth: {
      type: Number,
      default: 0
    },
    xName: {
      type: String,
      default: ''
    },
    rainfall: {
      type: Boolean,
      default: false
    },
    groupData: {
      type: Array,
      default: () => ['2020-01', '2020-02', '2020-03', '2020-04', '2020-05', '2020-06', '2020-07', '2020-08', '2020-09', '2020-10']
    },
    yName: {
      type: String,
      default: ''
    },
    tipFormat: {
      type: Function,
      default: () => []
    },
    areaColor: {
      type: Array,
      default: () => ['#83bff6']
    },
    shadowColor: {
      type: String,
      default: 'rgba(15, 151, 222, 0.1)'
    }
  },
  data() {
    return {
      chart: null,
      height: ''
    }
  },
  watch: {
    data: function() {
      this.chart.dispose()
      this.initChart()
    },
  },
  created() {
    this.height= this.groupHeight
  },
  mounted() {
    this.initChart()
  },
  computed: {
    styles() {
      let style = {}
      style.width = this.width
      style.height = this.groupHeight
      setTimeout(() => {
        this.$echarts.init(this.$refs['MultiGraphChart']).resize()
      }, 100)
      return style
    },
  },
  methods: {
    initChart() {
      this.chart = this.$echarts.init(this.$refs['MultiGraphChart'])
      window.addEventListener('resize', () => {
        this.chart.resize()
      })
      var img = new Image()
      img.src = 'https://img2.baidu.com/it/u=1943148896,3671649787&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800'
      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            animation: false
          },
          formatter: this.tipFormat
        },
        axisPointer: {
          link: {xAxisIndex: 'all'}
        },
        grid: this.data.map((item, index) => {
          const cItem = {}
          cItem['left'] = 20
          cItem['right'] = 50
          cItem['height'] = 25
          cItem['top'] = 30 * index + 10
          return cItem
        }),
        xAxis: this.data.map((item, index) => {
          const cItem = {}
          cItem['gridIndex'] = index
          cItem['type'] = 'category'
          cItem['data'] = this.groupData
          cItem['show'] = (index !== this.data.length - 1) ? Boolean(false) : Boolean(true)
          cItem['splitLine'] = { show: false }
          return cItem
        }),
        yAxis: this.data.map((item, index) => {
          const cItem = {}
          cItem['gridIndex'] = index
          cItem['name'] = ''
          cItem['type'] = 'value'
          cItem['splitLine'] = { show: false }
          cItem['scale'] = false
          cItem['axisLine'] = { show: false }
          cItem['axisTick'] = { show: false }
          cItem['minorTick'] = { show: false }
          cItem['axisLabel'] = {
            formatter: function () {
              return '';
            }
          }
          return cItem
        }),
        graphic: [
          {
            type: 'image', // 图形元素类型
            id: 'logo', // 更新或删除图形元素时指定更新哪个图形元素，如果不需要用可以忽略。
            left: '0', // 根据父元素进行定位 （居中）
            top: '0%', // 根据父元素进行定位   （0%）, 如果bottom的值是 0，也可以删除该bottom属性值。
            z: 0,  // 层叠
            bounding: 'all', // 决定此图形元素在定位时，对自身的包围盒计算方式
            style: {
              image: 'https://www.boxuegu.com/assets/user/background1.png', // 这里一定要注意、注意，必须是https开头的图片路径地址
              type: "pattern",
              repeat: "repeat",
              // width: 200,
              // height: 100
            }
          }
        ],
        series: this.data.map((item, index) => {
          const cItem = {}
          const data = item.data.map(item => {
            if (item) {
              return parseFloat(item.toFixed(2))
            }
            return item
          })
          cItem['name'] = item.name
          cItem['type'] = 'line'
          cItem['xAxisIndex'] = index
          cItem['yAxisIndex'] = index
          cItem['symbolSize'] = 8
          cItem['data'] = data
          cItem['label'] = { normal: { show: true, position: 'top' }}
          cItem['itemStyle'] = { normal: {
            color: this.areaColor[index],
            lineStyle: {
              color: this.areaColor[index],
              width: 2
            }
          }}
          // cItem['backgroundColor'] = {
          //   type: "pattern",
          //   repeat: "repeat",
          //   image: img // 赋值
          // }
          return cItem
        })
      }, true)
      if (this.rainfall) {
        const dataList = this.data
        this.chart.setOption({
          dataZoom: dataList.map((item, index) => {
            item['show'] = (index === 0 ? Boolean(true) :　Boolean(false))
            item['realtime'] = true
            item['start'] = 30
            item['end'] = 70
            item['type'] = index === 0 ? '' : 'inside'
            item['xAxisIndex'] = dataList.map((item, index) => { return index })
            return item
          })
        })
      }
    }
  }
}
</script>

<style scoped lang="scss">

</style>
