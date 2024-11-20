import { ACCESS_TOKEN } from "../entity/Auth"
import { authAxios, publicAxois } from "./fetcher"

export async function getMe() {
  return authAxios.get("/users/me").then((res) => res.data)
}

export async function login({ username, password }) {
  return publicAxois
    .post("/authenticate", {
      username,
      password,
    })
    .then((res) => {
      localStorage.setItem(ACCESS_TOKEN, res.data.accessToken)
      return res.data
    })
}

export async function logout() {
  localStorage.removeItem(ACCESS_TOKEN)
}

export async function register({
  username,
  password,
  role,
  address: { street, city, state, zip, country },
}) {
  return publicAxois.post("/register", {
    username,
    password,
    role,
    address: {
      street,
      city,
      state,
      zip,
      country,
    },
  })
}
