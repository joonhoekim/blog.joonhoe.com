// src/app/page.tsx
import GoogleLoginButton from '@/components/auth/GoogleLoginButton';

export default function LoginPage() {
    return (
        <div className="flex min-h-screen items-center justify-center">
            <div className="w-full max-w-md space-y-8 p-6">
                <div className="text-center">
                    <h2 className="text-3xl font-bold tracking-tight">
                        계정 로그인
                    </h2>
                </div>
                <div className="mt-8 space-y-6">
                    <GoogleLoginButton />
                </div>
            </div>
        </div>
    );
}