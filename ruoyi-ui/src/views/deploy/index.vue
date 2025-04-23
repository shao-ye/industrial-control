<template>
  <div class="container">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="查看工区" prop="look">
        <el-select v-model="ruleForm.look" size="small" multiple placeholder="请选择工区" @change="lookChange">
          <el-option
            v-for="item in workAreaList"
            :key="item.sectionName"
            :label="item.sectionName"
            :value="item.sectionName"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="刷新方式">
        <el-radio-group v-model="ruleForm.refreshStyle">
          <el-radio :label="1">只刷一个工区</el-radio>
          <el-radio :label="2">按车间刷（厂房）</el-radio>
          <el-radio :label="3">所有工区一起刷</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="ruleForm.refreshStyle === 1" label="工区" prop="refreshSectionFactoryNo">
        <el-select v-model="ruleForm.refreshSectionFactoryNo" size="small" placeholder="请选择工区">
          <el-option
            v-for="item in workAreaList2"
            :key="item.sectionName"
            :label="item.sectionName"
            :value="item.sectionName"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item v-if="ruleForm.refreshStyle === 2" label="厂房" prop="refreshPotroom">
        <el-select v-model="ruleForm.refreshPotroom" size="small" placeholder="请选择厂房">
          <el-option
            v-for="item in workAreaAll2"
            :key="item.factoryNo"
            :label="item.factoryName"
            :value="item.factoryNo"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" plain @click="submitForm('ruleForm')">保存</el-button>
        <el-button plain size="small" @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { refreshConfig, factoryAreaFactory, factoryAreaAll, refreshConfigEdit } from '@/api/real'
export default {
  data() {
    return {
      ruleForm: {
        refreshStyle: 1, // 刷新方式
        look: [], // 查看
        refreshSectionFactoryNo: '', // 工区
        refreshPotroom: '', // 厂房
      },
      workAreaList: [], // 工区集合
      workAreaAll: [], // 全部厂区集合
      workAreaList2: [], // 工区集合
      workAreaAll2: [], // 全部厂区集合
      rules: {
        look: [
          { required: true, message: '请选择查看的工区', trigger: 'change' }
        ],
        refreshSectionFactoryNo: [
          { required: true, message: '请选择工区', trigger: 'change' }
        ],
        refreshPotroom: [
          { required: true, message: '请选择厂房', trigger: 'change' }
        ]
      }
    };
  },
  mounted() {
    this.getRefreshConfig() // 配置信息查询
    this.getFactoryArea() // 厂区集合
    this.getFactoryAreaAll() // 获取全部厂区集合
  },
  methods: {
    // 配置信息查询
    getRefreshConfig() {
      refreshConfig().then(res => {
        if (res.code === 200) {
          this.ruleForm.refreshStyle = res.data.refreshStyle
          if (res.data.refreshStyle === 1) {
            const areaNo = res.data.refreshSectionAreaNo
            const factoryNo = res.data.refreshSectionFactoryNo
            for (let i = 0; i < this.workAreaList.length; i++) {
              const item = this.workAreaList[i]
              if (item.areaNo === areaNo && item.factoryNo === factoryNo) {
                this.ruleForm.refreshSectionFactoryNo = item.sectionName
              }
            }
          }
          let look = []
          res.data.factoryAreaList.forEach(item => {
            const factoryNo = item.factoryNo
            const areaNo = item.areaNo
            this.workAreaList.forEach(cItem => {
              if (factoryNo === cItem.factoryNo && areaNo === cItem.areaNo) {
                look.push(cItem.sectionName)
              }
            })
          })
          this.ruleForm.look = look
          this.lookChange()
          if (res.data.refreshStyle === 2) {
            this.ruleForm.refreshPotroom = res.data.refreshPotroom
          }
        }
      })
    },
    // 厂区集合
    getFactoryArea() {
      factoryAreaAll().then(res => {
        this.workAreaList = res.data
      })
    },
    // 获取全部厂区集合
    getFactoryAreaAll() {
      factoryAreaFactory().then(res => {
        this.workAreaAll = res.data
      })
    },
    // 改变查看的集合
    lookChange() {
      const look = this.ruleForm.look
      let arr = []
      let arr2 = []
      let arr3 = []
      this.workAreaList.forEach(item => {
        if (look.includes(item.sectionName)) {
          arr.push(item)
          if (arr2.indexOf(item.factoryNo) === -1) {
            arr2.push(item.factoryNo)
          }
        }
      })
      this.workAreaList2 = arr // 工区集合
      this.workAreaAll.forEach(item => {
        if (arr2.includes(item.factoryNo)) {
          arr3.push(item)
        }
      })
      this.workAreaAll2 = arr3 // 全部厂区集合
      this.ruleForm.refreshSectionFactoryNo = '' // 工区 m
      this.ruleForm.refreshPotroom = '' // 厂房
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.getRefreshConfigEdit()
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 保存按钮
    getRefreshConfigEdit() {
      let data = {}
      let form = this.ruleForm
      let factoryAreaList = []
      this.workAreaList.map(item => {
        if(form.look.includes(item.sectionName)) {
          factoryAreaList.push({ factoryNo: item.factoryNo, areaNo: item.areaNo })
        }
      })
      console.log(factoryAreaList)
      if (form.refreshStyle === 1) {
        let refreshSectionFactoryNo = ''
        let refreshSectionAreaNo = ''
        for (let i = 0; i < this.workAreaList.length; i++) {
          const item = this.workAreaList[i]
          if (item.sectionName === this.ruleForm.refreshSectionFactoryNo) {
            refreshSectionFactoryNo = item.factoryNo
            refreshSectionAreaNo = item.areaNo
          }
        }
        data = { refreshStyle: form.refreshStyle, refreshSectionFactoryNo: refreshSectionFactoryNo, refreshSectionAreaNo: refreshSectionAreaNo, factoryAreaList: factoryAreaList }
      } else if (form.refreshStyle === 2) {
        data = { refreshStyle: form.refreshStyle, refreshPotroom: form.refreshPotroom, factoryAreaList: factoryAreaList }
      } else {
        data = { refreshStyle: form.refreshStyle, factoryAreaList: factoryAreaList }
      }
      refreshConfigEdit(data).then(res => {
        if (res.code === 200) {
          this.$message.success('操作成功')
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped lang="scss">
.container {
  margin: auto;
  width: 80%;
  padding: 10vh 10% 40px 10%;
  .demo-ruleForm {
    ::v-deep .el-select {
      width: 500px;
    }
  }
}
</style>
