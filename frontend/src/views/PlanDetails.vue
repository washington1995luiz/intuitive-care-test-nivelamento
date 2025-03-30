<template>
  <div class="plan-details">
    <h1>Detalhes do Plano</h1>
    <div v-if="plan">
      <p><strong>Nome Fantasia:</strong> {{ plan.nome_fantasia }}</p>
      <p><strong>Razão Social:</strong> {{ plan.razao_social }}</p>
      <p><strong>Registro ANS:</strong> {{ plan.registro_ans }}</p>
      <p><strong>CNPJ:</strong> {{ plan.cnpj }}</p>
      <p><strong>Modalidade:</strong> {{ plan.modalidade }}</p>
      <p><strong>Endereço:</strong> {{ plan.logradouro }}, {{ plan.numero }} {{ plan.complemento }}</p>
      <p><strong>Bairro:</strong> {{ plan.bairro }}</p>
      <p><strong>Cidade:</strong> {{ plan.cidade }}</p>
      <p><strong>UF:</strong> {{ plan.uf }}</p>
      <p><strong>CEP:</strong> {{ plan.cep }}</p>
      <p><strong>Telefone:</strong> ({{ plan.ddd }}) {{ plan.telefone }}</p>
      <p><strong>Email:</strong> {{ plan.endereco_eletronico }}</p>
      <p><strong>Representante:</strong> {{ plan.representante }}</p>
      <p><strong>Cargo do Representante:</strong> {{ plan.cargo_representante }}</p>
      <p><strong>Data de Registro ANS:</strong> {{ plan.data_registro_ans }}</p>
    </div>
    <div v-else>
      <p>Carregando...</p>
    </div>
    <button @click="$router.push('/')">Voltar</button>
  </div>
</template>

<script>
export default {
  props: ['id'],
  data() {
    return {
      plan: null
    };
  },
  async mounted() {
    try {
      const response = await fetch(`http://localhost:8585/api/v1/health-plan-operators/${this.id}`);
      if (!response.ok) {
        throw new Error('Erro ao buscar detalhes do plano');
      }
      this.plan = await response.json();
    } catch (error) {
      console.error('Erro ao buscar detalhes do plano:', error);
    }
  }
};
</script>

<style>
/* Container principal */
.plan-details {
  max-width: 800px;
  margin: 20px auto;
  font-family: 'Roboto', Arial, sans-serif;
  background-color: #ffffff;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.15);
}

/* Título */
.plan-details h1 {
  text-align: center;
  color: #4caf50;
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: bold;
}

/* Informações do plano */
.plan-details div {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* Alinha os itens à esquerda */
  gap: 10px; /* Adiciona espaçamento entre os itens */
}

.plan-details p {
  font-size: 16px;
  margin: 0;
  line-height: 1.6;
}

/* Destaque para os rótulos */
.plan-details p strong {
  color: #333;
  font-weight: bold;
}

/* Botão de voltar */
.plan-details button {
  display: block;
  margin: 30px auto 0;
  padding: 12px 24px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease;
}

.plan-details button:hover {
  background-color: #388e3c;
}

/* Responsividade */
@media (max-width: 768px) {
  .plan-details {
    padding: 20px;
  }

  .plan-details h1 {
    font-size: 20px;
  }

  .plan-details p {
    font-size: 14px;
  }

  .plan-details button {
    font-size: 14px;
    padding: 10px 20px;
  }
}
</style>