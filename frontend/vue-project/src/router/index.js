import { createRouter, createWebHistory } from 'vue-router'

// 导入页面组件
import Login from '../views/login/index.vue'
import Emp from '../views/emp/index.vue'
import Dept from '../views/dept/index.vue'
import Clazz from '../views/clazz/index.vue'
import Stu from '../views/stu/index.vue'
import Log from '../views/log/index.vue'
import stuReport from '../views/report/stu/index.vue'
import empReport from '../views/report/emp/index.vue'
import Index from '../views/index/index.vue'
import layout from '../views/layout/index.vue'

// 定义路由
const routes = [
    {path:'/',redirect:"/layout",},
    {path:'/login',name:'Login',component:Login},
    {
        path: '/layout',
        name:'Layout',
        component:layout,
        redirect:"/index",
        children:[
            {path:'/index',name:'Index',component:Index},
            {path:'/emp',name:'Emp',component:Emp},
            {path:'/dept',name:'Dept',component:Dept},
            {path:'/clazz',name:'Clazz',component:Clazz},
            {path:'/stu',name:'Stu',component:Stu},
            {path:'/log',name:'Log',component:Log},
            {path:'/empReport',name:'empReport',component:empReport},
            {path:'/stuReport',name:'stuReport',component:stuReport}
        ]
    }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router