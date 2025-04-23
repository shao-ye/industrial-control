<template>
  <div class="resources-container">
    <tab-list :tab-list="tabList" @handleClick="handleClick"></tab-list>
    <column-table :plant-tile="plantTile" :table-data="tableData"></column-table>
  </div>
</template>

<script>
import ColumnTable from '@/components/table/AETable'
import TabList from '@/components/tabs/TabList'
import { factoryAreaAll, aeInfoList } from '@/api/real'
export default {
  name: "plan-feature",
  components: {
    ColumnTable,
    TabList
  },
  data() {
    return {
      loading: false,
      plantTile: '240kA 效应统计报表', // 表头名称
      tableData: [],
      tabList: [], // 车间分区
      idx: '0' // 默认选中
    }
  },
  mounted() {
    this.getFactoryArea() // 获取当前用户厂区集合
    this.getFactoryArea() // 获取当前用户厂区集合
  },
  methods: {
    // 获取当前用户厂区集合
    getFactoryArea() {
      factoryAreaAll().then(res => {
        if (res.code === 200) {
          this.tabList = res.data.map((item, index) => {
            item.value = (index + 1).toString()
            return item
          })
          this.baseInfo()
        }
      })
    },
    // 切换车间
    handleClick(data) {
      this.idx = data
      this.baseInfo()
    },
    // 造数据
    baseInfo() {
      const int = parseInt(this.idx)
      const data = { areaNo: this.tabList[int].areaNo }
      aeInfoList(data ).then(res => {
        if (res.code === 200) {
          this.tableData = res.rows
        }
      })
    },
    /**
     * 列表接口
     */
    getRoleList(page) {
      this.data.page = page ? 1 : this.data.page
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
    justify-content: flex-end;
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
