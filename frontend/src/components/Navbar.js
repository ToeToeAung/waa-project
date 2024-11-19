import Ract from "react"
import { AppBar, Badge, Button, IconButton, Toolbar } from "@mui/material"
import { Link } from "react-router-dom"
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart"

const routes = [
  { name: "products", route: "/products" },
  { name: "order history", route: "/orders" },
  { name: "order", route: "/seller/orders" },
  { name: "create product", route: "/seller/products/new" },
  { name: "manage product", route: "/seller/products" },
  { name: "approve seller", route: "/admin/approve-seller" },
]

export function Navbar() {
  return (
    <AppBar>
      <Toolbar sx={{ justifyContent: "space-between" }}>
        <div>
          {routes.map((r) => (
            <Button
              key={r.name}
              component={Link}
              to={r.route}
              sx={{ color: "white" }}
            >
              {r.name}
            </Button>
          ))}
        </div>
        <div>
          <IconButton component={Link} to="/cart" sx={{ color: "white" }}>
            <Badge badgeContent={5} color="error">
              <ShoppingCartIcon />
            </Badge>
          </IconButton>
          <Button
            component={Link}
            to="/seller/register"
            sx={{ color: "white" }}
          >
            Become seller
          </Button>
          <Button component={Link} to="/register" sx={{ color: "white" }}>
            register
          </Button>
          <Button component={Link} to="/login" sx={{ color: "white" }}>
            Login
          </Button>
        </div>
      </Toolbar>
    </AppBar>
  )
}
