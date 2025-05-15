import React, { useEffect, useState } from 'react';
import axios from '../../axios';
import { useParams, Link } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';

const PersonDetailPage = () => {
  const { personId } = useParams();
  const { role } = useAuth();
  const [person, setPerson] = useState<any>(null);

  useEffect(() => {
    if (personId) {
      axios.get(`http://localhost:8083/person/${personId}`).then(res => setPerson(res.data));
    }
  }, [personId]);

  if (!person) return <p>Loading...</p>;

  return (
      <div>
        <h2>Personnel Info</h2>
        <p><strong>ID:</strong> {person.id}</p>
        <p><strong>Name:</strong> {person.name} {person.surname}</p>
        <p><strong>Email:</strong> {person.email}</p>
        <p><strong>Phone:</strong> {person.phone}</p>
        <p><strong>Location:</strong> {person.location}</p>
        <p><strong>Date of Birth:</strong> {new Date(person.dateOfBirth).toLocaleDateString()}</p>
        <p><strong>Start of Service:</strong> {new Date(person.startOfServiceDate).toLocaleDateString()}</p>
        <p><strong>Rank:</strong> {person.rank}</p>
        <p><strong>Department:</strong> {person.department}</p>
        <p><strong>Position:</strong> {person.currentPosition}</p>
        <p><strong>Commander ID:</strong> {person.commanderId}</p>
        <p><strong>Last Updated:</strong> {new Date(person.lastUpdated).toLocaleString()}</p>

        {['ADMIN', 'MANAGER'].includes(role) && (
            <Link to={`/person/${person.id}/update`}>Update Info</Link>
        )}
      </div>
  );
};

export default PersonDetailPage;