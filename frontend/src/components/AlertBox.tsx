import React from 'react';

const AlertBox = ({ message, onClose }: { message: string; onClose: () => void }) => {
  return (
      <div style={{
        backgroundColor: '#fff6f6',
        color: '#9f3a38',
        border: '1px solid #e0b4b4',
        padding: '1rem',
        borderRadius: '8px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        marginBottom: '1rem',
        position: 'relative',
        maxWidth: '900px',
        marginInline: 'auto'
      }}>
        <button onClick={onClose} style={{
          position: 'absolute',
          top: '8px',
          right: '10px',
          background: 'none',
          border: 'none',
          fontSize: '1rem',
          color: '#9f3a38',
          cursor: 'pointer'
        }}>
          &times;
        </button>
        {message}
      </div>
  );
};

export default AlertBox;
