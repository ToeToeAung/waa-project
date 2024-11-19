import React, { useEffect, useState } from "react"
import { Product } from "./Product"
import {
  Box,
  Button,
  FormControl,
  Grid2,
  InputLabel,
  MenuItem,
  Pagination,
  Paper,
  Select,
  Slider,
  TextField,
  Typography,
} from "@mui/material"
import { Link } from "react-router-dom"

const getProducts = ({ page, pageSize }) => {
  return Promise.resolve({
    products: Array(pageSize)
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
    totalPage: 10,
  })
}

const PAGE_SIZE = 8

export function Products() {
  const [productData, setProductData] = useState(null)
  useEffect(() => {
    getProducts({ page: 1, pageSize: PAGE_SIZE }).then((res) =>
      setProductData(res),
    )
  }, [])

  return (
    <Box sx={{ display: "flex", gap: 4, justifyContent: "space-between" }}>
      <Filter />
      {!productData ? (
        "loading"
      ) : (
        <Box>
          <Grid2 container spacing={2}>
            {productData.products.map((p) => (
              <Grid2 size={3}>
                <Link
                  key={p.id}
                  to={`${p.id}`}
                  style={{ textDecoration: "none" }}
                >
                  <Product product={p} />
                </Link>
              </Grid2>
            ))}
          </Grid2>
          <Pagination
            sx={{ marginTop: 2 }}
            count={productData.totalPage}
            color="primary"
          />
        </Box>
      )}
    </Box>
  )
}

const categories = [
  { id: 1, name: "category-1" },
  { id: 2, name: "category-2" },
  { id: 3, name: "category-3" },
]

function Filter() {
  const [rating, setRating] = useState([0, 5])

  return (
    <Box>
      <Paper
        sx={{
          width: 300,
          padding: 2,
          display: "flex",
          flexDirection: "column",
          gap: 2,
        }}
        variant="outlined"
      >
        <Typography variant="h5" sx={{ pb: 2 }}>
          Filter
        </Typography>
        <FormControl fullWidth>
          <InputLabel id="category">Category</InputLabel>
          <Select label="Category" labelId="category">
            <MenuItem>no filter</MenuItem>
            {categories.map((c) => (
              <MenuItem value={c.id}>{c.name}</MenuItem>
            ))}
          </Select>
        </FormControl>
        <Box sx={{ display: "flex", gap: 2 }}>
          <TextField label="price >" />
          <TextField label="price <" />
        </Box>
        <Box sx={{ display: "flex", gap: 2 }}>
          <TextField label="rating >" />
          <TextField label="rating <" />
        </Box>
        <Button variant="contained">Apply</Button>
      </Paper>
    </Box>
  )
}
