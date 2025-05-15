import React from 'react';
import { Navigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

interface RequireRoleProps {
  role: string;
  children: React.ReactNode;
}

const RequireRole = ({ role, children }: RequireRoleProps) => {
  const { role: userRole } = useAuth();
  return userRole === role ? <>{children}</> : <Navigate to="/login" replace />;
};

export default RequireRole;
