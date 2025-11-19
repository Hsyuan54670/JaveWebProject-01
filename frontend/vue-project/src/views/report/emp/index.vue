<script setup>
// 导入 ECharts 库
import * as echarts from 'echarts';
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { getEmpJobReport, getEmpGenderReport } from '@/api/report'

const myJobChart = ref()
const myGenderChart=ref()
const job = ref([])
const value = ref([])
const gender=ref([])
const initJobChart = () => {
  const chartDom = document.getElementById('empJobChart');
  if (!chartDom) return;
  // 初始化 ECharts 实例
  myJobChart.value = echarts.init(chartDom);
  
  // 配置图表选项
  const option = {
    title: {
      text: '员工职位统计'
    },
    tooltip: {},
    xAxis: {
      type: 'category',
      data: job.value
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '数量',
        data: value.value,
        type: 'bar'
      }
    ]
  };
  
  // 将配置项应用到图表
  myJobChart.value.setOption(option);
};
const initGenderChart = () => {
  const chartDom = document.getElementById('empGenderChart');
  if (!chartDom) return;
  // 初始化 ECharts 实例
  myGenderChart.value = echarts.init(chartDom);
  
  // 配置图表选项
  const option = {
    title: {
      text: '员工性别统计',
      top:'5px'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '30px',
      left: 'center'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: gender.value
    }
  ]
  };
  
  // 将配置项应用到图表
  myGenderChart.value.setOption(option);
};
// 更新图表数据
const updateJobChart = () => {
  if (myJobChart.value) {
    const option = {
      xAxis: {
        data: job.value
      },
      series: [
        {
          data: value.value
        }
      ]
    };
    myChart.value.setOption(option);
  }
};
const updateGenderChart=()=>{
    if (myGenderChart.value) {
      const option = {
        series: [
          {
            data: gender.value
          }
        ]
      };
      myGenderChart.value.setOption(option);
    }
}
// 处理窗口大小变化
const handleResize = () => {
  // 使图表自适应窗口大小
  if (myJobChart.value) {
    myJobChart.value.resize();
  }
  if (myGenderChart.value) {
    myGenderChart.value.resize();
  }
};

// 查询员工职位报表数据
const QueryEmpJobReport = async () => {
  const result = await getEmpJobReport()
  if (result.code == 1) {
    job.value = result.data.jobList
    value.value = result.data.dataList
    
    // 数据获取完成后更新或初始化图表
    if (myJobChart.value) {
      updateJobChart();
    } else {
      initJobChart();
    }
  }
};

const QueryEmpGenderReport=async()=>{
    const request=await getEmpGenderReport()
    if(request.code==1){
        gender.value=request.data
    }
    if (myGenderChart.value) {
      updateGenderChart();
    } else {
      initGenderChart();
    }
}
// 在组件挂载后初始化图表
onMounted(() => {
  QueryEmpJobReport();
  QueryEmpGenderReport();
  // 监听窗口大小变化，使图表自适应
  window.addEventListener('resize', handleResize);
});

// 在组件销毁前销毁图表实例，释放资源
onBeforeUnmount(() => {
  if (myJobChart.value) {
    myJobChart.value.dispose();
  }
  if (myGenderChart.value) {
    myGenderChart.value.dispose();
  }
  
  // 移除窗口大小变化的监听
  window.removeEventListener('resize', handleResize);
});


</script>

<template>
<h1>员工数据统计</h1>
  <div class="container">
    <div class="chart-wrapper">
    <!-- 图表容器 -->
    <div id="empJobChart" style="width: 100%; height: 400px;"></div>
    <div id="empGenderChart" style="width: 100%; height: 400px;"></div>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  padding: 20px;
}

.chart-wrapper {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}
</style>