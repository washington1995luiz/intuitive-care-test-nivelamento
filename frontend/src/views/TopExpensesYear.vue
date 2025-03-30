<template>
  <div class="container">
    <h1 class="title">Top 10 Despesas - Último Ano</h1>
    <table>
      <thead>
        <tr>
          <th>Registro ANS</th>
          <th>Nome Fantasia</th>
          <th>Razão Social</th>
          <th>Descrição</th>
          <th>Despesas Totais</th>
          <th>Data</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="expense in expenses" :key="expense.registro_ans">
          <td>{{ expense.registro_ans }}</td>
          <td>{{ expense.nome_fantasia || 'N/A' }}</td>
          <td>{{ expense['razao-social'] }}</td>
          <td>{{ expense.descricao }}</td>
          <td>{{ expense.despesas_totais.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) }}</td>
          <td>{{ expense.data }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      expenses: []
    };
  },
  async mounted() {
    try {
      const response = await fetch('http://localhost:8585/api/v1/health-plan-operators/top-10-expenses-latest-year');
      if (!response.ok) {
        throw new Error('Erro ao buscar dados');
      }
      this.expenses = await response.json();
    } catch (error) {
      console.error('Erro ao carregar despesas:', error);
    }
  }
};
</script>

<style>
/* Adicione estilos semelhantes ao PlanList.vue */
.container {
  max-width: 1200px;
  margin: 20px auto;
  font-family: 'Roboto', Arial, sans-serif;
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.15);
}

.title {
  text-align: center;
  background-color: #4caf50;
  color: white;
  padding: 20px;
  border-radius: 10px;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
  background-color: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
}

thead {
  background-color: #4caf50;
  color: white;
  text-transform: uppercase;
}

thead th {
  padding: 15px;
  text-align: left;
  font-size: 14px;
}

tbody tr:nth-child(odd) {
  background-color: #f3f3f3;
}

tbody tr:nth-child(even) {
  background-color: #e8f5e9;
}

tbody tr:hover {
  background-color: #c8e6c9;
}

td {
  border: 1px solid #ddd;
  padding: 12px;
  text-align: left;
  color: #333;
  font-size: 14px;
}
</style>