<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { loginApi } from '@/api/login'
import { useRouter } from 'vue-router'
// 用户登录
const user = ref({
    username: '',
    password: ''
})
const router = useRouter()
// 登录提交
const submitForm = async () => {
    if (!user.value.username || !user.value.password) {
        ElMessage.warning('请输入用户名和密码')
        return
    }
    const result=await loginApi(user.value)
    if(result.code==1){
        ElMessage.success('登录成功')
        localStorage.setItem('loginUser',JSON.stringify(result.data))
        router.push('/')
        clear()
    }else{
        ElMessage.error('请输入正确的用户名或密码')
    }
}
const clear =()=>{
    user.value={
        username: '',
        password: ''
    }
}
</script>

<template>
<div class="container">
    <el-card style="width: 480px" shadow="always" class="login-card" align="center">
        <h1>用户登录</h1>
        <el-form :inline="false" :model="user" class="demo-form-inline" label-width="auto">
            <el-form-item label="用户名">
              <el-input v-model="user.username" placeholder="请输入用户名" clearable style="width:220px"/>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="user.password" placeholder="请输入密码" clearable style="width:220px"/>
              </el-form-item>
        </el-form>
        <template #footer>
              <el-button type="primary" @click="submitForm">登录</el-button>
              <el-button type="plain" @click="clear">清空</el-button>
        </template>
    </el-card>
</div>
</template>

<style scoped>
.container {
    background-color: #e4b1df; 
    min-height: 100vh; 
    padding: 20px;
}
.login-card{
    margin: 300px auto;
}
.demo-form-inline {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 20px 0;
}
.login-card h1{
    font-family: 楷体;
    font-size: 36px;
}

</style> 