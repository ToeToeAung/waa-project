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

export async function getProducts() {
  return authAxios.get("/products/seller").then((res) => res.data)
}

export async function deleteProductById(id) {
  return authAxios.delete(`/products/${id}`).then((res) => res.data)
}
