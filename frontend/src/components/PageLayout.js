import { Navbar } from "./Navbar"
import React from "react"
import { Container, Toolbar } from "@mui/material"
import { Outlet } from "react-router-dom"

export function PageLayout() {
  return (
    <>
      <Navbar />
      <Toolbar />
      <Container sx={{ marginTop: 4 }}>
        <Outlet />
      </Container>
    </>
  )
}
