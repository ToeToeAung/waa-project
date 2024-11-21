import { Box, Button, Chip, Paper, Typography } from "@mui/material"
import React from "react"
import { Link } from "react-router-dom"
import { deleteProductById } from "../../api/seller"

export function SellerProduct({ product: p, onDelete }) {
  return (
    <Paper
      sx={{ p: 2, display: "flex", justifyContent: "space-between" }}
      variant="outlined"
    >
      <Box>
        <Box sx={{ display: "flex", gap: 2 }}>
          <Typography variant="h6">{p.name}</Typography>
          {p.quantity == 0 && <Chip label="out of stock" size="small" />}
        </Box>
        <Typography variant="body2">quantity {p.quantity}</Typography>
        <Typography variant="body2">price ${p.price}</Typography>
      </Box>
      <Box>
        <Button variant="outlined" color="error" onClick={() => onDelete(p.id)}>
          Delete
        </Button>
        <Button
          sx={{ ml: 2 }}
          component={Link}
          to={`${p.id}`}
          variant="outlined"
        >
          Edit
        </Button>
      </Box>
    </Paper>
  )
}
