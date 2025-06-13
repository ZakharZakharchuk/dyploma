import React, { useState } from 'react';
import axios from '../../axios';
import { Link } from 'react-router-dom';

const SearchPage = () => {
  const [query, setQuery] = useState({
    name: '', surname: '', email: '', phone: '', location: '', rank: '',
    department: '', currentPosition: '', commanderId: '', skills: '',
    projectName: '', projectDescription: ''
  });

  const [results, setResults] = useState<any[]>([]);
  const [page, setPage] = useState(0);
  const [total, setTotal] = useState(0);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setQuery(prev => ({ ...prev, [name]: value }));
  };

  const handleSearch = async (pageOverride?: number) => {
    const skillsArray = query.skills.trim() !== '' ? query.skills.split(',').map(s => s.trim()) : null;

    const body = {
      ...query,
      skills: skillsArray,
      name: query.name || null,
      surname: query.surname || null,
      email: query.email || null,
      phone: query.phone || null,
      location: query.location || null,
      rank: query.rank || null,
      department: query.department || null,
      currentPosition: query.currentPosition || null,
      commanderId: query.commanderId || null,
      projectName: query.projectName || null,
      projectDescription: query.projectDescription || null,
      page: pageOverride ?? page,
      size: 10 // fixed page size
    };

    const res = await axios.post("http://localhost:8080/api/search/persons/search", body);
    setResults(res.data.items);
    setTotal(res.data.total);
    if (pageOverride !== undefined) setPage(pageOverride);
  };

  const labels: Record<string, string> = {
    name: 'Ім’я',
    surname: 'Прізвище',
    email: 'Електронна пошта',
    phone: 'Телефон',
    location: 'Місце служби',
    rank: 'Звання',
    department: 'Підрозділ',
    currentPosition: 'Посада',
    commanderId: 'Командир',
    skills: 'Навички',
    projectName: 'Назва операції / проєкту',
    projectDescription: 'Опис операції / проєкту'
  };

  return (
      <div className="container">
        <h2>Розширений пошук</h2>

        {/* Input fields */}
        {Object.entries(query).map(([key, value]) => (
            <div key={key} style={{ marginBottom: '1rem' }}>
              <label htmlFor={key} style={{ display: 'block', fontWeight: 'bold', marginBottom: '0.25rem' }}>
                {labels[key] || key}
              </label>
              <input
                  id={key}
                  name={key}
                  value={value}
                  onChange={handleChange}
                  placeholder={key === 'skills' ? 'напр. Лідерство, Управління особовим складом' : ''}
              />
            </div>
        ))}

        <button className="button" onClick={() => handleSearch(0)}>Пошук</button>

        {/* Results */}
        <div style={{ marginTop: '2rem', textAlign: 'center' }}>
          <p><strong>Знайдено:</strong> {total}</p>

          <ul style={{
            listStyle: 'none',
            padding: 0,
            margin: 0
          }}>
            {results.map((person) => (
                <li key={person.personId} style={{ marginBottom: '2rem', textAlign: 'center' }}>
                  <Link className="button" to={`/person/${person.personId}`}>
                    {person.name} {person.surname}
                  </Link>
                </li>
            ))}
          </ul>

          {/* Pagination Controls */}
          {results.length > 0 && (
              <div style={{ marginTop: '1.5rem' }}>
                <button
                    className="button"
                    onClick={() => handleSearch(page - 1)}
                    disabled={page === 0}
                    style={{ marginRight: '1rem' }}
                >
                  Назад
                </button>
                <span style={{ fontWeight: 'bold' }}>Сторінка: {page + 1}</span>
                <button
                    className="button"
                    onClick={() => handleSearch(page + 1)}
                    disabled={(page + 1) * 10 >= total}
                    style={{ marginLeft: '1rem' }}
                >
                  Далі
                </button>
              </div>
          )}
        </div>
      </div>
  );
};

export default SearchPage;
