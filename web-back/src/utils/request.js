import axios from 'axios'
import { Message } from 'element-ui'

// create an axios instance
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 5000 // request timeout
})

const errorMap = {
  400: '请求失败',
  401: '未授权',
  403: '禁止访问',
  404: '地址未找到',
  499: '未登陆'
}

// respone interceptor
service.interceptors.response.use(function(response) {
  // 正常的请求前拦截,在这里处理
  return response
}, function(error) {
  // 非200请求时的错误处理
  const res = error.response.data // 请求data
  const status = error.response.status // 请求状态吗
  // const message = res.message || (res.errors && res.errors[0].message) // 错误消息
  Message({
    message: errorMap[status] || '未知错误',
    type: 'error',
    duration: 5 * 1000
  })
  console.log(res)
  if (status === 499) {
    // iam 未登录错误
    window.location.href = res.url
  }
  // Do something with response error
  return Promise.reject(error)
})
export default service
