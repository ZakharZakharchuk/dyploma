import axios from './axios';

axios.interceptors.response.use(
    response => response,
    error => {
      if (error.response?.data) {
        const { type, title, details } = error.response.data;
        const message = details || title || type || 'Невідома помилка';
        alert(`Помилка: ${message}`);
      } else {
        alert('Невідома помилка. Спробуйте пізніше.');
      }
      return Promise.reject(error);
    }
);
