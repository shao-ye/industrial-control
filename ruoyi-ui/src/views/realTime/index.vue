<template>
  <div class="resources-container">
    <tab-list :tab-list="tabList" :default-active="defaultActive" @handleClick="handleClick"></tab-list>
    <div class="list-management-table-search">
      <el-checkbox v-model="isShift" @change="shiftChange">自动切换</el-checkbox>
      <table-toolbar :columns="tableHeadersAll" @changeColumn="changeColumn"></table-toolbar>
    </div>
    <table-real
      v-loading="loading"
      :border="false"
      :table-data="tableData"
      :table-headers="tableHeaders"
      :is-page="false"
      :is-selection="false"
      :is-stripe="false"
      :height="'760px'"
      @handleSelectionChange="handleSelectionChange"
      class="zdy_table"
      style="transform: scale(0.7); margin-left: -21.5%; width: 143%; margin-top: -118px; "
    >
      <template slot="statusName" slot-scope="scope">
        <span>{{ scope.row.connectStatus === 1 ? '无通信' : scope.row.statusName }}</span>
      </template>
      <template slot="aeCnt" slot-scope="scope">
        <span :class="scope.row.aeCnt !== 0 ? 'flashing' : ''">{{ scope.row.aeCnt }}</span>
      </template>
    </table-real>
    <div class="basic-info">
      <div class="basic-info-til">基础信息</div>
      <div class="basic-info-menu">
        <div class="each-basic">当前时间：{{ getTime }}</div>
        <div class="each-basic">平均设定电压：{{ averList.voltAll }}</div>
        <div class="each-basic">平均目标电压：{{ averList.targetVoltAll }}</div>
        <div class="each-basic">平均工作电压：{{ averList.workVoltAll }}</div>
        <div class="each-basic">AE频次：{{ averList.aeCntAll }}</div>
        <div class="each-basic">平均HF：{{ averList.highNoiseAll }}</div>
        <div class="each-basic">平均LF：{{ averList.lowNoiseAll }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import TableReal from '@/components/table/TableReal'
import TabList from '@/components/tabs/TabList'
import TableToolbar from '@/components/table/toolbar'
import { factoryArea, displayList, displayVolt } from '@/api/real'
export default {
  name: "plan-feature",
  components: {
    TableReal,
    TabList,
    TableToolbar
  },
  data() {
    return {
      loading: false,
      tableHeadersAll: [
        { prop: 'realSlotNo', label: '槽号', width: 40 },
        { prop: 'modleName', label: '手/自动', minWidth: 60 },
        { prop: 'statusName', label: '状态', minWidth: 60 },
        { prop: 'operateName', label: '动作', minWidth: 40 },
        { prop: 'volt', label: '设定电压', minWidth: 40 },
        { prop: 'targetVolt', label: '目标电压', minWidth: 40 },
        { prop: 'workVolt', label: '工作电压', minWidth: 40 },
        { prop: 'setNb', label: '设定NB', minWidth: 40 },
        { prop: 'actualNb', label: '实际NB', minWidth: 40 },
        { prop: 'feedingCycleName', label: '加料周期', minWidth: 60 },
        { prop: 'resistance', label: '实时电阻', minWidth: 60 },
        { prop: 'aeStartTime', label: '最近AE时刻', minWidth: 60 },
        { prop: 'aeAverageVoltage', label: 'AE均压', minWidth: 80 },
        { prop: 'aeLastingTime', label: 'AE持续时间', minWidth: 40 },
        { prop: 'aeType', label: 'AE类型', minWidth: 60 },
        { prop: 'aeCnt', label: '24H AE次数', minWidth: 40 },
        { prop: 'setFluoride', label: '设定氟盐量', minWidth: 40 },
        { prop: 'startAnodePosition', label: '今日阳极开始位置', minWidth: 40 },
        { prop: 'currentAnodePosition', label: '当前阳极位置', minWidth: 40 },
        { prop: 'currentFault', label: '当前故障', minWidth: 80 },
        { prop: 'highNoise', label: '高频噪声', minWidth: 40 },
        { prop: 'lowNoise', label: '低频噪声', minWidth: 40 }
      ],
      tableHeaders: [],
      tableData: [],
      tabList: [], // 车间分区
      averList: {}, // 基本信息
      getTime: '', // 当前时间
      idx: '0', // 默认选中
      defaultActive: '1', // 默认选中
      isShift: true, // 是否自动切换
      timer: null,
      timer1: null,
      timer2: null,
      timer3: null
    }
  },
  mounted() {
    this.tableHeaders = this.tableHeadersAll
    this.timer = setInterval(() => {
      this.getCurrentTime()
    }, 1000)
    this.timer1 = setInterval(() => {
      this.baseInfo() // 实时数据
    }, 10000)
    this.timer2 = setInterval(() => {
      this.getDisplayVolt() // 工作电压
    }, 3000)
    this.getFactoryArea() // 获取当前用户厂区集合
  },
  beforeDestroy() {
    clearInterval(this.timer)
    clearInterval(this.timer1)
    clearInterval(this.timer2)
    clearInterval(this.timer3)
  },
  methods: {
    // 获取当前用户厂区集合
    getFactoryArea() {
      factoryArea().then(res => {
        if (res.code === 200) {
          this.tabList = res.data.map((item, index) => {
            item.value = (index + 1).toString()
            return item
          })
          this.baseInfo()
          this.shiftChange() // 自动切换
        }
      })
    },
    // 自动切换改变
    shiftChange() {
      if(this.isShift) {
        this.timer3 = setInterval(() => {
          // this.tabList
          // idx: '0', // 默认选中
          const maxIdx = this.tabList.length - 1
          const int = parseInt(this.idx)
          let data = ''
          if (int < maxIdx) {
            this.defaultActive = (int + 2).toString()
            data = (int + 1).toString()
          } else {
            this.defaultActive = '1'
            data = '0'
          }
          this.handleClick(data)
        }, 11000)
      } else {
        clearInterval(this.timer3)
      }
    },
    // 改变表头
    changeColumn(data) {
      this.tableHeaders = data
    },
    getCurrentTime() {
      let yy = new Date().getFullYear()
      let mm = new Date().getMonth()+1
      let dd = new Date().getDate()
      let hh = new Date().getHours()
      let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes()
      let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds()
      this.getTime = yy + '-' + (mm >= 10 ? mm : '0' + mm) + '-' + (dd >= 10 ? dd : '0' + dd) + ' ' + hh + ':' + mf + ':' + ss
    },
    // 切换车间
    handleClick(data) {
      this.idx = data
      this.baseInfo()
    },
    // 实时数据
    baseInfo() {
      const int = parseInt(this.idx)
      const data = { factoryNo: this.tabList[int].factoryNo, areaNo: this.tabList[int].areaNo }
      displayList(data).then(res => {
        if (res.code === 0) {
          let voltAll = 0 // 设定电压
          let targetVoltAll = 0 // 目标电压
          let workVoltAll = 0 // 工作电压
          let aeCntAll = 0 // 24H AE次数
          let highNoiseAll = 0 // 高频噪声
          let lowNoiseAll = 0 // 低频噪声
          this.tableData = res.rows.map(item => {
            voltAll += item.volt
            targetVoltAll += item.targetVolt
            workVoltAll += item.targetVolt
            aeCntAll += item.aeCnt
            highNoiseAll += item.highNoise
            lowNoiseAll += item.lowNoise
            if (item.connectStatus === 1) {
              item.workVolt = 0 // 工作电压
              item.actualNb = 0 // 实际NB
              item.highNoise = 0 // 高频噪声
              item.lowNoise = 0 // 低频噪声
            }
            return item
          })
          this.averList = {
            voltAll: parseFloat(voltAll / res.total).toFixed(2),
            targetVoltAll: parseFloat(targetVoltAll / res.total).toFixed(2),
            workVoltAll: parseFloat(workVoltAll / res.total).toFixed(2),
            aeCntAll: parseFloat(aeCntAll / res.total).toFixed(2),
            highNoiseAll: parseFloat(highNoiseAll / res.total).toFixed(2),
            lowNoiseAll: parseFloat(lowNoiseAll / res.total).toFixed(2),
          }
          // AE总数
        }
      })
    },
    // 工作电压
    getDisplayVolt() {
      const int = parseInt(this.idx)
      const data = { factoryNo: this.tabList[int].factoryNo, areaNo: this.tabList[int].areaNo }
      displayVolt(data).then(res => {
        let ha = this.tableData
        if (res.code === 200) {
          let hb = res.data
          this.tableData = ha.map(item => {
            const queryData = hb.filter(function (_data) {
              return _data.realSlotNo === item.realSlotNo
            })
            item.workVolt = queryData[0].workVolt
            return item
          })
        }
      })
    },
    // 选择表格
    handleSelectionChange(data) {
      console.log(data)
    }
  }
}
</script>

<style scoped lang="scss">
.resources-container {
  width: 100%;
  background-color: #fff;
  padding: 0 20px;
  .operation {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    .operation-btn-delete {
      color: #FFFFFF;
      padding: 0 4px;
      cursor: pointer;
      font-size: 12px;
      background-color: rgb(255, 57, 57);
      border-radius: 4px;
    }
    .operation-btn-edit {
      color: #FFFFFF;
      padding: 0 4px;
      cursor: pointer;
      font-size: 12px;
      margin-right: 12px;
      background-color: rgb(30,157,147);
      border-radius: 4px;
    }
  }
  .zdy_table ::v-deep .el-table .cell {
    font-size: 16.5px !important;
  }
  .list-management-table-search {
    margin: 5px 0;
    width: 100%;
    justify-content: space-between;
    align-items: center;
    display: flex;
    .del-btn {
      width: 84px;
      height: 32px;
      padding: 0;
      border-radius: 4px;
      font-size: 14px;
      font-weight: 400;
    }
    .add-btn {
      width: 84px;
      height: 32px;
      padding: 0;
      background: #1E9D93;
      border-radius: 4px;
      font-size: 14px;
      font-weight: 400;
      color: #FFFFFF;
    }
    .add-btn:hover {
      background: #1E9D93;
    }
    .add-btn:active {
      border-color: #1E9D93;
    }
    .table-headers {
      width: 100%;
      .popover-menu {
        cursor: pointer;
      }
    }
  }
  ::v-deep .el-descriptions__header {
    margin-bottom: 5px;
  }
  ::v-deep .el-table .el-table__header-wrapper th, .el-table .el-table__fixed-header-wrapper th {
    height: 20px;
  }
  ::v-deep .el-descriptions--medium:not(.is-bordered) .el-descriptions-item__cell {
    padding-bottom: 5px;
  }
  .basic-info {
    width: 100%;
    margin-top: -115px;
    .basic-info-til {
      width: 100%;
      font-size: 16px;
      font-weight: bold
    }
    .basic-info-menu {
      width: 100%;
      display: flex;
      align-items: center;
      flex-wrap: wrap;
      .each-basic {
        margin-right: 25px;
        font-size: 14px;
        line-height: 20px;
        color: #606266;
      }
    }
  }
}
.delivery-checkbox-group {
  width: 100%;
  .el-checkbox {
    width: 100%;
  }
}
.search-menu {
  width: 100%;
  display: flex;
  align-items: flex-start;
  flex-wrap: wrap;
  .each-search {
    display: flex;
    align-items: center;
    margin-right: 45px;
    flex-wrap: nowrap;
    margin-bottom: 10px;
    >span {
      font-size: 14px;
      line-height: 32px;
      height: 32px;
      white-space: nowrap;
    }
    ::v-deep .el-input__inner {
      line-height: 32px;
      height: 32px;
    }
    ::v-deep .el-input__icon {
      line-height: 1;
    }
    ::v-deep .el-range-separator {
      line-height: 24px;
    }
  }
  .btn-menu {
    margin-left: 80px;
    display: flex;
    align-items: center;
    .search-btn {
      color: #FFFFFF;
      height: 32px;
      line-height: 32px;
      padding: 0 10px;
      cursor: pointer;
      font-size: 14px;
      margin-right: 12px;
      background-color: #1e9d93;
      border-radius: 4px;
      display: flex;
      justify-content: center;
      align-items: center;
      >span {
        padding-left: 5px;
      }
    }
  }
  .reset-btn {
    background: #FFFFFF;
    border: 1px solid #DFE0E3;
    font-weight: 400;
    color: #1F2329;
    height: 32px;
    line-height: 32px;
    padding: 0 10px;
    cursor: pointer;
    font-size: 14px;
    margin-right: 12px;
    border-radius: 4px;
    display: flex;
    justify-content: center;
    align-items: center;
    >span {
      padding-left: 5px;
    }
  }
}
.resources-container ::v-deep .el-table--medium .el-table__cell {
  padding: 0 !important;
}
.flashing{
  color: red;
  -webkit-animation: twinkling 1s infinite ease-in-out
}
.animated{
  -webkit-animation-duration: 1s;
  animation-duration: 1s;
  -webkit-animation-fill-mode: both;
  animation-fill-mode: both
}
@-webkit-keyframes twinkling{
  0%{
    opacity: 0.5;
  }
  100%{
    opacity: 1;
  }
}
@keyframes twinkling{
  0%{
    opacity: 0.5;
  }
  100%{
    opacity: 1;
  }
}
</style>
