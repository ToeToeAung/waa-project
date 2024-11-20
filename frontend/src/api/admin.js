import { authAxios } from "./fetcher"

export async function createCategory({ name }) {
  return authAxios.post("/categories", { name }).then((res) => res.data)
}

export async function deleteReview({ productId, reviewId }) {
  return authAxios
    .delete(`/products/reviews?productId=${productId}&reviewId=${reviewId}`)
    .then((res) => res.data)
}
