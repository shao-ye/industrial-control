<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="厂房号" prop="factoryNo">
        <el-input
          v-model="queryParams.factoryNo"
          placeholder="请输入厂房号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="厂区号" prop="areaNo">
        <el-input
          v-model="queryParams.areaNo"
          placeholder="请输入厂区号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="厂区名称" prop="sectionName">
        <el-input
          v-model="queryParams.sectionName"
          placeholder="请输入厂区名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始槽号" prop="startRealSlotNo">
        <el-input
          v-model="queryParams.startRealSlotNo"
          placeholder="请输入开始槽号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结束槽号" prop="endRealSlotNo">
        <el-input
          v-model="queryParams.endRealSlotNo"
          placeholder="请输入结束槽号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['industry:factoryArea:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['industry:factoryArea:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['industry:factoryArea:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['industry:factoryArea:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="factoryAreaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--<el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="厂房号" align="center" prop="factoryNo" />
      <el-table-column label="厂区号" align="center" prop="areaNo" />
      <el-table-column label="厂区名称" align="center" prop="sectionName" />
      <el-table-column label="开始槽号" align="center" prop="startRealSlotNo" />
      <el-table-column label="结束槽号" align="center" prop="endRealSlotNo" />
      <!--<el-table-column label="状态" align="center" prop="status" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['industry:factoryArea:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['industry:factoryArea:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改厂区对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="厂房号" prop="factoryNo">
          <el-input v-model="form.factoryNo" placeholder="请输入厂房号" />
        </el-form-item>
        <el-form-item label="厂区号" prop="areaNo">
          <el-input v-model="form.areaNo" placeholder="请输入厂区号" />
        </el-form-item>
        <el-form-item label="厂区名称" prop="sectionName">
          <el-input v-model="form.sectionName" placeholder="请输入厂区名称" />
        </el-form-item>
        <el-form-item label="开始槽号" prop="startRealSlotNo">
          <el-input v-model="form.startRealSlotNo" placeholder="请输入开始槽号" />
        </el-form-item>
        <el-form-item label="结束槽号" prop="endRealSlotNo">
          <el-input v-model="form.endRealSlotNo" placeholder="请输入结束槽号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFactoryArea, getFactoryArea, delFactoryArea, addFactoryArea, updateFactoryArea } from "@/api/industry/factoryArea";

export default {
  name: "FactoryArea",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 厂区表格数据
      factoryAreaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        factoryNo: null,
        areaNo: null,
        sectionName: null,
        startRealSlotNo: null,
        endRealSlotNo: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询厂区列表 */
    getList() {
      this.loading = true;
      listFactoryArea(this.queryParams).then(response => {
        this.factoryAreaList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        factoryNo: null,
        areaNo: null,
        sectionName: null,
        startRealSlotNo: null,
        endRealSlotNo: null,
        status: 0,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加厂区";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFactoryArea(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改厂区";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFactoryArea(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFactoryArea(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除厂区编号为"' + ids + '"的数据项？').then(function() {
        return delFactoryArea(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('industry/factoryArea/export', {
        ...this.queryParams
      }, `factoryArea_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
