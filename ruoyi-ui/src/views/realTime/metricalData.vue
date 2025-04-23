<template>
  <div class="resources-container">
    <tab-list :tab-list="tabList" @handleClick="handleClick"></tab-list>
    <div class="list-management-table-search">
      <div class="options-menu">
        <el-date-picker
          size="mini"
          v-model="startDate"
          type="date"
          :clearable="false"
          format="yyyy 年 MM 月 dd 日"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
          @change="dateChange"
        ></el-date-picker>
        <el-button type="primary" size="mini" :disabled="isEdit ? Boolean(false) : Boolean(true)" @click="delShow = true">确认修改</el-button>
        <el-button size="mini" :disabled="isEdit ? Boolean(false) : Boolean(true)" @click="getDelShow">取消修改</el-button>
      </div>
      <table-toolbar :columns="tableHeadersAll" @changeColumn="changeColumn"></table-toolbar>
    </div>
    <table-real
      ref="tableReal"
      v-loading="loading"
      v-if="renderComponent"
      :border="false"
      :table-data="tableData"
      :table-headers="tableHeaders"
      :is-page="false"
      :is-selection="false"
      :height="'calc(100vh - 160px)'"
      @handleSelectionChange="handleSelectionChange"
    >
      <template v-if="isEdit" slot="metalMass" slot-scope="scope">
        <el-input v-model="scope.row.metalMass" size="mini" @focus="getInputFocus($event)" @change="valChange($event, scope.$index, 'metalMass', 0, 3000)" :ref="'metalMass' + scope.$index" @keyup.native.enter="focusNext('metalMass', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="metalHeight" slot-scope="scope">
        <el-input v-model="scope.row.metalHeight" size="mini" @focus="getInputFocus($event)" @change="valChange($event, scope.$index, 'metalHeight', 0, 50)" :ref="'metalHeight' + scope.$index" @keyup.native.enter="focusNext('metalHeight', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="bathHeight" slot-scope="scope">
        <el-input v-model="scope.row.bathHeight" size="mini" @focus="getInputFocus($event)" @change="valChange($event, scope.$index, 'bathHeight', 0, 50)" :ref="'bathHeight' + scope.$index" @keyup.native.enter="focusNext('bathHeight', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="bathTemperature" slot-scope="scope">
        <el-input v-model="scope.row.bathTemperature" size="mini" @focus="getInputFocus($event)" @change="valChange($event, scope.$index, 'bathTemperature', 0, 1500)" :ref="'bathTemperature' + scope.$index" @keyup.native.enter="focusNext('bathTemperature', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="bathRatio" slot-scope="scope">
        <el-input v-model="scope.row.bathRatio" size="mini" @focus="getInputFocus($event)" @change="valChange($event, scope.$index, 'bathRatio', 0, 3.5)" :ref="'bathRatio' + scope.$index" @keyup.native.enter="focusNext('bathRatio', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d{0,4})?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="feContent" slot-scope="scope">
        <el-input v-model="scope.row.feContent" size="mini" @change="valChange($event, scope.$index, 'feContent', 0, 0.96)" :ref="'feContent' + scope.$index" @keyup.native.enter="focusNext('feContent', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d{0,4})?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="siContent" slot-scope="scope">
        <el-input v-model="scope.row.siContent" size="mini" @change="valChange($event, scope.$index, 'siContent', 0, 0.95)" :ref="'siContent' + scope.$index" @keyup.native.enter="focusNext('siContent', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d{0,4})?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="metalQuality" slot-scope="scope">
        <el-input v-model="scope.row.metalQuality" size="mini" @change="valChange($event, scope.$index, 'metalQuality', 0, 99.9999)" :ref="'metalQuality' + scope.$index" @keyup.native.enter="focusNext('metalQuality', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d{0,4})?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="al2o3Concen" slot-scope="scope">
        <el-input v-if="isEdit" v-model="scope.row.al2o3Concen" @focus="getInputFocus($event)" @change="valChange($event, scope.$index, 'al2o3Concen', 0, 20.5)" size="mini" :ref="'al2o3Concen' + scope.$index" @keyup.native.enter="focusNext('al2o3Concen', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d{0,4})?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="cvd" slot-scope="scope">
        <el-input v-model="scope.row.cvd" size="mini" @focus="getInputFocus($event)" @change="valChange($event, scope.$index, 'cvd', 0, 2000)" :ref="'cvd' + scope.$index" @keyup.native.enter="focusNext('cvd', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="acd" slot-scope="scope">
        <el-input v-model="scope.row.acd" size="mini" @focus="getInputFocus($event)" @change="valChange($event, scope.$index, 'acd', 0, 50)" :ref="'acd' + scope.$index" @keyup.native.enter="focusNext('acd', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="superheat" slot-scope="scope">
        <el-input v-model="scope.row.superheat" size="mini" @focus="getInputFocus($event)" @change="valChange($event, scope.$index, 'superheat', 0, 50)" :ref="'superheat' + scope.$index" @keyup.native.enter="focusNext('superheat', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template v-if="isEdit" slot="realmetalMass" slot-scope="scope">
        <el-input v-model="scope.row.realmetalMass" size="mini" :ref="'realmetalMass' + scope.$index" @keyup.native.enter="focusNext('realmetalMass', scope.$index)" onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1')" @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
    </table-real>
    <popup title="提示" :show.sync="delShow" :content="delContent" confirm-button-text="确 认" background="#FF645F" @toTrue="toTrue"></popup>
  </div>
</template>

<script>
import BarSimpleChart from '@/components/echarts/BarSimpleChart'
import TableReal from '@/components/table/TableReal'
import Popup from '@/components/popup/Popup'
import TabList from '@/components/tabs/TabList'
import TableToolbar from '@/components/table/toolbar'
import { factoryAreaAll, slotOffLineParam, slotOffLineParamEdit } from '@/api/real'
export default {
  name: "plan-feature",
  components: {
    BarSimpleChart,
    TableReal,
    Popup,
    TabList,
    TableToolbar
  },
  data() {
    return {
      loading: false,
      tableHeadersAll: [
        { prop: 'realSlotNo', label: '槽号' },
        { prop: 'metalMass', label: '出铝量' },
        { prop: 'metalHeight', label: '铝水高度' },
        { prop: 'bathHeight', label: '电解质高度' },
        { prop: 'bathTemperature', label: '槽温' },
        { prop: 'bathRatio', label: '分子比' },
        { prop: 'feContent', label: '铁含量' },
        { prop: 'siContent', label: '硅含量' },
        { prop: 'metalQuality', label: '质量' },
        { prop: 'al2o3Concen', label: '浓度' },
        { prop: 'cvd', label: '炉底降压' },
        { prop: 'acd', label: '极距' },
        { prop: 'superheat', label: '过热度' }
      ],
      // "realmetalMass": null // 实际出铝量
      tableHeaders: [],
      tableData: [],
      tableDataOld: [], // 修改之前的，用于校验
      startDate: '', // 日期时间
      tabList: [], // 车间分区
      delShow: false, // 确认修改
      delContent: '是否确认修改？',
      idx: '0', // 默认选中
      isEdit: false, // 是否可以修改
      isAluminum: false, // 实际出铝量
      renderComponent: true, // 组件是否展示
    }
  },
  mounted() {
    console.log(this.$store.state.user)
    const permissions = this.$store.state.user.permissions
    this.isEdit = (permissions.includes('*:*:*') || permissions.includes('metrical:data:edit')) ? Boolean(true) : Boolean(false)
    this.isAluminum = (permissions.includes('*:*:*') || permissions.includes('metrical:data:aluminum')) ? Boolean(true) : Boolean(false)
    let tableHeadersAll = this.tableHeadersAll
    if (this.isAluminum) {
      tableHeadersAll.push({ prop: 'realmetalMass', label: '实际出铝量' })
    }
    this.tableHeaders = tableHeadersAll
    this.getCurrentTime()
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
    // 监听回车事件
    focusNext(txt, idx) {
      const arr = this.tableHeaders.map(item => {
        return item.prop
      })
      if (arr.length === (arr.indexOf(txt) + 1) && idx === 28) {
        return false
      }
      const newIdx = idx === 28 ? 0 : (idx + 1)
      const ref = (idx === 28 ? arr[arr.indexOf(txt) + 1] : txt) + newIdx
      this.$refs[ref].focus()
    },

    getInputFocus(event) {
      event.currentTarget.select();
    },

    // 输入的值改变
    valChange(val, idx, txt, min, max) {
      const text = `输入范围${min}~${max}`;
      const oldVal = this.tableDataOld[idx][txt];
      if (parseFloat(val) < min || parseFloat(val) > max) {
        this.$message.warning(text);
        this.tableData[idx][txt] = oldVal;
      }
      // if (parseFloat(val) < min) {
      //   this.$message.warning(text);
      //   this.tableData[idx][txt] = min
      // }
      // if (parseFloat(val) > max) {
      //   this.$message.warning(text);
      //   this.tableData[idx][txt] = max
      // }
    },
    // 当前日期
    getCurrentTime() {
      let yy = new Date().getFullYear()
      let mm = new Date().getMonth()+1
      let dd = new Date().getDate()
      this.startDate = yy + '-' + (mm >= 10 ? mm : '0' + mm) + '-' + (dd >= 10 ? dd : '0' + dd)
    },
    // 改变表头
    changeColumn(data) {
      this.tableHeaders = data
    },
    // 切换车间
    handleClick(data) {
      this.idx = data
      this.baseInfo();
      this.renderComponent = false;

      this.$nextTick(() => {
        // 在 DOM 中添加 my-component 组件
        this.renderComponent = true;
      });
    },
    // 选择日期发生变化
    dateChange() {
      this.baseInfo() // 测量数据输入界面
    },
    // 确认修改
    toTrue() {
      let data = []
      this.tableData.forEach(item => {
        let list = {}
        const queryData = this.tableDataOld.filter(function (_data) {
          return item.realSlotNo === _data.realSlotNo
        })
        queryData[0].metalMass === item.metalMass ? '' : list['metalMass'] = item.metalMass // 出铝量
        queryData[0].metalHeight === item.metalHeight ? '' : list['metalHeight'] = item.metalHeight // 铝水高度
        queryData[0].bathHeight === item.bathHeight ? '' : list['bathHeight'] = item.bathHeight // 电解质高度
        queryData[0].bathTemperature === item.bathTemperature ? '' : list['bathTemperature'] = item.bathTemperature // 槽温
        queryData[0].bathRatio === item.bathRatio ? '' : list['bathRatio'] = item.bathRatio // 分子比
        queryData[0].feContent === item.feContent ? '' : list['feContent'] = item.feContent // 铁含量
        queryData[0].siContent === item.siContent ? '' : list['siContent'] = item.siContent // 硅含量
        queryData[0].metalQuality === item.metalQuality ? '' : list['metalQuality'] = item.metalQuality // 质量
        queryData[0].al2o3Concen === item.al2o3Concen ? '' : list['al2o3Concen'] = item.al2o3Concen // 浓度
        queryData[0].cvd === item.cvd ? '' : list['cvd'] = item.cvd // 炉底降压
        queryData[0].acd === item.acd ? '' : list['acd'] = item.acd // 极距
        queryData[0].superheat === item.superheat ? '' : list['superheat'] = item.superheat // 过热度
        queryData[0].realmetalMass === item.realmetalMass ? '' : list['realmetalMass'] = item.realmetalMass // 实际出铝量
        if ((Object.keys(list)).length !== 0) {
          list['slotNo'] = item.slotNo
          list['machineNo'] = item.machineNo
          list['realSlotNo'] = item.realSlotNo
          list['signTime'] = item.signTime
          data.push(list)
        }
      })
      if (data.length === 0) {
        this.$message.warning('暂无修改')
        this.delShow = false
        return false
      }
      slotOffLineParamEdit(data).then(res => {
        if (res.code === 200) {
          this.$message.success('修改成功')
          this.baseInfo() // 测量数据输入界面
        }
      }).finally(() => {
        this.delShow = false
      })
    },
    // 取消修改
    getDelShow() {
      this.baseInfo() // 测量数据输入界面
    },
    // 测量数据输入界面
    baseInfo() {
      const int = parseInt(this.idx)
      if (this.$refs.tableReal.$el.getElementsByClassName('changeSty').length !== 0) {
        const tableReal = this.$refs.tableReal.$el.getElementsByClassName('changeSty')
        for (let i = 0; i < tableReal.length; i++) {
          tableReal[0].className = 'el-input el-input--mini'
        }
      }
      const data = { signTime: this.startDate, areaNo: this.tabList[int].areaNo, factoryNo: this.tabList[int].factoryNo }
      slotOffLineParam(data).then(res => {
        if (res.code === 200) {
          this.tableData = res.rows.map(item => {
            return item
          })
          this.tableDataOld = JSON.parse(JSON.stringify(this.tableData));
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
    .options-menu {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      ::v-deep .el-button {
        margin-left: 10px;
      }
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
.changeSty ::v-deep .el-input__inner {
  color: red;
}
::v-deep .el-input--mini .el-input__inner {
  height: 25px;
  line-height: 25px;
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
