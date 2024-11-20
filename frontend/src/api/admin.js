import { authAxios } from "./fetcher"

export async function createCategory({ name }) {
  return authAxios.post("/categories", { name }).then((res) => res.data)
}

export async function deleteReview({ productId, reviewId }) {
  return authAxios
    .delete(`/products/reviews?productId=${productId}&reviewId=${reviewId}`)
    .then((res) => res.data)
}

export async function getPendingSeller() {
  return authAxios.get("/users/sellers").then((res) => res.data)
}

export async function approveSeller({ sellerId }) {
  return authAxios.put(`/users/sellers/${sellerId}`).then((res) => res.data)
}
