import React, { useEffect, useState } from 'react';
import axios from '../../axios';
import { Link } from 'react-router-dom';

const STATUS_OPTIONS: { value: string, label: string }[] = [
  { value: 'PENDING', label: 'Очікує' },
  { value: 'REVIEWED', label: 'Переглянуто' },
  { value: 'SHORTLISTED', label: 'У короткому списку' },
  { value: 'REJECTED', label: 'Відхилено' },
];

const SelectionPage = () => {
  const [selection, setSelection] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [persons, setPersons] = useState<Record<string, { name: string, surname: string }>>({});

  useEffect(() => {
    const loadData = async () => {
      try {
        const { data: entries } = await axios.get('http://localhost:8085/selection');
        setSelection(entries);

        const fetchPersons = await Promise.all(
            entries.map(async (entry: any) => {
              try {
                const res = await axios.get(`http://localhost:8083/person/${entry.candidateId}`);
                return { id: entry.candidateId, name: res.data.name, surname: res.data.surname };
              } catch {
                return { id: entry.candidateId, name: 'Невідомо', surname: '' };
              }
            })
        );

        const personMap: Record<string, { name: string, surname: string }> = {};
        fetchPersons.forEach(p => {
          personMap[p.id] = { name: p.name, surname: p.surname };
        });

        setPersons(personMap);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  const handleRemove = async (selectionId: string) => {
    await axios.delete('http://localhost:8085/selection', {
      params: { selectionId }
    });
    setSelection(prev => prev.filter(entry => entry.id !== selectionId));
  };

  const handleStatusChange = async (selectionId: string, newStatus: string) => {
    await axios.put('http://localhost:8085/selection/status', {
      selectionId,
      status: newStatus
    });

    setSelection(prev =>
        prev.map(entry =>
            entry.id === selectionId ? { ...entry, status: newStatus } : entry
        )
    );
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
          <h2 style={{ textAlign: 'center' }}>Обрані кандидати</h2>
          {loading ? (
              <p>Завантаження...</p>
          ) : (
              <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '1rem' }}>
                <thead>
                <tr style={{ backgroundColor: '#eaeaea' }}>
                  <th style={{ border: '1px solid #ccc', padding: '8px' }}>ПІБ</th>
                  <th style={{ border: '1px solid #ccc', padding: '8px' }}>Примітка</th>
                  <th style={{ border: '1px solid #ccc', padding: '8px' }}>Статус</th>
                  <th style={{ border: '1px solid #ccc', padding: '8px' }}>Дії</th>
                </tr>
                </thead>
                <tbody>
                {selection.map(entry => {
                  const person = persons[entry.candidateId] || { name: 'Невідомо', surname: '' };
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
                            {STATUS_OPTIONS.map(opt => (
                                <option key={opt.value} value={opt.value}>{opt.label}</option>
                            ))}
                          </select>
                        </td>
                        <td style={{ border: '1px solid #ccc', padding: '8px' }}>
                          <button className="button danger" onClick={() => handleRemove(entry.id)}>Видалити</button>
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
