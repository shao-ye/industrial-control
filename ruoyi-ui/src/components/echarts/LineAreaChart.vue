<template>
  <div ref="LineAreaChart" :style="{ width, height }" />
</template>

<script>
export default {
  name: 'LineAreaChart',
  props: {
    data: {
      type: Array,
      default: () => [
        {
          name: '邮件营销',
          data: [120, 132, 101, 134, 90, 230, 210]
        },
        {
          name: '联盟广告',
          data: [220, 182, 191, 234, 290, 330, 310]
        }
      ]
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '460px'
    },
    xName: {
      type: String,
      default: ''
    },
    xData: {
      type: Array,
      default: () => ['2020-01-01', '2020-01-02', '2020-01-03', '2020-01-04', '2020-01-05', '2020-01-06', '2020-01-07']
    },
    yName: {
      type: String,
      default: ''
    },
    tipFormat: {
      type: Function,
      default: () => []
    },
    colors: {
      type: Array,
      default: () => [
        'rgb(74, 195, 242)',
        'rgb(64, 125, 213)',
        'rgb(76, 194, 188)',
        'rgb(232, 160, 175)',
        'rgb(243, 95, 37)',
        'rgb(151, 118, 174)',
        'rgb(254, 211, 126)',
        'rgb(2, 102, 155)',
        'rgb(39, 139, 106)',
        'rgb(11, 125, 185)'
      ]
    },
    isArea: {
      type: Boolean,
      default: true
    },
    isRainfall: {
      type: Boolean,
      default: false
    },
    isInside: {
      type: Boolean,
      default: false
    },
    isStep: {
      type: Boolean,
      default: false
    },
    isSmooth: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    data: function() {
      this.chart.dispose()
      this.initChart()
    },

    xData: function() {
      this.chart.dispose()
      this.initChart()
    }
  },
  mounted() {
    this.initChart()
  },
  methods: {
    initChart() {
      this.chart = this.$echarts.init(this.$refs['LineAreaChart'])

      window.addEventListener('resize', () => {
        this.chart.resize()
      })

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            },
            lineStyle: {
              color: '#F0F2F5'
            }
          }
        },
        dataZoom: this.isRainfall && this.isInside ? [
          { type: 'inside' },
          { height: '20px', bottom: '0' }
        ] : this.isRainfall && !this.isInside ? [
          { height: '20px', bottom: '0' }
        ] : [],
        legend: {
          data: this.data.map(item => item.name),
        },
        grid: {
          left: '20px',
          right: this.xName ? '120px' : '70px',
          bottom: this.isRainfall ? '7%' : '2%',
          containLabel: true
        },
        xAxis: [
          {
            name: this.xName,
            type: 'category',
            boundaryGap: false,
            axisLine: {
              lineStyle: {
                fontSize: 12,
                color: '#F0F2F5'
              }
            },
            axisLabel: {
              show: true,
              textStyle: {
                fontSize: 12,
                color: '#303133'
              }
            },
            nameTextStyle: {
              color: '#909399',
              fontSize: 12,
              padding: [25, 0, 0, 24]
            },
            data: this.xData
          }
        ],
        yAxis: [
          {
            name: this.yName,
            type: 'value',
            axisLine: {
              lineStyle: {
                fontSize: 12,
                color: '#F0F2F5'
              }
            },
            axisLabel: {
              show: true,
              textStyle: {
                fontSize: 12,
                color: '#303133'
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#F0F2F5'
              }
            },
            nameTextStyle: {
              color: '#909399',
              fontSize: 12,
              padding: [0, 25, 15, 0]
            }
          }
        ],
        series: this.data.map((item, index) => {
          item['type'] = 'line'
          item['stack'] = this.data[0].name
          item['itemStyle'] = {
            normal: {
              color: this.rgbToRgba(this.colors[index], 0.8)
            }
          }
          if (this.isStep) {
            item['step'] = 'middle'
          }
          if (this.isSmooth) {
            item['smooth'] = true
          }
          if (this.isArea) {
            item['areaStyle'] = {
              normal: {
                color: new this.$echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: this.rgbToRgba(this.colors[index], 0.25) },
                  { offset: 0.8, color: this.rgbToRgba(this.colors[index], 0.1) },
                  { offset: 1, color: this.rgbToRgba(this.colors[index], 0) }
                ])
              }
            }
          }

          return item
        })
      }, true)
    },

    rgbToRgba(rgb, opacity) {
      return `rgba(${rgb.split('(')[1].split(')')[0]}, ${opacity})`
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
