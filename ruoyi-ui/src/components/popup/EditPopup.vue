<template>
  <div class="popup-container">
    <el-dialog
      :show-close="false"
      :visible.sync="visible"
      :show="editShow"
      :close-on-click-modal="closeModal"
      :width="width"
      @close="$emit('update:editShow', false)"
      @closed="toClose"
    >
      <div class="popup-box">
        <div class="popup-title">
          {{ title }}
        </div>
        <div :class="['popup-content', needBorder ? '' : 'no-border']">
          <slot />
        </div>
        <div class="popup-close-icon" @mouseover="showIcon = true" @mouseleave="showIcon = false" @click="toClose">
          <div :style="{'color': showIcon ? '#1E9D93' : ''}">
            <i class="el-icon-close" />
          </div>
        </div>
      </div>
      <span v-if="footer" slot="footer" class="popup-footer">
        <el-button class="cancel-btn" @click="toClose">取 消</el-button>
        <el-button class="true-btn" @click="toTrueEdit">确 定</el-button>
      </span>
      <div v-else class="footer-box" />
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: 'EditPopup',
  props: {
    title: {
      type: String,
      default: '提示'
    },
    editShow: {
      type: Boolean,
      default: false
    },
    closeModal: {
      type: Boolean,
      default: true
    },
    needBorder: {
      type: Boolean,
      default: true
    },
    footer: {
      type: Boolean,
      default: true
    },
    cancel: {
      type: Boolean,
      default: true
    },
    width: {
      type: String,
      default: '386px'
    }
  },
  data() {
    return {
      visible: this.editShow,
      showIcon: false
    }
  },
  watch: {
    editShow() {
      this.visible = this.editShow
    }
  },
  methods: {
    toTrueEdit() {
      this.$emit('toTrueEdit')
    },
    toClose() {
      console.log(this.cancel)
      this.$emit('closed')
      this.cancel ? this.$emit('update:editShow', false) : this.$emit('editShow', false)
    }
  }
}
</script>
<style lang="scss" scoped>
  .popup-container{
    .footer-box{
      height: 16px;
    }
    ::v-deep .el-dialog {
      border-radius: 8px;
      min-width: 386px;
      max-width: 900px;
    }
    ::v-deep .el-dialog__header {
      padding: 0;
    }
    ::v-deep .el-dialog__body {
      padding: 16px;
    }
    .popup-box {
      padding: 16px 16px 0;
      position: relative;
      .popup-title {
        font-size: 16px;
        font-weight: 600;
        color: #1F2329;
      }
      .popup-content {
        border-radius: 4px;
        border: 1px solid #DFE0E3;
        font-size: 14px;
        font-weight: 400;
        color: #1F2329;
        margin-top: 24px;
      }
      .no-border {
        border: 0;
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
    }
    .popup-footer {
      .cancel-btn {
        width: 66px;
        height: 32px;
        text-align: center;
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
        padding: 0;
        font-size: 14px;
        font-weight: 400;
        color: #FFFFFF;
        border: 1px solid #1E9D93;
      }
    }
  }
</style>
