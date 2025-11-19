<script setup>
import { ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import { reactive } from 'vue'

const tableData = [
  {date: '2016-05-03',name: 'Tom',address: 'No. 189, Grove St, Los Angeles',},
  {date: '2016-05-02',name: 'Tom',address: 'No. 189, Grove St, Los Angeles',},
  {date: '2016-05-04',name: 'Tom',address: 'No. 189, Grove St, Los Angeles',},
  {date: '2016-05-01',name: 'Tom',address: 'No. 189, Grove St, Los Angeles',},
]

// 分页
const currentPage4 = ref(1)
const pageSize4 = ref(100)
const size = ref('default')
const background = ref(false)
const disabled = ref(false)

const handleSizeChange = (val) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val) => {
  console.log(`current page: ${val}`)
}
// 对话框
const dialogVisible = ref(false)

const handleClose = (done) => {
  ElMessageBox.confirm('Are you sure to close this dialog?')
    .then(() => {
      done()
    })
    .catch(() => {
      // catch error
    })
}
//表单
const formInline = reactive({
  name: '',
  gender: '',
  date: '',
})

const onSubmit = () => {
  console.log('submit!', formInline)
}
</script>

<template>
    <!-- 按钮 -->
 <div class="study">
    <div class="example">
      <el-button>Default</el-button>
      <el-button type="primary">Primary</el-button>
      <el-button type="success">Success</el-button>
      <el-button type="info">Info</el-button>
      <el-button type="warning">Warning</el-button>
      <el-button type="danger">Danger</el-button>
    </div>

    <div class="example">
      <el-button plain>Plain</el-button>
      <el-button type="primary" plain>Primary</el-button>
      <el-button type="success" plain>Success</el-button>
      <el-button type="info" plain>Info</el-button>
      <el-button type="warning" plain>Warning</el-button>
      <el-button type="danger" plain>Danger</el-button>
    </div>

    <div class="example">
      <el-button round>Round</el-button>
      <el-button type="primary" round>Primary</el-button>
      <el-button type="success" round>Success</el-button>
      <el-button type="info" round>Info</el-button>
      <el-button type="warning" round>Warning</el-button>
      <el-button type="danger" round>Danger</el-button>
    </div>

    <div class="example">
      <el-button :icon="Search" circle />
      <el-button type="primary" :icon="Edit" circle />
      <el-button type="success" :icon="Check" circle />
      <el-button type="info" :icon="Message" circle />
      <el-button type="warning" :icon="Star" circle />
      <el-button type="danger" :icon="Delete" circle />
    </div>
    <!-- 表格 -->
    <div class="example">
      <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="date" label="日期  " width="220" align="center" />
      <el-table-column prop="name" label="姓名" width="220" align="center" />
      <el-table-column prop="address" label="地址" align="center" />
    </el-table>
    </div>
  </div>
  <!-- 分页条 -->
   <div class="example">
    <el-pagination
      v-model:current-page="currentPage4"
      v-model:page-size="pageSize4"
      :page-sizes="[100, 200, 300, 400]"
      :size="size"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="400"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
   </div>
   <!-- 对话框 -->
  <div class="example">
    <el-button plain @click="dialogVisible = true">
    Click to open the Dialog
  </el-button>

  <el-dialog
    v-model="dialogVisible"
    title="Tips"
    width="500"
    :before-close="handleClose"
  >
    <span>This is a message</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
  </div>
  <!-- 表单 -->
   <div class="example">
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="姓名">
      <el-input v-model="formInline.name" placeholder="请输入姓名" clearable />
    </el-form-item>
    <el-form-item label="性别">
      <el-select
        v-model="formInline.gender"
        placeholder="请选择性别"
        clearable
      >
        <el-option label="男" value="1" />
        <el-option label="女" value="0" />
      </el-select>
    </el-form-item>
    <el-form-item label="生日">
      <el-date-picker
        v-model="formInline.date"
        type="date"
        placeholder="请选择生日"
        value-format="YYYY-MM-DD"
        clearable
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">Query</el-button>
    </el-form-item>
  </el-form>
   </div>
</template>

<style scoped>
.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.demo-form-inline .el-select {
  --el-select-width: 220px;
}
.example {
    margin-bottom: 10px;
}
</style>
