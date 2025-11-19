<script setup>
// 导入 ECharts 库
import * as echarts from 'echarts';
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { getStuDegreeReport, getStuCountReport } from '@/api/report'

const myClazzChart = ref()
const myDegreeChart=ref()
const clazz = ref([])
const value = ref([])
const degree=ref([])
const initClazzChart = () => {
  const chartDom = document.getElementById('stuClazzChart');
  if (!chartDom) return;
  // 初始化 ECharts 实例
  myClazzChart.value = echarts.init(chartDom);
  
  // 配置图表选项
  const option = {
    title: {
      text: '学生班级统计'
    },
    tooltip: {},
    xAxis: {
      type: 'category',
      data: clazz.value
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
  myClazzChart.value.setOption(option);
};
const initDegreeChart = () => {
  const chartDom = document.getElementById('stuDegreeChart');
  if (!chartDom) return;
  // 初始化 ECharts 实例
  myDegreeChart.value = echarts.init(chartDom);
  
  // 配置图表选项
  const option = {
    title: {
      text: '学生学历统计',
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
      data: degree.value
    }
  ]
  };
  
  // 将配置项应用到图表
  myDegreeChart.value.setOption(option);
};
// 更新图表数据
const updateClazzChart = () => {
  if (myClazzChart.value) {
    const option = {
      xAxis: {
        data: clazz.value
      },
      series: [
        {
          data: value.value
        }
      ]
    };
    myClazzChart.value.setOption(option);
  }
};
const updateDegreeChart=()=>{
    if (myDegreeChart.value) {
      const option = {
        series: [
          {
            data: degree.value
          }
        ]
      };
      myDegreeChart.value.setOption(option);
    }
}
// 处理窗口大小变化
const handleResize = () => {
  // 使图表自适应窗口大小
  if (myClazzChart.value) {
    myClazzChart.value.resize();
  }
  if (myGenderChart.value) {
    myDegreeChart.value.resize();
  }
};

// 查询员工职位报表数据
const QueryStuCountReport = async () => {
  const result = await getStuCountReport()
  if (result.code == 1) {
    clazz.value = result.data.clazzList
    value.value = result.data.dataList
    
    // 数据获取完成后更新或初始化图表
    if (myClazzChart.value) {
      updateClazzChart();
    } else {
      initClazzChart();
    }
  }
};

const QueryStuDegreeReport=async()=>{
    const request=await getStuDegreeReport()
    if(request.code==1){
        degree.value=request.data
    }
    if (myDegreeChart.value) {
      updateDegreeChart();
    } else {
      initDegreeChart();
    }
}
// 在组件挂载后初始化图表
onMounted(() => {
  QueryStuCountReport();
  QueryStuDegreeReport();
  // 监听窗口大小变化，使图表自适应
  window.addEventListener('resize', handleResize);
});

// 在组件销毁前销毁图表实例，释放资源
onBeforeUnmount(() => {
  if (myClazzChart.value) {
    myClazzChart.value.dispose();
  }
  if (myDegreeChart.value) {
    myDegreeChart.value.dispose();
  }
  
  // 移除窗口大小变化的监听
  window.removeEventListener('resize', handleResize);
});


</script>

<template>
<h1>学生数据统计</h1>
  <div class="container">
    <div class="chart-wrapper">
    <!-- 图表容器 -->
    <div id="stuClazzChart" style="width: 100%; height: 400px;"></div>
    <div id="stuDegreeChart" style="width: 100%; height: 400px;"></div>
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