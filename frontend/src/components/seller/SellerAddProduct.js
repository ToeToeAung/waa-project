import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextareaAutosize,
  TextField,
  Typography,
} from "@mui/material"
import React from "react"

const categories = [
  { id: 1, name: "category-1" },
  { id: 2, name: "category-2" },
  { id: 3, name: "category-3" },
]
//id: Number, name: String, category: String, description: String, quantity: Number, price: Number, sellerId: Number, categoryId: Number
export function SellerAddProduct() {
  return (
    <Box
      sx={{
        width: 500,
        display: "flex",
        flexDirection: "column",
        gap: 2,
        ml: "auto",
        mr: "auto",
      }}
    >
      <Typography variant="h5">Add product</Typography>
      <FormControl fullWidth>
        <InputLabel id="category">Category</InputLabel>
        <Select label="Category" labelId="category">
          {categories.map((c) => (
            <MenuItem value={c.id}>{c.name}</MenuItem>
          ))}
        </Select>
      </FormControl>
      <TextField fullWidth label="name" />
      <Box sx={{ display: "flex", flexDirection: "column" }}>
        <Typography variant="caption">Description</Typography>
        <TextareaAutosize minRows={5} />
      </Box>
      <TextField fullWidth label="quantity" type="number" />
      <TextField fullWidth label="price" type="number" />
      <Button variant="contained">Add</Button>
    </Box>
  )
}
