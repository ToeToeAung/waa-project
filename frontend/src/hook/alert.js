import { useSnackbar } from "notistack"
import { useCallback } from "react"

export function useAlert() {
  const { enqueueSnackbar, closeSnackbar } = useSnackbar()

  return useCallback(
    ({ msg, level }) => {
      enqueueSnackbar(msg, {
        variant: level,
        anchorOrigin: { horizontal: "right", vertical: "top" },
        action: (id) => (
          <div
            style={{
              position: "absolute",
              top: 0,
              left: 0,
              width: "100%",
              height: "100%",
            }}
            onClick={() => closeSnackbar(id)}
          />
        ),
      })
    },
    [enqueueSnackbar, closeSnackbar],
  )
}
