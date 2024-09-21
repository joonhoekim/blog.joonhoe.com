import Image from "next/image";
import Link from "next/link";
import {Card} from "@/components/ui/card";
import {Button} from "@/components/ui/button";

export default function Home() {
  return (
    <>
      <main>
        <p>Welcome to blllg</p>

        <ul>
          <li>
            <Link href={"/edit"}><Button>edit</Button></Link>
          </li>
          <li>
            <Link href={"/posts"}><Button>posts</Button></Link>
          </li>
          <li>
            <Link href={"/posts/edit"}><Button>posts/edit</Button></Link>
          </li>
          <li>
            <Link href={"/posts/view"}><Button>posts/view</Button></Link>
          </li>
        </ul>
      </main>
    </>
  );
}
