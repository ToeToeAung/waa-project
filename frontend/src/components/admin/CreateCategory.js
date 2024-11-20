import {
  Box,
  Button,
  List,
  ListItem,
  TextField,
  Typography,
} from "@mui/material"
import React from "react"

const categories = [
  { id: 1, name: "toy" },
  { id: 2, name: "book" },
  { id: 3, name: "clothes" },
]

export function CreateCategory() {
  return (
    <Box>
      <Typography variant="h6">Categories</Typography>
      <TextField label="category name" size="small" />
      <Button sx={{ ml: 2 }} variant="contained">
        Add
      </Button>
      <List>
        {categories.map((c) => (
          <Category key={c.id} category={c} />
        ))}
      </List>
    </Box>
  )
}

function Category({ category }) {
  return (
    <ListItem>
      <Typography variant="body2">{category.name}</Typography>
    </ListItem>
  )
}
