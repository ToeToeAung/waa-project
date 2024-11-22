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
import { useNavigate } from "react-router-dom"
import { register } from "../api/auth"
import { useLogin } from "../hook/auth"
import { useAlert } from "../hook/alert"
import { ERR_EMPTY, ERR_UNKNOWN } from "../entity/error"

const SET_USERNAME = "set_username"
const SET_PASSWORD = "set_password"
const SET_ZIPCODE = "set_zipcode"
const SET_STREET = "set_street"
const SET_CITY = "set_city"
const SET_STATE = "set_state"
const TOGGLE_SHOW_PASSWORD = "toggle_show_password"
const SET_ERROR = "set_error"

export function Register(props) {
  const alert = useAlert()
  const login = useLogin()
  const navigate = useNavigate()
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
        case SET_STATE:
          return { ...state, state: action.state }
        case TOGGLE_SHOW_PASSWORD:
          return { ...state, showPassword: !state.showPassword }
        case SET_ERROR:
          return { ...state, error: action.error }
        default:
          return state
      }
    },
    {
      username: "",
      password: "",
      zipcode: "",
      street: "",
      city: "",
      state: "",
      showPassword: false,
    },
  )

  const validateForm = () => {
    const error = {}
    if (!state.username) {
      error.username = ERR_EMPTY
    }
    if (!state.password) {
      error.password = ERR_EMPTY
    }
    if (!state.zipcode) {
      error.zipcode = ERR_EMPTY
    }
    if (!state.street) {
      error.street = ERR_EMPTY
    }
    if (!state.city) {
      error.city = ERR_EMPTY
    }
    if (!state.state) {
      error.state = ERR_EMPTY
    }
    return error
  }

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
      <Typography variant="h5">Register {props.role}</Typography>
      <TextField
        error={!!state?.error?.username}
        helperText={state?.error?.username}
        label="username"
        value={state.username}
        onChange={(e) =>
          dispatch({ type: SET_USERNAME, username: e.target.value })
        }
      />
      <TextField
        error={!!state?.error?.password}
        helperText={state?.error?.password}
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
        error={!!state?.error?.zipcode}
        helperText={state?.error?.zipcode}
        label="zipcode"
        value={state.zipcode}
        onChange={(e) =>
          dispatch({ type: SET_ZIPCODE, zipcode: e.target.value })
        }
      />
      <TextField
        error={!!state?.error?.street}
        helperText={state?.error?.street}
        label="street"
        value={state.street}
        onChange={(e) => dispatch({ type: SET_STREET, street: e.target.value })}
      />
      <TextField
        error={!!state?.error?.city}
        helperText={state?.error?.city}
        label="city"
        value={state.city}
        onChange={(e) => dispatch({ type: SET_CITY, city: e.target.value })}
      />
      <TextField
        error={!!state?.error?.state}
        helperText={state?.error?.state}
        label="state"
        value={state.state}
        onChange={(e) => dispatch({ type: SET_STATE, state: e.target.value })}
      />
      <Button
        variant="contained"
        onClick={async () => {
          const data = {
            username: state.username,
            password: state.password,
            address: {
              street: state.street,
              city: state.city,
              state: state.state,
              zip: state.zipcode,
            },
            role: props.role,
          }
          const error = validateForm()
          if (Object.keys(error).length !== 0) {
            dispatch({ type: SET_ERROR, error })
            return
          } else {
            dispatch({ type: SET_ERROR, error: null })
          }

          try {
            await register(data)
            await login({ username: state.username, password: state.password })
            navigate("/welcome")
          } catch (e) {
            const errorMessage = e.response?.data?.error
            if (errorMessage) {
              dispatch({ type: SET_ERROR, error: errorMessage })
            } else {
              alert({ msg: ERR_UNKNOWN, level: "error" })
            }
          }
        }}
      >
        Register
      </Button>
    </Paper>
  )
}
