import ShoppingCartIcon from "@mui/icons-material/ShoppingCart"
import {
  AppBar,
  Badge,
  Button,
  IconButton,
  Toolbar,
  Typography,
} from "@mui/material"
import React from "react"
import { useSelector } from "react-redux"
import { Link, useNavigate } from "react-router-dom"
import {
  USER_ROLE_ADMIN,
  USER_ROLE_BUYER,
  USER_ROLE_SELLER,
} from "../entity/Auth"
import { useLogout } from "../hook/auth"

const routes = [
  {
    name: "products",
    route: "/products",
    roles: [USER_ROLE_BUYER, USER_ROLE_ADMIN],
  },
  { name: "order history", route: "/orders", roles: [USER_ROLE_BUYER] },
  { name: "order", route: "/seller/orders", roles: [USER_ROLE_SELLER] },
  {
    name: "create product",
    route: "/seller/products/new",
    roles: [USER_ROLE_SELLER],
  },
  {
    name: "manage product",
    route: "/seller/products",
    roles: [USER_ROLE_SELLER],
  },
  {
    name: "approve seller",
    route: "/admin/approve-seller",
    roles: [USER_ROLE_ADMIN],
  },
  {
    name: "create category",
    route: "/admin/categories",
    roles: [USER_ROLE_ADMIN],
  },
]

export function Navbar() {
  const me = useSelector((state) => state.auth.me)
  const naviagte = useNavigate()
  const logout = useLogout()

  return (
    <AppBar>
      <Toolbar sx={{ justifyContent: "space-between" }}>
        <div>
          {routes.map((r) => {
            if (
              me?.role &&
              r.roles.findIndex((role) => role === me.role) !== -1
            )
              return (
                <Button
                  key={r.name}
                  component={Link}
                  to={r.route}
                  sx={{ color: "white" }}
                >
                  {r.name}
                </Button>
              )
            return null
          })}
        </div>
        {!me ? (
          <div>
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
        ) : (
          <div>
            {me.role === USER_ROLE_BUYER && (
              <IconButton component={Link} to="/cart" sx={{ color: "white" }}>
                <Badge badgeContent={5} color="error">
                  <ShoppingCartIcon />
                </Badge>
              </IconButton>
            )}

            <Button
              sx={{ color: "white" }}
              onClick={async () => {
                await logout()
                naviagte("/login")
              }}
            >
              Log out
            </Button>
            <Typography sx={{ display: "inline-block" }} variant="caption">
              role: {me.role}
            </Typography>
          </div>
        )}
      </Toolbar>
    </AppBar>
  )
}
