import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../../axios";
import { saveTokens } from "../../token";
import { jwtDecode } from "jwt-decode";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await axios.post("http://localhost:8084/auth/login", { email, password });
      saveTokens(res.data.accessToken, res.data.refreshToken);
      const decoded = jwtDecode<{ role: string; personId: string }>(res.data.accessToken);

      if (decoded.role === "USER") navigate(`/person/${decoded.personId}`);
      else navigate("/search");
    } catch (e) {
      alert("Login failed");
    }
  };

  return (
      <div className="main-content">
        <form
            className="container"
            onSubmit={(e) => {
              e.preventDefault();
              handleLogin();
            }}
        >
          <h2 style={{ textAlign: "center" }}>Login</h2>
          <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Email"
              required
          />
          <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Password"
              required
          />
          <button className="button" type="submit">
            Login
          </button>
        </form>
      </div>
  );
};

export default LoginPage;
