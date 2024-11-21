import { Box, Button, Paper, TextField, Typography } from "@mui/material"
import React, { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom"
import { getProductById } from "../../api/public"
import { updateStock } from "../../api/seller"
import { useAlert } from "../../hook/alert"
import { ERR_UNKNOWN } from "../../entity/error"

export function SellerEditProduct() {
  const alert = useAlert()
  const navigate = useNavigate()
  const { id } = useParams()
  const [product, setProduct] = useState()
  const [updateQuantity, setUpdateQuantity] = useState("")

  useEffect(() => {
    getProductById(id).then((res) => {
      setProduct(res)
      setUpdateQuantity(res.quantity)
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
          <Typography variant="body1">
            Category: {product.category.name}
          </Typography>
          <Typography variant="body1">Price: ${product.price}</Typography>
          <Box sx={{ display: "flex", flexDirection: "column", gap: 2, mt: 2 }}>
            <TextField
              label="quantity"
              value={updateQuantity}
              onChange={(e) => setUpdateQuantity(e.target.value)}
              type="number"
            />
            <Button
              variant="contained"
              disabled={updateQuantity == product.quantity}
              onClick={async () => {
                try {
                  await updateStock({ productId: id, quantity: updateQuantity })
                  alert({ msg: "stock updated", level: "success" })
                  navigate("..")
                } catch (e) {
                  alert({ msg: ERR_UNKNOWN, level: "error" })
                }
              }}
            >
              Update
            </Button>
          </Box>
        </>
      )}
    </Paper>
  )
}
