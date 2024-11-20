import React, { useEffect } from "react"
import { useSelector } from "react-redux"
import { useNavigate } from "react-router-dom"
import {
  USER_ROLE_ADMIN,
  USER_ROLE_BUYER,
  USER_ROLE_SELLER,
} from "../entity/Auth"

export function Welcome() {
  const me = useSelector((state) => state.auth.me)
  const navigate = useNavigate()

  useEffect(() => {
    const timeId = setTimeout(() => {
      switch (me?.role) {
        case USER_ROLE_ADMIN:
          navigate("/admin/approve-seller")
          return
        case USER_ROLE_SELLER:
          navigate("/seller/orders")
          return
        case USER_ROLE_BUYER:
          navigate("/products")
          return
        default:
          navigate("/login")
      }
    }, 500)
    return () => {
      clearTimeout(timeId)
    }
  }, [me])

  return null
}
