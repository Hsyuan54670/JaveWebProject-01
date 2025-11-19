import request from '@/utils/request'
export const getClazzApi= (data)=> {
    return request.get(`/clazzs?name=${data.name}&begin=${data.begin}&end=${data.end}&page=${data.page}&pageSize=${data.pageSize}`)
}

export const delClazzApi= (id)=> {
    return request.delete(`/clazzs/${id}`)
}
export const addClazzApi= (data)=> {
    return request.post('/clazzs',data)
}
export const updateClazzApi= (data)=> {
    return request.put(`/clazzs`,data)
}
export const getClazzByIdApi= (id)=> {
    return request.get(`/clazzs/${id}`)
}
export const getClazzListApi= ()=> {
    return request.get(`/clazzs/list`)
}