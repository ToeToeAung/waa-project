import { Cancel } from "@mui/icons-material"
import jsPDF from "jspdf"
import "jspdf-autotable"
import * as XLSX from "xlsx"

import {
  Box,
  Button,
  ButtonGroup,
  Divider,
  IconButton,
  List,
  ListItem,
  ListItemText,
  Paper,
  Typography,
} from "@mui/material"
import React, { useEffect, useState } from "react"
import {
  ORDER_CANCLED,
  ORDER_DELIVERED,
  ORDER_PENDING,
  ORDER_SHIPPING,
} from "../entity/OrderStatus"
import { OrderState } from "./OrderState"

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

function exportToExcel(order) {
  const data = order.items.map((item) => ({
    Product: item.product.name,
    Price: item.product.price,
    Quantity: item.quantity,
  }))

  const worksheet = XLSX.utils.json_to_sheet(data)
  const workbook = XLSX.utils.book_new()
  XLSX.utils.book_append_sheet(workbook, worksheet, `Order_${order.id}`)
  XLSX.writeFile(workbook, `Order_${order.id}.xlsx`)
}

function exportToPDF(order) {
  const doc = new jsPDF()

  doc.text(`Invoice No. ${order.id}`, 10, 10)

  // Add table for items
  const items = order.items.map((item) => [
    item.product.name,
    item.product.price,
    item.quantity,
  ])

  doc.autoTable({
    head: [["Product Name", "Price", "Quantity", "State"]],
    body: items,
    startY: 20,
  })

  // Save the PDF
  doc.save(`Order_${order.id}.pdf`)
}

function Order({ order }) {
  return (
    <Paper sx={{ p: 2 }} variant="outlined">
      <Typography variant="h6">Invoice No. {order.id}</Typography>
      <List>
        {order.items.map((oi) => (
          <OrderItem key={oi} item={oi} />
        ))}
      </List>
      <Divider sx={{ mt: 2, mb: 2 }} />
      <Typography variant="caption">Get Recipt</Typography>
      <ButtonGroup sx={{ ml: 2 }}>
        <Button onClick={() => exportToPDF(order)}>PDF</Button>
        <Button onClick={() => exportToExcel(order)}>XLS</Button>
      </ButtonGroup>
    </Paper>
  )
}

function OrderItem({ item }) {
  return (
    <ListItem
      secondaryAction={
        item.state === ORDER_PENDING ? (
          <IconButton edge="end">
            <Cancel />
          </IconButton>
        ) : null
      }
    >
      <ListItemText primary={item.product.name} />
      <OrderState state={item.state} />
    </ListItem>
  )
}
