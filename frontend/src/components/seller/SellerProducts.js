import { Box, Button, FormControlLabel, Switch } from "@mui/material"
import React, { useCallback, useEffect, useState } from "react"
import { Link } from "react-router-dom"
import { deleteProductById, getProducts } from "../../api/seller"
import { ERR_PRODUCT_ALREADY_BOUGTH } from "../../entity/error"
import { useAlert } from "../../hook/alert"
import { SellerProduct } from "./SellerProduct"

export function SellerProducts() {
  const [products, setProducts] = useState([])
  const [showOnlyOutOfStock, setShowOnlyOutOfStock] = useState(false)
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
      alert({ msg: ERR_PRODUCT_ALREADY_BOUGTH, level: "error" })
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
