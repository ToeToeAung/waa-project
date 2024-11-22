import {
  Box,
  Button,
  Checkbox,
  Dialog,
  DialogActions,
  DialogTitle,
  Divider,
  Paper,
  Typography,
} from "@mui/material"
import React, { useState } from "react"
import { CartItem } from "./CartItem"
import { useSelector } from "react-redux"
import { useCheckout } from "../hook/cart"
import { useNavigate } from "react-router-dom"

export function Cart() {
  const navigate = useNavigate()
  const cartItems = useSelector((state) => state.cart.cartItems || [])
  const [checkoutItems, setCheckoutItems] = useState(
    new Set(cartItems.map((c) => c.id)),
  )
  const [openPaymentDialog, setOpenPaymentDialog] = useState(false)
  const checkout = useCheckout()

  const closePaymentDialog = () => {
    setOpenPaymentDialog(false)
  }

  return (
    <Box>
      <Typography variant="h4">Cart</Typography>
      <Paper variant="outlined" sx={{ p: 2 }}>
        {cartItems.length === 0 ? (
          "empty"
        ) : (
          <>
            <Box sx={{ display: "flex", gap: 2, alignItems: "center", ml: 2 }}>
              <Checkbox
                checked={checkoutItems.size === cartItems.length}
                onChange={() => {
                  if (checkoutItems.size === cartItems.length) {
                    setCheckoutItems(new Set())
                  } else {
                    setCheckoutItems(new Set(cartItems.map((c) => c.id)))
                  }
                }}
              />
              <Typography variant="h6">All</Typography>
            </Box>
            <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
              {cartItems.map((c) => (
                <CartItem
                  key={c.id}
                  cartItem={c}
                  checked={checkoutItems.has(c.id)}
                  onChange={() => {
                    if (checkoutItems.has(c.id)) {
                      checkoutItems.delete(c.id)
                    } else {
                      checkoutItems.add(c.id)
                    }
                    setCheckoutItems(new Set(checkoutItems))
                  }}
                />
              ))}
            </Box>
            <Divider sx={{ mt: 2, mb: 2 }} />
            <Box sx={{ ml: 2, display: "flex", gap: 2, alignItems: "center" }}>
              <Typography variant="h6">Total</Typography>
              <Typography>
                $
                {cartItems.reduce((sum, cur) => {
                  if (checkoutItems.has(cur.id)) {
                    sum += cur.product.price * cur.quantity
                  }
                  return sum
                }, 0)}
              </Typography>
            </Box>
            <Button
              fullWidth
              variant="contained"
              disabled={checkoutItems.size === 0}
              onClick={() => setOpenPaymentDialog(true)}
            >
              Checkout
            </Button>
          </>
        )}
      </Paper>
      <Dialog open={openPaymentDialog} onClose={closePaymentDialog}>
        <DialogTitle>Confirm payment</DialogTitle>
        <DialogActions>
          <Button onClick={closePaymentDialog}>Cancle</Button>
          <Button
            onClick={async () => {
              await checkout(Array.from(checkoutItems))
              navigate("/orders")
            }}
          >
            Ok
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  )
}
