import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <main>
      <Link href={"/editor"}>editor</Link>
      <p>Welcome to blllg!!</p>
    </main>
  );
}
