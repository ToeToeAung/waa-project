import { Box, Button, FormControlLabel, Switch } from "@mui/material"
import React, { useCallback, useEffect, useState } from "react"
import { Link } from "react-router-dom"
import { SellerProduct } from "./SellerProduct"
import { deleteProductById, getProducts } from "../../api/seller"
import { useAlert } from "../../hook/alert"
import { ERR_UNKNOWN } from "../../entity/error"

export function SellerProducts() {
  const [products, setProducts] = useState([])
  const [showOnlyOutOfStock, setShowOnlyOutOfStock] = useState(true)
  const alert = useAlert()

  const syncProduct = useCallback(() => {
    getProducts({ onlyOutOfStock: showOnlyOutOfStock }).then((res) => {
      setProducts(res)
    })
  }, [setProducts, showOnlyOutOfStock])

  useEffect(() => {
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
        <FormControlLabel
          control={
            <Switch
              checked={showOnlyOutOfStock}
              onChange={(e) => {
                setShowOnlyOutOfStock(e.target.checked)
              }}
            />
          }
          label="show only out of stock"
        />

        {products.map((p) => (
          <SellerProduct key={p.id} product={p} onDelete={onDelete} />
        ))}
      </Box>
    </Box>
  )
}
