import { Edit } from "@mui/icons-material"
import {
  Box,
  Chip,
  IconButton,
  Menu,
  MenuItem,
  Paper,
  ToggleButton,
  ToggleButtonGroup,
  Typography,
} from "@mui/material"
import React, { useMemo, useState } from "react"
import {
  ORDER_ALL,
  ORDER_PENDING,
  ORDER_SHIPPING,
  ORDER_DELIVERED,
  ORDER_CANCLED,
} from "../../entity/OrderStatus"

const orderItems = [
  {
    id: 1,
    quantity: 10,
    state: ORDER_PENDING,
    product: {
      id: 1,
      name: "product 123",
      category: "category-1",
      description:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
      quantity: 20,
      price: 120,
      sellerId: 1,
      rating: 3.5,
    },
  },
  {
    id: 2,
    quantity: 10,
    state: ORDER_SHIPPING,
    product: {
      id: 1,
      name: "product 123",
      category: "category-1",
      description:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
      quantity: 20,
      price: 120,
      sellerId: 1,
      rating: 3.5,
    },
  },
  {
    id: 3,
    quantity: 10,
    state: ORDER_DELIVERED,
    product: {
      id: 1,
      name: "product 123",
      category: "category-1",
      description:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
      quantity: 20,
      price: 120,
      sellerId: 1,
      rating: 3.5,
    },
  },
  {
    id: 4,
    quantity: 10,
    state: ORDER_CANCLED,
    product: {
      id: 1,
      name: "product 123",
      category: "category-1",
      description:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
      quantity: 20,
      price: 120,
      sellerId: 1,
      rating: 3.5,
    },
  },
]

export function SellerOrders() {
  const [orderStatus, setOrderStatus] = useState(ORDER_ALL)

  return (
    <Box>
      <ToggleButtonGroup
        color="primary"
        value={orderStatus}
        onChange={(e) => {
          setOrderStatus(e.target.value)
        }}
      >
        <ToggleButton value={ORDER_ALL}>All</ToggleButton>
        <ToggleButton value={ORDER_PENDING}>Pending</ToggleButton>
        <ToggleButton value={ORDER_SHIPPING}>Shipping</ToggleButton>
        <ToggleButton value={ORDER_DELIVERED}>Delivered</ToggleButton>
        <ToggleButton value={ORDER_CANCLED}>Cancled</ToggleButton>
      </ToggleButtonGroup>
      <Box sx={{ display: "flex", flexDirection: "column", gap: 2, mt: 2 }}>
        {orderItems.map((o) => (
          <Order key={o.id} order={o} />
        ))}
      </Box>
    </Box>
  )
}

function Order({ order: o }) {
  return (
    <Paper sx={{ p: 2 }} variant="outlined">
      <Box sx={{ display: "flex", justifyContent: "space-between" }}>
        <Box>
          <Typography variant="h6">{o.product.name}</Typography>
          <Typography variant="body2">qty: {o.quantity}</Typography>
        </Box>
        <Box>
          <OrderState state={o.state} />
          <Action order={o} />
        </Box>
      </Box>
    </Paper>
  )
}

function OrderState({ state }) {
  switch (state) {
    case ORDER_PENDING:
      return <Chip color="primary" label="pending" size="small" />
    case ORDER_SHIPPING:
      return <Chip color="warning" label="shipping" size="small" />
    case ORDER_DELIVERED:
      return <Chip color="success" label="delivered" size="small" />
    case ORDER_CANCLED:
      return <Chip color="error" label="cancled" size="small" />
  }
}

function Action({ order: o }) {
  const [anchorEl, setAnchorEl] = useState(null)
  const actions = useMemo(() => {
    const res = []

    if (o.state === ORDER_PENDING) res.push("ship")
    res.push("deliver", "cancle")
    return res
  }, [o.state])

  if (o.state === ORDER_CANCLED || o.state === ORDER_DELIVERED) return null

  return (
    <>
      <IconButton onClick={(e) => setAnchorEl(e.currentTarget)}>
        <Edit />
      </IconButton>
      <Menu
        open={Boolean(anchorEl)}
        onClose={() => setAnchorEl(null)}
        anchorEl={anchorEl}
      >
        {actions.map((a) => (
          <MenuItem key={a}>{a}</MenuItem>
        ))}
      </Menu>
    </>
  )
}
