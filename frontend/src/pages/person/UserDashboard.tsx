import React, { useEffect, useState } from 'react';
import useAuth from '../../hooks/useAuth';
import axios from '../../axios';
import { useParams } from 'react-router-dom';
import SkillsPage from './SkillsPage';
import ProjectsPage from './ProjectsPage';
import jsPDF from 'jspdf';

const UserDashboard = () => {
  const { role, personId: tokenPersonId } = useAuth();
  const { personId: paramPersonId } = useParams();
  const personId = paramPersonId || tokenPersonId;

  const [person, setPerson] = useState<any>(null);
  const [skills, setSkills] = useState<any[]>([]);
  const [projects, setProjects] = useState<any[]>([]);
  const [showSkills, setShowSkills] = useState(false);
  const [showProjects, setShowProjects] = useState(false);
  const [note, setNote] = useState('');

  useEffect(() => {
    if (personId) {
      axios.get(`http://localhost:8083/person/${personId}`).then(res => setPerson(res.data));
      axios.get(`http://localhost:8082/qualification/${personId}`).then(res => {
        setSkills(res.data.skills);
        setProjects(res.data.projects);
      });
    }
  }, [personId]);

  const generatePdf = () => {
    const doc = new jsPDF();
    const margin = 10;
    const lineHeight = 8;
    const maxWidth = 180;
    let y = 15;

    doc.setFontSize(18);
    doc.text('Профіль військовослужбовця', 105, y, { align: 'center' });

    doc.setFontSize(12);
    y += 15;

    const lines = [
      `ID особи: ${person.id}`,
      `ПІБ: ${person.name} ${person.surname}`,
      `Email: ${person.email}`,
      `Телефон: ${person.phone}`,
      `Місце служби: ${person.location}`,
      `Дата народження: ${new Date(person.dateOfBirth).toLocaleDateString()}`,
      `Дата початку служби: ${new Date(person.startOfServiceDate).toLocaleDateString()}`,
      `Військове звання: ${person.rank}`,
      `Підрозділ: ${person.department}`,
      `Посада: ${person.currentPosition}`,
      `ID командира: ${person.commanderId}`,
      `Останнє оновлення: ${new Date(person.lastUpdated).toLocaleString()}`
    ];

    lines.forEach(text => {
      const split = doc.splitTextToSize(text, maxWidth);
      split.forEach((line: string) => {
        if (y > 280) {
          doc.addPage();
          y = 15;
        }
        doc.text(line, margin, y);
        y += lineHeight;
      });
    });

    doc.setFontSize(14);
    y += 6;
    doc.text('Навички', margin, y);
    y += lineHeight;

    doc.setFontSize(12);
    if (skills.length === 0) {
      doc.text('- Немає навичок.', margin + 5, y);
      y += lineHeight;
    } else {
      skills.forEach(skill => {
        if (y > 280) {
          doc.addPage();
          y = 15;
        }
        doc.text(`- ${skill.name}`, margin + 5, y);
        y += lineHeight;
      });
    }

    doc.setFontSize(14);
    y += 6;
    doc.text('Операції / проєкти', margin, y);
    y += lineHeight;

    doc.setFontSize(12);
    if (projects.length === 0) {
      doc.text('- Немає проєктів.', margin + 5, y);
      y += lineHeight;
    } else {
      projects.forEach(p => {
        const content = `- ${p.name}: ${p.description}`;
        const wrapped = doc.splitTextToSize(content, maxWidth);
        wrapped.forEach((line: string) => {
          if (y > 280) {
            doc.addPage();
            y = 15;
          }
          doc.text(line, margin + 5, y);
          y += lineHeight;
        });
      });
    }

    doc.save(`${person.name}_${person.surname}_профіль.pdf`);
  };

  const handleAddToSelection = async () => {
    await axios.post('http://localhost:8085/selection', {
      candidateId: person.id,
      note: note || `Додано ${role} через інтерфейс`
    });
    alert('Кандидата додано до вашого списку.');
  };

  const handleDeleteUser = async () => {
    const confirmDelete = window.confirm('Ви впевнені, що хочете видалити цього користувача? Цю дію неможливо скасувати.');
    if (!confirmDelete) return;

    await axios.delete(`http://localhost:8083/person/${person.id}`);
    alert('Користувача успішно видалено.');
    window.location.href = '/search';
  };

  return (
      <div style={{ backgroundColor: '#f3efdb', padding: '2rem', minHeight: 'calc(100vh - 120px)' }}>
        <div style={{
          maxWidth: '900px',
          margin: '0 auto',
          backgroundColor: '#fff',
          padding: '2rem',
          borderRadius: '8px',
          boxShadow: '0 0 10px rgba(0,0,0,0.1)'
        }}>
          {person ? (
              <>
                <h2 style={{ textAlign: 'center' }}>Профіль особи</h2>
                <div style={{ textAlign: 'left' }}>
                  <p><strong>ID особи:</strong> {person.id}</p>
                  <p><strong>ПІБ:</strong> {person.name} {person.surname}</p>
                  <p><strong>Електронна пошта:</strong> {person.email}</p>
                  <p><strong>Телефон:</strong> {person.phone}</p>
                  <p><strong>Місце служби:</strong> {person.location}</p>
                  <p><strong>Дата народження:</strong> {new Date(person.dateOfBirth).toLocaleDateString()}</p>
                  <p><strong>Дата початку служби:</strong> {new Date(person.startOfServiceDate).toLocaleDateString()}</p>
                  <p><strong>Звання:</strong> {person.rank}</p>
                  <p><strong>Підрозділ:</strong> {person.department}</p>
                  <p><strong>Посада:</strong> {person.currentPosition}</p>
                  <p><strong>ID командира:</strong> {person.commanderId}</p>
                  <p><strong>Останнє оновлення:</strong> {new Date(person.lastUpdated).toLocaleString()}</p>
                </div>

                <div style={{ marginTop: '1rem', display: 'flex', flexWrap: 'wrap', gap: '1rem', justifyContent: 'center' }}>
                  <button className="button" onClick={generatePdf}>Завантажити PDF</button>

                  {['ADMIN', 'MANAGER'].includes(role) && (
                      <button
                          className="button secondary"
                          onClick={() => window.location.href = `/person/${person.id}/update`}
                      >
                        Оновити інформацію
                      </button>
                  )}

                  {['ADMIN', 'HR'].includes(role) && (
                      <button className="button" onClick={handleAddToSelection}>
                        Додати до списку відбору
                      </button>
                  )}

                  {role === 'ADMIN' && (
                      <button className="button danger" onClick={handleDeleteUser}>
                        Видалити користувача
                      </button>
                  )}

                  <button className="button" onClick={() => { setShowSkills(true); setShowProjects(false); }}>
                    Переглянути навички
                  </button>
                  <button className="button" onClick={() => { setShowProjects(true); setShowSkills(false); }}>
                    Переглянути проєкти
                  </button>
                </div>

                {['ADMIN', 'HR'].includes(role) && (
                    <div style={{ marginTop: '1.5rem', display: 'flex', justifyContent: 'center' }}>
                <textarea
                    value={note}
                    onChange={(e) => setNote(e.target.value)}
                    placeholder="Нотатка для HR списку..."
                    style={{
                      color: '#000',
                      border: 'none',
                      borderRadius: '8px',
                      padding: '0.75rem 1rem',
                      width: '300px',
                      minHeight: '70px',
                      resize: 'none',
                      fontSize: '14px',
                      fontFamily: 'inherit',
                      boxShadow: '0 0 5px rgba(0,0,0,0.1)'
                    }}
                />
                    </div>
                )}

                <div style={{ marginTop: '2rem' }}>
                  {showSkills && <SkillsPage />}
                  {showProjects && <ProjectsPage />}
                </div>
              </>
          ) : (
              <p>Завантаження даних...</p>
          )}
        </div>
      </div>
  );
};

export default UserDashboard;
