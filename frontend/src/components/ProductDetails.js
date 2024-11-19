import {
  Box,
  Button,
  Paper,
  Divider,
  Rating,
  TextareaAutosize,
  Typography,
  TextField,
} from "@mui/material"
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

const getReviews = (productId) => {
  return Promise.resolve([
    { id: 1, content: "good", rating: 4.5 },
    { id: 1, content: "bad", rating: 2.5 },
    { id: 1, content: "very good", rating: 5 },
  ])
}

export function ProductDetails() {
  const { id } = useParams()
  const [product, setProduct] = useState()
  const [reviews, setReviews] = useState()

  useEffect(() => {
    getProduct(id).then((res) => setProduct(res))
  }, [id])

  useEffect(() => {
    getReviews(id).then((res) => setReviews(res))
  }, [id])

  return (
    <Box>
      {!product ? (
        "loading..."
      ) : (
        <>
          <Typography variant="h5">{product.name}</Typography>
          <Typography variant="h6">Category</Typography>
          <Typography variant="body2">{product.category}</Typography>
          <Typography variant="h6">Rating</Typography>
          <Rating readOnly value={product.rating} precision={0.5} />
          <Divider sx={{ my: 2 }} />
          <Typography variant="body1">{product.description}</Typography>
          <Box sx={{ display: "flex", gap: 2, alignItems: "center" }}>
            <Button variant="contained">Add to cart</Button>
            <TextField
              label="quantity"
              type="number"
              size="small"
              sx={{ width: 80 }}
              value={1}
            />
          </Box>
        </>
      )}
      <Typography variant="h6" sx={{ mt: 4 }}>
        Add review
      </Typography>
      <Box sx={{ display: "flex", flexDirection: "column", gap: 1 }}>
        <Rating precision={0.5} />
        <TextareaAutosize minRows={5} />
      </Box>
      {reviews && (
        <>
          <Typography variant="h6" sx={{ mt: 4 }}>
            Reviews
          </Typography>
          <Box sx={{ display: "flex", flexDirection: "column", gap: 2, mb: 4 }}>
            {reviews.map((r) => (
              <Paper variant="outlined" sx={{ padding: 2 }}>
                <Rating readOnly value={r.rating} precision={0.5} />
                <Typography>{r.content}</Typography>
              </Paper>
            ))}
          </Box>
        </>
      )}
    </Box>
  )
}
