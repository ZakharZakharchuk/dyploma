import React from 'react';
import { Navigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';

interface RequireRolesProps {
  roles: string[];
  children: React.ReactNode;
}

const RequireRoles = ({ roles, children }: RequireRolesProps) => {
  const { role: userRole } = useAuth();
  return roles.includes(userRole) ? <>{children}</> : <Navigate to="/login" replace />;
};

export default RequireRoles;
