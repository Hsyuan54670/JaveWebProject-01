import request from '@/utils/request'

export const queryEmpListApi = (data) => {
    return request.get(`/emps?name=${data.name}&gender=${data.gender}&begin=${data.begin}&end=${data.end}&page=${data.page}&pageSize=${data.pageSize}`)
}

export const addEmpApi = (data) => {
    return request.post(`/emps`,data)
}
export const queryEmpByIdApi = (id) => {
    return request.get(`/emps/${id}`)
}
export const updateEmpApi = (data) => {
    return request.put(`/emps`,data)
}
export const delEmpByIdApi = (ids) => {
    return request.delete(`/emps?ids=${ids}`)
}
export const queryMasterListApi = () => {
    return request.get(`/emps/list`)
}

export const changePasswordApi = (data) => {
    return request.put(`/emps/changePassword`,data)
}