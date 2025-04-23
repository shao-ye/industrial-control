<template>
  <el-table
    :data="tableData"
    style="width: 100%"
    height="calc(100vh - 90px)"
    stripe
    :row-class-name="tableRowClassName"
    class="column-table"
  >
    <el-table-column align="center" :label="plantTile">
      <el-table-column
        prop="realSlotNo"
        label="槽号"
        align="center"
        width="60">
      </el-table-column>
      <!--  最近一次效应    -->
      <el-table-column align="center" label="最近一次效应">
        <el-table-column prop="aeStartTime1" label="发生时刻" min-width="80" align="center"></el-table-column>
        <el-table-column prop="aeAverageVoltage1" label="平均电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :style="{ fontSize: scope.row.aeAverageVoltage1 > 20 ? '15px': '', fontWeight: scope.row.aeAverageVoltage1 > 20 ? 'bold': '' }">{{ scope.row.aeAverageVoltage1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="aePeakVoltage1" label="峰值电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :class="scope.row.aePeakVoltage1 > 35 ? 'flashing' : ''" :style="{
              fontSize: scope.row.aePeakVoltage1 > 35 ? '15px': '',
              fontWeight: scope.row.aePeakVoltage1 > 35 ? 'bold': '',
              color: scope.row.aePeakVoltage1 > 35 ? 'red' : ''
            }">
              {{ scope.row.aePeakVoltage1 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeLastingTime1" label="持续时间" align="center" width="80">
          <template slot-scope="scope">
             <span :style="{
              fontSize: scope.row.aeLastingTime1 > 60 ? '15px': '',
              fontWeight: scope.row.aeLastingTime1 > 60 ? 'bold': '',
            }">
              {{ scope.row.aeLastingTime1 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeType1" label="效应类型" align="center" min-width="80"></el-table-column>
      </el-table-column>
      <!--  前一次效应-->
      <el-table-column align="center" label="前一次效应">
        <el-table-column prop="aeStartTime2" label="发生时刻" align="center" min-width="80"></el-table-column>
        <el-table-column prop="aeAverageVoltage2" label="平均电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :style="{
              fontSize: scope.row.aeAverageVoltage2 > 20 ? '15px': '',
              fontWeight: scope.row.aeAverageVoltage2 > 20 ? 'bold': ''
            }">
              {{ scope.row.aeAverageVoltage2 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aePeakVoltage2" label="峰值电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :class="scope.row.aePeakVoltage1 > 35 ? 'flashing' : ''" :style="{
              fontSize: scope.row.aePeakVoltage2 > 35 ? '15px' : '',
              fontWeight: scope.row.aePeakVoltage2 > 35 ? 'bold' : '',
              color: scope.row.aePeakVoltage2 > 35 ? 'red' : ''
            }">
              {{ scope.row.aePeakVoltage2 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeLastingTime2" label="持续时间" align="center" width="80">
          <template slot-scope="scope">
            <span :style="{
              fontSize: scope.row.aeLastingTime2 > 60 ? '15px': '',
              fontWeight: scope.row.aeLastingTime2 > 60 ? 'bold': '',
            }">
              {{ scope.row.aeLastingTime2 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeType2" label="效应类型" align="center" min-width="80"></el-table-column>
      </el-table-column>
      <!--  前二次效应    -->
      <el-table-column align="center" label="前二次效应">
        <el-table-column prop="aeStartTime3" label="发生时刻" align="center" min-width="80"></el-table-column>
        <el-table-column prop="aeAverageVoltage3" label="平均电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :style="{
              fontSize: scope.row.aeAverageVoltage3 > 20 ? '15px': '',
              fontWeight: scope.row.aeAverageVoltage3 > 20 ? 'bold': ''
            }">
              {{ scope.row.aeAverageVoltage3 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aePeakVoltage3" label="峰值电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :class="scope.row.aePeakVoltage1 > 35 ? 'flashing' : ''" :style="{
              fontSize: scope.row.aePeakVoltage3 > 35 ? '15px': '',
              fontWeight: scope.row.aePeakVoltage3 > 35 ? 'bold': '',
              color: scope.row.aePeakVoltage3 > 35 ? 'red': ''
            }">
              {{ scope.row.aePeakVoltage3 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeLastingTime3" label="持续时间" align="center" width="80">
          <template slot-scope="scope">
            <span :style="{
              fontSize: scope.row.aeLastingTime3 > 60 ? '15px': '',
              fontWeight: scope.row.aeLastingTime3 > 60 ? 'bold': '',
            }">
              {{ scope.row.aeLastingTime3 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeType3" label="效应类型" align="center" min-width="80"></el-table-column>
      </el-table-column>
      <!--  前三次效应    -->
      <el-table-column align="center" label="前三次效应">
        <el-table-column prop="aeStartTime4" label="发生时刻" align="center" min-width="80"></el-table-column>
        <el-table-column prop="aeAverageVoltage4" label="平均电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :style="{
              fontSize: scope.row.aeAverageVoltage4 > 20 ? '15px': '',
              fontWeight: scope.row.aeAverageVoltage4 > 20 ? 'bold': ''
            }">
              {{ scope.row.aeAverageVoltage4 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aePeakVoltage4" label="峰值电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :class="scope.row.aePeakVoltage1 > 35 ? 'flashing' : ''" :style="{
              fontSize: scope.row.aePeakVoltage4 > 35 ? '15px': '',
              fontWeight: scope.row.aePeakVoltage4 > 35 ? 'bold': '',
              color: scope.row.aePeakVoltage4 > 35 ? 'red': ''
            }">
              {{ scope.row.aePeakVoltage4 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeLastingTime4" label="持续时间" align="center" width="80">
          <template slot-scope="scope">
            <span :style="{
              fontSize: scope.row.aeLastingTime4 > 60 ? '15px': '',
              fontWeight: scope.row.aeLastingTime4 > 60 ? 'bold': '',
            }">
              {{ scope.row.aeLastingTime4 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeType4" label="效应类型" align="center" min-width="80"></el-table-column>
      </el-table-column>
      <!--  前四次效应    -->
      <el-table-column align="center" label="前四次效应">
        <el-table-column prop="aeStartTime5" label="发生时刻" align="center" min-width="80"></el-table-column>
        <el-table-column prop="aeAverageVoltage5" label="平均电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :style="{
              fontSize: scope.row.aeAverageVoltage5 > 20 ? '15px': '',
              fontWeight: scope.row.aeAverageVoltage5 > 20 ? 'bold': ''
            }">
              {{ scope.row.aeAverageVoltage5 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aePeakVoltage5" label="峰值电压" align="center" min-width="80">
          <template slot-scope="scope">
            <span :class="scope.row.aePeakVoltage1 > 35 ? 'flashing' : ''" :style="{
              fontSize: scope.row.aePeakVoltage5 > 35 ? '15px': '',
              fontWeight: scope.row.aePeakVoltage5 > 35 ? 'bold': '',
              color: scope.row.aePeakVoltage5 > 35 ? 'red': ''
            }">
              {{ scope.row.aePeakVoltage5 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeLastingTime5" label="持续时间" align="center" width="80">
          <template slot-scope="scope">
            <span :style="{
              fontSize: scope.row.aeLastingTime5 > 60 ? '15px': '',
              fontWeight: scope.row.aeLastingTime5 > 60 ? 'bold': '',
            }">
              {{ scope.row.aeLastingTime5 }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="aeType5" label="效应类型" align="center" min-width="80"></el-table-column>
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
        // console.log(val)
      }
    },
    immediate:true,
    deep: true
  },
  methods: {
    tableRowClassName({row, rowIndex}) {
      return '';
    }
  }
}
</script>

<style scoped lang="scss">
.column-table ::v-deep .warning-row {
  background: rgb(31, 199, 237, 0.53);
}
.column-table ::v-deep th {
  padding: 0;
}
.column-table ::v-deep .el-table__cell > .cell {
  font-size: 12px;
  line-height: 1;
}
.column-table ::v-deep .success-row {
  background: #ffd9aa;
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
