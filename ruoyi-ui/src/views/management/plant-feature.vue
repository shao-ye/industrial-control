<template>
  <div class="resources-container">
    <tab-list :tab-list="tabList" @handleClick="handleClick"></tab-list>
    <el-descriptions title="基础信息" :column="4">
      <el-descriptions-item label="当前时间">{{ getTime }}</el-descriptions-item>
      <el-descriptions-item label="系列电压(V)">439.0</el-descriptions-item>
      <el-descriptions-item label="系列电流(KA)">240.63</el-descriptions-item>
      <el-descriptions-item label="厂房电压(V)">216.9</el-descriptions-item>
    </el-descriptions>
    <div class="list-management-table-search">
      <div>
        <el-button class="del-btn" type="danger" plain @click="addForm">
          <i class="el-icon-delete"></i>
          清空
        </el-button>
<!--        <el-button class="add-btn" @click="addForm">-->
<!--          <i class="el-icon-download"></i>-->
<!--          导出-->
<!--        </el-button>-->
      </div>
      <el-popover
          placement="bottom"
          width="120"
          trigger="hover">
        <div class="table-headers">
          <el-checkbox-group class="delivery-checkbox-group" v-model="tableHeadersClick" @change="headersClick">
            <el-checkbox v-for="item in tableHeadersAll" :key="item.prop" :label="item.prop">
              {{ item.label }}
            </el-checkbox>
          </el-checkbox-group>
        </div>
        <div slot="reference" class="popover-menu">
          <i class="el-icon-menu" style="font-size: 25px; cursor: pointer"/>
        </div>
      </el-popover>
    </div>
    <Table
      v-loading="loading"
      :border="false"
      :table-data="tableData"
      :table-headers="tableHeaders"
      :height="'calc(100vh - 380px)'"
      :is-page="false"
      :is-selection="true"
      @handleSelectionChange="handleSelectionChange"
    >
      <template slot="save" slot-scope="scope">
        <span :style="{ color: scope.row.save === 'OFF' ? 'red' : '' }">{{ scope.row.save }}</span>
      </template>
      <template slot="alf" slot-scope="scope">
        <span :style="{ color: scope.row.alf === 'OFF' ? 'red' : '' }">{{ scope.row.alf }}</span>
      </template>
      <template slot="rc" slot-scope="scope">
        <span :style="{ color: scope.row.rc === 'OFF' ? 'red' : '' }">{{ scope.row.rc }}</span>
      </template>
      <template slot="nb" slot-scope="scope">
        <span :style="{ color: scope.row.nb === 'OFF' ? 'red' : '' }">{{ scope.row.nb }}</span>
      </template>
      <template slot="aeb" slot-scope="scope">
        <span :style="{ color: scope.row.aeb === 'OFF' ? 'red' : '' }">{{ scope.row.aeb }}</span>
      </template>
      <template slot="control" slot-scope="scope">
        <span :style="{ color: scope.row.control === 'OFF' ? 'red' : '' }">{{ scope.row.control }}</span>
      </template>
      <template slot="status" slot-scope="scope">
        <span :style="{ color: scope.row.status === 'Stop' ? 'red' : '' }">{{ scope.row.status }}</span>
      </template>
      <template slot="options" slot-scope="scope">
        <div class="operation">
          <div class="operation-btn-edit" @click="editForm(scope.row)">
            编辑
          </div>
        </div>
      </template>
    </Table>
    <popup title="提示" :show.sync="delshow" :content="delContent" confirm-button-text="删 除" background="#FF645F" @toTrue="toTrue"></popup>
  </div>
</template>

<script>
import BarSimpleChart from '@/components/echarts/BarSimpleChart'
import Table from '@/components/table/Table'
import Popup from '@/components/popup/Popup'
import TabList from '@/components/tabs/TabList'
export default {
  name: "plan-feature",
  components: {
    BarSimpleChart,
    Table,
    Popup,
    TabList
  },
  data() {
    return {
      loading: false,
      tableHeaders: [
        { prop: 'num', label: '槽号', minWidth: 80 },
        { prop: 'save', label: '保留', minWidth: 100 },
        { prop: 'alf', label: 'ALF', minWidth: 100 },
        { prop: 'rc', label: '自动RC', minWidth: 100 },
        { prop: 'nb', label: '自动NB', minWidth: 100 },
        { prop: 'aeb', label: '自动AEB', minWidth: 100 },
        { prop: 'control', label: '深度控制', minWidth: 100 },
        { prop: 'status', label: '工作状态', minWidth: 100 },
        { prop: 'options', label: '操作', width: 120, fixed: 'right' }
      ],
      tableHeadersAll: [
        { prop: 'num', label: '槽号', minWidth: 80 },
        { prop: 'save', label: '保留', minWidth: 100 },
        { prop: 'alf', label: 'ALF', minWidth: 100 },
        { prop: 'rc', label: '自动RC', minWidth: 100 },
        { prop: 'nb', label: '自动NB', minWidth: 100 },
        { prop: 'aeb', label: '自动AEB', minWidth: 100 },
        { prop: 'control', label: '深度控制', minWidth: 100 },
        { prop: 'status', label: '工作状态', minWidth: 100 },
        { prop: 'options', label: '操作', width: 120, fixed: 'right' }
      ], // 全部表头
      tableHeadersClick: ['num', 'save', 'alf', 'rc', 'nb', 'aeb', 'control', 'status', 'options'], // 表格头选中
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
      getTime: '', // 当前时间
      addEditShow: false,
      id: ''
    }
  },
  mounted() {
    this.baseInfo()
    setInterval(() => {
      this.getCurrentTime()
    }, 1000)
  },
  methods: {
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
      console.log(data)
      this.baseInfo()
    },
    // 造数据
    baseInfo() {
      const arr = []
      const whether = ['OFF', 'NO']
      const whether1 = ['NORM', 'Stop']
      for (let i = 0; i < 20; i++) {
        arr.push({
          num: Math.floor(Math.random() * 100+ 1),
          save: whether[Math.round(Math.random())],
          alf: whether[Math.round(Math.random())],
          rc: whether[Math.round(Math.random())],
          nb: whether[Math.round(Math.random())],
          aeb: whether[Math.round(Math.random())],
          control: whether[Math.round(Math.random())],
          status: whether1[Math.round(Math.random())]
        })
      }
      this.tableData = arr
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
    // 表格头切换
    headersClick(e) {
      const all = this.tableHeadersAll
      const arr = []
      all.forEach(item => {
        e.includes(item.prop) ? arr.push(item) : ''
      })
      this.tableHeaders = arr
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
  padding: 6px 20px;
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
  .list-management-table-search {
    margin-bottom: 20px;
    margin-top: 20px;
    width: 100%;
    justify-content: space-between;
    align-items: center;
    display: flex;
    //::v-deep .el-button:hover {
    //  border-color: #1E9D93;
    //}
    //::v-deep .el-button {
    //  border-color: #1E9D93;
    //}
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
</style>
