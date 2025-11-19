<script setup>
import { ref,onMounted } from 'vue'
import { useRouter } from 'vue-router'
import {ElMessageBox,ElMessage} from 'element-plus'
import { changePasswordApi } from '@/api/emp'
const router = useRouter()
const user=ref('')
const dialogVisible=ref(false)
const subForm=ref({
  username:'',
  password:'',
  confirmPassword:''
})
onMounted(()=>{
  const loginUser=JSON.parse(localStorage.getItem('loginUser'))
  if(loginUser&&loginUser.name){
    user.value=loginUser.name
  }
  if(loginUser&&loginUser.username){
    subForm.value.username=loginUser.username
  }
})

// 退出登录
const logout=()=>{
  // 确认退出登录
  ElMessageBox.confirm(
    '确定退出登录吗？',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    localStorage.removeItem('loginUser')
    user.value=''
    router.push('/login')
  }).catch(() => {
    ElMessage.info('已取消退出登录')
  })
}
// 修改密码
const cpFormRef=ref()
const openChangePassword=()=>{
  if(cpFormRef.value){
    cpFormRef.value.resetFields()
  }
  subForm.value.password=''
  subForm.value.confirmPassword=''
  dialogVisible.value=true
}
const submitChangePassword=async()=>{
  if(!cpFormRef.value){
    ElMessage.error('请填写完整密码信息')
    return
  }
  cpFormRef.value.validate(async(valid)=>{
    if(!valid){
      ElMessage.error('请填写完整密码信息')
      return
    }else{
      const result=await changePasswordApi(subForm.value)
      if(result.code==1){
        ElMessage.success(result.msg)
        dialogVisible.value=false
      }else{
        ElMessage.error(result.msg)
      }
    }
  })
}
const rules=ref({
  password: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 32, message: '密码长度应在6到32位之间', trigger: 'blur' },
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { 
      validator: (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else if (value !== subForm.value.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      }, 
      trigger: 'blur' 
    }
  ]
})
</script>

<template>
  <div class="common-layout">
    <!-- 头部 -->
    <el-container class="first-container">
      <el-header class="header">
        <span class="title">信息管理辅助系统</span>
        <span class="right-tools">
          <a href="javascript:void(0)" @click="openChangePassword">
            <el-icon><EditPen /></el-icon>修改密码 &nbsp;&nbsp;|&nbsp;&nbsp;
          </a>
          <a href="javascript:void(0)" @click="logout">
            <el-icon><SwitchButton /></el-icon>退出登录【{{user}}】
          </a>
        </span>
      </el-header>
      <el-container class="second-container">
      <!-- 侧边栏 -->
        <el-aside width="300px" class="aside">
          <el-menu router="true">

          <el-menu-item index="/index">
            <template #title>
              <el-icon><HomeFilled /></el-icon>首页
            </template>
          </el-menu-item>
            
          <el-sub-menu index="1">
            <template #title>
              <el-icon><Menu /></el-icon>班级学员管理
            </template>
              <el-menu-item index="/clazz"><el-icon><Promotion /></el-icon>班级管理</el-menu-item>
              <el-menu-item index="/stu"><el-icon><UserFilled /></el-icon>学员管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="2">
            <template #title>
              <el-icon><HelpFilled /></el-icon>部门员工管理
            </template>
              <el-menu-item index="/dept"><el-icon><CircleCheckFilled /></el-icon>部门管理</el-menu-item>
              <el-menu-item index="/emp"><el-icon><Avatar /></el-icon>员工管理</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="3">
            <template #title>
              <el-icon><Histogram /></el-icon>数据统计管理
            </template>
              <el-menu-item index="/empReport"><el-icon><Check /></el-icon>员工信息统计</el-menu-item>
              <el-menu-item index="/stuReport"><el-icon><CircleCheck /></el-icon>学员信息统计</el-menu-item>
              <el-menu-item index="/log"><el-icon><Edit /></el-icon>日志信息统计</el-menu-item>
          </el-sub-menu>

        </el-menu>
        </el-aside>
      <!-- 主体 -->
        <el-main>
          <!-- 路由出口，页面内容将在这里渲染 -->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>

  <div class="change-password-dialog">
    <el-dialog title="修改密码" 
     v-model="dialogVisible" 
      width="400px"
    >
      <el-form :model="subForm" ref="cpFormRef" :rules="rules" label-width="120px">
        <el-form-item label="新密码" prop="password">
          <el-input v-model="subForm.password" placeholder="请输入新密码" clearable style="width: 220px;" type="password" />
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input v-model="subForm.confirmPassword" placeholder="请确认新密码" clearable style="width: 220px;" type="password" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button type="primary" @click="submitChangePassword">确定</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.header {
  height: 60px;
  display: flex;
  justify-content: space-between; 
  align-items: center;
  background: linear-gradient(to right, #9659f3, #51b7e0);
}
.title {
  font-size: 40px;
  font-weight: bold;
  color: white;
  font-family: '楷体';
}
.right-tools {
  display: flex;
  align-items: center;
  font-size: 20px;
  font-family: '楷体';
}
.right-tools a{
  display: inline-flex;
  gap:3px;
  color: white;
  text-decoration: none;
}
.right-tools a:hover {
  color: #f0f0f0; 
}
.second-container .aside {
  border-right: 2px solid #e0e0e0;
  background-color: #f8f9fa;
  height: calc(100vh - 60px);
  overflow-y: auto;
}
</style>
