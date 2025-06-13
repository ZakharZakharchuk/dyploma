import React, { useEffect, useState } from 'react';
import axios from '../../axios';
import { useParams, Link } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';

const PersonDetailPage = () => {
  const { personId } = useParams();
  const { role } = useAuth();
  const [person, setPerson] = useState<any>(null);

  useEffect(() => {
    const fetchPerson = async () => {
      if (!personId) return;
      const res = await axios.get(`http://localhost:8083/person/${personId}`);
      setPerson(res.data);
    };

    fetchPerson();
  }, [personId]);

  if (!person) return <p>Завантаження...</p>;

  return (
      <div>
        <h2>Інформація про персонал</h2>
        <p><strong>ID:</strong> {person.id}</p>
        <p><strong>Ім’я:</strong> {person.name} {person.surname}</p>
        <p><strong>Email:</strong> {person.email}</p>
        <p><strong>Телефон:</strong> {person.phone}</p>
        <p><strong>Локація:</strong> {person.location}</p>
        <p><strong>Дата народження:</strong> {new Date(person.dateOfBirth).toLocaleDateString()}</p>
        <p><strong>Початок служби:</strong> {new Date(person.startOfServiceDate).toLocaleDateString()}</p>
        <p><strong>Звання:</strong> {person.rank}</p>
        <p><strong>Підрозділ:</strong> {person.department}</p>
        <p><strong>Посада:</strong> {person.currentPosition}</p>
        <p><strong>ID командира:</strong> {person.commanderId}</p>
        <p><strong>Останнє оновлення:</strong> {new Date(person.lastUpdated).toLocaleString()}</p>

        {['ADMIN', 'MANAGER'].includes(role) && (
            <Link to={`/person/${person.id}/update`}>Редагувати інформацію</Link>
        )}
      </div>
  );
};

export default PersonDetailPage;
