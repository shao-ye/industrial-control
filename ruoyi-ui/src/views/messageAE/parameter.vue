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
        <el-radio-group v-model="displayType" @change="displayChange">
          <el-radio :label="'commonParam'">常用参数</el-radio>
          <el-radio :label="'predeterminedArea'">设定范围</el-radio>
          <el-radio :label="'operationProcess'">特殊操作过程参数</el-radio>
          <el-radio :label="'concentrationParam'">浓度参数</el-radio>
          <el-radio :label="'performAction'">执行动作参数</el-radio>
          <el-radio :label="'controlSwitch'">控制开关</el-radio>
        </el-radio-group>
        <el-button type="primary" size="mini" @click="delShow = true">确认修改</el-button>
      </div>
    </div>
    <table-real
      v-loading="loading"
      ref="myTable"
      v-if="renderComponent"
      :border="false"
      :table-data="tableData"
      :table-headers="tableHeaders"
      :is-page="false"
      :is-selection="false"
      :height="'calc(100vh - 100px)'"
      @handleSelectionChange="handleSelectionChange"
    >
      <!--  常用参数    -->
      <template slot="volt" slot-scope="scope">
        <el-input v-model="scope.row.volt" size="mini"
                  :ref="'volt' + scope.$index" @keyup.native.enter="focusNext('volt', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'volt', 3600, 4800)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="feedIntvl0" slot-scope="scope">
        <el-input v-model="scope.row.feedIntvl0" size="mini"
                  :ref="'feedIntvl0' + scope.$index" @keyup.native.enter="focusNext('feedIntvl0', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'feedIntvl0', 600, 2000)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="aeIntvl" slot-scope="scope">
        <el-input v-model="scope.row.aeIntvl" size="mini"
                  :ref="'aeIntvl' + scope.$index" @keyup.native.enter="focusNext('aeIntvl', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'aeIntvl', 24, 1000)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="bvAngle" slot-scope="scope">
        <el-input v-model="scope.row.bvAngle" size="mini"
                  :ref="'bvAngle' + scope.$index" @keyup.native.enter="focusNext('bvAngle', scope.$index)"
                  @focus="getInputFocus($event)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d{0,4})?).*$/g, '$1')"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="alf3Mass" slot-scope="scope">
        <el-input v-model="scope.row.alf3Mass" size="mini"
                  :ref="'alf3Mass' + scope.$index" @keyup.native.enter="focusNext('alf3Mass', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'alf3Mass', 0, 200)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="alf3Intvl" slot-scope="scope">
        <el-input v-model="scope.row.alf3Intvl" size="mini"
                  :ref="'alf3Intvl' + scope.$index" @keyup.native.enter="focusNext('alf3Intvl', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'alf3Intvl', 10, 50)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="onOffPrestaSwitch" slot-scope="scope">
        <el-select v-model="scope.row.onOffPrestaSwitch" size="mini" placeholder="请选择">
          <el-option
            v-for="item in prestaList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </template>
      <!--  设定范围  -->
      <template slot="downResistRange1" slot-scope="scope">
        <el-input v-model="scope.row.downResistRange1" size="mini"
                  :ref="'downResistRange1' + scope.$index" @keyup.native.enter="focusNext('downResistRange1', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'downResistRange1', 10, 200)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="upResistRange1" slot-scope="scope">
        <el-input v-model="scope.row.upResistRange1" size="mini"
                  :ref="'upResistRange1' + scope.$index" @keyup.native.enter="focusNext('upResistRange1', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'upResistRange1', 10, 300)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="downResistRange0" slot-scope="scope">
        <el-input v-model="scope.row.downResistRange0" size="mini"
                  :ref="'downResistRange0' + scope.$index" @keyup.native.enter="focusNext('downResistRange0', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'downResistRange0', 10, 200)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="upResistRange0" slot-scope="scope">
        <el-input v-model="scope.row.upResistRange0" size="mini"
                  :ref="'upResistRange0' + scope.$index" @keyup.native.enter="focusNext('upResistRange0', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'upResistRange0', 10, 300)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="lowVoltageAlarm" slot-scope="scope">
        <el-input v-model="scope.row.lowVoltageAlarm" size="mini"
                  :ref="'lowVoltageAlarm' + scope.$index" @keyup.native.enter="focusNext('lowVoltageAlarm', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'lowVoltageAlarm', 3600, 4200)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="stopFeedCurrent0" slot-scope="scope">
        <el-input v-model="scope.row.stopFeedCurrent0" size="mini"
                  :ref="'stopFeedCurrent0' + scope.$index" @keyup.native.enter="focusNext('stopFeedCurrent0', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'stopFeedCurrent0', 2000, 2550)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="upCurrentRange0" slot-scope="scope">
        <el-input v-model="scope.row.upCurrentRange0" size="mini"
                  :ref="'upCurrentRange0' + scope.$index" @keyup.native.enter="focusNext('upCurrentRange0', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'upCurrentRange0', 50, 300)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="plCurrent0" slot-scope="scope">
        <el-input v-model="scope.row.plCurrent0" size="mini"
                  :ref="'plCurrent0' + scope.$index" @keyup.native.enter="focusNext('plCurrent0', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'plCurrent0', 2000, 2550)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="highVoltageAlarm" slot-scope="scope">
        <el-input v-model="scope.row.highVoltageAlarm" size="mini"
                  :ref="'highVoltageAlarm' + scope.$index" @keyup.native.enter="focusNext('highVoltageAlarm', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'highVoltageAlarm', 4300, 4800)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="downVoltageRange" slot-scope="scope">
        <el-input v-model="scope.row.downVoltageRange" size="mini"
                  :ref="'downVoltageRange' + scope.$index" @keyup.native.enter="focusNext('downVoltageRange', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'downVoltageRange', 3600, 3800)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="upVoltageRange" slot-scope="scope">
        <el-input v-model="scope.row.upVoltageRange" size="mini"
                  :ref="'upVoltageRange' + scope.$index" @keyup.native.enter="focusNext('upVoltageRange', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'upVoltageRange', 4000, 4800)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="downCurrentRange0" slot-scope="scope">
        <el-input v-model="scope.row.downCurrentRange0" size="mini"
                  :ref="'downCurrentRange0' + scope.$index" @keyup.native.enter="focusNext('downCurrentRange0', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'downCurrentRange0', 0, 300)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <!--  特殊操作过程参数    -->
      <template slot="processAcFeedTime" slot-scope="scope">
        <el-input v-model="scope.row.processAcFeedTime" size="mini"
                  :ref="'processAcFeedTime' + scope.$index" @keyup.native.enter="focusNext('processAcFeedTime', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'processAcFeedTime', 0, 120)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="processAcAddVolt" slot-scope="scope">
        <el-input v-model="scope.row.processAcAddVolt" size="mini"
                  :ref="'processAcAddVolt' + scope.$index" @keyup.native.enter="focusNext('processAcAddVolt', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'processAcAddVolt', 0, 300)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="processMetalFeedTime" slot-scope="scope">
        <el-input v-model="scope.row.processMetalFeedTime" size="mini"
                  :ref="'processMetalFeedTime' + scope.$index" @keyup.native.enter="focusNext('processMetalFeedTime', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'processMetalFeedTime', 0, 120)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="processMetalAddVolt" slot-scope="scope">
        <el-input v-model="scope.row.processMetalAddVolt" size="mini"
                  :ref="'processMetalAddVolt' + scope.$index" @keyup.native.enter="focusNext('processMetalAddVolt', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'processMetalAddVolt', 0, 200)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="processMetalAddVoltTime" slot-scope="scope">
        <el-input v-model="scope.row.processMetalAddVoltTime" size="mini"
                  :ref="'processMetalAddVoltTime' + scope.$index" @keyup.native.enter="focusNext('processMetalAddVoltTime', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'processMetalAddVoltTime', 0, 120)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="processAcAddVoltTime" slot-scope="scope">
        <el-input v-model="scope.row.processAcAddVoltTime" size="mini"
                  :ref="'processAcAddVoltTime' + scope.$index" @keyup.native.enter="focusNext('processAcAddVoltTime', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'processAcAddVoltTime', 0, 360)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="aeStartVolt0" slot-scope="scope">
        <el-input v-model="scope.row.aeStartVolt0" size="mini"
                  :ref="'aeStartVolt0' + scope.$index" @keyup.native.enter="focusNext('aeStartVolt0', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'aeStartVolt0', 6000, 10000)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="noiseAddMaxVolt" slot-scope="scope">
        <el-input v-model="scope.row.noiseAddMaxVolt" size="mini"
                  :ref="'noiseAddMaxVolt' + scope.$index" @keyup.native.enter="focusNext('noiseAddMaxVolt', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'noiseAddMaxVolt', 50, 300)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="addSideAddVoltTime" slot-scope="scope">
        <el-input v-model="scope.row.addSideAddVoltTime" size="mini"
                  :ref="'addSideAddVoltTime' + scope.$index" @keyup.native.enter="focusNext('addSideAddVoltTime', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'addSideAddVoltTime', 0, 240)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="noiseAddVoltTime" slot-scope="scope">
        <el-input v-model="scope.row.noiseAddVoltTime" size="mini"
                  :ref="'noiseAddVoltTime' + scope.$index" @keyup.native.enter="focusNext('noiseAddVoltTime', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'noiseAddVoltTime', 30, 360)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="processBrFeedTime" slot-scope="scope">
        <el-input v-model="scope.row.processBrFeedTime" size="mini"
                  :ref="'processBrFeedTime' + scope.$index" @keyup.native.enter="focusNext('processBrFeedTime', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'processBrFeedTime', 0, 240)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="addSideFeedTime" slot-scope="scope">
        <el-input v-model="scope.row.addSideFeedTime" size="mini"
                  :ref="'addSideFeedTime' + scope.$index" @keyup.native.enter="focusNext('addSideFeedTime', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'addSideFeedTime', 0, 240)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="noiseHigh" slot-scope="scope">
        <el-input v-model="scope.row.noiseHigh" size="mini"
                  :ref="'noiseHigh' + scope.$index" @keyup.native.enter="focusNext('noiseHigh', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'noiseHigh', 50, 300)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="addSideAddVolt" slot-scope="scope">
        <el-input v-model="scope.row.addSideAddVolt" size="mini"
                  :ref="'addSideAddVolt' + scope.$index" @keyup.native.enter="focusNext('addSideAddVolt', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'addSideAddVolt', 0, 200)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1'); if(value >= 200) value=200;"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="noiseLow" slot-scope="scope">
        <el-input v-model="scope.row.noiseLow" size="mini"
                  :ref="'noiseLow' + scope.$index" @keyup.native.enter="focusNext('noiseLow', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'noiseLow', 50, 300)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <!--  浓度参数    -->
      <template slot="adptUnderDur" slot-scope="scope">
        <el-input v-model="scope.row.adptUnderDur" size="mini"
                  :ref="'adptUnderDur' + scope.$index" @keyup.native.enter="focusNext('adptUnderDur', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'adptUnderDur', 10, 240)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="adptCycleCoef" slot-scope="scope">
        <el-input v-model="scope.row.adptCycleCoef" size="mini"
                  :ref="'adptCycleCoef' + scope.$index" @keyup.native.enter="focusNext('adptCycleCoef', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'adptCycleCoef', 10, 500)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="adptUnderCoef" slot-scope="scope">
        <el-input v-model="scope.row.adptUnderCoef" size="mini"
                  :ref="'adptUnderCoef' + scope.$index" @keyup.native.enter="focusNext('adptUnderCoef', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'adptUnderCoef', 105, 200)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="adptNormalDur" slot-scope="scope">
        <el-input v-model="scope.row.adptNormalDur" size="mini"
                  :ref="'adptNormalDur' + scope.$index" @keyup.native.enter="focusNext('adptNormalDur', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'adptNormalDur', 1, 30)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="adptOverDur" slot-scope="scope">
        <el-input v-model="scope.row.adptOverDur" size="mini"
                  :ref="'adptOverDur' + scope.$index" @keyup.native.enter="focusNext('adptOverDur', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'adptOverDur', 10, 60)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="adptOverCoef" slot-scope="scope">
        <el-input v-model="scope.row.adptOverCoef" size="mini"
                  :ref="'adptOverCoef' + scope.$index" @keyup.native.enter="focusNext('adptOverCoef', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'adptOverCoef', 55, 95)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <!--  执行动作参数    -->
      <template slot="shellFeedCount" slot-scope="scope">
        <el-input v-model="scope.row.shellFeedCount" size="mini"
                  :ref="'shellFeedCount' + scope.$index" @keyup.native.enter="focusNext('shellFeedCount', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'shellFeedCount', 1, 6)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="actionCrustTime1" slot-scope="scope">
        <el-input v-model="scope.row.actionCrustTime1" size="mini"
                  :ref="'actionCrustTime1' + scope.$index" @keyup.native.enter="focusNext('actionCrustTime1', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'actionCrustTime1', 1, 10)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="actionCrustTime2" slot-scope="scope">
        <el-input v-model="scope.row.actionCrustTime2" size="mini"
                  :ref="'actionCrustTime2' + scope.$index" @keyup.native.enter="focusNext('actionCrustTime2', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'actionCrustTime2', 1, 10)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <template slot="actionAl2o3FeedTime" slot-scope="scope">
        <el-input v-model="scope.row.actionAl2o3FeedTime" size="mini"
                  :ref="'actionAl2o3FeedTime' + scope.$index" @keyup.native.enter="focusNext('actionAl2o3FeedTime', scope.$index)"
                  @focus="getInputFocus($event)"
                  @change="valChange($event, scope.$index, 'actionAl2o3FeedTime', 1, 10)"
                  onkeyup="value=value.replace(/^\D*(\d*(?:\.\d)?).*$/g, '$1');"
                  @change.native="$event.currentTarget.className = 'changeSty el-input el-input--mini'"></el-input>
      </template>
      <!--  控制开关    -->
      <template slot="temperatureSwitch" slot-scope="scope">
        <el-switch v-model="scope.row.temperatureSwitch" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="noiseMonSwitch" slot-scope="scope">
        <el-switch v-model="scope.row.noiseMonSwitch" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="onOffLineSwitch" slot-scope="scope">
        <el-switch v-model="scope.row.onOffLineSwitch" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="adaptiveSwitch" slot-scope="scope">
        <el-switch v-model="scope.row.adaptiveSwitch" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="noiseSwitch" slot-scope="scope">
        <el-switch v-model="scope.row.noiseSwitch" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="alf3Switch" slot-scope="scope">
        <el-switch v-model="scope.row.alf3Switch" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="spareSwitch1" slot-scope="scope">
        <el-switch v-model="scope.row.spareSwitch1" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="spareSwitch2" slot-scope="scope">
        <el-switch v-model="scope.row.spareSwitch2" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="valveSwitch" slot-scope="scope">
        <el-switch v-model="scope.row.valveSwitch" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="aeCancelSwitch" slot-scope="scope">
        <el-switch v-model="scope.row.aeCancelSwitch" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
      <template slot="aeQuenchSwitch" slot-scope="scope">
        <el-switch v-model="scope.row.aeQuenchSwitch" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0" active-text="开" inactive-text="关"></el-switch>
      </template>
    </table-real>
    <popup title="提示" :show.sync="delShow" :content="delContent" confirm-button-text="确 认" background="#FF645F" @toTrue="toTrue"></popup>
  </div>
</template>

<script>
import TableReal from '@/components/table/TableReal'
import Popup from '@/components/popup/Popup'
import { factoryAreaAll } from '@/api/real'
import { slotSingleParam, slotSingleParamEdit } from '@/api/real/parameter'
export default {
  name: "Parameter",
  components: {
    Popup,
    TableReal
  },
  data() {
    return {
      loading: false,
      dateArr: [], // 选择的日期
      displayType: 'commonParam', // 厂房,工区,单槽
      district: '', // 选中的工区
      districtList: [],
      prestaList: [
        {
          label: '焙烧',
          value: 0
        },
        {
          label: '启动',
          value: 1
        },
        {
          label: '运行',
          value: 2
        },
        {
          label: '非生产',
          value: 4
        }
      ], // 常用参数 工作状态
      tableData: [],
      tableDataAll: [], // 所有的数据
      tableDataAllOld: [], // 所有的数据，原来的值
      headerList: {
        'operationProcess': [
          { prop: 'realSlotNo', label: '槽号', width: 60 },
          { prop: 'processAcFeedTime', label: '换极加料时间', minWidth: 120 },
          { prop: 'processAcAddVolt', label: '换极附加电压', minWidth: 120 },
          { prop: 'processMetalFeedTime', label: '出铝特殊加料时间', minWidth: 120 },
          { prop: 'processMetalAddVolt', label: '出铝附加电压', minWidth: 80 },
          { prop: 'processMetalAddVoltTime', label: '出铝附加电压时间', minWidth: 120 },
          { prop: 'processAcAddVoltTime', label: '换极电压时间', minWidth: 120 },
          { prop: 'aeStartVolt0', label: '效应判定电压', minWidth: 120 },
          { prop: 'noiseAddMaxVolt', label: '波动附加电压上限', minWidth: 120 },
          { prop: 'addSideAddVoltTime', label: '边加附加电压时间', minWidth: 120 },
          { prop: 'noiseAddVoltTime', label: '波动附加电压时间', minWidth: 120 },
          { prop: 'processBrFeedTime', label: '抬母线特殊加料时间', minWidth: 120 },
          { prop: 'addSideFeedTime', label: '边加加料时间', minWidth: 120 },
          { prop: 'noiseHigh', label: '波动判断值', minWidth: 120 },
          { prop: 'addSideAddVolt', label: '边加附加电压', minWidth: 120 },
          { prop: 'noiseLow', label: '槽平稳判断值', minWidth: 120 },
        ], // 特殊操作过程参数
        'performAction': [
          { prop: 'realSlotNo', label: '槽号', width: 60 },
          { prop: 'shellFeedCount', label: '打壳加料次数比', minWidth: 120 },
          { prop: 'actionCrustTime1', label: '打壳动作时间', minWidth: 120 },
          { prop: 'actionCrustTime2', label: '打加间隔时间', minWidth: 120 },
          { prop: 'actionAl2o3FeedTime', label: '加料动作时间', minWidth: 120 }
        ], // 执行动作参数
        'commonParam': [
          { prop: 'realSlotNo', label: '槽号', width: 60 },
          { prop: 'volt', label: '设定电压', minWidth: 120 },
          { prop: 'feedIntvl0', label: '设定NB', minWidth: 120 },
          { prop: 'aeIntvl', label: '效应间隔', minWidth: 120 },
          { prop: 'bvAngle', label: '设定RC脉冲值', minWidth: 120 },
          { prop: 'onOffPrestaSwitch', label: '工作状态', minWidth: 120 },
          { prop: 'alf3Mass', label: '氟盐量', minWidth: 120 },
          { prop: 'alf3Intvl', label: '氟盐料斗容量', minWidth: 120 }
        ], // 常用参数
        'concentrationParam': [
          { prop: 'realSlotNo', label: '槽号', width: 60 },
          { prop: 'adptUnderDur', label: '减量限定时间', minWidth: 120 },
          { prop: 'adptCycleCoef', label: '浓度系数', minWidth: 120 },
          { prop: 'adptUnderCoef', label: '减量系数', minWidth: 120 },
          { prop: 'adptNormalDur', label: '正常期限定时间', minWidth: 120 },
          { prop: 'adptOverDur', label: '增量限定时间', minWidth: 120 },
          { prop: 'adptOverCoef', label: '增量系数', minWidth: 120 }
        ], // 浓度参数

        'predeterminedArea': [
          { prop: 'realSlotNo', label: '槽号', width: 60 },
          { prop: 'downResistRange1', label: '小灵敏下限', minWidth: 120 },
          { prop: 'upResistRange1', label: '小灵敏上限', minWidth: 120 },
          { prop: 'downResistRange0', label: '大灵敏下限', minWidth: 120 },
          { prop: 'upResistRange0', label: '大灵敏上限', minWidth: 120 },
          { prop: 'lowVoltageAlarm', label: '电压低报警值', minWidth: 120 },
          { prop: 'stopFeedCurrent0', label: '停NB电流', minWidth: 120 },
          { prop: 'upCurrentRange0', label: '设定电流上限', minWidth: 120 },
          { prop: 'plCurrent0', label: '设定电流', minWidth: 120 },
          { prop: 'highVoltageAlarm', label: '电压高报警值', minWidth: 120 },
          { prop: 'downVoltageRange', label: 'RC电压下限', minWidth: 120 },
          { prop: 'upVoltageRange', label: 'RC电压上限', minWidth: 120 },
          { prop: 'downCurrentRange0', label: '设定电流下限', minWidth: 120 }
        ], // 设定范围

        'controlSwitch': [
          { prop: 'realSlotNo', label: '槽号', width: 60 },
          { prop: 'temperatureSwitch', label: '温度开关', minWidth: 100 },
          { prop: 'noiseMonSwitch', label: '波动跟踪开关', minWidth: 100 },
          { prop: 'onOffLineSwitch', label: '联机开关', minWidth: 100 },
          { prop: 'adaptiveSwitch', label: '自适应开关', minWidth: 100 },
          { prop: 'noiseSwitch', label: '波动控制开关', minWidth: 100 },
          { prop: 'alf3Switch', label: '氟盐开关', minWidth: 100 },
          { prop: 'spareSwitch1', label: '电流检修开关', minWidth: 100 },
          { prop: 'spareSwitch2', label: '停加料控制开关', minWidth: 100 },
          { prop: 'valveSwitch', label: 'RC脉冲复位开关', minWidth: 100 },
          { prop: 'aeCancelSwitch', label: 'AEB开关', minWidth: 100 },
          { prop: 'aeQuenchSwitch', label: 'AE熄灭开关', minWidth: 100 }
        ], // 控制开关

      }, // 所有的标头
      delShow: false, // 确认修改
      delContent: '是否确认修改？',
      tableHeaders: [],
      renderComponent: true
    }
  },
  mounted() {
    this.tableHeaders = this.headerList[this.displayType]
    this.getFactoryAreaAll() // 获取全部厂区集合
  },
  methods: {
    // 监听回车事件
    focusNext(txt, idx) {
      const arr = []
      this.tableHeaders.forEach(item => {
        if (item.prop !== 'onOffPrestaSwitch') {
          arr.push(item.prop)
        }
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
      const text = `输入范围${min}~${max}`
      const oldVal = this.tableDataAllOld[this.displayType][idx][txt];
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
    // 获取全部厂区集合
    getFactoryAreaAll() {
      factoryAreaAll().then(res => {
        this.districtList = res.data
        this.district = res.data[0].sectionName // 选中的工区
      }).finally(() => {
        this.baseInfo() // 当前页面数据
      })
    },
    // 确认修改
    toTrue() {
      let data = []
      // 输入后
      const allList = this.tableDataAll
      const attr1 = allList['commonParam']
      const attr2 = allList['operationProcess']
      const attr3 = allList['performAction']
      const attr4 = allList['concentrationParam']
      const attr5 = allList['predeterminedArea']
      const attr6 = allList['controlSwitch']

      // 原来的值
      const allListOld = this.tableDataAllOld
      const attrOld1 = allListOld['commonParam']
      const attrOld2 = allListOld['operationProcess']
      const attrOld3 = allListOld['performAction']
      const attrOld4 = allListOld['concentrationParam']
      const attrOld5 = allListOld['predeterminedArea']
      const attrOld6 = allListOld['controlSwitch']

      attr1.forEach(item => {
        let list = {}
        const queryData = attr2.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })
        const queryData1 = attr3.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })
        const queryData2 = attr4.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })
        const queryData3 = attr5.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })
        const queryData4 = attr6.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })

        const queryDataOld = attrOld1.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })
        const queryDataOld1 = attrOld2.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })
        const queryDataOld2 = attrOld3.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })
        const queryDataOld3 = attrOld4.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })
        const queryDataOld4 = attrOld5.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })
        const queryDataOld5 = attrOld6.filter(function (_data) {
          return _data.realSlotNo === item.realSlotNo
        })

        const commonParam = {} // 常用参数
        queryDataOld[0].volt === item.volt ? '' : commonParam['volt'] = { 'value': item.volt, 'name': '设定电压' } // 设定电压
        queryDataOld[0].feedIntvl0 === item.feedIntvl0 ? '' : commonParam['feedIntvl0'] = { 'value': item.feedIntvl0, 'name': '设定NB' } // 设定NB
        queryDataOld[0].aeIntvl === item.aeIntvl ? '' : commonParam['aeIntvl'] = { 'value': item.aeIntvl, 'name': '效应间隔' } // 效应间隔
        queryDataOld[0].bvAngle === item.bvAngle ? '' : commonParam['bvAngle'] = { 'value': item.bvAngle, 'name': '设定RC脉冲值' } // 设定RC脉冲值
        queryDataOld[0].onOffPrestaSwitch === item.onOffPrestaSwitch ? '' : commonParam['onOffPrestaSwitch'] = { 'value': item.onOffPrestaSwitch, 'name': '工作状态' } // 工作状态
        queryDataOld[0].alf3Mass === item.alf3Mass ? '' : commonParam['alf3Mass'] = { 'value': item.alf3Mass, 'name': '氟盐量' } // 氟盐量
        queryDataOld[0].alf3Intvl === item.alf3Intvl ? '' : commonParam['alf3Intvl'] = { 'value': item.alf3Intvl, 'name': '氟盐料斗容量' } // 氟盐料斗容量
        if ((Object.keys(commonParam)).length !== 0) {
          list['commonParam'] = commonParam
        }

        // 特殊操作过程参数
        const operationProcess = {}
        queryData[0].processAcFeedTime === queryDataOld1[0].processAcFeedTime ? '' : operationProcess['processAcFeedTime'] = { 'value': queryData[0].processAcFeedTime, 'name': '换极加料时间' } // 换极加料时间
        queryData[0].processAcAddVolt === queryDataOld1[0].processAcAddVolt ? '' : operationProcess['processAcAddVolt'] = { 'value': queryData[0].processAcAddVolt, 'name': '换极附加电压' } // 换极附加电压
        queryData[0].processMetalFeedTime === queryDataOld1[0].processMetalFeedTime ? '' : operationProcess['processMetalFeedTime'] = { 'value': queryData[0].processMetalFeedTime, 'name': '出铝特殊加料时间' } // 出铝特殊加料时间
        queryData[0].processMetalAddVolt === queryDataOld1[0].processMetalAddVolt ? '' : operationProcess['processMetalAddVolt'] = { 'value': queryData[0].processMetalAddVolt, 'name': '出铝附加电压' } // 出铝附加电压
        queryData[0].processMetalAddVoltTime === queryDataOld1[0].processMetalAddVoltTime ? '' : operationProcess['processMetalAddVoltTime'] = { 'value': queryData[0].processMetalAddVoltTime, 'name': '出铝附加电压时间' } // 出铝附加电压时间
        queryData[0].processAcAddVoltTime === queryDataOld1[0].processAcAddVoltTime ? '' : operationProcess['processAcAddVoltTime'] = { 'value': queryData[0].processAcAddVoltTime, 'name': '换极电压时间' } // 换极电压时间
        queryData[0].aeStartVolt0 === queryDataOld1[0].aeStartVolt0 ? '' : operationProcess['aeStartVolt0'] = { 'value': queryData[0].aeStartVolt0, 'name': '效应判定电压' } // 效应判定电压
        queryData[0].noiseAddMaxVolt === queryDataOld1[0].noiseAddMaxVolt ? '' : operationProcess['noiseAddMaxVolt'] ={ 'value': queryData[0].noiseAddMaxVolt, 'name': '波动附加电压上限' } // 波动附加电压上限
        queryData[0].addSideAddVoltTime === queryDataOld1[0].addSideAddVoltTime ? '' : operationProcess['addSideAddVoltTime'] = { 'value': queryData[0].addSideAddVoltTime, 'name': '边加附加电压时间' } // 边加附加电压时间
        queryData[0].noiseAddVoltTime === queryDataOld1[0].noiseAddVoltTime ? '' : operationProcess['noiseAddVoltTime'] = { 'value': queryData[0].noiseAddVoltTime, 'name': '波动附加电压时间' } // 波动附加电压时间
        queryData[0].processBrFeedTime === queryDataOld1[0].processBrFeedTime ? '' : operationProcess['processBrFeedTime'] = { 'value': queryData[0].processBrFeedTime, 'name': '抬母线特殊加料时间' } // 抬母线特殊加料时间
        queryData[0].addSideFeedTime === queryDataOld1[0].addSideFeedTime ? '' : operationProcess['addSideFeedTime'] = { 'value': queryData[0].addSideFeedTime, 'name': '边加加料时间' } // 边加加料时间
        queryData[0].noiseHigh === queryDataOld1[0].noiseHigh ? '' : operationProcess['noiseHigh'] = { 'value': queryData[0].noiseHigh, 'name': '波动判断值' } // 波动判断值
        queryData[0].addSideAddVolt === queryDataOld1[0].addSideAddVolt ? '' : operationProcess['addSideAddVolt'] = { 'value': queryData[0].addSideAddVolt, 'name': '边加附加电压' } // 边加附加电压
        queryData[0].noiseLow === queryDataOld1[0].noiseLow ? '' : operationProcess['noiseLow'] = { 'value': queryData[0].noiseLow, 'name': '槽平稳判断值' } // 槽平稳判断值
        if ((Object.keys(operationProcess)).length !== 0) {
          list['operationProcess'] = operationProcess
        }

        // 执行动作参数
        const performAction = {}
        queryData1[0].shellFeedCount === queryDataOld2[0].shellFeedCount ? '' : performAction['shellFeedCount'] = { 'value': queryData1[0].shellFeedCount, 'name': '打壳加料次数比' } // 打壳加料次数比
        queryData1[0].actionCrustTime1 === queryDataOld2[0].actionCrustTime1 ? '' : performAction['actionCrustTime1'] = { 'value': queryData1[0].actionCrustTime1, 'name': '打壳动作时间' } // 打壳动作时间
        queryData1[0].actionCrustTime2 === queryDataOld2[0].actionCrustTime2 ? '' : performAction['actionCrustTime2'] = { 'value': queryData1[0].actionCrustTime2, 'name': '打加间隔时间' } // 打加间隔时间
        queryData1[0].actionAl2o3FeedTime === queryDataOld2[0].actionAl2o3FeedTime ? '' : performAction['actionAl2o3FeedTime'] = { 'value': queryData1[0].actionAl2o3FeedTime, 'name': '加料动作时间' } // 加料动作时间
        if ((Object.keys(performAction)).length !== 0) {
          list['performAction'] = performAction
        }

        // 浓度参数
        const concentrationParam = {}
        queryData2[0].adptUnderDur === queryDataOld3[0].adptUnderDur ? '' : concentrationParam['adptUnderDur'] = { 'value': queryData2[0].adptUnderDur, 'name': '减量限定时间' } // 减量限定时间
        queryData2[0].adptCycleCoef === queryDataOld3[0].adptCycleCoef ? '' : concentrationParam['adptCycleCoef'] = { 'value': queryData2[0].adptCycleCoef, 'name': '浓度系数' } // 浓度系数
        queryData2[0].adptUnderCoef === queryDataOld3[0].adptUnderCoef ? '' : concentrationParam['adptUnderCoef'] = { 'value': queryData2[0].adptUnderCoef, 'name': '减量系数' } // 减量系数
        queryData2[0].adptNormalDur === queryDataOld3[0].adptNormalDur ? '' : concentrationParam['adptNormalDur'] = { 'value': queryData2[0].adptNormalDur, 'name': '正常期限定时间' } // 正常期限定时间
        queryData2[0].adptOverDur === queryDataOld3[0].adptOverDur ? '' : concentrationParam['adptOverDur'] = { 'value': queryData2[0].adptOverDur, 'name': '增量限定时间' } // 增量限定时间
        queryData2[0].adptOverCoef === queryDataOld3[0].adptOverCoef ? '' : concentrationParam['adptOverCoef'] = { 'value': queryData2[0].adptOverCoef, 'name': '增量系数' } // 增量系数
        if ((Object.keys(concentrationParam)).length !== 0) {
          list['concentrationParam'] = concentrationParam
        }

        // 设定电流下限
        const predeterminedArea = {}
        queryData3[0].downResistRange1 === queryDataOld4[0].downResistRange1 ? '' : predeterminedArea['downResistRange1'] = { 'value': queryData3[0].downResistRange1, 'name': '小灵敏下限' } // 小灵敏下限
        queryData3[0].upResistRange1 === queryDataOld4[0].upResistRange1 ? '' : predeterminedArea['upResistRange1'] = { 'value': queryData3[0].upResistRange1, 'name': '小灵敏上限' } // 小灵敏上限
        queryData3[0].downResistRange0 === queryDataOld4[0].downResistRange0 ? '' : predeterminedArea['downResistRange0'] = { 'value': queryData3[0].downResistRange0, 'name': '大灵敏下限' } // 大灵敏下限
        queryData3[0].upResistRange0 === queryDataOld4[0].upResistRange0 ? '' : predeterminedArea['upResistRange0'] = { 'value': queryData3[0].upResistRange0, 'name': '大灵敏上限' } // 大灵敏上限
        queryData3[0].lowVoltageAlarm === queryDataOld4[0].lowVoltageAlarm ? '' : predeterminedArea['lowVoltageAlarm'] = { 'value': queryData3[0].lowVoltageAlarm, 'name': '电压低报警值' } // 电压低报警值
        queryData3[0].stopFeedCurrent0 === queryDataOld4[0].stopFeedCurrent0 ? '' : predeterminedArea['stopFeedCurrent0'] = { 'value': queryData3[0].stopFeedCurrent0, 'name': '停NB电流' } // 停NB电流
        queryData3[0].upCurrentRange0 === queryDataOld4[0].upCurrentRange0 ? '' : predeterminedArea['upCurrentRange0'] = { 'value': queryData3[0].upCurrentRange0, 'name': '设定电流上限' } // 设定电流上限
        queryData3[0].plCurrent0 === queryDataOld4[0].plCurrent0 ? '' : predeterminedArea['plCurrent0'] = { 'value': queryData3[0].plCurrent0, 'name': '设定电流' } // 设定电流
        queryData3[0].highVoltageAlarm === queryDataOld4[0].highVoltageAlarm ? '' : predeterminedArea['highVoltageAlarm'] = { 'value': queryData3[0].highVoltageAlarm, 'name': '电压高报警值' } // 电压高报警值
        queryData3[0].downVoltageRange === queryDataOld4[0].downVoltageRange ? '' : predeterminedArea['downVoltageRange'] = { 'value': queryData3[0].downVoltageRange, 'name': 'RC电压下限' } // RC电压下限
        queryData3[0].upVoltageRange === queryDataOld4[0].upVoltageRange ? '' : predeterminedArea['upVoltageRange'] = { 'value': queryData3[0].upVoltageRange, 'name': 'RC电压上限' } // RC电压上限
        queryData3[0].downCurrentRange0 === queryDataOld4[0].downCurrentRange0 ? '' : predeterminedArea['downCurrentRange0'] = { 'value': queryData3[0].downCurrentRange0, 'name': '设定电流下限' } // 设定电流下限
        if ((Object.keys(predeterminedArea)).length !== 0) {
          list['predeterminedArea'] = predeterminedArea
        }

        // 控制开关
        const controlSwitch = {}
        queryData4[0].temperatureSwitch === queryDataOld5[0].temperatureSwitch ? '' : controlSwitch['temperatureSwitch'] = { 'value': queryData4[0].temperatureSwitch, 'name': '温度开关' } // 温度开关
        queryData4[0].noiseMonSwitch === queryDataOld5[0].noiseMonSwitch ? '' : controlSwitch['noiseMonSwitch'] = { 'value': queryData4[0].noiseMonSwitch, 'name': '波动跟踪开关' } // 波动跟踪开关
        queryData4[0].onOffLineSwitch === queryDataOld5[0].onOffLineSwitch ? '' : controlSwitch['onOffLineSwitch'] = { 'value': queryData4[0].onOffLineSwitch, 'name': '联机开关' } // 联机开关
        queryData4[0].adaptiveSwitch === queryDataOld5[0].adaptiveSwitch ? '' : controlSwitch['adaptiveSwitch'] = { 'value': queryData4[0].adaptiveSwitch, 'name': '自适应开关' } // 自适应开关
        queryData4[0].noiseSwitch === queryDataOld5[0].noiseSwitch ? '' : controlSwitch['noiseSwitch'] = { 'value': queryData4[0].noiseSwitch, 'name': '波动控制开关' } // 波动控制开关
        queryData4[0].alf3Switch === queryDataOld5[0].alf3Switch ? '' : controlSwitch['alf3Switch'] = { 'value': queryData4[0].alf3Switch, 'name': '氟盐开关' } // 氟盐开关
        queryData4[0].spareSwitch1 === queryDataOld5[0].spareSwitch1 ? '' : controlSwitch['spareSwitch1'] = { 'value': queryData4[0].spareSwitch1, 'name': '电流检修开关' } // 电流检修开关
        queryData4[0].spareSwitch2 === queryDataOld5[0].spareSwitch2 ? '' : controlSwitch['spareSwitch2'] = { 'value': queryData4[0].spareSwitch2, 'name': '停加料控制开关' } // 停加料控制开关
        queryData4[0].valveSwitch === queryDataOld5[0].valveSwitch ? '' : controlSwitch['valveSwitch'] = { 'value': queryData4[0].valveSwitch, 'name': 'RC脉冲复位开关' } // RC脉冲复位开关
        queryData4[0].aeCancelSwitch === queryDataOld5[0].aeCancelSwitch ? '' : controlSwitch['aeCancelSwitch'] = { 'value': queryData4[0].aeCancelSwitch, 'name': 'AEB开关' } // AEB开关
        queryData4[0].aeQuenchSwitch === queryDataOld5[0].aeQuenchSwitch ? '' : controlSwitch['aeQuenchSwitch'] = { 'value': queryData4[0].aeQuenchSwitch, 'name': 'AE熄灭开关' } // AE熄灭开关
        if ((Object.keys(controlSwitch)).length !== 0) {
          list['controlSwitch'] = controlSwitch
        }

        if ((Object.keys(list)).length !== 0) {
          list['realSlotNo'] = item.realSlotNo
          data.push(list)
        }
        // data.push({
        //   'realSlotNo': item.realSlotNo,
        //   'commonParam': commonParam,
        //   'operationProcess': operationProcess,
        //   'performAction': performAction,
        //   'concentrationParam': concentrationParam,
        //   'predeterminedArea': predeterminedArea,
        //   'controlSwitch': controlSwitch
        // })
      })
      if (data.length === 0) {
        this.$message.warning('暂无修改')
        this.delShow = false
        return false
      }
      let dataList = {}
      for (let i = 0; i < this.districtList.length; i++) {
        const item = this.districtList[i]
        if (item.sectionName === this.district) {
          dataList = { factoryNo: item.factoryNo, areaNo: item.areaNo, data: data }
        }
      }
      slotSingleParamEdit(dataList).then(res => {
        if (res.code === 200) {
          this.$message.success(res.msg)
          this.tableDataAll = []
          this.baseInfo() // 测量数据输入界面
        }
      }).finally(() => {
        this.delShow = false
      })
    },
    // 切换车间
    handleClick() {
      this.baseInfo()
      debugger
      this.renderComponent = false;

      this.$nextTick(() => {
        // 在 DOM 中添加 my-component 组件
        this.renderComponent = true;
      });
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
      slotSingleParam(data).then(res => {
        this.tableDataAll = res.data
        this.tableDataAllOld = JSON.parse(JSON.stringify(this.tableDataAll));
        this.tableData = res.data[this.displayType]
      })
    },
    // 显示类型改变
    displayChange() {
      this.tableData = []
      this.tableData = this.tableDataAll[this.displayType]
      this.tableHeaders = this.headerList[this.displayType]
      this.renderComponent = false;

      this.$nextTick(() => {
        // 在 DOM 中添加 my-component 组件
        this.renderComponent = true;
      });
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
}
::v-deep .el-input--mini .el-input__inner {
  height: 25px;
  line-height: 25px;
}
.changeSty ::v-deep .el-input__inner {
  color: red;
}
</style>
