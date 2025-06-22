// Login.jsx
import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, senha }),
      });

      if (!response.ok) {
        throw new Error("Email ou senha incorretos.");
      }

      const data = await response.json();
      const token = data.token;
      const dono = data.dono;

      if (!token) {
        throw new Error("Resposta do servidor não contém token.");
      }
      if (!dono || !dono.id) {
        throw new Error("Resposta do servidor não contém dados do dono.");
      }

      // Salva token e donoId no localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("donoId", dono.id.toString());

      alert("Login realizado com sucesso!");
      navigate("/pets");
    } catch (error) {
      alert("Erro: " + error.message);
    }
  };

  return (
    <div style={estilo.container}>
      <form onSubmit={handleLogin} style={estilo.form}>
        <h2 style={estilo.titulo}>Login</h2>

        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={estilo.input}
          required
        />

        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          style={estilo.input}
          required
        />

        <button type="submit" style={estilo.botao}>
          Entrar
        </button>

        <Link to="/" style={estilo.linkBotao}>
          Não tem conta? Cadastre-se
        </Link>
      </form>
    </div>
  );
}

const estilo = {
  container: {
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)",
  },
  form: {
    backgroundColor: "white",
    padding: 30,
    borderRadius: 8,
    width: 320,
    boxShadow: "0 4px 15px rgba(0,0,0,0.2)",
    textAlign: "center",
  },
  titulo: {
    marginBottom: 20,
    color: "#38f9d7",
  },
  input: {
    width: "100%",
    padding: 10,
    marginBottom: 15,
    borderRadius: 4,
    border: "1px solid #ccc",
    fontSize: 16,
  },
  botao: {
    width: "100%",
    padding: 10,
    backgroundColor: "#38f9d7",
    color: "white",
    fontSize: 16,
    border: "none",
    borderRadius: 4,
    cursor: "pointer",
  },
  linkBotao: {
    display: "inline-block",
    marginTop: 20,
    padding: "10px 20px",
    backgroundColor: "#43e97b",
    borderRadius: 6,
    color: "white",
    textDecoration: "none",
    fontWeight: "bold",
  },
};

export default Login;
