// next.config.mjs
/** @type {import('next').NextConfig} */
const nextConfig = {
  async rewrites() {
     return [
      // 나머지 api 요청은 백엔드로 리라이트
      {
        source: '/api/:path*',
        destination: `${process.env.SERVER_URL}/auth/:path*/api/:path*`
      }
    ]
  }
};

export default nextConfig;