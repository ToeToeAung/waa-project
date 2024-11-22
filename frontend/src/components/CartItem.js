import { Delete } from "@mui/icons-material"
import {
  Box,
  Checkbox,
  Divider,
  IconButton,
  Paper,
  TextField,
  Typography,
} from "@mui/material"
import React, { useEffect, useState } from "react"
import { useDispatch } from "react-redux"
import { deleteItemFromCart } from "../api/buyer"
import { ERR_UNKNOWN } from "../entity/error"
import { useAlert } from "../hook/alert"
import { useAddItemToCart } from "../hook/cart"
import { cartAction } from "../store"

export function CartItem({ cartItem, checked, onChange, onDelete }) {
  const { product: p } = cartItem
  const [quantity, setQuantity] = useState(cartItem.quantity)
  const dispatch = useDispatch()
  const alert = useAlert()
  const addItemToCart = useAddItemToCart()

  useEffect(() => {
    if (quantity === cartItem.quantity) return
    const timeId = setTimeout(async () => {
      addItemToCart({ productId: p.id, quantity })
    }, 500)

    return () => {
      clearTimeout(timeId)
    }
  }, [quantity, addItemToCart, p.id, cartItem.quantity])

  return (
    <Paper sx={{ p: 2 }} variant="outlined">
      <Box sx={{ display: "flex", gap: 2, alignItems: "start" }}>
        <Checkbox checked={checked} onChange={onChange} />
        <Box>
          <Typography variant="h6">{p.name}</Typography>
          <Typography variant="body2">${Number(p.price).toFixed(2)}</Typography>
          <TextField
            sx={{ mt: 2, width: 80 }}
            label="quantity"
            value={quantity}
            size="small"
            type="number"
            onChange={(e) => setQuantity(e.target.value)}
          />
        </Box>
        <IconButton
          sx={{ ml: "auto" }}
          onClick={async () => {
            try {
              const cart = await deleteItemFromCart({ itemId: cartItem.id })
              dispatch(cartAction.setCart(cart?.cartItems || []))
              onDelete?.(cartItem.id)
              alert({ msg: "removed from cart", level: "success" })
            } catch (e) {
              alert({ msg: ERR_UNKNOWN, level: "error" })
            }
          }}
        >
          <Delete />
        </IconButton>
      </Box>
      <Divider sx={{ mt: 2, mb: 2 }} />
      <Typography sx={{ ml: 4 }} variant="body2">
        ${Number(p.price * cartItem.quantity).toFixed(2)}
      </Typography>
    </Paper>
  )
}
