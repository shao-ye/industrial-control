<template>
  <div class="resources-container">
    <div class="list-management-table-search">
      <div class="options-menu">
        <el-select v-model="district" size="mini" placeholder="请选择工区" @change="handleClick()">
          <el-option
            v-for="item in districtList"
            :key="item.sectionName"
            :label="item.sectionName"
            :value="item.sectionName"
          >
          </el-option>
        </el-select>
        <el-button type="primary" size="mini" @click="delShow = true">确认修改</el-button>
      </div>
    </div>
    <table-real
      v-loading="loading"
      :border="false"
      :table-data="tableData"
      :table-headers="tableHeader"
      :is-page="false"
      :is-selection="false"
      :height="'calc(100vh - 100px)'"
    >
      <template slot="potCurrentGeneration" slot-scope="scope">
        <el-select v-model="scope.row.potCurrentGeneration" size="mini" clearable placeholder="请选择" @change="addRealPotId(scope.row.realPotId)">
          <el-option
            v-for="item in potList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </template>
      <template slot="currentGenStartTime" slot-scope="scope">
        <el-date-picker v-model="scope.row.currentGenStartTime" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genStartTime1" slot-scope="scope">
        <el-date-picker v-model="scope.row.genStartTime1" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genEndTime1" slot-scope="scope">
        <el-date-picker v-model="scope.row.genEndTime1" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genStartTime2" slot-scope="scope">
        <el-date-picker v-model="scope.row.genStartTime2" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genEndTime2" slot-scope="scope">
        <el-date-picker v-model="scope.row.genEndTime2" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genStartTime3" slot-scope="scope">
        <el-date-picker v-model="scope.row.genStartTime3" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genEndTime3" slot-scope="scope">
        <el-date-picker v-model="scope.row.genEndTime3" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genStartTime4" slot-scope="scope">
        <el-date-picker v-model="scope.row.genStartTime4" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genEndTime4" slot-scope="scope">
        <el-date-picker v-model="scope.row.genEndTime4" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genStartTime5" slot-scope="scope">
        <el-date-picker v-model="scope.row.genStartTime5" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
      <template slot="genEndTime5" slot-scope="scope">
        <el-date-picker v-model="scope.row.genEndTime5" type="date" format="yyyy 年 MM 月 dd 日"
                        value-format="yyyy-MM-dd" placeholder="选择日期" size="mini" clearable @change="addRealPotId(scope.row.realPotId)"></el-date-picker>
      </template>
    </table-real>
    <popup title="提示" :show.sync="delShow" :content="delContent" confirm-button-text="确 认" background="#FF645F" @toTrue="toTrue"></popup>
  </div>
</template>

<script>
import TableReal from '@/components/table/TableReal'
import Popup from '@/components/popup/Popup'
import { factoryAreaAll } from '@/api/real'
import { potAgeList, potAgeEdit } from '@/api/real/parameter'
export default {
  name: "Parameter",
  components: {
    Popup,
    TableReal
  },
  data() {
    return {
      loading: false,
      district: '', // 选中的工区
      districtList: [],
      tableData: [],
      tableHeader: [
        { prop: 'realPotId', label: '槽号', width: 80 },
        { prop: 'potCurrentGeneration', label: '当前槽代', minWidth: 100 },
        { prop: 'currentGenStartTime', label: '当前起槽时间', minWidth: 160 },
        { prop: 'genStartTime1', label: '1代开始时间', minWidth: 160 },
        { prop: 'genEndTime1', label: '1代结束时间', minWidth: 160 },
        { prop: 'genStartTime2', label: '2代开始时间', minWidth: 160 },
        { prop: 'genEndTime2', label: '2代结束时间', minWidth: 160 },
        { prop: 'genStartTime3', label: '3代开始时间', minWidth: 160 },
        { prop: 'genEndTime3', label: '3代结束时间', minWidth: 160 },
        { prop: 'genStartTime4', label: '4代开始时间', minWidth: 160 },
        { prop: 'genEndTime4', label: '4代结束时间', minWidth: 160 },
        { prop: 'genStartTime5', label: '5代开始时间', minWidth: 160 },
        { prop: 'genEndTime5', label: '5代结束时间', minWidth: 160 },
      ], // 表头
      potList: [
        {
          label: '1代',
          value: 1
        },
        {
          label: '2代',
          value: 2
        },
        {
          label: '3代',
          value: 3
        },
        {
          label: '4代',
          value: 4
        },
        {
          label: '5代',
          value: 5
        }
      ], // 当前槽代
      delShow: false, // 确认修改
      delContent: '是否确认修改？',
      tableHeaders: [],
      realPotIdArr: [], // 改变的集合
    }
  },
  mounted() {
    this.getFactoryAreaAll() // 获取全部厂区集合
  },
  methods: {
    // 获取全部厂区集合
    getFactoryAreaAll() {
      factoryAreaAll().then(res => {
        this.districtList = res.data
        this.district = res.data[0].sectionName // 选中的工区
      }).finally(() => {
        this.baseInfo() // 当前页面数据
      })
    },
    // 改变了哪些
    addRealPotId(realPotId) {
      if (this.realPotIdArr.indexOf(realPotId) === -1) {
        this.realPotIdArr.push(realPotId)
      }
    },
    // 确认修改
    toTrue() {
      // const data = this.tableData
      let data = []
      this.tableData.forEach(item => {
        if (this.realPotIdArr.indexOf(item.realPotId) !== -1) {
          data.push(item)
        }
      })
      if (data.length === 0) {
        this.$message.warning('暂无修改')
        this.delShow = false
        return false
      }


      potAgeEdit(data).then(res => {
        if (res.code === 200) {
          this.$message.success('修改成功')
          this.baseInfo() // 测量数据输入界面
        }
      }).finally(() => {
        this.delShow = false
      })
    },
    // 切换车间
    handleClick() {
      this.baseInfo()
    },
    // 当前页面数据
    baseInfo() {
      let data = {}
      for (let i = 0; i < this.districtList.length; i++) {
        const item = this.districtList[i]
        if (item.sectionName === this.district) {
          data = { factoryNo: item.factoryNo, areaNo: item.areaNo }
        }
      }
      potAgeList(data).then(res => {
        this.tableData = res.data
      })
    },
  }
}
</script>

<style scoped lang="scss">
.resources-container {
  width: 100%;
  background-color: #fff;
  padding: 6px 20px;
  .list-management-table-search {
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
      ::v-deep .el-input--suffix {
        width: 120px;
        margin-right: 10px;
      }
    }
  }
  ::v-deep .el-date-editor.el-input, .el-date-editor.el-input__inner {
    width: 155px;
  }
}
.changeSty ::v-deep .el-input__inner {
  color: red;
}
</style>
