import { Noto_Sans_KR, Nanum_Myeongjo } from "next/font/google";

//variable = next/font 에서 생성한 클래스네임

export const sansSerif = Noto_Sans_KR({
  subsets: ["latin"],
  weight: ["100", "400", "700", "900"],
  variable: "--font-sans",
  display: "swap",
});

export const serif = Nanum_Myeongjo({
  subsets: ["latin"],
  weight: ["400", "700"],
  variable: "--font-serif",
  display: "swap",
});
