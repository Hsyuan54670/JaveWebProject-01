<script setup>
import { ref,onMounted } from 'vue'
import axios from 'axios'


const user=ref({
    name: '',
    gender: '',
    job: '',
})
const userList = ref([])
const Query = async() => {
    const result=await axios.get(`https://web-server.itheima.net/emps/list?name=${user.value.name}&gender=${user.value.gender}&job=${user.value.job}`) 
    userList.value = result.data.data
  
}
const Clear = () => {
  user.value={name:'',gender:'',job:''}
  Query()
}
onMounted(()=>{
    Query()
})
</script>

<template>
    <el-container>
        <el-header>
            <el-form :inline="true" :model="user" class="demo-form-inline">
    <el-form-item label="姓名">
      <el-input v-model="user.name" placeholder="请输入姓名" clearable  />
    </el-form-item>
    <el-form-item label="性别">
      <el-select
        v-model="user.gender"
        placeholder="请选择"
        clearable
      >
        <el-option label="男" value="1" />
        <el-option label="女" value="2" />
      </el-select>
    </el-form-item>
    <el-form-item label="职位">
      <el-select
        v-model="user.job"
        placeholder="请选择"
        clearable
      >
        <el-option label="学生" value="1" />
        <el-option label="老师" value="2" />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="Query">查询</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="Clear">清空</el-button>
    </el-form-item>
  </el-form>
        </el-header>
        <el-main>
            <el-table :data="userList" border style="width: 100%; margin: 0 auto;" align="center">
    <el-table-column prop="id" label="ID" width="100" />
    <el-table-column prop="name" label="姓名" width="200" />
    <el-table-column  label="头像" width="200" >
        <template #default="scope">
            <img :src="scope.row.image" height="50">
        </template>
    </el-table-column>
    <el-table-column prop="gender" label="性别" width="100" >
        <template #default="scope">
            {{scope.row.gender==1?'男':'女'}}
        </template>
    </el-table-column>
    <el-table-column prop="job" label="职位" width="200" >
        <template #default="scope">
            <span v-if="scope.row.job==1">学生</span>
            <span v-else-if="scope.row.job==2">老师</span>
            <span v-else>其他</span>
        </template>
    </el-table-column>
    <el-table-column prop="entrydate" label="入职日期" width="400" />
    <el-table-column prop="updatetime" label="更新时间"  />
  </el-table>
        </el-main>
    </el-container>

</template>

<style scoped>
.el-container{
    width:70%;
    margin-left:15%;
    margin-right:15%;
}
.demo-form-inline .el-input {
  --el-input-width: 220px;
}

.demo-form-inline .el-select {
  --el-select-width: 220px;
}
</style>
