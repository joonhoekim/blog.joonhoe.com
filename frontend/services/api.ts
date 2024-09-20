import api from '@/lib/axios'

// axios api 호출 함수를 재사용 하기 쉽게 정리함

// 예시
//export const createUser = (signUpData) => api.post('/user', signUpData)
//export const getUser = (id) => api.get(`/user/${id}`)
//export const putUser = (id, putData) => api.put(`/user/${id}`, putData)
//export const deleteUser = (id) => api.delete(`/user/${id}`)
//export const findAllUsers = () => api.get(`/user/findAll`)

// POST 관련
export const getPostById = (id: number) => {api.get(`/posts/${id}`)}
export const createPost = (postData: any) => api.post(`/posts/post`, postData)
export const deletePost = (id: number) => {api.delete(`/posts/${id}`)}