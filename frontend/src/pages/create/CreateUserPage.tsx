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
      <form onSubmit={handleSubmit}>
        <h2>Create New User</h2>
        <input name="name" placeholder="Name" value={form.name} onChange={handleChange} />
        <input name="surname" placeholder="Surname" value={form.surname} onChange={handleChange} />
        <input name="email" placeholder="Email" value={form.email} onChange={handleChange} />
        <input type="password" name="password" placeholder="Password" value={form.password} onChange={handleChange} />
        <select name="role" value={form.role} onChange={handleChange}>
          <option value="USER">USER</option>
          <option value="ADMIN">ADMIN</option>
          <option value="HR">HR</option>
          <option value="MANAGER">MANAGER</option>
        </select>
        <input name="phone" placeholder="Phone" value={form.phone} onChange={handleChange} />
        <input name="location" placeholder="Location" value={form.location} onChange={handleChange} />
        <input type="datetime-local" name="dateOfBirth" value={form.dateOfBirth} onChange={handleChange} />
        <input type="datetime-local" name="startOfServiceDate" value={form.startOfServiceDate} onChange={handleChange} />
        <input name="rank" placeholder="Rank" value={form.rank} onChange={handleChange} />
        <input name="department" placeholder="Department" value={form.department} onChange={handleChange} />
        <input name="currentPosition" placeholder="Position" value={form.currentPosition} onChange={handleChange} />
        <input name="commanderId" placeholder="Commander ID" value={form.commanderId} onChange={handleChange} />
        <button type="submit">Create</button>
      </form>
  );
};

export default CreateUserPage;
