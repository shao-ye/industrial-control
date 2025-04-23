<template>
  <div class="resources-container">
    <div class="list-management-table-search">
      <div class="options-menu">
        <el-radio-group v-model="displayType" @change="displayChange">
          <el-radio :label="1">按工区查询</el-radio>
          <el-radio :label="2">按单槽查询</el-radio>
        </el-radio-group>
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
        <el-select v-model="district" size="mini" placeholder="请选择" @change="displayChange()">
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
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          :default-time="['12:00:00', '08:00:00']"
          :clearable="false"
          @change="dateChange"
        ></el-date-picker>
      </div>
    </div>
    <table-real
      v-loading="loading"
      :border="false"
      :table-data="tableData"
      :table-headers="tableHeaders"
      :is-page="true"
      :total="total"
      :page="current"
      :page-size="size"
      :page-sizes="[25, 50, 100]"
      :page-background="false"
      page-layout="total, sizes, prev, pager, next, jumper"
      page-position="right"
      body-color="#001741"
      @currentPage="currentPage"
      @sizeChange="sizeChange"
      :is-selection="false"
      :height="'calc(100vh - 150px)'"
    ></table-real>
  </div>
</template>

<script>
import { factoryAreaAll } from '@/api/real'
import { paramModifyList } from '@/api/real/parameter'
import TableReal from '@/components/table/TableReal'
export default {
  name: "",
  components: {
    TableReal
  },
  data() {
    return {
      loading: false,
      tabList: [], // 车间分区
      dateArr: [], // 选择的日期
      displayType: 1, // 工区,单槽
      district: '', // 选中的工区
      districtList: [],
      idx: '0', // 默认选中
      tableHeaders: [
        { prop: 'slotId', label: '槽号', width: 80 },
        { prop: 'writeDate', label: '修改时间' },
        { prop: 'author', label: '修改人' },
        { prop: 'paramStyle', label: '参数名称' },
        { prop: 'originalValue', label: '原始值' },
        { prop: 'newValue', label: '修改值' }
      ],
      tableDataAll: [], // 全部数据
      tableData: [], // 数据
      size: 25, // 条数
      current: 1, // 页数
      total: 0, // 总页数
    }
  },
  mounted() {
    this.getFactoryArea() // 获取当前用户厂区集合
    this.defaultTime() // 默认一个月
  },
  methods: {
    // 默认时间
    defaultTime() {
      let yy = new Date().getFullYear()
      let mm = new Date().getMonth() + 1
      let dd = new Date().getDate()
      let hh = new Date().getHours()
      let ms = new Date().getMinutes()
      let ss = new Date().getSeconds()
      const end = yy + '-' + (mm >= 10 ? mm : '0' + mm) + '-' + (dd >= 10 ? dd : '0' + dd) + ' ' + (hh >= 10 ? hh : '0' + hh) + ':' + (ms >= 10 ? ms : '0' + ms) + ':' + (ss >= 10 ? ss : '0' + ss)
      let start = new Date(parseInt(new Date().getTime() - 3600 * 1000 * 24))
      const yy1 = start.getFullYear()
      const mm1 = start.getMonth() + 1
      const dd1 = start.getDate()
      const hh1 = start.getHours()
      const ms1 = start.getMinutes()
      const ss1 = start.getSeconds()
      start = yy1 + '-' + (mm1 >= 10 ? mm1 : '0' + mm1) + '-' + (dd1 >= 10 ? dd1 : '0' + dd1) + ' ' + (hh1 >= 10 ? hh1 : '0' + hh1) + ':' + (ms1 >= 10 ? ms1 : '0' + ms1) + ':' + (ss1 >= 10 ? ss1 : '0' + ss1)
      this.dateArr = [start, end]
    },
    // 点击前一区，后一区
    alterArea(e) {
      this.idx = parseInt(this.idx) + parseInt(e)
      this.districtList = this.tabList[parseInt(this.idx)].realSlotNoList
      this.district = this.tabList[parseInt(this.idx)].realSlotNoList[0]
      this.loading = true
      this.baseInfo() // 图形数据
    },
    // 获取当前用户厂区集合
    getFactoryArea() {
      this.loading = true
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
      this.loading = true
      this.baseInfo() // 图形数据
    },
    // 显示类型改变
    displayChange() {
      this.current = 1
      this.loading = true
      this.baseInfo() // 图形数据
    },
    // 数据
    baseInfo() {
      let data = {
        startDate: this.dateArr[0], // 开始日期
        endDate: this.dateArr[1], // 结束日期
      }
      const item = this.tabList[parseInt(this.idx)]
      this.displayType === 1 ? (data['factoryNo'] = item.factoryNo, data['areaNo'] = item.areaNo) : data['realSlotNo'] = this.district
      paramModifyList(data).then(res => {
        if (res.code === 200) {
          this.tableDataAll = res.rows
          const start = (this.current - 1) * (this.size)
          const end = (this.current) * (this.size) - 1
          this.tableData = res.rows.slice(start, end)
          this.total = res.total
        }
      }).finally(() => {
        this.loading = false
      })
    },
    currentPage(page) {
      this.current = page
      this.tableData = []
      this.loading = true
      setTimeout(() => {
        this.loading = false
        const start = (page - 1) * (this.size)
        const end = page * (this.size) - 1
        this.tableData = this.tableDataAll.slice(start, end)
      }, 1000)
    },
    /**
     * 分页大小改变
     * */
    sizeChange(size) {
      this.size = size
      this.tableData = []
      this.loading = true
      setTimeout(() => {
        this.loading = false
        const start = (page - 1) * (this.size)
        const end = page * (this.size) - 1
        this.tableData = this.tableDataAll.slice(start, end)
      }, 1000)
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
      ::v-deep .el-checkbox {
        margin-right: 20px;
      }
      ::v-deep .el-input--suffix {
        width: 85px;
        margin: 0 10px;
      }
    }
  }
  ::v-deep .el-table--scrollable-y .el-table__body-wrapper {
    height: calc(100vh - 305px) !important;
  }
}
</style>
