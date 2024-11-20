import { authAxios } from "./fetcher"

// TODO: remove userId
export async function createProduct({
  categoryId,
  description,
  quantity,
  price,
}) {
  return authAxios
    .post(`/products?categoryId=${categoryId}&userId=${1}`, {
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
