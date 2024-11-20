import { Chip } from "@mui/material"
import React from "react"
import {
  ORDER_CANCLED,
  ORDER_DELIVERED,
  ORDER_PENDING,
  ORDER_SHIPPING,
} from "../entity/OrderStatus"

export function OrderState({ state }) {
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
