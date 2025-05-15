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

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setQuery(prev => ({ ...prev, [name]: value }));
  };

  const handleSearch = async () => {
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
      projectDescription: query.projectDescription || null
    };

    try {
      const res = await axios.post("http://localhost:8080/api/search/persons/search", body);
      setResults(res.data);
    } catch (e) {
      console.error("Search failed", e);
    }
  };

  return (
      <div className="container">
        <h2>Advanced Search</h2>

        {/* Fields with labels */}
        {Object.entries(query).map(([key, value]) => (
            <div key={key} style={{ marginBottom: '1rem' }}>
              <label htmlFor={key} style={{ display: 'block', fontWeight: 'bold', marginBottom: '0.25rem' }}>
                {key.replace(/([A-Z])/g, ' $1').replace(/^./, s => s.toUpperCase())}
              </label>
              <input
                  id={key}
                  name={key}
                  value={value}
                  onChange={handleChange}
                  placeholder={key === 'skills' ? 'e.g. Java, Git, Spring' : ''}
              />
            </div>
        ))}

        <button className="button" onClick={handleSearch}>Search</button>

        <ul style={{ marginTop: '2rem' }}>
          {results.map((person) => (
              <li key={person.personId}>
                <Link to={`/person/${person.personId}`}>{person.name} {person.surname}</Link>
              </li>
          ))}
        </ul>
      </div>
  );
};

export default SearchPage;
