import React, { useEffect, useState } from 'react';
import axios from '../../axios';
import { useParams } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';

const ProjectsPage = () => {
  const { personId } = useParams();
  const { role } = useAuth();

  const [projects, setProjects] = useState<any[]>([]);
  const [project, setProject] = useState({
    id: '',
    name: '',
    description: '',
    startDate: '',
    endDate: ''
  });
  const [isEditing, setIsEditing] = useState(false);

  const canEdit = ['USER', 'ADMIN', 'MANAGER'].includes(role);

  const fetchProjects = async () => {
    if (!personId) return;
    const res = await axios.get(`http://localhost:8082/qualification/${personId}`);
    setProjects(res.data.projects || []);
  };

  const handleSave = async () => {
    const payload = {
      personId,
      name: project.name,
      description: project.description,
      startDate: new Date(project.startDate).toISOString(),
      endDate: new Date(project.endDate).toISOString()
    };

    if (isEditing && project.id) {
      await axios.put(`http://localhost:8082/qualification/project`, { ...payload, id: project.id });
    } else {
      await axios.post(`http://localhost:8082/qualification/project`, payload);
    }

    setProject({ id: '', name: '', description: '', startDate: '', endDate: '' });
    setIsEditing(false);
    fetchProjects();
  };

  const handleEdit = (p: any) => {
    setProject({
      id: p.id,
      name: p.name,
      description: p.description,
      startDate: p.startDate.slice(0, 16),
      endDate: p.endDate.slice(0, 16)
    });
    setIsEditing(true);
  };

  const handleDelete = async (id: string) => {
    await axios.delete(`http://localhost:8082/qualification/project/${id}`);
    fetchProjects();
  };

  useEffect(() => {
    fetchProjects();
  }, [personId]);

  return (
      <div>
        <h2>Проєкти</h2>

        {projects.map(p => (
            <div key={p.id} style={{
              background: '#f9f9f9',
              padding: '1rem',
              borderRadius: '6px',
              marginBottom: '1rem',
              boxShadow: '0 1px 4px rgba(0,0,0,0.1)'
            }}>
              <p><strong>Назва:</strong> {p.name}</p>
              <p><strong>Опис:</strong> {p.description}</p>
              <p><strong>Початок:</strong> {new Date(p.startDate).toLocaleDateString()}</p>
              <p><strong>Завершення:</strong> {new Date(p.endDate).toLocaleDateString()}</p>
              {canEdit && (
                  <>
                    <button
                        className="button danger"
                        onClick={() => handleDelete(p.id)}
                        style={{ marginRight: '0.5rem' }}
                    >
                      Видалити
                    </button>
                    <button className="button" onClick={() => handleEdit(p)}>Редагувати</button>
                  </>
              )}
            </div>
        ))}

        {canEdit && (
            <>
              <h3>{isEditing ? 'Редагування проєкту' : 'Додавання проєкту'}</h3>

              <input
                  placeholder="Назва"
                  value={project.name}
                  onChange={(e) => setProject({ ...project, name: e.target.value })}
              />
              <input
                  placeholder="Опис"
                  value={project.description}
                  onChange={(e) => setProject({ ...project, description: e.target.value })}
              />
              <input
                  type="datetime-local"
                  value={project.startDate}
                  onChange={(e) => setProject({ ...project, startDate: e.target.value })}
              />
              <input
                  type="datetime-local"
                  value={project.endDate}
                  onChange={(e) => setProject({ ...project, endDate: e.target.value })}
              />

              <button className="button" onClick={handleSave}>
                {isEditing ? 'Оновити проєкт' : 'Додати проєкт'}
              </button>
              {isEditing && (
                  <button
                      className="button secondary"
                      onClick={() => {
                        setIsEditing(false);
                        setProject({ id: '', name: '', description: '', startDate: '', endDate: '' });
                      }}
                      style={{ marginLeft: '0.5rem' }}
                  >
                    Скасувати
                  </button>
              )}
            </>
        )}
      </div>
  );
};

export default ProjectsPage;
