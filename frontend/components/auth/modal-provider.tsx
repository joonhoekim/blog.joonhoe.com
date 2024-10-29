// /components/auth/modal-provider.tsx
'use client'

import { createContext, useState } from 'react'
import { LoginModal } from './LoginModal'

interface LoginModalContextType {
    openLoginModal: () => void
    closeLoginModal: () => void
}

export const LoginModalContext = createContext<LoginModalContextType>({
    openLoginModal: () => { },
    closeLoginModal: () => { },
})

export function AuthModalProvider({ children }: { children: React.ReactNode }) {
    const [isLoginModalOpen, setIsLoginModalOpen] = useState(false)

    const openLoginModal = () => {
        console.log('Opening modal...') // 함수가 호출되는지 확인
        setIsLoginModalOpen(true)
        console.log('Modal state after open:', isLoginModalOpen) // 상태 변경 확인
    }

    const closeLoginModal = () => {
        console.log('Closing modal...') // 함수가 호출되는지 확인
        setIsLoginModalOpen(false)
    }

    console.log('Current modal state:', isLoginModalOpen) // 렌더링시 상태 확인

    return (
        <LoginModalContext.Provider value={{ openLoginModal, closeLoginModal }}>
            {children}
            <LoginModal
                isOpen={isLoginModalOpen}
                onClose={closeLoginModal}
            />
        </LoginModalContext.Provider>
    )
}