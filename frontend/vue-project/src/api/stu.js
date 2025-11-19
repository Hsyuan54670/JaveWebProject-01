import request from '@/utils/request'
export const getStuListApi = (data) => {
    return request.get(`/students?name=${data.name}&degree=${data.degree}&clazzId=${data.clazzId}&page=${data.page}&pageSize=${data.pageSize}`)
}

export const delStuApi = (ids) => {
    return request.delete(`/students/${ids}`)
}
export const addStuApi = (data) => {
    return request.post('/students',data)
}
export const updateStuApi = (data) => {
    return request.put(`/students`,data)
}
export const violationApi = (data,score) => {
    return request.put(`/students/violation/${data.id}/${score}`)
}
export const getStuByIdApi = (id) => {
    return request.get(`/students/${id}`)
}