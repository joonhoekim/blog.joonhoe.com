'use client'
import { siteConfig } from '@/config/site'

// ui components
import { MainNav } from '@/components/main-nav'
import { ThemeToggle } from '@/components/theme-toggle'
import { Button } from '@/components/ui/button'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"

// react hooks
import { useContext } from 'react'

// OAuth
import { LoginModalContext } from '@/components/auth/modal-provider'
import { useAuth } from '@/contexts/AuthContext'

export function SiteHeader() {
  const { openLoginModal } = useContext(LoginModalContext);
  const { isAuthenticated, logout, user } = useAuth();  // user 정보도 useAuth에서 가져옴

  return (
    <header className="bg-background sticky top-0 z-40 w-full border-b">
      <div className="container flex h-16 items-center space-x-4 sm:justify-between sm:space-x-0">
        <MainNav items={siteConfig.mainNav} />
        <div className="flex flex-1 items-center justify-end space-x-4">
          <nav className="flex items-center space-x-1">
            {isAuthenticated && user ? (
              <DropdownMenu>
                <DropdownMenuTrigger asChild>
                  <Button variant="outline" className="gap-2">
                    {user.name}
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent align="end">
                  <DropdownMenuItem onClick={logout}>
                    로그아웃
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            ) : (
              <Button
                variant="outline"
                onClick={openLoginModal}
              >
                로그인
              </Button>
            )}
            <ThemeToggle />
          </nav>
        </div>
      </div>
    </header>
  )
}