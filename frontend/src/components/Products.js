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
  TextField,
  Typography,
} from "@mui/material"
import React, { useEffect, useReducer, useState } from "react"
import { Link } from "react-router-dom"
import { Product } from "./Product"
import { getCategories } from "../api/public"
import { getProducts } from "../api/public"

const PAGE_SIZE = 4

export function Products() {
  const [productData, setProductData] = useState(null)
  const [page, setPage] = useState(1)
  const [filter, setFilter] = useState({})

  useEffect(() => {
    getProducts({ ...filter, page, pageSize: PAGE_SIZE }).then((res) =>
      setProductData(res),
    )
  }, [page, filter])

  return (
    <Box sx={{ display: "flex", gap: 4, justifyContent: "space-between" }}>
      <Filter onFilter={(filter) => setFilter(filter)} />
      {!productData ? (
        "loading"
      ) : (
        <Box sx={{ flexGrow: 1 }}>
          <Grid2 container spacing={2}>
            {productData.content.map((p) => (
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
            count={productData.totalPages}
            color="primary"
            page={page}
            onChange={(e, v) => setPage(v)}
          />
        </Box>
      )}
    </Box>
  )
}

// const categories = [
//   { id: 1, name: "category-1" },
//   { id: 2, name: "category-2" },
//   { id: 3, name: "category-3" },
// ]

const SET_CATEGORY_FILTER_ID = "set_category_filter_id"
const SET_PRICE_FILTER_GT = "set_price_filter_gt"
const SET_PRICE_FILTER_LT = "set_price_filter_lt"
const SET_RATING_FILTER_GT = "set_rating_filter_gt"
const SET_RATING_FILTER_LT = "set_rating_filter_lt"

function Filter({ onFilter }) {
  const [categories, setCategories] = useState([])
  const [state, dispatch] = useReducer(
    (state, action) => {
      switch (action.type) {
        case SET_CATEGORY_FILTER_ID:
          return { ...state, categoryId: action.categoryId }
        case SET_PRICE_FILTER_GT:
          return { ...state, priceGt: action.priceGt }
        case SET_PRICE_FILTER_LT:
          return { ...state, priceLt: action.priceLt }
        case SET_RATING_FILTER_GT:
          return { ...state, ratingGt: action.ratingGt }
        case SET_RATING_FILTER_LT:
          return { ...state, ratingLt: action.ratingLt }
        default:
          return state
      }
    },
    {
      categoryId: "",
      priceGt: "",
      priceLt: "",
      ratingGt: "",
      ratingLt: "",
    },
  )

  useEffect(() => {
    getCategories().then((res) => setCategories(res))
  }, [])

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
          <Select
            label="Category"
            labelId="category"
            value={state.categoryId}
            onChange={(e) => {
              dispatch({
                type: SET_CATEGORY_FILTER_ID,
                categoryId: e.target.value,
              })
            }}
          >
            <MenuItem>no filter</MenuItem>
            {categories.map((c) => (
              <MenuItem value={c.id}>{c.name}</MenuItem>
            ))}
          </Select>
        </FormControl>
        <Box sx={{ display: "flex", gap: 2 }}>
          <TextField
            type="number"
            label="price >"
            value={state.priceGt}
            onChange={(e) =>
              dispatch({ type: SET_PRICE_FILTER_GT, priceGt: e.target.value })
            }
          />
          <TextField
            type="number"
            label="price <"
            value={state.priceLt}
            onChange={(e) =>
              dispatch({ type: SET_PRICE_FILTER_LT, priceLt: e.target.value })
            }
          />
        </Box>
        <Box sx={{ display: "flex", gap: 2 }}>
          <TextField
            type="number"
            label="rating >"
            value={state.ratingGt}
            onChange={(e) =>
              dispatch({ type: SET_RATING_FILTER_GT, ratingGt: e.target.value })
            }
          />
          <TextField
            type="number"
            label="rating <"
            value={state.ratingLt}
            onChange={(e) =>
              dispatch({ type: SET_RATING_FILTER_LT, ratingLt: e.target.value })
            }
          />
        </Box>
        <Button
          variant="contained"
          onClick={() => {
            onFilter(state)
          }}
        >
          Apply
        </Button>
      </Paper>
    </Box>
  )
}
