<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane v-for="(item, index) in tabList" :key="index" :label="item.sectionName" :name="item.value"></el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  name: "TabList",
  props: {
    tabList: {
      type: Array,
      default: () => []
    },
    defaultActive: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      activeName: '1'
    }
  },
  watch: {
    defaultActive: {
      handler(val) {
        this.activeName = val
      }
    },
    immediate:true,
    deep: true
  },
  methods: {
    handleClick(tab) {
      this.$emit('handleClick', tab.index)
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep .el-tabs__item {
  height: 20px;
  line-height: 20px;
}
::v-deep .el-tabs__header {
  margin: 0;
}
</style>
