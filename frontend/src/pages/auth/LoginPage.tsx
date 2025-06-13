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
    const res = await axios.post("http://localhost:8084/auth/login", { email, password });
    saveTokens(res.data.accessToken, res.data.refreshToken);

    const decoded = jwtDecode<{ role: string; personId: string }>(res.data.accessToken);

    if (decoded.role === "USER") navigate(`/person/${decoded.personId}`);
    else navigate("/search");
  };

  return (
      <div style={{ backgroundColor: "#f3efdb", padding: "2rem", minHeight: "100vh" }}>
        <div style={{
          maxWidth: "500px",
          margin: "0 auto",
          backgroundColor: "#fff",
          padding: "2rem",
          borderRadius: "8px",
          boxShadow: "0 0 10px rgba(0,0,0,0.1)"
        }}>
          <form
              onSubmit={(e) => {
                e.preventDefault();
                handleLogin();
              }}
              style={{ display: "flex", flexDirection: "column", gap: "1rem" }}
          >
            <h2 style={{ textAlign: "center" }}>Вхід до системи</h2>

            <label>
              Email:
              <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="example@email.com"
                  required
              />
            </label>

            <label>
              Пароль:
              <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="********"
                  required
              />
            </label>

            <button className="button" type="submit">Увійти</button>
          </form>
        </div>
      </div>
  );
};

export default LoginPage;
