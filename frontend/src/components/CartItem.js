import { Paper, Typography, Checkbox, Box } from "@mui/material"
import React from "react"

export function CartItem({ cartItem, checked, onChange }) {
  const { product: p } = cartItem
  return (
    <Paper sx={{ p: 2 }} variant="outlined">
      <Box sx={{ display: "flex", gap: 2, alignItems: "start" }}>
        <Checkbox checked={checked} onChange={onChange} />
        <Box>
          <Typography variant="h6">{p.name}</Typography>
          <Typography variant="body2">${p.price}</Typography>
        </Box>
      </Box>
    </Paper>
  )
}
