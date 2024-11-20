import axios from "axios"
import { ACCESS_TOKEN } from "../entity/Auth"

// //   "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W10sInN1YiI6InN1ZUBtaXUuZWR1IiwiaWF0IjoxNzMyMDc0MTcxLCJleHAiOjE3MzIwNzUyNTF9.abKoqb_vWzbfM2qFvtfuw4pC1-7slUVQrGWerSb5ZMGBIzC4-axEEiwPzmP0dqsWK5uYuwcSx6UmP5XgcEvxsA"
// const token =
//   "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6W10sInN1YiI6InN1ZUBtaXUuZWR1IiwiaWF0IjoxNzMyMTIyMDcwLCJleHAiOjE3MzIxMjMxNTB9.9R7QTaqIxvLjsZVZK1zdd2-VLH_HT20gCIRBiIjxwxZkSmB_GV5M_v6m2gy8TYLy-XPoeeLHKt4ODpnEBK_4uw"

// localStorage.setItem(ACCESS_TOKEN, token)

const baseURL = "http://localhost:8080/api/v1"

export const publicAxois = axios.create({
  baseURL,
})

export const authAxios = axios.create({
  baseURL,
})

authAxios.interceptors.request.use((config) => {
  return {
    ...config,
    headers: {
      ...config.headers,
      Authorization: `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    },
  }
})
