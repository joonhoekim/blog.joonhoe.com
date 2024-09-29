import type { Metadata } from "next";

//설정
import { siteConfig } from '@/config/site'
import { cn } from '@/lib/utils'
import "./globals.css";

//font
import {serif, sansSerif} from '@/lib/fonts'

//커스텀 컴포넌트
import { SiteHeader } from '@/components/layout/site-header'
import { TailwindIndicator } from '@/components/layout/tailwind-indicator'
import { ThemeProvider } from '@/components/layout/theme-provider'

export const metadata: Metadata = {
  title: {
    default: siteConfig.name,
    template: `%s - ${siteConfig.name}`,
  },
  description: siteConfig.description,
  themeColor: [
    { media: '(prefers-color-scheme: light)', color: 'white' },
    { media: '(prefers-color-scheme: dark)', color: 'black' },
  ],
  icons: {
    icon: '/favicon.ico',
    shortcut: '/favicon-16x16.png',
    apple: '/apple-touch-icon.png',
  },
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
      <ThemeProvider attribute="class" defaultTheme="system" enableSystem>
        <div className="relative flex min-h-screen flex-col">
          <SiteHeader />
          <div className="flex-1">{children}</div>
        </div>
        <TailwindIndicator />
      </ThemeProvider>
      </body>
    </html>
  );
}
