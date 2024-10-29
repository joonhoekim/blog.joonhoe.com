// src/lib/api.ts

import axios from 'axios';
import { auth } from './auth';

const api = axios.create({
  baseURL: '/api'
});

// 요청 인터셉터에서 토큰 추가
api.interceptors.request.use((config) => {
  const token = auth.getToken();
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// 응답 인터셉터에서 401 에러 처리
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      auth.removeToken();
      // 필요한 경우 로그인 모달 열기
      // modalContext.openLoginModal(); // 전역 상태 관리를 통해 처리
    }
    return Promise.reject(error);
  }
);

export default api;