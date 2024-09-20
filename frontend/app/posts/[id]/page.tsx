import { springDataRest } from "@/services/api";

export default async function PostPage({ params }: { params: { id: string } }) {
  const getPost = () => springDataRest.getOne("posts", "2");

  let post = await getPost();
  // 사용 시
  getPost()
    .then((response) => {
      console.log(response.data);
    })
    .catch((error) => {
      console.error("Error:", error);
    });

  return (
    <article>
      <h1>{post.title}</h1>
      <p>{post.content}</p>
    </article>
  );
}
