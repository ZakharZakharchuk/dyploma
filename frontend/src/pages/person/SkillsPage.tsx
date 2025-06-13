import React, { useEffect, useState } from 'react';
import axios from '../../axios';
import { useParams } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';

const SkillsPage = () => {
  const { personId } = useParams();
  const { role } = useAuth();
  const [skills, setSkills] = useState<any[]>([]);
  const [newSkill, setNewSkill] = useState('');

  const canEdit = ['USER', 'ADMIN', 'MANAGER'].includes(role);

  const fetchSkills = async () => {
    if (!personId) return;
    const res = await axios.get(`http://localhost:8082/qualification/${personId}`);
    setSkills(res.data.skills || []);
  };

  const addSkill = async () => {
    if (!canEdit || !newSkill.trim()) return;
    await axios.post('http://localhost:8082/qualification/skill', { personId, name: newSkill });
    setNewSkill('');
    fetchSkills();
  };

  const deleteSkill = async (skillId: string) => {
    if (!canEdit) return;
    await axios.delete(`http://localhost:8082/qualification/skill/${skillId}`);
    fetchSkills();
  };

  useEffect(() => {
    fetchSkills();
  }, [personId]);

  return (
      <div>
        <h2>Навички</h2>
        <ul style={{ paddingLeft: 0 }}>
          {skills.map(skill => (
              <li key={skill.id} style={{ marginBottom: '0.5rem', listStyle: 'none' }}>
                {skill.name}
                {canEdit && (
                    <button
                        className="button secondary"
                        onClick={() => deleteSkill(skill.id)}
                        style={{ marginLeft: '1rem' }}
                    >
                      Видалити
                    </button>
                )}
              </li>
          ))}
        </ul>

        {canEdit && (
            <div style={{ marginTop: '1rem' }}>
              <input
                  placeholder="Нова навичка"
                  value={newSkill}
                  onChange={(e) => setNewSkill(e.target.value)}
              />
              <button className="button" onClick={addSkill}>Додати навичку</button>
            </div>
        )}
      </div>
  );
};

export default SkillsPage;
