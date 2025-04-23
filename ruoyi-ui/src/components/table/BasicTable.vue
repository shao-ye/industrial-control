<template>
  <div class="table-container">
    <el-table
      :show-header="showHeader"
      :data="tableData"
      :border="border"
      :header-cell-style="{ backgroundColor: '#F7F9FB', color: '#646A73' }"
      :cell-style="{ color: '#1F2329' }"
      :max-height="height"
      style="width: 100%"
      @row-click="handleCurrentChange"
      @cell-mouse-enter="cellMouseEnter"
      @cell-mouse-leave="cellMouseLeave"
    >
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
            <div v-if="isAdd && rowId === scope.row.idx && index === 0 && isAddDatum" class="add-row" @click="addRow(scope.$index)">
              <i class="el-icon-plus" />
            </div>
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
    border: {
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
    isCurrent: {
      type: Boolean,
      default: false
    }, // 是否启用单击当前行
    isAddDatum: {
      type: Boolean,
      default: false
    }, // 是否启用新增
    height: {
      type: String,
      default: '100%'
    },
    showHeader: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      privatePage: 1,
      rowId: '',
      isAdd: false
    }
  },
  watch: {
    page: function() {
      this.privatePage = this.page
    }
  },
  methods: {
    /**
     * 当前页选中的时候触发
     * */
    currentPage(e) {
      this.$emit('currentPage', e)
    },
    _handleSizeChange(val) {
      this.$emit('sizeChange', val)
    },
    // 选中当前行
    handleCurrentChange(val) {
      console.log(val)
      if (this.isCurrent) {
        this.$emit('currentRow', val)
      }
    },
    // 鼠标移入
    cellMouseEnter(row) {
      this.rowId = row.idx
      this.isAdd = true
    },
    // 鼠标移出
    cellMouseLeave() {
      this.rowId = ''
      this.isAdd = false
    },
    // 新增数据
    addRow(e) {
      console.log(e)
      this.$emit('addDatum', e)
    },
    setPagePositionStyle() {
      let temp = ''
      if (this.pagePosition === 'left') {
        temp = 'flex-start'
      } else if (this.pagePosition === 'center') {
        temp = 'center'
      } else if (this.pagePosition === 'right') {
        temp = 'flex-end'
      }
      return { justifyContent: temp }
    }
  }
}
</script>
<style scoped lang="scss">
.table-container {
  position: relative;
  ::v-deep .el-table .cell {
    padding-left: 24px;
    position: relative;
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
.add-row {
  position: absolute;
  left: 0;
  top: 6px;
  bottom: 0;
  border-radius: 2px;
  border: 1px solid rgba(218, 218, 218, 1);
  line-height: 1;
  height: 20px;
  width: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
</style>
