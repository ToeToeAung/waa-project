import {
  Box,
  Card,
  CardContent,
  Paper,
  Rating,
  Typography,
} from "@mui/material"
import React from "react"

export function Product({ product }) {
  return (
    <Card variant="outlined" sx={{ width: 200 }}>
      <CardContent>
        <Typography component="h2" variant="h5">
          {product.name}
        </Typography>
        <Typography component="p" variant="body2">
          {product.description}
        </Typography>
        <Box sx={{ display: "flex", gap: 2, alignItems: "center" }}>
          <Rating value={product.rating} readOnly precision={0.5} />
          <Typography variant="body2">${product.price}</Typography>
        </Box>
      </CardContent>
    </Card>
  )
}
