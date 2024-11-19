import { Box, Paper, Typography } from "@mui/material"
import React, { useState, useEffect } from "react"
import {
  ORDER_ALL,
  ORDER_PENDING,
  ORDER_SHIPPING,
  ORDER_DELIVERED,
  ORDER_CANCLED,
} from "../entity/OrderStatus"

const getOrders = () => {
  return Promise.resolve(
    Array(4)
      .fill(null)
      .map((_, idx) => ({
        id: idx + 1,
        buyerId: 1,
        date: new Date(),
        items: [
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
        ],
      })),
  )
}

export function OrderHistory() {
  const [orders, setOrders] = useState([])
  useEffect(() => {
    getOrders().then((res) => {
      setOrders(res)
    })
  }, [])
  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
      {orders.map((o) => (
        <Order key={o.id} order={o} />
      ))}
    </Box>
  )
}

function Order({ order }) {
  return (
    <Paper sx={{ p: 2 }} variant="outlined">
      <Typography>Invoice No. {order.id}</Typography>
    </Paper>
  )
}
