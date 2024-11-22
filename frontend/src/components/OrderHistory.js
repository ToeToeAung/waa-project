import { Cancel } from "@mui/icons-material"
import jsPDF from "jspdf"
import "jspdf-autotable"
import * as XLSX from "xlsx"
import { cancleOrder, getOrderHistory } from "../api/buyer"
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
import React, { useCallback, useEffect, useState } from "react"
import { ORDER_PENDING } from "../entity/OrderStatus"
import { OrderState } from "./OrderState"

export function OrderHistory() {
  const [orders, setOrders] = useState([])

  const syncOrder = useCallback(() => {
    getOrderHistory().then((res) => {
      setOrders(res)
    })
  }, [setOrders])

  useEffect(() => {
    syncOrder()
  }, [syncOrder])

  const onOrderItemCancle = async (orderItemId) => {
    try {
      await cancleOrder(orderItemId)
      syncOrder()
    } catch (e) {
      console.error(e)
    }
  }

  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
      {orders.map((o) => (
        <Order key={o.id} order={o} onOrderItemCancle={onOrderItemCancle} />
      ))}
    </Box>
  )
}

function exportToExcel(order) {
  const data = order.orderItems.map((item) => ({
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
  const items = order.orderItems.map((item) => [
    item.product.name,
    item.product.price,
    item.quantity,
    item.orderStatus,
  ])

  doc.autoTable({
    head: [["Product Name", "Price", "Quantity", "State"]],
    body: items,
    startY: 20,
  })

  // Save the PDF
  doc.save(`Order_${order.id}.pdf`)
}

function Order({ order, onOrderItemCancle }) {
  return (
    <Paper sx={{ p: 2 }} variant="outlined">
      <Typography variant="h6">Invoice No. {order.id}</Typography>
      <List>
        {order.orderItems.map((oi) => (
          <OrderItem key={oi} item={oi} onOrderItemCancle={onOrderItemCancle} />
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

function OrderItem({ item, onOrderItemCancle }) {
  return (
    <ListItem
      secondaryAction={
        item.orderStatus === ORDER_PENDING ? (
          <IconButton edge="end" onClick={() => onOrderItemCancle(item.id)}>
            <Cancel />
          </IconButton>
        ) : null
      }
    >
      <ListItemText
        primary={item.product.name}
        secondary={`qty: ${item.quantity}, $${item.product.price} | total: $${
          item.quantity * item.product.price
        }`}
      />
      <OrderState state={item.orderStatus} />
    </ListItem>
  )
}
