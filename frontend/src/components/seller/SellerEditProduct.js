import { Box, Button, Paper, TextField, Typography } from "@mui/material"
import React, { useEffect, useState } from "react"
import { useParams } from "react-router-dom"

const getProduct = (id) => {
  return Promise.resolve({
    id,
    name: "product 123",
    category: "category-1",
    description:
      "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
    quantity: 20,
    price: 120,
    sellerId: 1,
    rating: 3.5,
  })
}

export function SellerEditProduct() {
  const { id } = useParams()
  const [product, setProduct] = useState()

  useEffect(() => {
    getProduct(id).then((res) => {
      setProduct(res)
    })
  }, [id])

  return (
    <Paper sx={{ width: 500, p: 4, ml: "auto", mr: "auto" }} variant="outlined">
      {!product ? (
        "loading..."
      ) : (
        <>
          <Typography variant="h5">Edit product</Typography>
          <Typography variant="h6">{product.name}</Typography>
          <Typography variant="body1">
            Dscription: {product.description}
          </Typography>
          <Typography variant="body1">Category: {product.category}</Typography>
          <Typography variant="body1">Price: ${product.price}</Typography>
          <Box sx={{ display: "flex", flexDirection: "column", gap: 2, mt: 2 }}>
            <TextField
              label="quantity"
              value={product.quantity}
              type="number"
            />
            <Button variant="contained">Update</Button>
          </Box>
        </>
      )}
    </Paper>
  )
}
