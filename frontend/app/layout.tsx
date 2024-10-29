// /app/layout.tsx

// nextjs metadata
import type { Metadata } from "next";

// classname 처리 도구 + 폰트 + global css
import { sansSerif, serif } from '@/lib/fonts';
import { cn } from '@/lib/utils';
import "./globals.css";

// 설정 가져오기
import { siteConfig } from '@/config/site';

//커스텀 컴포넌트
import { SiteHeader } from '@/components/site-header';

// tailwind 설정
import { TailwindIndicator } from '@/components/tailwind-indicator';
import { ThemeProvider } from '@/components/theme-provider';

//OAuth and login modal
import { AuthModalProvider } from "@/components/auth/modal-provider";
import { AuthProvider } from '@/contexts/AuthContext';

export const metadata: Metadata = {
  title: {
    default: siteConfig.name,
    template: `%s - ${siteConfig.name}`,
  },
  description: siteConfig.description,
  icons: {
    icon: '/favicon.ico',
    shortcut: '/favicon-16x16.png',
    apple: '/apple-touch-icon.png',
  },
}

// nextjs 14 부터 themeColor는 뷰포트 객체에서 설정해줘야 함
export const viewport = {
  themeColor: [
    { media: '(prefers-color-scheme: light)', color: 'white' },
    { media: '(prefers-color-scheme: dark)', color: 'black' },
  ],
  width: 'device-width',
  initialScale: 1,
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={cn(
        'min-h-screen bg-background font-sans antialiased',
        serif.variable, sansSerif.variable
      )}>

        <AuthProvider> {/* 전역 AUTH 관리 */}
          <ThemeProvider attribute="class" defaultTheme="system" enableSystem>
            <AuthModalProvider> {/* 로그인 모달 프로바이더 */}
              <div className="relative flex min-h-screen flex-col">
                <SiteHeader />
                <div className="flex-1">

                  {children}

                </div>
              </div>
            </AuthModalProvider>
            <TailwindIndicator />
          </ThemeProvider>
        </AuthProvider>
      </body>
    </html>
  );
}
