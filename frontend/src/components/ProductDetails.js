import {
  Box,
  Button,
  Divider,
  Paper,
  Rating,
  TextField,
  Typography,
} from "@mui/material"
import React, { useCallback, useEffect, useReducer, useState } from "react"
import { useSelector } from "react-redux"
import { useParams } from "react-router-dom"
import { deleteReview } from "../api/admin"
import { addReview } from "../api/buyer"
import { getProductById, getReviews } from "../api/public"
import { USER_ROLE_ADMIN, USER_ROLE_BUYER } from "../entity/Auth"
import { ERR_EMPTY, ERR_UNKNOWN } from "../entity/error"
import { useAlert } from "../hook/alert"
import { useAddItemToCart } from "../hook/cart"

const SET_CONTENT = "set_content"
const SET_RATING = "set_rating"
const CLEAR_FORM = "clear_form"
const SET_ERROR = "set_error"

const emptyForm = {
  content: "",
  rating: "",
}

export function ProductDetails() {
  const { id } = useParams()
  const me = useSelector((state) => state.auth.me)
  const addItemToCart = useAddItemToCart()
  const [quantity, setQuantity] = useState(1)
  const alert = useAlert()
  const [product, setProduct] = useState()
  const [reviews, setReviews] = useState()
  const [state, dispatch] = useReducer((state, action) => {
    switch (action.type) {
      case SET_CONTENT:
        return { ...state, content: action.content }
      case SET_RATING:
        return { ...state, rating: action.rating }
      case CLEAR_FORM:
        return { ...emptyForm }
      case SET_ERROR:
        return { ...state, error: action.error }
      default:
        return state
    }
  }, emptyForm)

  useEffect(() => {
    getProductById(id).then((res) => setProduct(res))
  }, [id])

  const syncReview = useCallback(() => {
    getReviews({ productId: id }).then((res) => setReviews(res))
  }, [id])

  useEffect(() => {
    syncReview()
  }, [syncReview])

  const validateForm = () => {
    const error = {}
    if (!state.content) {
      error.content = ERR_EMPTY
    }
    return error
  }

  return (
    <Box>
      {!product ? (
        "loading..."
      ) : (
        <>
          <Typography variant="h5">{product.name}</Typography>
          <Typography variant="h6">Category</Typography>
          <Typography variant="body2">{product.category.name}</Typography>
          <Typography variant="h6">Rating</Typography>
          <Rating readOnly value={product.overAllRating} precision={0.5} />
          <Divider sx={{ my: 2 }} />
          <Typography variant="body1">{product.description}</Typography>
        </>
      )}
      {me?.role === USER_ROLE_BUYER && (
        <>
          <Box sx={{ display: "flex", gap: 2, alignItems: "center" }}>
            <Button
              variant="contained"
              onClick={async () => {
                try {
                  await addItemToCart({ productId: product.id, quantity })
                  alert({ msg: "added to cart", level: "success" })
                } catch (e) {
                  console.error(e)
                }
              }}
            >
              Add to cart
            </Button>
            <TextField
              label="quantity"
              type="number"
              size="small"
              sx={{ width: 80 }}
              value={quantity}
              onChange={(e) => {
                setQuantity(e.target.value)
              }}
            />
          </Box>
          <Typography variant="h6" sx={{ mt: 4 }}>
            Add review
          </Typography>
          <Box sx={{ display: "flex", flexDirection: "column", gap: 1 }}>
            <Rating
              precision={0.5}
              value={state.rating}
              onChange={(e, v) => dispatch({ type: SET_RATING, rating: v })}
            />
            <TextField
              error={!!state.error?.content}
              helperText={state.error?.content}
              multiline
              minRows={3}
              value={state.content}
              onChange={(e) =>
                dispatch({ type: SET_CONTENT, content: e.target.value })
              }
            />
          </Box>
          <Button
            sx={{ mt: 1 }}
            variant="outlined"
            onClick={async () => {
              const error = validateForm()
              if (Object.keys(error).length !== 0) {
                dispatch({ type: SET_ERROR, error })
                return
              }
              dispatch({ type: SET_ERROR, error: null })
              try {
                await addReview({
                  productId: id,
                  content: state.content,
                  rating: state.rating,
                })
                alert({ msg: "review added", level: "success" })
                dispatch({ type: CLEAR_FORM })
                syncReview()
              } catch (e) {
                alert({ msg: ERR_UNKNOWN, level: "error" })
              }
            }}
          >
            Submit
          </Button>
        </>
      )}
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
                {me?.role === USER_ROLE_ADMIN && (
                  <Button
                    sx={{ mt: 2 }}
                    color="error"
                    variant="outlined"
                    onClick={async () => {
                      try {
                        await deleteReview({ productId: id, reviewId: r.id })
                        alert({ msg: "deleted review", level: "success" })
                        syncReview()
                      } catch (e) {
                        alert({ msg: ERR_UNKNOWN, level: "error" })
                      }
                    }}
                  >
                    Delete
                  </Button>
                )}
              </Paper>
            ))}
          </Box>
        </>
      )}
    </Box>
  )
}
