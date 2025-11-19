<script setup>
import { queryEmpListApi,queryEmpByIdApi,delEmpByIdApi,addEmpApi,updateEmpApi } from '@/api/emp' 
import { ref,watch,onMounted } from 'vue'
import { ElMessage,ElMessageBox } from 'element-plus'
import { queryDeptListApi } from '@/api/dept' 

const page=ref({
    total:'',
    rows:[]
})
const token=ref('')
const getToken=()=>{
    const data=JSON.parse (localStorage.getItem('loginUser'))
    if(data&&data.token){
        token.value=data.token
    }
}
const emp=ref({
    name:'',
    gender:'',
    date:[],
    begin:'',
    end:'',
    page:ref(1),
    pageSize:ref(10)
})

const employee = ref({
  username: '',
  name: '',
  gender: '',
  phone: '',
  job: '',
  salary: '',
  deptId: '',
  entryDate: '',
  image: '',
  exprList: []
})
const depts=ref([])
const genders=ref([{name:'男',value:1},{name:'女',value:2}])
const jobs=ref([{name:'班主任',value:1},{name:'讲师',value:2},{name:'学生主管',value:3},{name:'教研主管',value:4},{name:'咨询师',value:5},{name:"其他",value:6}])
const dialogVisible = ref(false)
const dialogTitle = ref('新增员工')
 

const handleAvatarSuccess = (response) => {
  employee.value.image=response.data
  console.log(response);
}
// 文件上传之前触发
const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('只支持上传图片')
    return false
  } else if (rawFile.size / 1024 / 1024 > 10) {
    ElMessage.error('只能上传10M以内图片')
    return false
  }
  return true
}
watch(()=>emp.value.date,(newVal,oldVal)=>{
    if(newVal.length === 2){
        emp.value.begin = newVal[0]
        emp.value.end = newVal[1]
    }
})
const QueryEmp=async()=>{
    const result = await queryEmpListApi(emp.value)
    if(result.code === 1){
        ElMessage.success(result.msg)
        page.value.total = result.data.total
        page.value.rows = result.data.rows
    }else{
        ElMessage.error(result.msg)
    }
}
onMounted(async()=>{
  const result = await queryDeptListApi()
    if(result.code === 1){
        ElMessage.success(result.msg)
        depts.value = result.data
    }else{
        ElMessage.error(result.msg)
    }
    getToken()
    QueryEmp()
})
const clear=()=>{
    emp.value.name=''
    emp.value.gender=''
    emp.value.date=[]
    QueryEmp()
}

const handleSizeChange = (val) => {
  console.log(`${val} items per page`)
  emp.value.pageSize = val
  QueryEmp()
}
const handleCurrentChange = (val) => {
  console.log(`current page: ${val}`)
  emp.value.page = val
  QueryEmp()
}
const selectedRows=ref([])
const handleSelectionChange = (selection) => {
  // selection是用户当前选中的所有行数据
  selectedRows.value = selection
  console.log(selection)
}
const openDelEmp = (row) => {
  if (selectedRows.value.length === 0&&!row.id) {
    ElMessage.warning('请先选择要删除的员工!')
    return
  }
  ElMessageBox.confirm(
    '确定执行删除吗？',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    const ids = selectedRows.value.map(item => item.id)
    ids.push(row.id)
    console.log(ids)
    const result = await delEmpByIdApi(ids)
    if(result.code === 1){
        ElMessage.success(result.msg)
        QueryEmp()
    }else{
        ElMessage.error(result.msg)
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}
const openAddEmp = () => {
  if(empFormRef.value){
    empFormRef.value.resetFields()
  }
    dialogTitle.value = '新增员工'
    employee.value={
      username: '',
      name: '',
      gender: '',
      phone: '',
      job: '',
      salary: '',
      deptId: '',
      entryDate: '',
      image: '',
      exprList: []
    }   
    dialogVisible.value = true
}
const openEditEmp = async (row) => {
  if(empFormRef.value){
    empFormRef.value.resetFields()
  }
    const result = await queryEmpByIdApi(row.id)
    if(result.code === 1){
      employee.value = result.data
    }
    let exprList=employee.value.exprList
    if(exprList&&exprList.length > 0){
        exprList.forEach((expr)=>{
          expr.exprDate=[expr.begin,expr.end]
        })
    }
    dialogTitle.value = '修改员工'
    dialogVisible.value = true
}
const empFormRef = ref()
const addOrUpdateEmp = async () => {
    if(!empFormRef.value){
        ElMessage.error('请填写合法信息')
        return
    }
    empFormRef.value.validate(async (valid)=>{
        if(!valid){
            ElMessage.error('请填写合法信息')
            return
        }else{
            const result = employee.value.id ? await updateEmpApi(employee.value) : await addEmpApi(employee.value)
            if(result.code === 1){
                ElMessage.success(result.msg)
                dialogVisible.value = false
                QueryEmp()
            }else{
                ElMessage.error(result.msg)
            }
        }
    })
    
}
const addExprItem = () => {
  employee.value.exprList.push({
    company: '',
    job: '',
    begin: '',
    end: '',
    exprDate:[]
  })
}
const delExprItem = (index) => {
  employee.value.exprList.splice(index, 1)
}
watch(()=>employee.value.exprList,(newVal,oldVal)=>{
    if(newVal&&newVal.length > 0){
        employee.value.exprList.forEach((expr)=>{
          expr.begin=expr.exprDate[0]
          expr.end=expr.exprDate[1]
      })
    }
},{deep:true})

const rules=ref({
    username: [{ required: true, message: '请输入用户名', trigger: 'blur' },
    {min: 2, max: 20, message: '用户名长度必须在2到20位之间', trigger: 'blur' },
    ],
    name: [{ required: true, message: '请输入姓名', trigger: 'blur' },
      {min:2, max:10, message: '姓名长度必须在2到10位之间', trigger: 'blur' },
    ],
    gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
    phone: [{ required: true, message: '请输入手机号', trigger: 'blur' },
      {pattern:/^1[3456789]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' },
    ], 
})
</script>

<template>
<h1>员工管理</h1>

<div class="container">
    <el-form :inline="true" :model="emp" class="demo-form-inline">
    <el-form-item label="姓名">
      <el-input v-model="emp.name" placeholder="请输入姓名" clearable />
    </el-form-item>
    <el-form-item label="性别">
      <el-select v-model="emp.gender" placeholder="请选择性别" clearable>
        <el-option label="男" value="1" />
        <el-option label="女" value="2" />
      </el-select>
    </el-form-item>
    <el-form-item label="入职日期">
        <div class="demo-date-picker">
          <div class="block">
            <el-date-picker
              v-model="emp.date"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="YYYY-MM-DD"
            />
          </div> 
        </div>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="QueryEmp">查询</el-button>
      <el-button type="plain" @click="clear">清空</el-button>
    </el-form-item>
  </el-form>
</div>
<div class="container">
    <el-button @click="openAddEmp" type="primary" round>+新增员工</el-button>
    <el-button @click="openDelEmp" type="danger" round>-批量删除</el-button>
</div>
<div class="container">
    <el-table :data="page.rows" border style="width: 100%"
    @selection-change="handleSelectionChange"
    >
    <el-table-column type="selection" width="55" align="center" />
    <el-table-column prop="name" label="姓名" width="100" align="center" />
    <el-table-column prop="gender" label="性别" width="55" align="center" >
        <template #default="scope">
            {{scope.row.gender === 1 ? '男' : '女'}}
        </template>
    </el-table-column>
    <el-table-column prop="image" label="头像" width="180" align="center" >
        <template #default="scope">
            <img :src="scope.row.image"  style="width: 40px; height: 40px;">
        </template>
    </el-table-column>
    <el-table-column prop="deptName" label="所属部门" width="180" align="center" />
    <el-table-column prop="job" label="职位" width="180" align="center" >
        <template #default="scope">
            <span v-if="scope.row.job === 1">班主任</span>
            <span v-else-if="scope.row.job === 2">讲师</span>
            <span v-else-if="scope.row.job === 3">学工主管</span>
            <span v-else-if="scope.row.job === 4">教研主管</span>
            <span v-else-if="scope.row.job === 5">咨询师</span>
            <span v-else>其他</span>
        </template>
    </el-table-column>
    <el-table-column prop="entryDate" label="入职日期" width="180" align="center" />
    <el-table-column prop="updateTime" label="最后操作时间" width="180" align="center" />
    <el-table-column label="操作" align="center">
        <template #default="scope">
            <el-button type="primary" size="mini" @click="openEditEmp(scope.row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="openDelEmp(scope.row)">删除</el-button>
        </template>
    </el-table-column>
  </el-table>
</div>

<div class="container">
    <el-pagination
      v-model:current-page="emp.page"
      v-model:page-size="emp.pageSize"
      :page-sizes="[5, 10, 20, 50]"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="page.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>

<div class="container">


  <!-- 员工信息表单 -->
   <el-dialog v-model="dialogVisible" :title="dialogTitle" >
      <el-form :model="employee" label-width="80px" :rules="rules" ref="empFormRef">
        <!-- 基本信息 -->
         <!-- {{employee}} -->
        <!-- 第一行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="employee.username" placeholder="请输入员工用户名，2-20个字"></el-input>
            </el-form-item>
          </el-col>
           
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="employee.name" placeholder="请输入员工姓名，2-10个字"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
 
        <!-- 第二行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="employee.gender" placeholder="请选择性别" style="width: 100%;">
                <el-option v-for="item in genders" :key="item.value" :label="item.name" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
 
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="employee.phone" placeholder="请输入员工手机号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
 
        <!-- 第三行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位">
              <el-select v-model="employee.job" placeholder="请选择职位" style="width: 100%;">
                <el-option v-for="item in jobs" :key="item.value" :label="item.name" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资">
              <el-input v-model="employee.salary" placeholder="请输入员工薪资"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
 
        <!-- 第四行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门">
              <el-select v-model="employee.deptId" placeholder="请选择部门" style="width: 100%;">
                <el-option v-for="item in depts" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入职日期">
              <el-date-picker v-model="employee.entryDate" type="date" style="width: 100%;" placeholder="选择日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
 
        <!-- 第五行 -->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="头像">
              <el-upload
                class="avatar-uploader"
                action="/api/upload"
                :headers="{'token':token}"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                >
                <img v-if="employee.image" :src="employee.image" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
         
 
        <!-- 工作经历 -->
        <!-- 第六行 -->
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作经历">
              <el-button type="success" size="small" @click="addExprItem">+ 添加工作经历</el-button>
              
            </el-form-item>
          </el-col>
        </el-row>
         
        <!-- 第七行 ...  工作经历 -->
        <el-row :gutter="3" v-for="(expr,index) in employee.exprList">
          <el-col :span="10">
            <el-form-item size="small" label="时间" label-width="80px">
              <el-date-picker v-model="expr.exprDate" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" ></el-date-picker>
            </el-form-item>
          </el-col>
 
          <el-col :span="6">
            <el-form-item size="small" label="公司" label-width="60px">
              <el-input v-model="expr.company" placeholder="请输入公司名称"></el-input>
            </el-form-item>
          </el-col>
 
          <el-col :span="6">
            <el-form-item size="small" label="职位" label-width="60px">
              <el-input v-model="expr.job" placeholder="请输入职位"></el-input>
            </el-form-item>
          </el-col>
 
          <el-col :span="2">
            <el-form-item size="small" label-width="0px">
              <el-button type="danger" @click="delExprItem(index)" >- 删除</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
       
      <!-- 底部按钮 -->
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addOrUpdateEmp">保存</el-button>
        </span>
      </template>
  </el-dialog>
</div>
</template>

<style scoped>
.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.demo-form-inline .el-select {
  --el-select-width: 220px;
}
.container{
    margin-bottom: 10px;
}
.avatar {
  height: 40px;
}
.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
 
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
 
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
  border-radius: 10px;
  /* 添加灰色的虚线边框 */
  border: 1px dashed var(--el-border-color);
}
</style>
