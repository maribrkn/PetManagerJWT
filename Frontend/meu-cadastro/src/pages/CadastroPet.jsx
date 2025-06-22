import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function CadastroPet() {
  // Estados para armazenar os dados do pet no formulário
  const [nome, setNome] = useState("");
  const [especie, setEspecie] = useState("CACHORRO");
  const [dataNascimento, setDataNascimento] = useState("");
  const [peso, setPeso] = useState("");
  const [raca, setRaca] = useState("");

  // Hook para navegação programática entre rotas
  const navigate = useNavigate();

  useEffect(() => {
    // Verifica se o token JWT está no localStorage ao carregar a página
    const token = localStorage.getItem("token");
    if (!token) {
      // Se não houver token, redireciona para a página de login
      navigate("/login");
    }
  }, [navigate]);

  // Função para tratar o envio do formulário
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validação dos campos obrigatórios
    if (!nome || !dataNascimento || !peso || !raca) {
      alert("Por favor, preencha todos os campos.");
      return;
    }

    // Validação do formato da data (AAAA-MM-DD)
    const regexData = /^\d{4}-\d{2}-\d{2}$/;
    if (!regexData.test(dataNascimento)) {
      alert("Data de nascimento inválida. Use o formato AAAA-MM-DD.");
      return;
    }

    // Conversão e validação do peso como número positivo
    const pesoNum = parseFloat(peso);
    if (isNaN(pesoNum) || pesoNum <= 0) {
      alert("Peso inválido. Informe um número positivo.");
      return;
    }

    // Busca do donoId salvo no localStorage para enviar na requisição
    const donoIdString = localStorage.getItem("donoId");
    if (!donoIdString) {
      alert("Erro: donoId não encontrado. Faça login novamente.");
      navigate("/login");
      return;
    }
    const donoId = Number(donoIdString);

    // Busca do token JWT para enviar no cabeçalho Authorization
    const token = localStorage.getItem("token");

    try {
      // Envia requisição POST para criar novo pet
      const response = await fetch("http://localhost:8080/pets", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`, // Token JWT para autenticação
        },
        body: JSON.stringify({
          nome,
          especie,
          dataNascimento,
          peso: pesoNum,
          raca,
          donoId, // Associar pet ao dono logado
        }),
      });

      if (!response.ok) {
        // Caso o backend retorne erro, lança exceção para ser tratada abaixo
        throw new Error("Erro ao cadastrar pet.");
      }

      // Alerta sucesso para o usuário
      alert(`Pet ${nome} cadastrado com sucesso!`);

      // Limpa os campos do formulário para novo cadastro
      setNome("");
      setEspecie("CACHORRO");
      setDataNascimento("");
      setPeso("");
      setRaca("");

      // Redireciona para a página de agradecimento após cadastro
      navigate("/obrigado-pet");
    } catch (error) {
      // Em caso de erro, mostra mensagem para o usuário
      alert("Erro: " + error.message);
    }
  };

  return (
    <div style={estilos.container}>
      <form onSubmit={handleSubmit} style={estilos.form}>
        <h2 style={estilos.titulo}>Cadastro de Pet</h2>

        <input
          type="text"
          placeholder="Nome do pet"
          value={nome}
          onChange={(e) => setNome(e.target.value)}
          style={estilos.input}
          required
        />

        <select
          value={especie}
          onChange={(e) => setEspecie(e.target.value)}
          style={estilos.input}
        >
          <option value="CACHORRO">Cachorro</option>
          <option value="GATO">Gato</option>
          <option value="HAMSTER">Hamster</option>
          <option value="PASARO">Pássaro</option>
          <option value="OUTRO">Outro</option>
        </select>

        <input
          type="date"
          placeholder="Data de nascimento"
          value={dataNascimento}
          onChange={(e) => setDataNascimento(e.target.value)}
          style={estilos.input}
          required
        />

        <input
          type="number"
          placeholder="Peso (kg)"
          value={peso}
          onChange={(e) => setPeso(e.target.value)}
          style={estilos.input}
          min="0.1"
          step="0.01"
          required
        />

        <input
          type="text"
          placeholder="Raça"
          value={raca}
          onChange={(e) => setRaca(e.target.value)}
          style={estilos.input}
          required
        />

        <button type="submit" style={estilos.botao}>
          Cadastrar Pet
        </button>
      </form>
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
    color: "#fda085",
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
    backgroundColor: "#fda085",
    color: "white",
    fontSize: 16,
    border: "none",
    borderRadius: 4,
    cursor: "pointer",
  },
};

export default CadastroPet;
