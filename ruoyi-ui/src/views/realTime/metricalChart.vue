<template>
  <div class="resources-container">
    <div class="list-management-table-search">
      <div class="options-menu">
<!--        <el-radio-group v-model="displayType" @change="displayChange">-->
<!--          <el-radio :label="1">厂房</el-radio>-->
<!--          <el-radio :label="2">工区</el-radio>-->
<!--          <el-radio :label="3">单槽</el-radio>-->
<!--        </el-radio-group>-->
        <el-button
          type="primary"
          :disabled="idx === '0' ? Boolean(true) : Boolean(false)"
          size="mini"
          icon="el-icon-arrow-left"
          @click="alterArea(-1)"
        >
          前一区
        </el-button>
        <el-button
          type="primary"
          :disabled="parseInt(idx) === tabList.length - 1 ? Boolean(true) : Boolean(false)"
          size="mini"
          @click="alterArea(1)"
        >
          后一区<i class="el-icon-arrow-right el-icon--right"></i>
        </el-button>
        <el-select v-model="district" size="mini" placeholder="请选择" @change="baseInfo()">
          <el-option
            v-for="item in districtList"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
        <el-date-picker
          size="mini"
          v-model="dateArr"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="yyyy 年 MM 月 dd 日"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
          :clearable="false"
          @change="dateChange"
        ></el-date-picker>
        <el-button type="primary" size="mini" @click="baseInfo()">刷新</el-button>
        <el-button size="mini">退出</el-button>
      </div>
      <div class="options-menu">
        <el-checkbox-group v-model="checkedShow" @change="checkedChange">
          <el-checkbox v-for="trace in traceList" :label="trace.value" :key="trace.value">{{ trace.label }}</el-checkbox>
        </el-checkbox-group>
      </div>
    </div>
    <multi-graph-chart :group-data="groupData" :data="dataList" :group-height="groupHeight" :tip-format="formatter" :rainfall="true"></multi-graph-chart>
  </div>
</template>

<script>
import MultiGraphChart from '@/components/echarts/MultiGraphChart'
import { slotOffLineCheckbox, factoryAreaAll, slotOffChart } from '@/api/real'
export default {
  name: "plan-feature",
  components: {
    MultiGraphChart
  },
  data() {
    return {
      loading: false,
      tabList: [], // 车间分区
      dateArr: [], // 选择的日期
      displayType: 3, // 厂房,工区,单槽
      district: '', // 选中的工区
      districtList: [],
      checkedShow: [], // 显示选中的
      traceList: [
        {
          label: '设定电压',
          value: 'tartgetVoltage'
        },
        {
          label: '工作电压',
          value: 'workingVoltage'
        },
        {
          label: '平均电压',
          value: 'averageVoltage'
        },
        {
          label: '高频噪声',
          value: 'instHighNoiseValue'
        },
        {
          label: '低频噪声',
          value: 'instLowNoiseValue'
        },
        {
          label: '实际氟盐量',
          value: 'actualFluorideSalt'
        },
        {
          label: 'AE次数',
          value: 'dayReport'
        },
        {
          label: '出铝指示量',
          value: 'metalMass'
        },
        {
          label: '铝水高度',
          value: 'metalHeight'
        },
        {
          label: '电解质高度',
          value: 'bathHeight'
        },
        {
          label: '槽温/电解质温度',
          value: 'bathTemperature'
        },
        {
          label: '分子比',
          value: 'bathRatio'
        },
        {
          label: '铁含量',
          value: 'feContent'
        },
        {
          label: '硅含量',
          value: 'siContent'
        },
        {
          label: '质量',
          value: 'metalQuality'
        },
        {
          label: '浓度',
          value: 'al2O3Concen'
        },
        {
          label: '炉底压降',
          value: 'cvd'
        },
        {
          label: '极距',
          value: 'acd'
        },
        {
          label: '实际出铝量',
          value: 'realMetalMass'
        },
      ],
      groupData: [], // 横坐标
      dataList: [], // 纵坐标
      groupHeight: '', // 高度
      formatter: function(params) {
        let str = ''
        params.forEach(item => {
          const value = item.value ? parseFloat(item.value.toFixed(2)) : 0
          str += '<br>' + item.seriesName + '：' + value
        })
        return '<div style="font-size: 12px;margin-bottom: 7px">' + params[0].name + str + '</div>'
      }, // 基本信息 提示
      idx: '0' // 默认选中
    }
  },
  mounted() {
    this.getSlotOffLineCheckbox() // 测量数据显示界面
    this.getFactoryArea() // 获取当前用户厂区集合
    this.defaultTime() // 默认一个月
  },
  methods: {
    // 默认时间
    defaultTime() {
      let yy = new Date().getFullYear()
      let mm = new Date().getMonth() + 1
      let dd = new Date().getDate()
      const end = yy + '-' + (mm >= 10 ? mm : '0' + mm) + '-' + (dd >= 10 ? dd : '0' + dd)
      let start = new Date(parseInt(new Date().getTime() - 3600 * 1000 * 24 * 30))
      const yy1 = start.getFullYear()
      const mm1 = start.getMonth() + 1
      const dd1 = start.getDate()
      start = yy1 + '-' + (mm1 >= 10 ? mm1 : '0' + mm1) + '-' + (dd1 >= 10 ? dd1 : '0' + dd1)
      this.dateArr = [start, end]
    },
    // 点击前一区，后一区
    alterArea(e) {
      this.idx = parseInt(this.idx) + parseInt(e)
      this.districtList = this.tabList[parseInt(this.idx)].realSlotNoList
      this.district = this.tabList[parseInt(this.idx)].realSlotNoList[0]
      this.baseInfo() // 图形数据
    },
    // 测量数据显示界面
    getSlotOffLineCheckbox() {
      slotOffLineCheckbox().then(res => {
        if (res.code === 200) {
          let arr = []
          res.data.tartgetVoltage === 0 ? arr.push('tartgetVoltage') : ''  // 设定电压(0:是，1：否)
          res.data.workingVoltage === 0 ? arr.push('workingVoltage') : ''  // 工作电压(0:是，1：否)
          res.data.averageVoltage === 0 ? arr.push('averageVoltage') : ''  // 平均电压(0:是，1：否)
          res.data.instHighNoiseValue === 0 ? arr.push('instHighNoiseValue') : ''  // 高频噪声(0:是，1：否)
          res.data.instLowNoiseValue === 0 ? arr.push('instLowNoiseValue') : ''  // 低频噪声(0:是，1：否)
          res.data.actualFluorideSalt === 0 ? arr.push('actualFluorideSalt') : ''  // 实际氟盐量(0:是，1：否)
          res.data.dayReport === 0 ? arr.push('dayReport') : ''  // AE次数(0:是，1：否)
          res.data.metalMass === 0 ? arr.push('metalMass') : ''  // 出铝指示量(0:是，1：否)
          res.data.metalHeight === 0 ? arr.push('metalHeight') : ''  // 铝水平(0:是，1：否)
          res.data.bathHeight === 0 ? arr.push('bathHeight') : ''  // 电解质水平(0:是，1：否)
          res.data.bathTemperature === 0 ? arr.push('bathTemperature') : ''  // 电解温度(0:是，1：否)
          res.data.bathRatio === 0 ? arr.push('bathRatio') : ''  // 分子比(0:是，1：否)
          res.data.feContent === 0 ? arr.push('feContent') : ''  // Fe含量(0:是，1：否)
          res.data.siContent === 0 ? arr.push('siContent') : ''  // Si含量(0:是，1：否)
          res.data.metalQuality === 0 ? arr.push('metalQuality') : ''  // 质量(0:是，1：否)
          res.data.al2O3Concen === 0 ? arr.push('al2O3Concen') : ''  // Al2O3量(0:是，1：否)
          res.data.cvd === 0 ? arr.push('cvd') : ''  // 炉底压降(0:是，1：否)
          res.data.acd === 0 ? arr.push('acd') : ''  // 极距(0:是，1：否)
          res.data.realMetalMass === 0 ? arr.push('realMetalMass') : ''  // 实际出铝量(0:是，1：否)
          this.checkedShow = arr
        }
      })
    },
    // 获取当前用户厂区集合
    getFactoryArea() {
      factoryAreaAll().then(res => {
        if (res.code === 200) {
          this.tabList = res.data.map((item, index) => {
            item.value = (index + 1).toString()
            return item
          })
          this.districtList = this.tabList[0].realSlotNoList
          this.district = this.tabList[0].realSlotNoList[0]
          this.baseInfo() // 图形数据
        }
      })
    },
    // 选择日期发生变化
    dateChange() {
      this.baseInfo() // 图形数据
    },
    // 改变类型
    checkedChange() {
      this.baseInfo() // 图形数据
    },
    // 显示类型改变
    displayChange() {
      console.log('显示类型改变')
    },
    // 图形数据
    baseInfo() {
      let data = {
        realPotId: this.district, // 槽号
        startDate: this.dateArr[0], // 开始日期
        endDate: this.dateArr[1], // 结束日期
      }
      this.traceList.forEach(item => {
        this.checkedShow.includes(item.value) ? data[item.value] = 0 : data[item.value] = 1
      })
      slotOffChart(data).then(res => {
        if (res.code === 200) {
          this.groupHeight = 30 * res.data.yList.length + 60 + 'px'
          this.groupData = res.data.xList // 横坐标
          this.dataList = res.data.yList// 纵坐标
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.resources-container {
  width: 100%;
  background-color: #fff;
  padding: 0 20px;
  .list-management-table-search {
    margin: 0;
    width: 100%;
    align-items: center;
    display: flex;
    flex-wrap: wrap;
    .options-menu {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      margin: 5px 0;
      ::v-deep .el-button {
        margin-left: 10px;
      }
      ::v-deep .el-radio {
        margin-right: 10px;
      }
      ::v-deep .el-checkbox {
        margin-right: 20px;
      }
      ::v-deep .el-input--suffix {
        width: 85px;
        margin: 0 10px;
      }
    }
  }
}
</style>
