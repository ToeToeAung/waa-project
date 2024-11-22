import { ORDER_CANCLED } from "../entity/OrderStatus"
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

export async function checkout(cartItemIds) {
  return authAxios.post("/orders/cart", cartItemIds).then((res) => res.data)
}

export async function getOrderHistory() {
  return authAxios.get("/orders/buyer").then((res) => res.data)
}

export async function cancleOrder(id) {
  return authAxios
    .put("/orders/order-items/status", null, {
      params: { id, status: ORDER_CANCLED },
    })
    .then((res) => res.data)
}
