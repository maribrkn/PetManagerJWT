import React from "react";
import { Routes, Route } from "react-router-dom";

// Importa as páginas com a capitalização correta do arquivo
import Cadastro from "./pages/Cadastro";
import Login from "./pages/Login";
import CadastroPet from "./pages/CadastroPet";
import ObrigadoPet from "./pages/ObrigadoPet";

function App() {
  return (
    <Routes>
      {/* Rota principal para cadastro */}
      <Route path="/" element={<Cadastro />} />
      {/* Rota para login */}
      <Route path="/login" element={<Login />} />
      {/* Rota para cadastro de pet (protegida via token no componente) */}
      <Route path="/pets" element={<CadastroPet />} />
      {/* Rota para finalizar cadastro de pet */}
      <Route path="/obrigado-pet" element={<ObrigadoPet />} />
    </Routes>
  );
}

export default App;
