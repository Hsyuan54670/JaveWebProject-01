<script setup>
import {ref,onMounted} from 'vue'
import { getLogReport } from '@/api/report'

const disabled=ref(false)
const background=ref(false)
const pageInfo=ref({
    page:1,
    pageSize:10
})
const page=ref({
    total:'',
    rows:[]
})
const QueryLog=async ()=>{
    const result=await getLogReport(pageInfo.value)
    if(result.code==1){
        page.value=result.data
    }
}
onMounted(()=>{
    QueryLog()
})
const handleSizeChange = (val) => {
  console.log(`${val} items per page`)
  pageInfo.value.pageSize = val
  QueryLog()
}
const handleCurrentChange = (val) => {
  console.log(`current page: ${val}`)
  pageInfo.value.page = val
  QueryLog()
}

</script>

<template>
<h1>日志管理</h1>
<!-- 表格 -->
<div class="container">
    <el-table :data="page.rows" border style="width: 100%" class="log-operate">
        <el-table-column prop="operateEmpName" label="操作人" width="65"  align="center"/>
        <el-table-column prop="operateTime" label="操作时间" width="150" align="center"/>
        <el-table-column prop="className" label="类名" width="200" align="center"/>
        <el-table-column prop="methodName" label="方法名" width="130" align="center"/>
        <el-table-column prop="costTime" label="操作耗时(ms)" width="100" align="center"/>
        <el-table-column prop="methodParams" label="请求参数" width="225" align="center" show-overflow-tooltip="true"/>   
        <el-table-column prop="returnValue" label="返回值"  align="center"/>
    </el-table>
</div>
<!-- 分页 -->
<div class="container">
    <el-pagination
      v-model:current-page="pageInfo.page"
      v-model:page-size="pageInfo.pageSize"
      :page-sizes="[5, 10, 20, 50]"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="page.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
</div>
</template>

<style scoped>
.container .log-operate{
    font-size: 12px;
}

</style>
