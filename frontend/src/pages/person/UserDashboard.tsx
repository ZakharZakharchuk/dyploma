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
    doc.text('Personnel Profile', 105, y, { align: 'center' });

    doc.setFontSize(12);
    y += 15;

    const lines = [
      `Person ID: ${person.id}`,
      `Name: ${person.name} ${person.surname}`,
      `Email: ${person.email}`,
      `Phone: ${person.phone}`,
      `Location: ${person.location}`,
      `Date of Birth: ${new Date(person.dateOfBirth).toLocaleDateString()}`,
      `Start of Service: ${new Date(person.startOfServiceDate).toLocaleDateString()}`,
      `Rank: ${person.rank}`,
      `Department: ${person.department}`,
      `Current Position: ${person.currentPosition}`,
      `Commander ID: ${person.commanderId}`,
      `Last Updated: ${new Date(person.lastUpdated).toLocaleString()}`
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
    doc.text('Skills', margin, y);
    y += lineHeight;

    doc.setFontSize(12);
    if (skills.length === 0) {
      doc.text('- No skills listed.', margin + 5, y);
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
    doc.text('Projects', margin, y);
    y += lineHeight;

    doc.setFontSize(12);
    if (projects.length === 0) {
      doc.text('- No projects listed.', margin + 5, y);
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

    doc.save(`${person.name}_${person.surname}_profile.pdf`);
  };

  const handleAddToSelection = async () => {
    try {
      await axios.post('http://localhost:8085/hr-selection', {
        candidateId: person.id,
        note: `Added by ${role} via dashboard`
      });
      alert('Candidate added to your selection list!');
    } catch (error) {
      alert('Failed to add candidate to selection.');
    }
  };

  const handleDeleteUser = async () => {
    const confirmDelete = window.confirm('Are you sure you want to delete this user? This action cannot be undone.');
    if (!confirmDelete) return;

    try {
      await axios.delete(`http://localhost:8083/person/${person.id}`);
      alert('User deleted successfully.');
      window.location.href = '/search';
    } catch (error) {
      alert('Failed to delete user.');
    }
  };

  return (
      <div style={{ backgroundColor: '#f3efdb', padding: '2rem', minHeight: 'calc(100vh - 120px)' }}>
        <div style={{ maxWidth: '900px', margin: '0 auto', backgroundColor: '#fff', padding: '2rem', borderRadius: '8px', boxShadow: '0 0 10px rgba(0,0,0,0.1)' }}>
          {person ? (
              <>
                <h2 style={{ textAlign: 'center' }}>Profile info</h2>
                <div style={{ textAlign: 'left' }}>
                  <p><strong>Person ID:</strong> {person.id}</p>
                  <p><strong>Name:</strong> {person.name} {person.surname}</p>
                  <p><strong>Email:</strong> {person.email}</p>
                  <p><strong>Phone:</strong> {person.phone}</p>
                  <p><strong>Location:</strong> {person.location}</p>
                  <p><strong>Date of Birth:</strong> {new Date(person.dateOfBirth).toLocaleDateString()}</p>
                  <p><strong>Start of Service:</strong> {new Date(person.startOfServiceDate).toLocaleDateString()}</p>
                  <p><strong>Rank:</strong> {person.rank}</p>
                  <p><strong>Department:</strong> {person.department}</p>
                  <p><strong>Current Position:</strong> {person.currentPosition}</p>
                  <p><strong>Commander ID:</strong> {person.commanderId}</p>
                  <p><strong>Last Updated:</strong> {new Date(person.lastUpdated).toLocaleString()}</p>
                </div>

                <div style={{ marginTop: '1rem', display: 'flex', flexWrap: 'wrap', gap: '1rem', justifyContent: 'center' }}>
                  <button className="button" onClick={generatePdf}>Download PDF</button>

                  {['ADMIN', 'MANAGER'].includes(role) && (
                      <button
                          className="button secondary"
                          onClick={() => window.location.href = `/person/${person.id}/update`}
                      >
                        Update Info
                      </button>
                  )}

                  {['ADMIN', 'HR'].includes(role) && (
                      <button className="button" onClick={handleAddToSelection}>
                        Add to Selection List
                      </button>
                  )}

                  {role === 'ADMIN' && (
                      <button className="button danger" onClick={handleDeleteUser}>
                        Delete User
                      </button>
                  )}

                  <button className="button" onClick={() => { setShowSkills(true); setShowProjects(false); }}>View Skills</button>
                  <button className="button" onClick={() => { setShowProjects(true); setShowSkills(false); }}>View Projects</button>
                </div>

                <div style={{ marginTop: '2rem' }}>
                  {showSkills && <SkillsPage />}
                  {showProjects && <ProjectsPage />}
                </div>
              </>
          ) : (
              <p>Loading personal info...</p>
          )}
        </div>
      </div>
  );
};

export default UserDashboard;
