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
          <span className="logo">Система управління персоналом</span>
        </div>

        {role && (
            <nav className="header-center">
              {role === 'USER' && <Link to={`/person/${personId}`}>Профіль</Link>}
              {['ADMIN', 'MANAGER', 'HR'].includes(role) && <Link to="/search">Пошук</Link>}
              {['ADMIN', 'MANAGER'].includes(role) && <Link to="/create">Створити користувача</Link>}
              {['ADMIN', 'HR'].includes(role) && <Link to="/selection">Вибрані кандидати</Link>}
            </nav>
        )}

        <div className="header-right">
          {role && <button onClick={logout}>Вийти</button>}
        </div>
      </header>
  );
};

export default Header;
