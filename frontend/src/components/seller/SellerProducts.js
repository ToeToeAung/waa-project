import { Box, Button, FormControlLabel, Switch } from "@mui/material"
import React, { useCallback, useState } from "react"
import { Link } from "react-router-dom"
import { SellerProduct } from "./SellerProduct"
import { deleteProductById, getProducts } from "../../api/seller"
import { useAlert } from "../../hook/alert"
import { ERR_UNKNOWN } from "../../entity/error"

export function SellerProducts() {
  const [products, setProducts] = useState([])
  const alert = useAlert()

  const syncProduct = useCallback(() => {
    getProducts().then((res) => {
      setProducts(res)
    })
  }, [getProducts, setProducts])

  useState(() => {
    syncProduct()
  }, [syncProduct])

  const onDelete = async (productId) => {
    try {
      await deleteProductById(productId)
      alert({ msg: "product deleted", level: "success" })
      syncProduct()
    } catch (e) {
      alert({ msg: ERR_UNKNOWN, level: "error" })
    }
  }

  return (
    <Box>
      <Button sx={{ mb: 4 }} component={Link} to="new" variant="contained">
        Add product
      </Button>
      <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
        <FormControlLabel control={<Switch />} label="show only out of stock" />

        {products.map((p) => (
          <SellerProduct key={p.id} product={p} onDelete={onDelete} />
        ))}
      </Box>
    </Box>
  )
}
