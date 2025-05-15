import { jwtDecode } from "jwt-decode";
import { getAccessToken } from "../token";

const useAuth = () => {
  const token = getAccessToken();
  if (!token) return { role: "", personId: "" };
  const decoded = jwtDecode<{ role: string; personId: string }>(token);
  return {
    role: decoded.role,
    personId: decoded.personId
  };
};

export default useAuth;
