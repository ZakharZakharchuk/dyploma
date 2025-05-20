// App.tsx
import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './pages/auth/LoginPage';
import UserDashboard from './pages/person/UserDashboard';
import PersonDetailPage from './pages/person/PersonDetailPage';
import SkillsPage from './pages/person/SkillsPage';
import ProjectsPage from './pages/person/ProjectsPage';
import SearchPage from './pages/search/SearchPage';
import CreateUserPage from './pages/create/CreateUserPage';
import UpdatePersonPage from './pages/person/UpdatePersonPage';
import RequireRole from './auth/RequireRole';
import RequireRoles from './auth/RequireRoles';
import Header from './components/Header';
import Footer from './components/Footer';
import SelectionPage from './pages/person/SelectionPage';
import './App.css';

function App() {
  return (
      <div className="app-container">
        <Header />
        <main className="main-content">
          <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/person/:personId" element={<RequireRoles roles={["USER", "ADMIN", "HR", "MANAGER"]}><UserDashboard /></RequireRoles>} />
            <Route path="/person/:personId/skills" element={<RequireRoles roles={["USER", "ADMIN", "HR", "MANAGER"]}><SkillsPage /></RequireRoles>} />
            <Route path="/person/:personId/projects" element={<RequireRoles roles={["USER", "ADMIN", "HR", "MANAGER"]}><ProjectsPage /></RequireRoles>} />
            <Route path="/search" element={<RequireRoles roles={["ADMIN", "MANAGER", "HR"]}><SearchPage /></RequireRoles>} />
            <Route path="/create" element={<RequireRoles roles={["ADMIN", "MANAGER"]}><CreateUserPage /></RequireRoles>} />
            <Route path="/person/:personId/update" element={<RequireRoles roles={["ADMIN", "MANAGER"]}><UpdatePersonPage /></RequireRoles>} />
            <Route path="/selection" element={<RequireRoles roles={["ADMIN", "HR"]}><SelectionPage /></RequireRoles>} />
            <Route path="*" element={<Navigate to="/login" replace />} />
          </Routes>
        </main>
      </div>
  );
}

export default App;
