import { Box, Card, CardContent, Rating, Typography } from "@mui/material"
import React from "react"

export function Product({ product }) {
  return (
    <Card variant="outlined" sx={{ width: 200 }}>
      <CardContent>
        <Typography
          component="h2"
          variant="h5"
          sx={{
            textOverflow: "ellipsis",
            overflow: "hidden",
            whiteSpace: "nowrap",
          }}
        >
          {product.name}
        </Typography>
        <Typography
          component="p"
          variant="body2"
          sx={{
            textOverflow: "ellipsis",
            overflow: "hidden",
            whiteSpace: "nowrap",
          }}
        >
          {product.description}
        </Typography>
        <Rating value={product.overAllRating} readOnly precision={0.5} />
        <Typography variant="body2">${product.price}</Typography>
      </CardContent>
    </Card>
  )
}
