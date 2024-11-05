// middleware.ts
// next.js 미들웨어는 요청과 응답 사이에 위치하는 소프트웨어 컴포넌트로, 요청을 처리하고 응답을 반환하는 과정에서 추가적인 기능을 수행함. (루트에 있어야 자동 인식됨)
// 인증관련 API 경로에 대한 체크나, CSRF 토큰에 대한 검증이나, 보안 헤더에 대한 설정, 백엔드로 요청을 전달하거나, 특정 페이지에 대한 보호 등의 역할을 수행함. (JWT를 사용하므로 CSRF 토큰은 사용하지 않음.)
// 미들웨어에서 별도 처리가 없는 요청/응답은 미들웨어가 없을 때 처리되는 것과 동일하게 처리됨.
// 인증, 인가 처리는 모두 백엔드에서 처리하며, JWT는 HTTP ONLY 쿠키로 백엔드에서 받아온 데이터를 저장함.

import type { NextRequest } from 'next/server'
import { NextResponse } from 'next/server'

export function middleware(request: NextRequest) {
  // API 요청 처리로 api로 시작하는 경우에만 api 서버로 요청 전달
  if (request.nextUrl.pathname.startsWith('/api/')) {
    // HTTP ONLY 쿠키에서 JWT 토큰 추출
    const jwt = request.cookies.get('jwt')

    // 토큰이 존재하는 경우에만 추출하고 헤더에 추가
    const requestHeaders = new Headers(request.headers)
    if (jwt?.value) {
      requestHeaders.set('Authorization', `Bearer ${jwt.value}`)
    }
    // 백엔드로 전달 (인증/인가 실패는 백엔드 응답에 따라 처리)
    return NextResponse.rewrite(
      new URL(request.nextUrl.pathname, process.env.BACKEND_URL),
      {
        request: {
          headers: requestHeaders,
        },
      }
    )
  }
}
