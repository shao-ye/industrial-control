<template>
  <div class="resources-container">
    <div class="resources-tab">
      <tab-list :tab-list="tabList" @handleClick="handleClick"></tab-list>
    </div>
    <div class="menu-container">
      <column-table :plant-tile="plantTile" :table-data="tableData"></column-table>
      <div v-if="groupDataY.length !== 0">
        <div class="menu-title">各区效应个数图</div>
        <bar-simple-chart :crosswise="true" :tip-format="formatter" :group-data="groupData" :data="groupDataY"></bar-simple-chart>
      </div>
      <div v-if="lineData.length !== 0">
        <div class="menu-title" style="padding-bottom: 30px;">效应时间和效应功效对比图</div>
        <line-area-chart :x-data="lineGroup" :is-step="false" :is-area="false" :data="lineData"></line-area-chart>
      </div>
    </div>
    <popup title="提示" :show.sync="delshow" :content="delContent" confirm-button-text="删 除" background="#FF645F" @toTrue="toTrue"></popup>
  </div>
</template>

<script>
import BarSimpleChart from '@/components/echarts/BarSimpleChart'
import LineAreaChart from '@/components/echarts/LineAreaChart'
import ColumnTable from '@/components/table/ColumnTable'
import Popup from '@/components/popup/Popup'
import TabList from '@/components/tabs/TabList'
export default {
  name: "plan-feature",
  components: {
    LineAreaChart,
    BarSimpleChart,
    Popup,
    TabList,
    ColumnTable
  },
  data() {
    return {
      loading: false,
      tableData: [],
      tabList: [
        {
          label: '一车间',
          value: '1'
        },
        {
          label: '二车间',
          value: '2'
        },
        {
          label: '三车间',
          value: '3'
        },
        {
          label: '四车间',
          value: '4'
        },
        {
          label: '五车间',
          value: '5'
        }
      ], // 车间分区
      delshow: false, // 删除弹出框
      delContent: '请确认是否删除？',
      editData: {},
      title: '',
      plantTile: '一车间效应信息值', // 车间效应值
      addEditShow: false,
      groupData: [], // 车间效应示意图
      groupDataY: [], // 车间效应示意图数据
      lineGroup: [], // 效应时间和效应功效对比图
      lineData: [], // 效应时间和效应功效对比图数据
      formatter: function(params) {
        return '<div style="font-size: 12px;margin-bottom: 7px">' + params.name + '<br>' + params.seriesName + ':' + params.value + '</div>'
      }, // 基本信息 提示
      id: ''
    }
  },
  mounted() {
    this.baseInfo() // 造数据
    this.basePlant(1) // 车间区间造数据
    this.basePlant(2) // 车间区间造数据
  },
  methods: {
    // 切换车间
    handleClick(data) {
      this.plantTile = this.tabList[data].label + '效应信息值'
      this.baseInfo()
      this.basePlant(1, this.tabList[data].label)
      this.basePlant(2, this.tabList[data].label)
    },
    // 造数据
    baseInfo() {
      const arr = []
      const whether = ['合计', '平均']
      const whether1 = ['总合计', '总平均']
      for (let i = 0; i < 8; i++) {
        const int = i % 2
        const title = i < 6 ? whether[int] : whether1[int]
        arr.push({
          title: title,
          fitVoltage: (Math.random() * 500+ 1).toFixed(2),
          peakVoltage: (Math.random() * 500+ 1).toFixed(2),
          fitTime: Math.floor(Math.random() * 500+ 1),
          fitDegree: (Math.random() * 500+ 1).toFixed(2),
          twinkleDegree: (Math.random() * 500+ 1).toFixed(2),
          fitCurrent: (Math.random() * 500+ 1).toFixed(2),
          fitPower: (Math.random() * 500+ 1).toFixed(2),
        })
      }
      this.tableData = arr
    },
    // 车间区间造数据
    basePlant(type, e) {
      const label = e ? e : '一车间'
      const arr = []
      const arr1 = []
      const arr2 = []
      for (let j = 0; j < 6; j++) {
        label + ''
        arr.push(label + (j + 1) + '区')
        arr1.push(Math.floor(Math.random() * 500+ 1))
        arr2.push(Math.floor(Math.random() * 500+ 1))
      }
      if (type === 1) {
        this.groupData = arr
        this.groupDataY = [
          { names: '闪烁个数', value: arr1 },
          { names: '效应个数', value: arr2 }
        ]
      } else {
        this.lineGroup = arr
        this.lineData = [
          { name: '效应时间(s)', data: arr1 },
          { name: '效应功耗(kW)', data: arr2 }
        ]
      }
    },
    /**
     * 列表接口
     */
    getRoleList(page) {
      this.data.page = page ? 1 : this.data.page
    },
    /**
     * 删除接口
     */
    getDeleteRole() {
      console.log('删除角色')
    },
    getDelShow(item) {
      this.id = item.id
      this.delshow = true
    },
    toTrue() {
      this.getDeleteRole()
    },
    addForm() {
      console.log('导出')
    },
    editForm(item) {
      this.title = '编辑'
      this.addEditShow = false
      this.$refs.formShow.init()
      this.$nextTick(() => {
        this.editData = Object.assign({}, item)
      })
    }
  }
}
</script>

<style scoped lang="scss">
.resources-container {
  width: 100%;
  padding: 6px 20px;
  background-color: #fff;
  .resources-tab {
    width: calc(100% - 290px);
    position: fixed;
    top: 104px;
    z-index: 99;
    background: #ffffff;
  }
  .menu-container {
    padding-top: 60px;
    background-color: #fff;
    .menu-title {
      width: 100%;
      font-size: 18px;
      font-weight: bold;
      text-align: center;
      line-height: 1;
      margin-top: 30px;
    }
  }
}
</style>
