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

  if (!form) return <p>Loading...</p>;

  return (
      <div style={{ backgroundColor: '#f3efdb', padding: '2rem', minHeight: 'calc(100vh - 120px)' }}>
        <div style={{ maxWidth: '800px', margin: '0 auto', backgroundColor: '#fff', padding: '2rem', borderRadius: '8px', boxShadow: '0 0 10px rgba(0,0,0,0.1)' }}>
          <h2 style={{ textAlign: 'center' }}>Update Personnel Info</h2>
          <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <div><strong>Person ID:</strong> {form.id}</div>

            <label>Name:<input name="name" value={form.name} onChange={handleChange} /></label>
            <label>Surname:<input name="surname" value={form.surname} onChange={handleChange} /></label>
            <label>Email:<input name="email" value={form.email} onChange={handleChange} /></label>
            <label>Phone:<input name="phone" value={form.phone} onChange={handleChange} /></label>
            <label>Location:<input name="location" value={form.location} onChange={handleChange} /></label>
            <label>Date of Birth:<input name="dateOfBirth" type="datetime-local" value={form.dateOfBirth} onChange={handleChange} /></label>
            <label>Start of Service:<input name="startOfServiceDate" type="datetime-local" value={form.startOfServiceDate} onChange={handleChange} /></label>
            <label>Rank:<input name="rank" value={form.rank} onChange={handleChange} /></label>
            <label>Department:<input name="department" value={form.department} onChange={handleChange} /></label>
            <label>Current Position:<input name="currentPosition" value={form.currentPosition} onChange={handleChange} /></label>
            <label>Commander ID:<input name="commanderId" value={form.commanderId} onChange={handleChange} /></label>

            <button type="submit" className="button">Save Changes</button>
          </form>
        </div>
      </div>
  );
};

export default UpdatePersonPage;
