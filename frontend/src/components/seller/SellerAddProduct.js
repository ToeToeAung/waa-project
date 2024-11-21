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
import React, { useEffect, useReducer, useState } from "react"
import { getCategories } from "../../api/public"
import { createProduct } from "../../api/seller"
import { useAlert } from "../../hook/alert"
import { ERR_UNKNOWN } from "../../entity/error"

const SET_CATEGORY_ID = "set_category_id"
const SET_DESCRIPTION = "set_description"
const SET_QUANTITY = "set_quantity"
const SET_PRICE = "set_price"
const SET_NAME = "set_name"
const CLEAR_FORM = "clear_form"

const emptyForm = {
  categoryId: "",
  name: "",
  description: "",
  quantity: "",
  price: "",
}

export function SellerAddProduct() {
  const alert = useAlert()
  const [categories, setCategories] = useState([])
  const [state, dispatch] = useReducer((state, action) => {
    switch (action.type) {
      case SET_CATEGORY_ID:
        return { ...state, categoryId: action.categoryId }
      case SET_DESCRIPTION:
        return { ...state, description: action.description }
      case SET_QUANTITY:
        return { ...state, quantity: action.quantity }
      case SET_PRICE:
        return { ...state, price: action.price }
      case SET_NAME:
        return { ...state, name: action.name }
      case CLEAR_FORM:
        return { ...emptyForm }
      default:
        return state
    }
  }, emptyForm)

  useEffect(() => {
    getCategories().then((res) => setCategories(res))
  }, [])

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
        <Select
          label="Category"
          labelId="category"
          value={state.categoryId}
          onChange={(e) => {
            dispatch({ type: SET_CATEGORY_ID, categoryId: e.target.value })
          }}
        >
          {categories.map((c) => (
            <MenuItem key={c.id} value={c.id}>
              {c.name}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <TextField
        fullWidth
        label="name"
        value={state.name}
        onChange={(e) => dispatch({ type: SET_NAME, name: e.target.value })}
      />
      <Box sx={{ display: "flex", flexDirection: "column" }}>
        <Typography variant="caption">Description</Typography>
        <TextareaAutosize
          minRows={5}
          value={state.description}
          onChange={(e) => {
            dispatch({ type: SET_DESCRIPTION, description: e.target.value })
          }}
        />
      </Box>
      <TextField
        fullWidth
        label="quantity"
        type="number"
        value={state.quantity}
        onChange={(e) => {
          dispatch({ type: SET_QUANTITY, quantity: e.target.value })
        }}
      />
      <TextField
        fullWidth
        label="price"
        type="number"
        value={state.price}
        onChange={(e) => {
          dispatch({ type: SET_PRICE, price: e.target.value })
        }}
      />
      <Button
        variant="contained"
        onClick={async () => {
          try {
            await createProduct({
              categoryId: state.categoryId,
              name: state.name,
              description: state.description,
              quantity: state.quantity,
              price: state.price,
            })
            dispatch({ type: CLEAR_FORM })
            alert({ msg: "product created", level: "success" })
          } catch (e) {
            alert({ msg: ERR_UNKNOWN, level: "error" })
          }
        }}
      >
        Add
      </Button>
    </Box>
  )
}
