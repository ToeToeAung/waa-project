import { Box, Button, FormControlLabel, Switch } from "@mui/material"
import React, { useState } from "react"
import { Link } from "react-router-dom"
import { SellerProduct } from "./SellerProduct"

const getProducts = () => {
  return Promise.resolve(
    Array(10)
      .fill(null)
      .map((_, idx) => ({
        id: idx + 1,
        name: `product-${idx + 1}`,
        description:
          "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
        quantity: 10,
        price: 20,
        sellerId: 1,
        categoryId: 3,
        rating: 2.5,
      })),
  )
}

export function SellerProducts() {
  const [products, setProducts] = useState([])
  useState(() => {
    getProducts().then((res) => {
      setProducts(res)
    })
  }, [])

  return (
    <Box>
      <Button sx={{ mb: 4 }} component={Link} to="new" variant="contained">
        Add product
      </Button>
      <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
        <FormControlLabel control={<Switch />} label="show only out of stock" />

        {products.map((p) => (
          <SellerProduct key={p.id} product={p} />
        ))}
      </Box>
    </Box>
  )
}
