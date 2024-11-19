import { CssBaseline } from "@mui/material"
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom"
import { Login } from "./components/Login"
import { PageLayout } from "./components/PageLayout"
import { Products } from "./components/Products"
import { ProductDetails } from "./components/ProductDetails"
import { Cart } from "./components/Cart"
import { ApproveSeller } from "./components/admin/ApproveSeller"
import { Register } from "./components/Register"
import { SellerProducts } from "./components/seller/SellerProducts"
import { SellerEditProduct } from "./components/seller/SellerEditProduct"
import { SellerAddProduct } from "./components/seller/SellerAddProduct"
import { SellerOrders } from "./components/seller/SellerOrders"
import { OrderHistory } from "./components/OrderHistory"

function App() {
  return (
    <>
      <BrowserRouter>
        <CssBaseline />
        <Routes>
          <Route path="*" element={<Navigate to="/login" replace />} />
          <Route element={<PageLayout />}>
            <Route path="/products">
              <Route index element={<Products />} />
              <Route path=":id" element={<ProductDetails />} />
            </Route>
            <Route path="/orders" element={<OrderHistory />} />
            <Route path="/register" element={<Register role="buyer" />} />
            <Route path="/login" element={<Login />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/admin">
              <Route path="approve-seller" element={<ApproveSeller />} />
            </Route>
            <Route path="/seller">
              <Route path="register" element={<Register role="seller" />} />
              <Route path="products">
                <Route index element={<SellerProducts />} />
                <Route path=":id" element={<SellerEditProduct />} />
                <Route path="new" element={<SellerAddProduct />} />
              </Route>
              <Route path="orders" element={<SellerOrders />} />
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
