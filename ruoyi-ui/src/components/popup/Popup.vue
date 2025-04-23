<template>
  <div class="popup-container">
    <el-dialog
      :show-close="false"
      :visible.sync="visible"
      :show="show"
      width="600px"
      @close="$emit('update:show', false)"
    >
      <div class="popup-box">
        <div class="popup-title">
          {{ title }}
        </div>
        <div class="popup-content" v-html="content" />
        <div class="popup-close-icon" @mouseover="showIcon = true" @mouseleave="showIcon = false" @click="toClose">
          <div :style="{'color': showIcon ? '#1E9D93' : ''}">
            <i class="el-icon-close" />
          </div>
        </div>
        <div v-if="checkboxShow" class="popup-checkbox">
          <el-checkbox v-model="checked" @change="handleChecked">
            {{ checkboxContent }}
          </el-checkbox>
        </div>
      </div>
      <span v-if="footer" slot="footer" class="popup-footer">
        <el-button class="cancel-btn" @click="toClose">取 消</el-button>
        <el-button class="true-btn" :style="{ background }" @click="toTrue">{{ confirmButtonText }}</el-button>
      </span>
      <div v-else class="footer-box" />
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'Popup',
  props: {
    title: {
      type: String,
      default: '提示'
    },
    content: {
      type: String,
      default: '启用服务后，租户将可继续使用应用服务，请确认是否启用？'
    },
    checkboxContent: {
      type: String,
      default: '通知店长'
    },
    checkboxShow: {
      type: Boolean,
      default: false
    },
    confirmButtonText: {
      type: String,
      default: '确 定'
    },
    show: {
      type: Boolean,
      default: false
    },
    background: {
      type: String,
      default: ''
    },
    footer: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      visible: this.show,
      showIcon: false,
      checked: true
    }
  },
  watch: {
    show() {
      this.visible = this.show
    }
  },
  methods: {
    toTrue() {
      this.$emit('toTrue')
    },
    toClose() {
      this.$emit('update:show', false)
    },
    handleChecked(val) {
      this.$emit('handleChecked', val)
    }
  }
}
</script>
<style lang="scss" scoped>
  .popup-container{
    .footer-box{
      height: 48px;
    }
    ::v-deep .el-dialog {
      border-radius: 8px;
      min-width: 600px;
      max-width: 900px;
    }
    ::v-deep .el-dialog__header {
      padding: 0;
    }
    ::v-deep .el-dialog__body {
      padding: 16px;
    }
    .popup-box {
      padding-left: 16px;
      position: relative;
      .popup-title {
        padding: 16px 0 0 0;
        font-size: 16px;
        font-weight: 600;
        color: #1F2329;
      }
      .popup-content {
        font-size: 14px;
        font-weight: 400;
        color: #1F2329;
        margin-top: 24px;
      }
      .popup-close-icon {
        font-size: 20px;
        text-align: center;
        position: absolute;
        top: 0;
        right: 0;
        width: 24px;
        height: 24px;
        line-height: 24px;
        cursor: pointer;
      }
      .popup-close-icon:hover {
        width: 24px;
        height: 24px;
        line-height: 24px;
        background: rgba(30,157,147,0.08);
        border-radius: 4px;
        text-align: center;
      }
      .popup-checkbox{
        margin-top: 20px;
      }
    }
    .popup-footer {
      .cancel-btn {
        width: 66px;
        height: 32px;
        text-align: center;
        line-height: 32px;
        padding: 0;
        background: #FFFFFF;
        border-radius: 4px;
        border: 1px solid #DFE0E3;
        font-size: 14px;
        font-weight: 400;
        color: #1F2329;
      }
      .true-btn {
        width: 66px;
        height: 32px;
        background: #1E9D93;
        border-radius: 4px;
        text-align: center;
        line-height: 32px;
        padding: 0;
        font-size: 14px;
        font-weight: 400;
        color: #FFFFFF;
        border: none;
      }
      ::v-deep .el-button:active {
        border-color: transparent;
      }
    }
  }
</style>
