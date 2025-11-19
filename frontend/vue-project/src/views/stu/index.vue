<script setup>
import { ref, onMounted } from 'vue'
import { getStuListApi,delStuApi,addStuApi,updateStuApi,violationApi,getStuByIdApi } from '@/api/stu'
import { ElMessage,ElMessageBox } from 'element-plus';
import { getClazzListApi } from '@/api/clazz'

const stu = ref({
    name: '',
    degree:'',
    clazzId:'',
    page:ref(1),
    pageSize:ref(10)
})
const stuImpl = ref({
    id:'',
    name: '',
    no: '',
    gender:'',
    phone:'',
    idCard:'',
    clazzId:'',
    isCollege:'',
    address:'',
    degree:'',
    graduationDate:'',
    clazzId:'',
    clazzName:'',
    violationCount:'',
    violationScore:''
})
const stuFormRef = ref()
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isCollegeList = ref([{name:'是',value:1},{name:'否',value:0}])
const genderList = ref([{name:'男',value:1},{name:'女',value:2}])
const degreeList = ref([{name:'初中',value:1},{name:'高中',value:2},{name:'大专',value:3},{name:'本科',value:4},{name:'硕士',value:5},{name:'博士',value:6}])
const clazzList = ref([])
const page=ref({
    total:'',
    rows:[]
})
onMounted(()=>{
    QueryStuList()
    QueryClazz()
})
const QueryStuList = async () => {
    const result = await getStuListApi(stu.value)
    if (result.code === 1) {
        page.value.rows = result.data.rows
        page.value.total = result.data.total
    }
}
const QueryClazz = async () => {
    const res = await getClazzListApi()
    if (res.code === 1) {
        clazzList.value = res.data
    }
}
const clear=()=>{
    stu.value.name = ''
    stu.value.degree = ''
    stu.value.clazzId = ''
    stu.value.page = 1
    stu.value.pageSize = 10
    QueryStuList()
}
const handleSizeChange = (val) => {
  console.log(`${val} items per page`)
  stu.value.pageSize = val
  QueryStuList()
}
const handleCurrentChange = (val) => {
  console.log(`current page: ${val}`)
  stu.value.page = val
  QueryStuList()
}
const openAddStu=()=>{
    if(stuFormRef.value){
        stuFormRef.value.resetFields()
    }
    // 重置表单数据
    stuImpl.value = {...stuImpl.value}
    dialogTitle.value = '添加学生'
    dialogVisible.value = true
}
const openUpdateStu= async (row)=>{
    if(stuFormRef.value){
        stuFormRef.value.resetFields()
    }
    const result=await getStuByIdApi(row.id)
    stuImpl.value = result.data
    dialogTitle.value = '编辑学生'
    dialogVisible.value = true
}
const FormSubmit=async()=>{
    if(!stuFormRef.value){
        ElMessage.error('请先填写完整信息')
        return
    }
    stuFormRef.value.validate(async (valid) => {
        if (valid) {
            if (dialogTitle.value === '添加学生') {
                await addStuApi(stuImpl.value)
            } else {
                await updateStuApi(stuImpl.value)
            }
            ElMessage.success('操作成功')
            dialogVisible.value = false
            QueryStuList()
        } else {
            ElMessage.error('请填写完整信息')
        }
    })

}

const selectedRows=ref([])
const selectionChange=(selection)=>{
    selectedRows.value=selection
    console.log(selectedRows.value)
}
const delStu=async (row)=>{
    if(selectedRows.value.length==0&&!row){
        ElMessage.error('请选择要删除的学生')
        return
    }
    ElMessageBox.confirm('确定执行删除操作吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(async () => {
        let ids=[]
        if(!row){
            ids = selectedRows.value.map(item => item.id)
        }else{
            ids=[row.id]
        }
        const result=await delStuApi(ids)
        if(result.code===1){
            ElMessage.success('删除成功')
        }else{
            ElMessage.error('删除失败')
        }
        QueryStuList()
    }).catch(() => {
        ElMessage.info('已取消删除')
    })
}
const dialogVisible1 = ref(false)
const dialogTitle1 = ref('')
const vioFormRef=ref()
const score=ref()
const violation=async (row)=>{
    const result=await getStuByIdApi(row.id)
    stuImpl.value = result.data
    dialogTitle1.value ='提示'
    dialogVisible1.value = true
}

const vioSubmit=async()=>{
    if(!vioFormRef.value){
        ElMessage.error('请先填写完整信息')
        return
    }
    vioFormRef.value.validate(async (valid) => {
        if (valid) {
            const result = await violationApi(stuImpl.value,score.value)
            if(result.code===1){
                ElMessage.success('操作成功')
            }else{
                ElMessage.error('操作失败')
            }
            dialogVisible1.value = false
            QueryStuList()
        } else {
            ElMessage.error('请填写完整信息')
        }
    })
}

const rules={
    name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
    no: [{ required: true, message: '请输入学号', trigger: 'blur' },
        {min:10,max:10,message:'学号长度必须是10位',trigger:'blur'}],
    gender: [{ required: true, message: '请选择性别', trigger: 'blur' }],
    phone: [{ required: true, message: '请输入手机号', trigger: 'blur' },
        {pattern:/^1[3456789]\d{9}$/,message:'请输入正确的手机号',trigger:'blur'}],
    idCard: [{ required: true, message: '请输入身份证号', trigger: 'blur' },
        {pattern:/^\d{17}[\dXx]|\d{15}$/,message:'请输入正确的身份证号',trigger:'blur'}],
    isCollege: [{ required: true, message: '请选择是否是大学生', trigger: 'blur' }],
    clazzId: [{ required: true, message: '请选择班级', trigger: 'blur' }],
}
const rules_score={
    score: [{ required: true, message: '请输入违规扣分', trigger: 'blur' },
        {min:0,max:10,message:'违规扣分必须在0-10分之间',trigger:'blur'}
    ],
}
</script>

<template>
<h1>学生管理</h1>{{score}}
<!-- 查询 -->
 <div class="container">
    <el-form :inline="true" :model="stu" class="form-inline">
    <el-form-item label="姓名">
      <el-input v-model="stu.name" placeholder="请输入姓名" clearable />
    </el-form-item>
    <el-form-item label="学历">
      <el-select v-model="stu.degree" placeholder="请选择学历" clearable>
        <el-option v-for="item in degreeList" :key="item.value" :label="item.name" :value="item.value" />
      </el-select>
    </el-form-item>
    <el-form-item label="班级">
      <el-select v-model="stu.clazzId" placeholder="请选择班级" clearable>
        <el-option v-for="item in clazzList" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="QueryStuList">查询</el-button>
      <el-button type="plain" @click="clear">清空</el-button>
    </el-form-item>
    </el-form>
 </div>
<!-- 新增 -->
<div class="container">
    <el-button type="primary" @click="openAddStu" round>+添加学生</el-button>
    <el-button @click="delStu()" type="danger"  round>-批量删除</el-button>
</div>
<!-- 表格 -->
<div class="container">
    <el-table :data="page.rows" border style="width: 100%" class="stu-table"
    @selection-change="selectionChange"
    >
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column prop="name" label="姓名" width="80" align="center"/>
        <el-table-column prop="no" label="学号" width="120" align="center"/>
        <el-table-column prop="clazzName" label="班级" width="150" align="center"/>
        <el-table-column prop="gender" label="性别" width="60" align="center">
            <template #default="scope">
                {{scope.row.gender === 1 ? '男' : '女'}}
            </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="120" align="center"/>
        <el-table-column prop="idCard" label="身份证号" width="150" align="center"/>
        <el-table-column prop="violationCount" label="违纪次数" width="80" align="center"/>
        <el-table-column prop="violationScore" label="违纪扣分" width="80" align="center"/>
        <el-table-column prop="updateTime" label="更新时间" width="180" align="center"/>
        <el-table-column label="操作" align="center">
            <template #default="scope">
                <el-button type="primary" size="mini" @click="openUpdateStu(scope.row)">编辑</el-button>
                <el-button type="warning" size="mini" @click="violation(scope.row)">违纪</el-button>
                <el-button type="danger" size="mini" @click="delStu(scope.row)">删除</el-button>
            </template>
        </el-table-column>


    </el-table>
</div>
<!-- 分页 -->
<div class="container">
    <el-pagination
      v-model:current-page="stu.page"
      v-model:page-size="stu.pageSize"
      :page-sizes="[5, 10, 20, 50]"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="page.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
</div>
<!-- 表单stu -->
<div class="container">
    <el-dialog
    v-model="dialogVisible" 
    :title="dialogTitle"
    >
        <el-form :model="stuImpl" label-width="180px" ref="stuFormRef" :rules="rules" >

            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="姓名" prop="name">
                        <el-input v-model="stuImpl.name" placeholder="请输入姓名" clearable />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="学号" prop="no">
                        <el-input v-model="stuImpl.no" placeholder="请输入学号" clearable />
                    </el-form-item>
                </el-col>
            </el-row>
            <!-- 性别，手机号 -->   
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="性别" prop="gender">
                        <el-select v-model="stuImpl.gender" placeholder="请选择性别" clearable>
                            <el-option v-for="item in genderList" :key="item.value" :label="item.name" :value="item.value" />
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="手机号" prop="phone">
                        <el-input v-model="stuImpl.phone" placeholder="请输入手机号" clearable />
                    </el-form-item>
                </el-col>
            </el-row>
            <!-- 身份证号，是否大学生 -->
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="身份证号" prop="idCard">
                        <el-input v-model="stuImpl.idCard" placeholder="请输入身份证号" clearable />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="是否大学生" prop="isCollege">
                        <el-select v-model="stuImpl.isCollege" placeholder="请选择是否大学生" clearable>
                            <el-option v-for="item in isCollegeList" :key="item.value" :label="item.name" :value="item.value" />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <!-- 联系地址，最高学历 -->
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="联系地址" prop="address">
                        <el-input v-model="stuImpl.address" placeholder="请输入联系地址" clearable />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="最高学历" prop="highestEducation">
                        <el-select v-model="stuImpl.degree" placeholder="请选择最高学历" clearable>
                            <el-option v-for="item in degreeList" :key="item.value" :label="item.name" :value="item.value" />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <!-- 毕业时间，所属班级 -->
            <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="毕业时间" prop="graduationTime">
                        <el-date-picker v-model="stuImpl.graduationDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择毕业时间" clearable />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="所属班级" prop="clazzId">
                        <el-select v-model="stuImpl.clazzId" placeholder="请选择所属班级" clearable>
                            <el-option v-for="item in clazzList" :key="item.id" :label="item.name" :value="item.id" />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="FormSubmit">保存</el-button>
          </span>
        </template>
    </el-dialog>
</div>
<!-- 表单violation -->
 <div class="container">
    <el-dialog
    v-model="dialogVisible1" 
    :title="dialogTitle1"
    width="320px"
    >
        <el-form ref="vioFormRef" :rules="rules_score" :model="score">
            <el-form-item label="违规扣分" prop="violation">
                <el-input v-model="score" placeholder="请输入违规扣分" clearable style="width:220px" />
            </el-form-item>
        </el-form>
        <template #footer>
                <el-button @click="dialogVisible1 = false">取消</el-button>
                <el-button type="primary" @click="vioSubmit">保存</el-button>
        </template>
    </el-dialog>
</div>
</template>

<style scoped>
.form-inline .el-input{
    width: 220px;
}
.form-inline .el-select{
    width: 220px;
}
.container{
    margin-bottom: 10px;
}
.stu-table{
    font-size: 12px;
}
</style>
