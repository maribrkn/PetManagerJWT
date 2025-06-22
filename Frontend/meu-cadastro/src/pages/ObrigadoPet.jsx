import React from "react";
import { useNavigate } from "react-router-dom";

function ObrigadoPet() {
  const navigate = useNavigate();

  return (
    <div style={estilos.container}>
      <div style={estilos.box}>
        <h1>Obrigado por confiar-nos seu Pet!</h1>
        <p>Seu pet foi cadastrado com sucesso.</p>

        <div style={estilos.botoes}>
          <button style={estilos.botao} onClick={() => navigate("/pets")}>
            Cadastrar outro Pet
          </button>

          <button style={estilos.botao} onClick={() => navigate("/")}>
            Voltar ao Início
          </button>
        </div>
      </div>
    </div>
  );
}

const estilos = {
  container: {
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "linear-gradient(135deg, #f6d365 0%, #fda085 100%)",
  },
  box: {
    backgroundColor: "white",
    padding: 40,
    borderRadius: 10,
    textAlign: "center",
    boxShadow: "0 4px 15px rgba(0,0,0,0.2)",
    maxWidth: 400,
  },
  botoes: {
    marginTop: 30,
    display: "flex",
    justifyContent: "space-around",
  },
  botao: {
    padding: "10px 20px",
    backgroundColor: "#fda085",
    color: "white",
    border: "none",
    borderRadius: 6,
    cursor: "pointer",
    fontSize: 16,
  },
};

export default ObrigadoPet;
