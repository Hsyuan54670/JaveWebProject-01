<script setup>
import { ref,onMounted } from 'vue'
import { addDeptApi, queryDeptListApi,queryDeptByIdApi,editDeptApi,delDeptByIdApi } from '@/api/dept'
import { ElMessage,ElMessageBox } from 'element-plus'
//查询部门
const QueryDept =async () => {
     const result   = await queryDeptListApi()
     if(result.code === 1){
        deptList.value = result.data
     }else{
        ElMessage.error(result.msg)
     }
 }
 const deptList = ref([])
 onMounted(()=>{
    QueryDept()
 })


//表单检验
const deptFormRef =ref()
const rules = ref({
    name: [
        { required: true, message: '部门名称不能为空', trigger: 'blur' },
        { min: 2, max: 10, message: '部门名称长度必须在 2 到 10 个字符', trigger: 'blur' }
    ]
})
//新增部门
const dialogVisibleAdd = ref(false)
const fromtitle = ref('')
const dept = ref({
    id:'',
    name:'',
    updateTime:'',
    createTime:''
})
//新增部门
const openAddDept = () => {
    fromtitle.value = '新增部门'
    dept.value.id = ''
    dept.value.name = ''
    if(deptFormRef.value){
        deptFormRef.value.resetFields()
    }
    dialogVisibleAdd.value = true
}
//编辑部门
const openEditDept = async (id) => {
    if(deptFormRef.value){
        deptFormRef.value.resetFields()
    }
    const result = await queryDeptByIdApi(id)
    if(result.code === 1){
        dept.value = result.data
        fromtitle.value = '编辑部门'
        dialogVisibleAdd.value = true
    }else{
        ElMessage.error(result.msg)
    }
}
//删除部门
const openDelDept = async (id) => {
    ElMessageBox.confirm(
    '确定删除该部门吗？',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    const result = await delDeptByIdApi(id)
    if(result.code === 1){
        ElMessage.success(result.msg)
        QueryDept()
    }else{
        ElMessage.error(result.msg)
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}
//提交
const handleSubmit = async () => {
    if(!deptFormRef.value){
        return
    }
    deptFormRef.value.validate(async(valid)=>{
        if(valid){
            const result = dept.value.id? await editDeptApi(dept.value):await addDeptApi(dept.value)
            if(result.code === 1){
                ElMessage.success(result.msg)
                dialogVisibleAdd.value = false
                QueryDept()
            }else{
                ElMessage.error(result.msg)
            }
        }else{
            ElMessage.error('表单校验不通过')
        }
    })
}


</script>

<template>
    <h1>部门管理</h1>
    <div class="container">
    <el-button @click="openAddDept" type="primary" round>+新增部门</el-button>

        <el-dialog
        v-model="dialogVisibleAdd"
        :title="fromtitle"
        width="500"
        >
            <el-form :model="dept" :rules="rules" ref="deptFormRef">
                <el-form-item label="部门名称" prop="name">
                    <el-input v-model="dept.name" placeholder="请输入部门名称,长度在2到10个字符"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="dialogVisibleAdd = false">取消</el-button>
                    <el-button type="primary" @click="handleSubmit">确认</el-button>
                </div>
            </template>
        </el-dialog>
    </div>

    <div class="container">
        <el-table :data="deptList" border>
            <el-table-column prop="id" label="部门ID" width="200" type="index" align="center"/>
            <el-table-column prop="name" label="部门名称" width="300" align="center"/>
            <el-table-column prop="updateTime" label="更新时间" width="400" align="center"/>
            <el-table-column prop="operation" label="操作" align="center">
                <template #default="scope">
                    <el-button type="primary" size="mini" @click="openEditDept(scope.row.id)">编辑</el-button>
                    <el-button type="danger" size="mini" @click="openDelDept(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<style scoped>
.container{
    margin-bottom: 20px;
}
</style>
