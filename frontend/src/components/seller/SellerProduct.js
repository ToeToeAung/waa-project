import { Box, Button, Paper, Typography } from "@mui/material"
import React from "react"
import { Link } from "react-router-dom"

export function SellerProduct({ product: p }) {
  return (
    <Paper
      sx={{ p: 2, display: "flex", justifyContent: "space-between" }}
      variant="outlined"
    >
      <Box>
        <Typography variant="h6">{p.name}</Typography>
        <Typography variant="body2">quantity {p.quantity}</Typography>
        <Typography variant="body2">price ${p.price}</Typography>
      </Box>
      <Box>
        <Button variant="outlined" color="error">
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
