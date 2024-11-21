import { Delete } from "@mui/icons-material"
import {
  Paper,
  Typography,
  Checkbox,
  Box,
  TextField,
  Divider,
  IconButton,
} from "@mui/material"
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
          <TextField
            sx={{ mt: 2, width: 80 }}
            label="quantity"
            value={p.quantity}
            size="small"
            type="number"
          />
        </Box>
        <IconButton sx={{ ml: "auto" }}>
          <Delete />
        </IconButton>
      </Box>
      <Divider sx={{ mt: 2, mb: 2 }} />
      <Typography sx={{ ml: 4 }} variant="body2">
        ${p.price * p.quantity}
      </Typography>
    </Paper>
  )
}
