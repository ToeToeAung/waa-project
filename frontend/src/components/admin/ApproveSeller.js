import { Box, Button, Paper, Typography } from "@mui/material"
import React, { useEffect, useState } from "react"
import { getPendingSeller } from "../../api/admin"

// const sellers = [
//   {
//     id: 1,
//     username: "seller 1",
//     address: {
//       id: 1,
//       zipcode: "zip-1234",
//       street: "1st street",
//       city: "CA",
//     },
//   },
//   {
//     id: 2,
//     username: "seller 2",
//     address: {
//       id: 2,
//       zipcode: "zip-1234",
//       street: "1st street",
//       city: "CA",
//     },
//   },
//   {
//     id: 3,
//     username: "seller 3",
//     address: {
//       id: 3,
//       zipcode: "zip-1234",
//       street: "1st street",
//       city: "CA",
//     },
//   },
// ]

export function ApproveSeller() {
  const [sellers, setSellers] = useState([])

  useEffect(() => {
    getPendingSeller().then((res) => setSellers(res))
  }, [])

  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2 }}>
      {sellers.map((s) => (
        <Seller key={s.id} seller={s} />
      ))}
    </Box>
  )
}

function Seller({ seller: s }) {
  const { address: a } = s
  return (
    <Paper
      sx={{ display: "flex", justifyContent: "space-between", p: 2 }}
      variant="outlined"
    >
      <Box>
        <Typography>{s.username}</Typography>
        <Typography>
          Address: {a.street}, {a.zipcode}, {a.city}
        </Typography>
      </Box>
      <Button sx={{ flexGrow: 0 }} variant="outlined">
        Approve
      </Button>
    </Paper>
  )
}
