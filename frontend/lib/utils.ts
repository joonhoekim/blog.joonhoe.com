//shadcn-ui 컴포넌트들이 사용하는 코드임 (cn 함수)
import { type ClassValue, clsx } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}
