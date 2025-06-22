// Importa React e o hook useState para controlar estado (variáveis reativas)
import React, { useState } from "react";

// Importa Link para navegar entre páginas sem recarregar e useNavigate para redirecionar programaticamente
import { Link, useNavigate } from "react-router-dom";

function Cadastro() {
  // Variável que armazena o nome digitado pelo usuário
  const [nome, setNome] = useState("");
  // Variável que armazena o email digitado
  const [email, setEmail] = useState("");
  // Variável que armazena a senha digitada
  const [senha, setSenha] = useState("");
  // Variável que armazena a confirmação da senha digitada
  const [confirmarSenha, setConfirmarSenha] = useState("");

  // Hook para redirecionar o usuário para outra página
  const navigate = useNavigate();

  // Função chamada quando o formulário for enviado
  const handleSubmit = async (e) => {
    e.preventDefault(); // Impede recarregamento da página ao enviar o formulário

    // Valida se as senhas são iguais; se não, mostra alerta e sai da função
    if (senha !== confirmarSenha) {
      alert("As senhas não coincidem."); // Alerta para usuário
      return; // Para a execução da função aqui
    }

    try {
      // Faz a requisição POST para o backend, enviando nome, email, senha e confirmarSenha em JSON
      const response = await fetch("http://localhost:8080/donos/register", {
        method: "POST", // Método POST para criar recurso no backend
        headers: { "Content-Type": "application/json" }, // Define que o corpo da requisição é JSON
        body: JSON.stringify({
          nome, // nome digitado
          email, // email digitado
          senha, // senha digitada
          confirmarSenha, // confirmacao da senha digitada (obrigatório enviar)
        }),
      });

      // Se o backend responder com erro, lança exceção para tratar abaixo
      if (!response.ok) {
        // Tenta ler mensagem do backend para mostrar melhor o erro
        const errorData = await response.json().catch(() => null);
        const message = errorData?.message || "Erro ao cadastrar usuário.";
        throw new Error(message);
      }

      // Se chegou aqui, cadastro foi bem-sucedido
      alert("Cadastro realizado com sucesso!"); // Mensagem de sucesso
      // Redireciona para a página de login após cadastro
      navigate("/login");
    } catch (error) {
      // Se deu erro na requisição ou backend, mostra alerta com a mensagem do erro
      alert("Erro: " + error.message);
    }
  };

  // JSX que define a interface da tela de cadastro
  return (
    <div style={estilo.container}>
      {/* Formulário que chama handleSubmit no envio */}
      <form onSubmit={handleSubmit} style={estilo.form}>
        {/* Título da tela */}
        <h2 style={estilo.titulo}>Cadastro</h2>

        {/* Campo texto para nome */}
        <input
          type="text"
          placeholder="Nome"
          value={nome} // valor atual do campo nome
          onChange={(e) => setNome(e.target.value)} // atualiza o estado conforme usuário digita
          style={estilo.input}
          required // campo obrigatório para enviar formulário
        />

        {/* Campo email com validação de formato padrão */}
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={estilo.input}
          required
        />

        {/* Campo senha */}
        <input
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          style={estilo.input}
          required
        />

        {/* Campo confirmação senha */}
        <input
          type="password"
          placeholder="Confirmar Senha"
          value={confirmarSenha}
          onChange={(e) => setConfirmarSenha(e.target.value)}
          style={estilo.input}
          required
        />

        {/* Botão para enviar o formulário */}
        <button type="submit" style={estilo.botao}>
          Cadastrar
        </button>

        {/* Link para ir para página de login */}
        <Link to="/login" style={estilo.linkBotao}>
          Já tem login? Clique aqui
        </Link>
      </form>
    </div>
  );
}

// Estilos em objeto para usar inline no JSX
const estilo = {
  container: {
    height: "100vh", // altura total da tela
    display: "flex", // usa flexbox para layout
    justifyContent: "center", // centraliza horizontalmente
    alignItems: "center", // centraliza verticalmente
    background: "linear-gradient(135deg, #f093fb 0%, #f5576c 100%)", // fundo gradiente colorido
  },
  form: {
    backgroundColor: "white", // fundo branco do formulário
    padding: 30, // espaçamento interno do formulário
    borderRadius: 8, // cantos arredondados do formulário
    width: 320, // largura fixa para o formulário
    boxShadow: "0 4px 15px rgba(0,0,0,0.2)", // sombra leve para profundidade
    textAlign: "center", // centraliza texto dentro do formulário
  },
  titulo: {
    marginBottom: 20, // espaço abaixo do título
    color: "#f5576c", // cor do texto do título
  },
  input: {
    width: "100%", // input ocupa toda a largura disponível
    padding: 10, // espaçamento interno dos inputs
    marginBottom: 15, // espaço entre os inputs
    borderRadius: 4, // cantos arredondados dos inputs
    border: "1px solid #ccc", // borda cinza clara dos inputs
    fontSize: 16, // tamanho da fonte dentro dos inputs
  },
  botao: {
    width: "100%", // botão ocupa toda largura do form
    padding: 10, // espaçamento interno do botão
    backgroundColor: "#f5576c", // cor de fundo do botão
    color: "white", // cor do texto do botão
    fontSize: 16, // tamanho da fonte do botão
    border: "none", // sem borda no botão
    borderRadius: 4, // cantos arredondados no botão
    cursor: "pointer", // cursor tipo mãozinha ao passar em cima
  },
  linkBotao: {
    display: "inline-block", // exibe como bloco inline para aplicar padding
    marginTop: 20, // espaço acima do link
    padding: "10px 20px", // espaçamento interno no link
    backgroundColor: "#f093fb", // cor de fundo do link
    borderRadius: 6, // cantos arredondados do link
    color: "white", // cor do texto do link
    textDecoration: "none", // remove sublinhado do link
    fontWeight: "bold", // texto em negrito
  },
};

export default Cadastro;
