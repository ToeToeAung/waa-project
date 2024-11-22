import { useCallback } from "react"
import { addItemToCart, checkout, getCart } from "../api/buyer"
import { cartAction } from "../store"
import { useDispatch } from "react-redux"

export function useAddItemToCart() {
  const dispatch = useDispatch()

  return useCallback(
    async ({ productId, quantity }) => {
      const cart = await addItemToCart({
        productId,
        quantity,
      })
      dispatch(cartAction.setCart(cart?.cartItems || []))
    },
    [dispatch],
  )
}

export function useCheckout() {
  const dispatch = useDispatch()

  return useCallback(
    async (cartItemIds) => {
      await checkout(cartItemIds)
      const cart = await getCart()
      dispatch(cartAction.setCart(cart?.cartItems || []))
    },
    [dispatch],
  )
}
