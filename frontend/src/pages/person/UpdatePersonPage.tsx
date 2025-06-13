import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../../axios';

const UpdatePersonPage = () => {
  const { personId } = useParams();
  const [form, setForm] = useState<any>(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios.get(`http://localhost:8083/person/${personId}`).then(res => setForm(res.data));
  }, [personId]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm((prev: any) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await axios.put(`http://localhost:8083/person`, form);
    navigate(`/person/${personId}`);
  };

  if (!form) return <p>Завантаження...</p>;

  return (
      <div style={{ backgroundColor: '#f3efdb', padding: '2rem', minHeight: 'calc(100vh - 120px)' }}>
        <div style={{
          maxWidth: '800px',
          margin: '0 auto',
          backgroundColor: '#fff',
          padding: '2rem',
          borderRadius: '8px',
          boxShadow: '0 0 10px rgba(0,0,0,0.1)'
        }}>
          <h2 style={{ textAlign: 'center' }}>Оновити дані військовослужбовця</h2>
          <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <div><strong>ID особи:</strong> {form.id}</div>

            <label>Ім’я:<input name="name" value={form.name} onChange={handleChange} /></label>
            <label>Прізвище:<input name="surname" value={form.surname} onChange={handleChange} /></label>
            <label>Електронна пошта:<input name="email" value={form.email} onChange={handleChange} /></label>
            <label>Телефон:<input name="phone" value={form.phone} onChange={handleChange} /></label>
            <label>Місце служби:<input name="location" value={form.location} onChange={handleChange} /></label>
            <label>Дата народження:<input name="dateOfBirth" type="datetime-local" value={form.dateOfBirth} onChange={handleChange} /></label>
            <label>Дата початку служби:<input name="startOfServiceDate" type="datetime-local" value={form.startOfServiceDate} onChange={handleChange} /></label>
            <label>Військове звання:<input name="rank" value={form.rank} onChange={handleChange} /></label>
            <label>Підрозділ:<input name="department" value={form.department} onChange={handleChange} /></label>
            <label>Посада:<input name="currentPosition" value={form.currentPosition} onChange={handleChange} /></label>
            <label>Ідентифікатор командира:<input name="commanderId" value={form.commanderId} onChange={handleChange} /></label>

            <button type="submit" className="button">Зберегти зміни</button>
          </form>
        </div>
      </div>
  );
};

export default UpdatePersonPage;
