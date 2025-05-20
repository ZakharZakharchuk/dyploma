import React, { useEffect, useState } from 'react';
import axios from '../../axios';
import { Link } from 'react-router-dom';

const STATUS_OPTIONS = ['PENDING', 'REVIEWED', 'SHORTLISTED', 'REJECTED'];

const SelectionPage = () => {
  const [selection, setSelection] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [persons, setPersons] = useState<Record<string, { name: string, surname: string }>>({});

  useEffect(() => {
    axios.get('http://localhost:8085/hr-selection')
    .then(async res => {
      const entries = res.data;
      setSelection(entries);

      const fetchPersons = await Promise.all(
          entries.map(async (entry: any) => {
            try {
              const res = await axios.get(`http://localhost:8083/person/${entry.candidateId}`);
              return { id: entry.candidateId, name: res.data.name, surname: res.data.surname };
            } catch {
              return { id: entry.candidateId, name: 'Unknown', surname: '' };
            }
          })
      );

      const personMap: Record<string, { name: string, surname: string }> = {};
      fetchPersons.forEach(p => {
        personMap[p.id] = { name: p.name, surname: p.surname };
      });

      setPersons(personMap);
    })
    .catch(() => alert('Failed to load selected users'))
    .finally(() => setLoading(false));
  }, []);

  const handleRemove = async (selectionId: string) => {
    try {
      await axios.delete('http://localhost:8085/hr-selection', {
        params: { selectionId }
      });
      setSelection(prev => prev.filter(entry => entry.id !== selectionId));
    } catch (error) {
      alert('Failed to remove from selection list');
    }
  };

  const handleStatusChange = async (selectionId: string, newStatus: string) => {
    try {
      await axios.put('http://localhost:8085/hr-selection/status', {
        selectionId,
        status: newStatus
      });

      setSelection(prev =>
          prev.map(entry =>
              entry.id === selectionId ? { ...entry, status: newStatus } : entry
          )
      );
    } catch {
      alert('Failed to update status');
    }
  };

  return (
      <div style={{ padding: '2rem', backgroundColor: '#f3efdb', minHeight: 'calc(100vh - 120px)' }}>
        <div style={{
          maxWidth: '900px',
          margin: '0 auto',
          backgroundColor: '#fff',
          padding: '2rem',
          borderRadius: '8px',
          boxShadow: '0 0 10px rgba(0,0,0,0.1)'
        }}>
          <h2 style={{ textAlign: 'center' }}>Selected Users</h2>
          {loading ? (
              <p>Loading...</p>
          ) : (
              <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '1rem' }}>
                <thead>
                <tr style={{ backgroundColor: '#eaeaea' }}>
                  <th style={{ border: '1px solid #ccc', padding: '8px' }}>Name</th>
                  <th style={{ border: '1px solid #ccc', padding: '8px' }}>Note</th>
                  <th style={{ border: '1px solid #ccc', padding: '8px' }}>Status</th>
                  <th style={{ border: '1px solid #ccc', padding: '8px' }}>Actions</th>
                </tr>
                </thead>
                <tbody>
                {selection.map(entry => {
                  const person = persons[entry.candidateId] || { name: 'Unknown', surname: '' };
                  return (
                      <tr key={entry.id}>
                        <td style={{ border: '1px solid #ccc', padding: '8px' }}>
                          <Link to={`/person/${entry.candidateId}`}>
                            {person.name} {person.surname}
                          </Link>
                        </td>
                        <td style={{ border: '1px solid #ccc', padding: '8px' }}>{entry.note}</td>
                        <td style={{ border: '1px solid #ccc', padding: '8px' }}>
                          <select
                              value={entry.status || 'PENDING'}
                              onChange={(e) => handleStatusChange(entry.id, e.target.value)}
                          >
                            {STATUS_OPTIONS.map(status => (
                                <option key={status} value={status}>{status}</option>
                            ))}
                          </select>
                        </td>
                        <td style={{ border: '1px solid #ccc', padding: '8px' }}>
                          <button className="button danger" onClick={() => handleRemove(entry.id)}>Remove</button>
                        </td>
                      </tr>
                  );
                })}
                </tbody>
              </table>
          )}
        </div>
      </div>
  );
};

export default SelectionPage;
