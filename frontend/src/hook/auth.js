import { useCallback, useEffect } from "react"
import { useDispatch } from "react-redux"
import { getMe, login } from "../api/auth"
import { authAction } from "../store"
import { ACCESS_TOKEN } from "../entity/Auth"

export function useInitAuthStore() {
  const dispatch = useDispatch()

  useEffect(() => {
    ;(async () => {
      const accessToken = localStorage.getItem(ACCESS_TOKEN)
      if (!accessToken) return
      try {
        const me = await getMe()
        dispatch(authAction.setMe(me))
      } catch (e) {
        console.error(e)
      }
    })()
  }, [])
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
