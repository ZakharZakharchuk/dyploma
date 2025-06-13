import React, { createContext, useContext, useState } from 'react';

const AlertContext = createContext<{
  alert: string | null;
  setAlert: (msg: string | null) => void;
}>({
  alert: null,
  setAlert: () => {},
});

export const AlertProvider = ({ children }: { children: React.ReactNode }) => {
  const [alert, setAlert] = useState<string | null>(null);
  return (
      <AlertContext.Provider value={{ alert, setAlert }}>
        {children}
      </AlertContext.Provider>
  );
};

export const useAlert = () => useContext(AlertContext);
