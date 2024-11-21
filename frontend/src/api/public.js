import { publicAxois } from "./fetcher"

export async function getCategories() {
  return publicAxois.get("/categories").then((res) => res.data)
}

export async function getProducts() {
  return publicAxois.get("/products/filter").then((res) => res.data)
}

export async function getProductById(id) {
  return publicAxois.get(`products/${id}`).then((res) => res.data)
}

export async function getReviews({ productId }) {
  return publicAxois
    .get(`products/reviews/${productId}`)
    .then((res) => res.data)
}
