import type { Metadata } from "next";
import { Noto_Sans_KR, Nanum_Myeongjo } from "next/font/google";
import "./globals.css";
import Link from "next/link";

// const inter = Inter({ subsets: ["latin"] });
const notoSansKr = Noto_Sans_KR({
  subsets: ["latin"],
  weight: ["100", "400", "700", "900"],
  variable: "--font-noto-sans-kr",
  display: "swap",
});

const nanumMyeongjo = Nanum_Myeongjo({
  subsets: ["latin"],
  weight: ["400", "700"],
  variable: "--font-nanum-myeongjo",
  display: "swap",
});

export const metadata: Metadata = {
  title: "blllg",
  description: "blllg is self-hostable web app inspired by obsidian.",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={`${notoSansKr.variable} ${nanumMyeongjo.variable}`}>
        <header className="flex items-center justify-between">
          <Link href="/">HOME</Link>
        </header>
        {children}
      </body>
    </html>
  );
}
