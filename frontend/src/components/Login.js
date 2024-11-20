import { Visibility, VisibilityOff } from "@mui/icons-material"
import {
  Button,
  IconButton,
  InputAdornment,
  Paper,
  TextField,
  Typography,
} from "@mui/material"
import React, { useReducer } from "react"
import { useLogin } from "../hook/auth"
import { useNavigate } from "react-router-dom"

const SET_USERNAME = "set_username"
const SET_PASSWORD = "set_password"
const TOGGLE_SHOW_PASSWORD = "toggle_show_password"

export function Login() {
  const login = useLogin()
  const naviagte = useNavigate()
  const [state, dispatch] = useReducer(
    (state, action) => {
      switch (action.type) {
        case SET_USERNAME:
          return { ...state, username: action.username }
        case SET_PASSWORD:
          return { ...state, password: action.password }
        case TOGGLE_SHOW_PASSWORD:
          return { ...state, showPassword: !state.showPassword }
      }
    },
    { username: "", password: "", showPassword: false },
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
      <Typography variant="h4" component="h1" sx={{ textAlign: "center" }}>
        Login
      </Typography>
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
      <Button
        variant="contained"
        onClick={async () => {
          await login({
            username: state.username,
            password: state.password,
          })
          naviagte("/welcome")
        }}
      >
        Login
      </Button>
    </Paper>
  )
}
