import request from '@/utils/request'

export const queryDeptListApi = () => {
    return request.get('/depts')
}

export const addDeptApi = (data) => {
    return request.post('/depts',data)
}
export const queryDeptByIdApi = (id) => {
    return request.get(`/depts/${id}`)
}
export const editDeptApi = (data) => {
    return request.put('/depts',data)
}
export const delDeptByIdApi = (id) => {
    return request.delete(`/depts?id=${id}`)
}