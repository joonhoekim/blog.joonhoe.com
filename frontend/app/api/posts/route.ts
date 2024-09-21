// app/api/posts/route.ts
// 클라이언트가 직접 백엔드로 요청하는 게 아니라, 프론트엔드 서버에서 대신 요청하도록 프록시 구성

import { NextResponse } from 'next/server';
import axios from 'axios';

const BACKEND_URL = process.env.SERVER_URL;

export async function POST(request: Request) {
  try {
    const body = await request.json();
    const response = await axios.post(`${BACKEND_URL}/posts`, body, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    return NextResponse.json(response.data, { status: response.status });
  } catch (error) {
    console.error('Error:', error);
    return NextResponse.json({ message: 'Internal Server Error' }, { status: 500 });
  }
}