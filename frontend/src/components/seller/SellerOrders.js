import React, { useMemo, useState, useEffect, useCallback } from "react"
import { Edit } from "@mui/icons-material"
import {
  Box,
  IconButton,
  Menu,
  MenuItem,
  Paper,
  ToggleButton,
  ToggleButtonGroup,
  Typography,
} from "@mui/material"
import {
  ORDER_ALL,
  ORDER_CANCLED,
  ORDER_DELIVERED,
  ORDER_PENDING,
  ORDER_SHIPPING,
} from "../../entity/OrderStatus"
import { OrderState } from "../OrderState"
import { changeOrderItemStatus, getOrderItems } from "../../api/seller"

export function SellerOrders() {
  const [orderItems, setOrderItems] = useState([])
  const [orderStatus, setOrderStatus] = useState(ORDER_ALL)

  const syncOrderItem = useCallback(() => {
    getOrderItems().then((res) => setOrderItems(res))
  }, [setOrderItems])

  useEffect(() => {
    syncOrderItem()
  }, [syncOrderItem])

  const onAction = async ({ orderItemId, orderStatus }) => {
    await changeOrderItemStatus({ orderItemId, status: orderStatus })
    syncOrderItem()
  }

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
          <Order key={o.id} order={o} onAction={onAction} />
        ))}
      </Box>
    </Box>
  )
}

function Order({ order: o, onAction }) {
  return (
    <Paper sx={{ p: 2 }} variant="outlined">
      <Box sx={{ display: "flex", justifyContent: "space-between" }}>
        <Box>
          <Typography variant="h6">{o.product.name}</Typography>
          <Typography variant="body2">qty: {o.quantity}</Typography>
        </Box>
        <Box>
          <OrderState state={o.orderStatus} />
          <Action order={o} onAction={onAction} />
        </Box>
      </Box>
    </Paper>
  )
}

const ACTION_DISPLAY_SHIP = "ship"
const ACTION_DISPLAY_DELIVER = "deliver"
const ACTION_DISPLAY_CANCEL = "cancel"

const actionDisplayToOrderStatus = (a) => {
  switch (a) {
    case ACTION_DISPLAY_CANCEL:
      return ORDER_CANCLED
    case ACTION_DISPLAY_DELIVER:
      return ORDER_DELIVERED
    case ACTION_DISPLAY_SHIP:
      return ORDER_SHIPPING
    default:
      return ""
  }
}

function Action({ order: o, onAction }) {
  const [anchorEl, setAnchorEl] = useState(null)

  const onClose = () => setAnchorEl(null)

  const actions = useMemo(() => {
    const res = []

    if (o.orderStatus === ORDER_PENDING) res.push(ACTION_DISPLAY_SHIP)
    res.push(ACTION_DISPLAY_DELIVER, ACTION_DISPLAY_CANCEL)
    return res
  }, [o.orderStatus])

  if (o.orderStatus === ORDER_CANCLED || o.orderStatus === ORDER_DELIVERED)
    return null

  return (
    <>
      <IconButton onClick={(e) => setAnchorEl(e.currentTarget)}>
        <Edit />
      </IconButton>
      <Menu open={Boolean(anchorEl)} onClose={onClose} anchorEl={anchorEl}>
        {actions.map((a) => (
          <MenuItem
            key={a}
            onClick={() => {
              const orderStatus = actionDisplayToOrderStatus(a)
              if (!orderStatus) return
              onClose()
              onAction({ orderStatus, orderItemId: o.id })
            }}
          >
            {a}
          </MenuItem>
        ))}
      </Menu>
    </>
  )
}
