// app/auth/callback/google/page.tsx
'use client'

import { auth } from "@/lib/auth"
import { useRouter, useSearchParams } from 'next/navigation'
import { useEffect } from 'react'

export default function GoogleCallback() {
    const searchParams = useSearchParams()
    const router = useRouter()

    useEffect(() => {
        const token = searchParams.get('token')
        const email = searchParams.get('email')
        const name = searchParams.get('name')

        if (token && email && name) {
            auth.handleAuthCallback(token, email, name)
            // 원래 페이지로 리다이렉트
            const state = searchParams.get('state')
            const returnUrl = state ? decodeURIComponent(state) : '/'
            router.push(returnUrl)
        }
    }, [searchParams, router])

    return <div>로그인 처리중...</div>
}