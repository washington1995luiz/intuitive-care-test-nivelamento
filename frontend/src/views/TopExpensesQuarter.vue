<template>
  <div class="container">
    <h1 class="title">Top 10 Despesas - Último Trimestre</h1>
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
      const response = await fetch('http://localhost:8585/api/v1/health-plan-operators/top-10-expenses-latest-quarter');
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
/* Use os mesmos estilos do TopExpensesYear.vue */
</style>