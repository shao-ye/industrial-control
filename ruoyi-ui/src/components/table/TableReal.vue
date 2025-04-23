<template>
  <div class="table-container">
    <el-table
      :show-header="showHeader	"
      :data="tableData"
      :border="border"
      :header-cell-style="{ backgroundColor: '#F7F9FB', color: '#646A73' }"
      :cell-style="{ color: '#1F2329' }"
      style="width: 100%"
      :stripe="isStripe"
      :height="height"
      :checkData="multipleSelection"
      :row-key="rowKey"
      :tree-props="treeProps"
      :row-class-name="tableRowClassName"
      @selection-change="handleSelectionChange"
    >
      <template v-if="isSelection">
        <el-table-column type="selection" width="75" />
      </template>
      <template v-for="(item, index) in tableHeaders">
        <el-table-column
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :fixed="item.fixed"
          :width="item.width"
          :min-width="item.minWidth"
          :align="item.align"
          :show-overflow-tooltip="item.tooltip"
          :sortable="item.options ? !!item.options.map(i => i.type).includes('sort') : false"
        >
          <template v-if="item.prompt" slot="header">
            <span>{{ item.label }}</span>
            <el-tooltip popper-class="table-hint-tooltip-item" :visible-arrow="true" effect="dark" :content="item.prompt.content" placement="top">
              <i :class="item.prompt.icon" class="tooltip-icon" />
            </el-tooltip>
          </template>
          <template slot-scope="scope">
            <slot :name="item.prop" :row="scope.row" :$index="scope.$index">
              <span>{{ scope.row ? scope.row[item.prop] : '' }}</span>
            </slot>
          </template>
        </el-table-column>
      </template>
    </el-table>
    <div v-if="isPage" class="pagination-container" style="display: flex;" :style="setPagePositionStyle()">
      <el-pagination
        :background="pageBackground"
        :page-sizes="pageSizes"
        :page-size="pageSize"
        :layout="pageLayout"
        :total="total"
        :hide-on-single-page="hideOnSinglePage"
        :current-page.sync="privatePage"
        @current-change="currentPage"
        @size-change="_handleSizeChange"
      />
    </div>
  </div>
</template>
<script>
export default {
  name: 'TablePeople',
  props: {
    // table表格数据
    tableData: {
      type: Array,
      default: () => []
    },
    isSelection: {
      type: Boolean,
      default: false
    },
    border: {
      type: Boolean,
      default: true
    },
    isStripe: {
      type: Boolean,
      default: true
    },
    tableHeaders: {
      type: Array,
      default: () => []
    },
    pageLayout: {
      type: String,
      default: () => { return 'prev, pager, next, sizes, jumper' }
    }, // 分页模板
    total: {
      type: Number,
      default: 0
    }, // 总条数
    pageSize: {
      type: Number,
      default: 10
    }, // 分页大小
    pageSizes: {
      type: Array,
      default: () => { return [5, 10, 25, 50, 100] }
    }, // 可更改的分页大小
    pageBackground: {
      type: Boolean,
      default: true
    }, // 分页器背景
    hideOnSinglePage: {
      type: Boolean,
      default: false
    }, // 只有一个页面时是否隐藏分页器
    pagePosition: {
      type: String,
      default: 'center'
    },
    page: {
      type: Number,
      default: 1
    }, // 当前页
    isPage: {
      type: Boolean,
      default: true
    }, // 是否启用表格分页
    height: {
      type: String,
      default: '100%'
    },
    showHeader: {
      type: Boolean,
      default: true
    },
    rowKey: {
      type: String,
      default: () => ''
    }, // 是否折叠显示
    treeProps: {
      type: Object,
      default: () => {}
    } // 树类型通过指定 row 中的 hasChildren 字段来指定哪些行是包含子节点
  },
  data() {
    return {
      privatePage: 1,
      multipleSelection: []
    }
  },
  watch: {
    page: function() {
      this.privatePage = this.page
    }
  },
  methods: {
    // reload() {
    //   this.$forceUpdate()
    // },

    /**
     * 当前多选
     * @param val
     */
    handleSelectionChange(val){
      this.multipleSelection = val;
      console.log(this.multipleSelection,'选中')
      this.$emit('table-check', this.multipleSelection);
    },
    /**
     * 当前页选中的时候触发
     * */
    currentPage(e) {
      this.$emit('currentPage', e)
    },
    _handleSizeChange(val) {
      this.$emit('sizeChange', val)
    },
    setPagePositionStyle() {
      let temp = ''
      // connectStatus
      if (this.pagePosition === 'left') {
        temp = 'flex-start'
      } else if (this.pagePosition === 'center') {
        temp = 'center'
      } else if (this.pagePosition === 'right') {
        temp = 'flex-end'
      }

      return { justifyContent: temp }
    },
    tableRowClassName({row, rowIndex}) {
      if (row.connectStatus && row.connectStatus === 1) {
        return 'warning-row'
      }
      return '';
    }
  }
}
</script>
<style scoped lang="scss">
.table-container {
  position: relative;
  ::v-deep .el-table .cell {
    padding: 0;
    line-height: 22px;
    text-align: center;
  }
  ::v-deep .el-table--medium .el-table__cell {
    padding: 5px 0;
  }
  ::v-deep .el-table__fixed-right-patch {
    background-color: rgb(247, 249, 251);
  }
  .pagination-container {
    margin-top: 24px;
  }
}
.tooltip-icon {
  margin: 0 4px;
}
.table-container ::v-deep .el-table .warning-row {
  background: rgba(155, 155, 155, 1);
}
//.table-container ::v-deep .is-scrolling-none {
//  //height: calc(100vh - 265px) !important;
//  height: 100% !important;
//  padding-bottom: 40px;
//}
//.table-container ::v-deep .el-table--scrollable-y .el-table__body-wrapper {
//  //height: calc(100vh - 265px) !important;
//  height: 100% !important;
//  padding-bottom: 40px;
//}
</style>
