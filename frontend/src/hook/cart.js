import { useCallback } from "react"
import { addItemToCart, checkout, getCart } from "../api/buyer"
import { cartAction } from "../store"
import { useDispatch } from "react-redux"

export function useAddItemToCart() {
  const dispatch = useDispatch()

  return useCallback(
    async ({ productId, quantity }) => {
      try {
        const cart = await addItemToCart({
          productId,
          quantity,
        })
        dispatch(cartAction.setCart(cart?.cartItems || []))
      } catch (e) {
        console.error(e)
      }
    },
    [dispatch, addItemToCart],
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
    [dispatch, checkout, getCart],
  )
}
