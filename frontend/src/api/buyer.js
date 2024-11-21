import { authAxios } from "./fetcher"

export async function addReview({ productId, content, rating }) {
  return authAxios
    .post(`products/${productId}/reviews`, {
      content,
      rating,
    })
    .then((res) => res.data)
}
