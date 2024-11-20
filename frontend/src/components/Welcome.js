import React, { useEffect } from "react"
import { useSelector } from "react-redux"
import { useNavigate } from "react-router-dom"

export function Welcome() {
  const me = useSelector((state) => state.auth.me)
  const navigate = useNavigate()

  useEffect(() => {
    console.log("welcome", me)
  }, [me])

  return null
}
