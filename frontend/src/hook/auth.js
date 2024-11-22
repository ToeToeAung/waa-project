import { useCallback, useEffect } from "react"
import { useDispatch } from "react-redux"
import { getMe, login, logout } from "../api/auth"
import { authAction, cartAction } from "../store"
import { ACCESS_TOKEN, USER_ROLE_BUYER } from "../entity/Auth"
import { getCart } from "../api/buyer"

export function useInitAuthStore() {
  const dispatch = useDispatch()

  useEffect(() => {
    ;(async () => {
      const accessToken = localStorage.getItem(ACCESS_TOKEN)
      if (!accessToken) return
      try {
        const me = await getMe()
        dispatch(authAction.setMe(me))
        if (me.role === USER_ROLE_BUYER) {
          const cart = await getCart()
          dispatch(cartAction.setCart(cart?.cartItems || []))
        }
      } catch (e) {
        console.error(e)
      }
    })()
  }, [dispatch])
}

export function useLogin() {
  const dispatch = useDispatch()

  return useCallback(
    async ({ username, password }) => {
      await login({ username, password })
      const me = await getMe()
      dispatch(authAction.setMe(me))
    },
    [dispatch],
  )
}

export function useLogout() {
  const dispatch = useDispatch()

  return useCallback(async () => {
    await logout()
    dispatch(authAction.setMe(null))
    dispatch(cartAction.setCart(null))
  }, [dispatch])
}
