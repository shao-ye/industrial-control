<template>
  <div ref="BarSimpleChart" :style="{ width, height }" />
</template>

<script>
export default {
  name: 'BarSimpleChart',
  props: {
    data: {
      type: Array,
      default: () => [
        { names: '闪烁个数', value: [100, 200, 300, 500, 5000, 3000, 2345, 3456, 4234, 243] },
        { names: '效应个数', value: [1000, 1000, 1000, 2000, 2345, 4000, 2000, 3456, 1234, 2403] }
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
    barWidth: {
      type: Number,
      default: 0
    },
    xName: {
      type: String,
      default: ''
    },
    crosswise: {
      type: Boolean,
      default: false
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
      default: () => ['#83bff6', '#00B6BC']
    },
    shadowColor: {
      type: String,
      default: 'rgba(15, 151, 222, 0.1)'
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
    }
  },
  mounted() {
    this.initChart()
  },
  methods: {
    initChart() {
      this.chart = this.$echarts.init(this.$refs['BarSimpleChart'])
      window.addEventListener('resize', () => {
        this.chart.resize()
      })
      this.chart.setOption({
        tooltip: {
          axisPointer: {
            type: 'shadow',
            shadowStyle: {
              color: 'rgba(78, 199, 213, 0.15)'
            }
          },
          formatter: this.tipFormat
        },
        calculable: true,
        grid: {
          top: '12%',
          left: '30',
          right: '90px',
          containLabel: true
        },
        xAxis: [
          {
            type: this.crosswise ? 'category' : 'value',
            name: this.xName,
            data: this.crosswise ? this.groupData : '',
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
              width: 40,
              padding: [0, 0, -60, -50]
            }
          }
        ],
        yAxis: [
          {
            type: this.crosswise ? 'value' : 'category',
            name: this.yName,
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
              fontSize: 12
            },
            data: this.crosswise ? '' : this.groupData
          }
        ],
        series: this.data.map((item, index) => {
          item['type'] = 'bar'
          item['barWidth'] = this.barWidth
          item['name'] = item.names
          item['itemStyle'] = { color: this.areaColor[index] }
          item['data'] = item.value
          item['label'] = { show: true, position: 'inside' }
          return item
        })
      })
      if (this.rainfall && this.crosswise) {
        this.chart.setOption({
          dataZoom: [
            {
              show: true,
              start: 40,
              end: 60
            },
            {
              type: 'inside',
              start: 94,
              end: 100
            },
            {
              show: false,
              yAxisIndex: 0,
              filterMode: 'empty',
              width: 30,
              height: '80%',
              showDataShadow: false,
              right: 'calc(100% - 45px)'
            }
          ]
        })
      }
      if (this.rainfall && !this.crosswise) {
        this.chart.setOption({
          dataZoom: [
            {
              show: true,
              yAxisIndex: 0,
              filterMode: 'empty',
              width: 30,
              height: '67%',
              showDataShadow: false,
              right: 15
            }
          ]
        })
      }
      if (this.data.length > 1) {
        this.chart.setOption({
          legend: {
            data: this.data.map(item => {
              return item.names
            }),
            orient: 'vertical',
            x:'right',
            y:'center'
          }
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
