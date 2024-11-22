import { authAxios } from "./fetcher"

export async function createProduct({
  categoryId,
  name,
  description,
  quantity,
  price,
}) {
  return authAxios
    .post("/products", {
      name,
      categoryId,
      description,
      quantity,
      price,
    })
    .then((res) => res.data)
}

export async function updateStock({ productId, quantity }) {
  return authAxios
    .put(`/products?id=${productId}&quantity=${quantity}`)
    .then((res) => res.data)
}

export async function getProducts({ onlyOutOfStock = false }) {
  return authAxios
    .get("/products/seller", {
      params: { stockQty: onlyOutOfStock ? 0 : null },
    })
    .then((res) => res.data)
}

export async function deleteProductById(id) {
  return authAxios.delete(`/products/${id}`).then((res) => res.data)
}

export async function getOrderItems({ orderStatus }) {
  return authAxios
    .get("/orders/seller", { params: { status: orderStatus } })
    .then((res) => res.data)
}

export async function changeOrderItemStatus({ orderItemId, status }) {
  return authAxios
    .put("/orders/order-items/status", null, {
      params: { id: orderItemId, status },
    })
    .then((res) => res.data)
}
