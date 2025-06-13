import React, { useState } from 'react';
import axios from '../../axios';
import { useNavigate } from 'react-router-dom';

const CreateUserPage = () => {
  const [form, setForm] = useState({
    name: '',
    surname: '',
    email: '',
    password: '',
    role: 'USER',
    phone: '',
    location: '',
    dateOfBirth: '',
    startOfServiceDate: '',
    rank: '',
    department: '',
    currentPosition: '',
    commanderId: ''
  });

  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const toIsoInstant = (value: string) =>
        value ? new Date(value).toISOString() : null;

    const payload = {
      ...form,
      dateOfBirth: toIsoInstant(form.dateOfBirth),
      startOfServiceDate: toIsoInstant(form.startOfServiceDate),
    };

    await axios.post("http://localhost:8083/person", payload);
    navigate("/search");
  };

  return (
      <div style={{ backgroundColor: '#f3efdb', padding: '2rem', minHeight: '100vh' }}>
        <div style={{
          maxWidth: '800px',
          margin: '0 auto',
          backgroundColor: '#fff',
          padding: '2rem',
          borderRadius: '8px',
          boxShadow: '0 0 10px rgba(0,0,0,0.1)'
        }}>
          <h2 style={{ textAlign: 'center' }}>Створити нового користувача</h2>
          <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <label>Ім’я:<input name="name" value={form.name} onChange={handleChange} /></label>
            <label>Прізвище:<input name="surname" value={form.surname} onChange={handleChange} /></label>
            <label>Email:<input name="email" value={form.email} onChange={handleChange} /></label>
            <label>Пароль:<input type="password" name="password" value={form.password} onChange={handleChange} /></label>
            <label>Роль:
              <select name="role" value={form.role} onChange={handleChange}>
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
                <option value="HR">HR</option>
                <option value="MANAGER">MANAGER</option>
              </select>
            </label>
            <label>Телефон:<input name="phone" value={form.phone} onChange={handleChange} /></label>
            <label>Локація:<input name="location" value={form.location} onChange={handleChange} /></label>
            <label>Дата народження:<input type="datetime-local" name="dateOfBirth" value={form.dateOfBirth} onChange={handleChange} /></label>
            <label>Початок служби:<input type="datetime-local" name="startOfServiceDate" value={form.startOfServiceDate} onChange={handleChange} /></label>
            <label>Звання:<input name="rank" value={form.rank} onChange={handleChange} /></label>
            <label>Підрозділ:<input name="department" value={form.department} onChange={handleChange} /></label>
            <label>Посада:<input name="currentPosition" value={form.currentPosition} onChange={handleChange} /></label>
            <label>ID командира:<input name="commanderId" value={form.commanderId} onChange={handleChange} /></label>
            <button type="submit" className="button">Створити</button>
          </form>
        </div>
      </div>
  );
};

export default CreateUserPage;
