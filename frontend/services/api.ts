import axios, { AxiosInstance, AxiosResponse } from "axios";

// Create Axios Instance
const api: AxiosInstance = axios.create({
  baseURL: process.env.SERVER_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Spring Data REST 에서 자동 생성한 엔드포인트에 대응할 수 있는 유틸리티 만들기

// 응답 인터셉터를 추가하여 Spring Data REST의 응답 구조를 처리합니다.
api.interceptors.response.use((response: AxiosResponse) => {
  // _embedded 속성이 있는 경우 해당 데이터를 반환합니다.
  if (response.data && response.data._embedded) {
    return response.data._embedded;
  }
  return response.data;
});

// Spring Data REST 리소스에 대한 기본 CRUD 작업을 수행하는 유틸리티 함수들
export const springDataRest = {
  getAll: (resource: string) => api.get(`/${resource}`),
  getOne: (resource: string, id: string) => api.get(`/${resource}/${id}`),
  create: (resource: string, data: any) => api.post(`/${resource}`, data),
  update: (resource: string, id: string, data: any) =>
    api.put(`/${resource}/${id}`, data),
  patch: (resource: string, id: string, data: any) =>
    api.patch(`/${resource}/${id}`, data),
  delete: (resource: string, id: string) => api.delete(`/${resource}/${id}`),
};

// 페이지네이션을 처리하는 함수
export const getPage = async (resource: string, page: number, size: number) => {
  const response = await api.get(`/${resource}`, {
    params: { page, size },
  });
  return {
    data: response.data,
    page: response.data.page,
  };
};

// 검색 기능을 처리하는 함수
export const search = (resource: string, searchTerm: string) =>
  api.get(`/${resource}/search`, { params: { q: searchTerm } });

export default api;
