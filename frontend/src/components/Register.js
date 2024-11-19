import { AcUnitOutlined, Visibility, VisibilityOff } from "@mui/icons-material"
import {
  Button,
  IconButton,
  InputAdornment,
  Paper,
  TextField,
  Typography,
} from "@mui/material"
import React, { useReducer, useState } from "react"

const SET_USERNAME = "set_username"
const SET_PASSWORD = "set_password"
const SET_ZIPCODE = "set_zipcode"
const SET_STREET = "set_street"
const SET_CITY = "set_city"
const TOGGLE_SHOW_PASSWORD = "toggle_show_password"

export function Register(props) {
  const [state, dispatch] = useReducer(
    (state, action) => {
      switch (action.type) {
        case SET_USERNAME:
          return { ...state, username: action.username }
        case SET_PASSWORD:
          return { ...state, password: action.password }
        case SET_ZIPCODE:
          return { ...state, zipcode: action.zipcode }
        case SET_STREET:
          return { ...state, street: action.street }
        case SET_CITY:
          return { ...state, city: action.city }
        case TOGGLE_SHOW_PASSWORD:
          return { ...state, showPassword: !state.showPassword }
      }
    },
    {
      username: "",
      password: "",
      zipcode: "",
      street: "",
      city: "",
      showPassword: false,
    },
  )

  return (
    <Paper
      elevation={3}
      sx={{
        width: 500,
        display: "flex",
        flexDirection: "column",
        padding: 4,
        gap: 2,
        margin: "0 auto",
      }}
    >
      <Typography variant="h5">Register</Typography>
      <TextField
        label="username"
        value={state.username}
        onChange={(e) =>
          dispatch({ type: SET_USERNAME, username: e.target.value })
        }
      />
      <TextField
        label="password"
        value={state.password}
        type={state.showPassword ? "text" : "password"}
        onChange={(e) =>
          dispatch({ type: SET_PASSWORD, password: e.target.value })
        }
        slotProps={{
          input: {
            endAdornment: (
              <InputAdornment position="end">
                <IconButton
                  edge="end"
                  onClick={() => dispatch({ type: TOGGLE_SHOW_PASSWORD })}
                >
                  {state.showPassword ? <Visibility /> : <VisibilityOff />}
                </IconButton>
              </InputAdornment>
            ),
          },
        }}
      />
      <Typography variant="h6">Address</Typography>
      <TextField
        label="zipcode"
        value={state.zipcode}
        onChange={(e) =>
          dispatch({ type: SET_ZIPCODE, zipcode: e.target.value })
        }
      />
      <TextField
        label="street"
        value={state.street}
        onChange={(e) => dispatch({ type: SET_STREET, street: e.target.value })}
      />
      <TextField
        label="city"
        value={state.city}
        onChange={(e) => dispatch({ type: SET_CITY, city: e.target.value })}
      />
      <Button
        variant="contained"
        onClick={() => {
          const data = {
            username: state.username,
            password: state.password,
            address: {
              zipcode: state.zipcode,
              street: state.street,
              city: state.city,
            },
            role: props.role,
          }
          console.log(data)
        }}
      >
        Register
      </Button>
    </Paper>
  )
}
