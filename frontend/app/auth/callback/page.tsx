// src/app/auth/callback/page.tsx
'use client';

import { handleAuthCallback } from '@/lib/auth';
import { useRouter, useSearchParams } from 'next/navigation';
import { useEffect } from 'react';

export default function AuthCallback() {
    const router = useRouter();
    const searchParams = useSearchParams();

    useEffect(() => {
        const token = searchParams.get('token');
        const email = searchParams.get('email');
        const name = searchParams.get('name');

        if (token && email && name) {
            handleAuthCallback(token, email, name)
                .then(() => {
                    router.push('/dashboard'); // 로그인 성공 후 리다이렉트할 페이지
                })
                .catch((error) => {
                    console.error('Authentication error:', error);
                    router.push('/login?error=auth');
                });
        } else {
            router.push('/login?error=missing_params');
        }
    }, [router, searchParams]);

    return (
        <div className="flex items-center justify-center min-h-screen">
            <div className="animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-gray-900" />
        </div>
    );
}