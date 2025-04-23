<template>
  <el-table
      :data="tableData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
      class="column-table"
  >
    <el-table-column
        prop="title"
        label=""
        min-width="150">
    </el-table-column>
    <el-table-column align="center" :label="plantTile">
      <el-table-column
          prop="fitVoltage"
          label="适应电压(V)"
          min-width="120">
      </el-table-column>
      <el-table-column
          prop="peakVoltage"
          label="峰值电压(V)"
          min-width="120">
      </el-table-column>
      <el-table-column
          prop="fitTime"
          label="适应时间(s)"
          min-width="120">
      </el-table-column>
      <el-table-column
          prop="fitDegree"
          label="适应次数"
          min-width="120">
      </el-table-column>
      <el-table-column
          prop="twinkleDegree"
          label="闪烁次数"
          min-width="120">
      </el-table-column>
      <el-table-column
          prop="fitCurrent"
          label="适应电流(kv)"
          min-width="120">
      </el-table-column>
      <el-table-column
          prop="fitPower"
          label="适应功耗"
          min-width="120">
      </el-table-column>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  name: "ColumnTable",
  props: {
    tableData: {
      type: Array,
      default: () => []
    },
    plantTile: {
      type: String,
      default: () => ''
    }
  },
  data() {
    return {
    }
  },
  watch: {
    tableData: {
      handler(val) {
        console.log(val)
      }
    },
    immediate:true,
    deep: true
  },
  methods: {
    tableRowClassName({row, rowIndex}) {
      const maxLength = this.tableData.length - 3
      if (rowIndex > maxLength) {
        return 'success-row'
      }
      if (rowIndex % 2 === 0) {
        return 'warning-row'
      }
      return '';
    }
  }
}
</script>

<style scoped lang="scss">
  .column-table ::v-deep .warning-row {
    background: rgb(31, 199, 237, 0.53);
  }

  .column-table ::v-deep .success-row {
    background: #ffd9aa;
  }
</style>