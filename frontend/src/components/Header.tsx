import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import { clearTokens } from '../token';
import './Header.css';

const Header = () => {
  const { role, personId } = useAuth();
  const navigate = useNavigate();

  const logout = () => {
    clearTokens();
    navigate('/login');
  };

  return (
      <header className="header">
        <div className="header-left">
          <span className="logo">Some name</span>
        </div>
        <nav className="header-center">
          {role === 'USER' && <Link to={`/person/${personId}`}>Dashboard</Link>}
          {['ADMIN', 'MANAGER', 'HR'].includes(role) && <Link to="/search">Search</Link>}
          {['ADMIN', 'MANAGER'].includes(role) && <Link to="/create">Create User</Link>}
          {['ADMIN', 'HR'].includes(role) && <Link to="/selection">Selection</Link>}
        </nav>
        <div className="header-right">
          {role && <button onClick={logout}>Logout</button>}
        </div>
      </header>
  );
};

export default Header;
