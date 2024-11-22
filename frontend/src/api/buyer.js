import { authAxios } from "./fetcher"

export async function addReview({ productId, content, rating }) {
  return authAxios
    .post(`products/${productId}/reviews`, {
      content,
      rating,
    })
    .then((res) => res.data)
}

export async function getCart() {
  return authAxios.get("/cart").then((res) => res.data)
}

export async function addItemToCart({ productId, quantity }) {
  return authAxios
    .post("/cart/items", {
      productId,
      quantity,
    })
    .then((res) => res.data)
}

export async function deleteItemFromCart({ itemId }) {
  return authAxios.delete(`/cart/items/${itemId}`).then((res) => res.data)
}
