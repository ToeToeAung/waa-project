import { Box, Button, Paper, Typography } from "@mui/material"
import React, { useEffect, useState, useCallback } from "react"
import { approveSeller, getPendingSeller } from "../../api/admin"

export function ApproveSeller() {
  const [sellers, setSellers] = useState([])

  const syncSellers = useCallback(() => {
    getPendingSeller().then((res) => setSellers(res))
  }, [setSellers])

  useEffect(() => {
    syncSellers()
  }, [syncSellers])

  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
      {sellers.map((s) => (
        <Seller key={s.id} seller={s} onApproved={syncSellers} />
      ))}
    </Box>
  )
}

function Seller({ seller: s, onApproved }) {
  const { address: a } = s
  return (
    <Paper
      sx={{ display: "flex", justifyContent: "space-between", p: 2 }}
      variant="outlined"
    >
      <Box>
        <Typography>{s.username}</Typography>
        <Typography>
          Address: {a.street}, {a.zip}, {a.city}, {a.state}
        </Typography>
      </Box>
      <Button
        sx={{ flexGrow: 0 }}
        variant="outlined"
        onClick={() => {
          approveSeller({ sellerId: s.id }).then(() => {
            onApproved()
          })
        }}
      >
        Approve
      </Button>
    </Paper>
  )
}
