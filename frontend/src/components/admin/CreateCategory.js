import {
  Box,
  Button,
  List,
  ListItem,
  TextField,
  Typography,
} from "@mui/material"
import React, { useEffect, useState } from "react"
import { getCategories } from "../../api/public"
import { createCategory } from "../../api/admin"

export function CreateCategory() {
  const [categories, setCategories] = useState([])
  const [newCategoryName, setNewCategoryName] = useState("")

  useEffect(() => {
    getCategories().then((res) => setCategories(res))
  }, [])

  return (
    <Box>
      <Typography variant="h6">Categories</Typography>
      <TextField
        label="category name"
        size="small"
        value={newCategoryName}
        onChange={(e) => setNewCategoryName(e.target.value)}
      />
      <Button
        sx={{ ml: 2 }}
        variant="contained"
        onClick={async () => {
          await createCategory({ name: newCategoryName })
          const categories = await getCategories()
          setCategories(categories)
          setNewCategoryName("")
        }}
      >
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
