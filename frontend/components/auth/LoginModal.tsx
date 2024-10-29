// src/components/auth/LoginModal.tsx
'use client'

import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { auth } from "@/lib/auth";
import { useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";

interface LoginModalProps {
    isOpen: boolean;
    onClose: () => void;
}

export function LoginModal({ isOpen, onClose }: LoginModalProps) {
    const [loading, setLoading] = useState(false);
    const searchParams = useSearchParams();

    useEffect(() => {
        const token = searchParams.get('token');
        const email = searchParams.get('email');
        const name = searchParams.get('name');

        if (token && email && name) {
            auth.handleAuthCallback(token, email, name);
            // URL에서 파라미터 제거
            window.history.replaceState({}, '', window.location.pathname);
            onClose();
        }
    }, [searchParams, onClose]);

    const handleGoogleLogin = () => {
        setLoading(true);
        const returnUrl = encodeURIComponent(window.location.href);
        // 백엔드 OAuth2 엔드포인트로 직접 리다이렉트
        window.location.href = `/api/auth/oauth2/authorize/google?state=${returnUrl}`;
    };

    return (
        <Dialog open={isOpen} onOpenChange={onClose}>
            <DialogContent className="sm:max-w-[425px]">
                <DialogHeader>
                    <DialogTitle>로그인</DialogTitle>
                </DialogHeader>
                <div className="flex flex-col items-center space-y-4 py-4">
                    <Button
                        variant="outline"
                        onClick={handleGoogleLogin}
                        disabled={loading}
                        className="w-full flex items-center justify-center"
                    >
                        <svg
                            className="mr-2 h-4 w-4"
                            aria-hidden="true"
                            focusable="false"
                            data-prefix="fab"
                            data-icon="google"
                            role="img"
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 488 512"
                        >
                            <path
                                fill="currentColor"
                                d="M488 261.8C488 403.3 391.1 504 248 504 110.8 504 0 393.2 0 256S110.8 8 248 8c66.8 0 123 24.5 166.3 64.9l-67.5 64.9C258.5 52.6 94.3 116.6 94.3 256c0 86.5 69.1 156.6 153.7 156.6 98.2 0 135-70.4 140.8-106.9H248v-85.3h236.1c2.3 12.7 3.9 24.9 3.9 41.4z"
                            ></path>
                        </svg>
                        Google로 계속하기
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
}