import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { getAccessToken, getRefreshToken, saveTokens, clearTokens } from "./token";

const api = axios.create();

api.interceptors.request.use(async (config) => {
  const token = getAccessToken();
  if (token) {
    const { exp } = jwtDecode<{ exp: number }>(token);
    if (Date.now() >= exp * 1000) {
      const refresh = getRefreshToken();
      if (refresh) {
        try {
          const res = await axios.post("http://localhost:8084/auth/refresh", { refreshToken: refresh });
          saveTokens(res.data.accessToken, res.data.refreshToken);
          config.headers.Authorization = `Bearer ${res.data.accessToken}`;
        } catch {
          clearTokens();
          window.location.href = "/login";
        }
      }
    } else {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});

export default api;
