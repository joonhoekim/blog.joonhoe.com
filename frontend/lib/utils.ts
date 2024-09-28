//shadcn-ui 컴포넌트들이 사용하는 코드임 (cn 함수)
import { type ClassValue, clsx } from "clsx";
import { twMerge } from "tailwind-merge";

// cn 함수는 여러 클래스네임들을 받아서 최적화하는 역할을 한다.

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}
