// src/lib/auth.ts
import { User } from "@/types/auth";

const TOKEN_KEY = 'token';
const USER_KEY = 'user';

export const auth = {
  setToken(token: string) {
    localStorage.setItem(TOKEN_KEY, token);
  },
  
  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  },
  
  setUser(user: User) {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
  },
  
  getUser(): User | null {
    const userStr = localStorage.getItem(USER_KEY);
    return userStr ? JSON.parse(userStr) : null;
  },
  
  removeToken() {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
  },
  
  isAuthenticated(): boolean {
    return !!this.getToken();
  },

  handleAuthCallback(token: string, email: string, name: string): void {
    this.setToken(token);
    this.setUser({ email, name });
  },

  logout() {
    this.removeToken();
  }
};