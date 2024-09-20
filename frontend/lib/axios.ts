//axios 사용 환경을 설정함
import axios from 'axios'

const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_SERVER_URL,
  headers: {
    'Content-Type': 'application/json; charset=UTF-8',
  },
})

export default api
