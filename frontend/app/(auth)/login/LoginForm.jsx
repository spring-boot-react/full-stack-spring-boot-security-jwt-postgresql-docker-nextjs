import InputAdornment from "@mui/material/InputAdornment";
import TextField from "@mui/material/TextField";
import AccountCircle from "@mui/icons-material/AccountCircle";
import Lock from "@mui/icons-material/Lock";

export default function LoginForm() {
  return (
    <>
      <div className="py-4">
        <TextField
          id="email"
          label="Email"
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <AccountCircle />
              </InputAdornment>
            ),
          }}
          variant="standard"
        />
      </div>
      <div className="py-4">
        <TextField
          id="password"
          label="Password"
          InputProps={{
            startAdornment: (
              <InputAdornment position="start">
                <Lock />
              </InputAdornment>
            ),
          }}
          variant="standard"
        />
      </div>
      <button className="btn-primary my-4">Login</button>
    </>
  );
}
