import { configureStore } from "@reduxjs/toolkit"
import { authSlice } from "./slices/authSlice"
import { cartSlice } from "./slices/cartSlice"

export const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
    cart: cartSlice.reducer,
  },
})

export const authAction = authSlice.actions
export const cartAction = cartSlice.actions
