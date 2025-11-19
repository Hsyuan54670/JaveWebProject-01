import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'
//创建axios实例
const service = axios.create({
    baseURL: '/api',
    timeout: 5000
})

//响应拦截器
service.interceptors.response.use(
    response => {
        return response.data
    },
    error => {
        if(error.response.status===401){
            ElMessage.error('登录状态错误，请重新登录')
            router.push('/login')
        }else{
            ElMessage.error('访问异常')
        }
        return Promise.reject(error)
    }
)
//请求拦截器
service.interceptors.request.use(
    config => {
        const data=JSON.parse (localStorage.getItem('loginUser'))
        if(data&&data.token){
            config.headers['token']=data.token
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)


export default service