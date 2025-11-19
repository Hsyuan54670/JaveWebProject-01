import request from '@/utils/request'

export const getEmpJobReport = () => {
    return request.get('/report/empJobData')
}

export const getEmpGenderReport = () => {
    return request.get('/report/empGenderData')
}

export const getStuDegreeReport = () => {
    return request.get('/report/studentDegreeData')
}
export const getStuCountReport = () => {
    return request.get('/report/studentCountData')
}

export const getLogReport = (data) => {
    return request.get(`/log/page?page=${data.page}&pageSize=${data.pageSize}`)
}