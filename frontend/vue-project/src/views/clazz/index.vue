<script setup>
import { ref,onMounted,watch } from 'vue'
import { getClazzApi,delClazzApi,addClazzApi,updateClazzApi,getClazzByIdApi } from '@/api/clazz'
import { ElMessage,ElMessageBox } from 'element-plus'
import { queryMasterListApi } from '@/api/emp'

// 班级管理
const clazz = ref({
    name: '',
    date: [],
    begin:'',
    end:'',
    page:ref(1),
    pageSize:ref(10)
})
const clazzImpl=ref({
    name:'',
    room:'',
    beginDate:'',
    endDate:'',
    masterId:'',
    subject:''
})
const clazzFormRef=ref()
const masters=ref([])
const subjects=ref([{value:1,name:'Java'},{value:2,name:'前端'},{value:3,name:'大数据'},{value:4,name:'Python'},{value:5,name:'Go'},{value:6,name:'嵌入式'}])
watch(()=>clazz.value.date,(newVal,oldVal)=>{
    if(newVal.length>0){
        clazz.value.begin=newVal[0]
        clazz.value.end=newVal[1]
    }
})
const clazzList=ref({
    total:'',
    rows:[]
})
const QueryClazz=async()=>{
    const result=await getClazzApi(clazz.value)    
    if(result.code==1){
        ElMessage.success('查询成功')
        clazzList.value=result.data
    }
}
const clear=()=>{
    clazz.value={
        name: '',
        date: [],
        begin:'',
        end:'',
        page:ref(1),
        pageSize:ref(10)
    }
    QueryClazz()
}
onMounted(()=>{
    QueryClazz()
    QueryMasters()
})
const handleSizeChange = (val) => {
  console.log(`${val} items per page`)
  clazz.value.pageSize = val
  QueryClazz()
}
const handleCurrentChange = (val) => {
  console.log(`current page: ${val}`)
  clazz.value.page = val
  QueryClazz()
}
const QueryMasters=async()=>{
    const result=await queryMasterListApi()    
    if(result.code==1){
        ElMessage.success('查询成功')
        masters.value=result.data
    }
}


const dialogVisible = ref(false)
const dialogTitle = ref('')
const openClazz=()=>{
    clazzImpl.value={
        name:'',
        room:'',
        beginDate:'',
        endDate:'',
        masterId:'',
        subject:''
    }
    if(clazzFormRef.value){
        clazzFormRef.value.resetFields()
    }
    dialogVisible.value=true
}
const rules=ref({
    name:[{required:true,message:'请输入课程名称',trigger:'blur'}],
    room:[{required:true,message:'请输入班级教室',trigger:'blur'}],
    beginDate:[{required:true,message:'请选择开课日期',trigger:'blur'}],
    endDate:[{required:true,message:'请选择结课日期',trigger:'blur'}],
    masterId:[{required:true,message:'请选择班主任',trigger:'blur'}],
    subject:[{required:true,message:'请选择学科',trigger:'blur'}]
})
const openUpdateClazz=async(row)=>{
    if(clazzFormRef.value){
        clazzFormRef.value.resetFields()
    }
    const result=await getClazzByIdApi(row.id)
    if(result.code==1){
        ElMessage.success('查询成功')
        dialogTitle.value='修改班级'
        clazzImpl.value=result.data
        dialogVisible.value=true
    }else{
        ElMessage.error(result.msg)
    }
}
const submitForm=async()=>{
    if(!clazzFormRef.value){
        ElMessage.error('请填写合法信息')
        return
    }
    clazzFormRef.value.validate(async (valid)=>{
        if(!valid){
            ElMessage.error('请填写合法信息')
            return
        }else{
            const result = clazzImpl.value.id ? await updateClazzApi(clazzImpl.value) : await addClazzApi(clazzImpl.value)
            if(result.code === 1){
                ElMessage.success(result.msg)
                dialogVisible.value = false
                QueryClazz()
            }else{
                ElMessage.error(result.msg)
            }
        }
    })
}
const delClazz=async(row)=>{
    ElMessageBox.confirm('确定删除该班级吗？','提示',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
        type:'warning'
    }).then(async()=>{
        const result=await delClazzApi(row.id)
        if(result.code==1){
            ElMessage.success(result.msg)
            QueryClazz()
        }else{
            ElMessage.error(result.msg)
        }
    })
}
</script>

<template>
<h1>班级管理</h1>
<!-- 查询 -->
<div class="container">
    <el-form :inline="true" :model="clazz" class="demo-form-inline">
    <el-form-item label="课程名称">
      <el-input v-model="clazz.name" placeholder="请输入课程名称" clearable />
    </el-form-item>
    <el-form-item label="开课日期">
        <div class="demo-date-picker">
          <div class="block">
            <el-date-picker
              v-model="clazz.date"
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
      <el-button type="primary" @click="QueryClazz">查询</el-button>
      <el-button type="plain" @click="clear">清空</el-button>
    </el-form-item>
  </el-form>
</div>
<!-- 新增班级 -->
<div class="container">
    <el-button type="primary" @click="openClazz">+新增班级</el-button>
</div>
 <!-- 班级列表 -->
<div class="container">
    
    <el-table :data="clazzList.rows" border style="width: 100%">
        <el-table-column prop="id" label="班级ID" width="80" type="index" align="center"/>
        <el-table-column prop="name" label="课程名称" width="180" align="center"/>
        <el-table-column prop="masterName" label="班主任" width="100" align="center"/>
        <el-table-column prop="room" label="班级教室" width="100" align="center"/>
        <el-table-column prop="beginDate" label="开课日期" width="180" align="center"/>
        <el-table-column prop="endDate" label="结课时间" width="180" align="center"/>
        <el-table-column prop="subject" label="学科" width="100" align="center">
            <template #default="scope">
                {{subjects.find(item=>item.value==scope.row.subject)?.name || '未知'}}
            </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
            <template #default="scope">
                <el-button type="primary" size="mini" @click="openUpdateClazz(scope.row)">编辑</el-button>
                <el-button type="danger" size="mini" @click="delClazz(scope.row)">删除</el-button>
            </template>
        </el-table-column>


    </el-table>
</div>
<!-- 分页 -->
<div class="container">
    <el-pagination
      v-model:current-page="clazz.page"
      v-model:page-size="clazz.pageSize"
      :page-sizes="[5, 10, 20, 50]"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="clazzList.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
</div>
<!-- 新增班级弹窗 -->
<div class="container">
    <el-dialog
      v-model="dialogVisible" 
      :title="dialogTitle"
      
      width="400px"
    >
      <el-form :model="clazzImpl" label-width="120px" ref="clazzFormRef" :rules="rules" >
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="clazzImpl.name" placeholder="请输入课程名称" clearable style="width: 220px;"/>
        </el-form-item>
        <el-form-item label="班级教室" prop="room">
          <el-input v-model="clazzImpl.room" placeholder="请输入班级教室" clearable style="width: 220px;"/>
        </el-form-item>
        <el-form-item label="开课日期" prop="beginDate" >
          <el-date-picker
            v-model="clazzImpl.beginDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择开课日期"
          />
        </el-form-item>
        <el-form-item label="结课日期" prop="endDate" >
          <el-date-picker
            v-model="clazzImpl.endDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择结课日期"
          />
        </el-form-item>
        <el-form-item label="班主任" prop="masterId">
          <el-select v-model="clazzImpl.masterId" placeholder="请选择班主任" style="width: 220px;">
            <el-option v-for="item in masters" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="学科" prop="subject">
          <el-select v-model="clazzImpl.subject" placeholder="请选择学科" style="width: 220px;">
            <el-option v-for="item in subjects" :key="item.value" :label="item.name" :value="item.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" @click="submitForm()">确定</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </template>
    </el-dialog>
</div>
</template>

<style scoped>
.container{
    margin-bottom: 10px;
}
</style>
