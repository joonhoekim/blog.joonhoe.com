// src/types/auth.ts
export interface User {
  email: string;
  name: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}