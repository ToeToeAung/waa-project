import { createSlice } from "@reduxjs/toolkit"

export const cartSlice = createSlice({
  name: "cart",
  initialState: {},
  reducers: {
    setCart(state, action) {
      state.cartItems = action.payload
    },
  },
})
