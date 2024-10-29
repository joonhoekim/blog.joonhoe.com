import { User } from "@/types/auth";

// src/lib/auth.ts
export const login = () => {
  window.location.href = 'http://localhost:8080/oauth2/authorization/google';
};

export const handleAuthCallback = async (
  token: string,
  email: string,
  name: string
): Promise<void> => {
  if (token) {
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify({ email, name }));
  }
};

export const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
};

export const getUser = (): User | null => {
  const userStr = localStorage.getItem('user');
  return userStr ? JSON.parse(userStr) : null;
};

export const getToken = (): string | null => {
  return localStorage.getItem('token');
};