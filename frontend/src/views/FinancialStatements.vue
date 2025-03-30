<template>
  <div class="container">
    <h1 class="title">Lista Completa de Despesas</h1>
    <div class="filters">
      <input v-model="filters.ansRegistration" placeholder="Filtrar por Registro ANS" @keyup.enter="applyFilters" />
      <input v-model="filters.cdAccountingAccount" placeholder="Filtrar por Código Conta Contábil" @keyup.enter="applyFilters" />
      <input v-model="filters.description" placeholder="Filtrar por Descrição" @keyup.enter="applyFilters" />
      <input v-model="filters.initialBalanceValue" placeholder="Filtrar por Saldo Inicial" @keyup.enter="applyFilters" />
      <input v-model="filters.finalBalanceValue" placeholder="Filtrar por Saldo Final" @keyup.enter="applyFilters" />
      <input v-model="filters.date" placeholder="Filtrar por Data (YYYY-MM-DD)" @keyup.enter="applyFilters" />
      <button @click="applyFilters">Aplicar Filtros</button>
    </div>
    <table>
      <thead>
        <tr>
          <th>Registro ANS</th>
          <th>Código Conta Contábil</th>
          <th>Descrição</th>
          <th>Saldo Inicial</th>
          <th>Saldo Final</th>
          <th>Data</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in financialStatements" :key="item.id">
          <td>{{ item.registro_ans }}</td>
          <td>{{ item.codigo_conta_contabil }}</td>
          <td>{{ item.descricao }}</td>
          <td>{{ item.balanco_saldo_inicial.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) }}</td>
          <td>{{ item.balanco_saldo_final.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) }}</td>
          <td>{{ item.data }}</td>
        </tr>
      </tbody>
    </table>
    <div class="pagination">
      <button @click="previousPage" :disabled="page.number === 0">Página Anterior</button>
      <span>Página {{ page.number + 1 }} de {{ page.totalPages }}</span>
      <button @click="nextPage" :disabled="page.number >= page.totalPages - 1">Próxima Página</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      financialStatements: [],
      filters: {
        ansRegistration: '',
        cdAccountingAccount: '',
        description: '',
        initialBalanceValue: '',
        finalBalanceValue: '',
        date: ''
      },
      page: {
        number: 0,
        size: 5,
        totalPages: 0
      }
    };
  },
  async mounted() {
    this.fetchFinancialStatements();
  },
  methods: {
    async fetchFinancialStatements() {
      try {
        const queryParams = new URLSearchParams({
          ansRegistration: this.filters.ansRegistration || '',
          cdAccountingAccount: this.filters.cdAccountingAccount || '',
          description: this.filters.description || '',
          initialBalanceValue: this.filters.initialBalanceValue || '',
          finalBalanceValue: this.filters.finalBalanceValue || '',
          date: this.filters.date || '',
          sort: 'id,desc',
          page: this.page.number,
          size: this.page.size
        }).toString();

        const url = `http://localhost:8585/api/v1/financial-statements?${queryParams}`;
        console.log('URL gerada:', url);

        const response = await fetch(url, {
          headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': '*'
          }
        });

        if (!response.ok) {
          throw new Error('Erro ao buscar dados');
        }

        const jsonData = await response.json();
        this.financialStatements = jsonData.content;
        this.page.totalPages = jsonData.page.totalPages;
        this.page.number = jsonData.page.number;
      } catch (error) {
        console.error('Erro ao carregar dados:', error);
      }
    },
    applyFilters() {
      this.page.number = 0; // Reinicia para a primeira página
      this.fetchFinancialStatements();
    },
    nextPage() {
      if (this.page.number < this.page.totalPages - 1) {
        this.page.number++;
        this.fetchFinancialStatements();
      }
    },
    previousPage() {
      if (this.page.number > 0) {
        this.page.number--;
        this.fetchFinancialStatements();
      }
    }
  }
};
</script>

<style>
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

.filters {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 25px;
  justify-content: space-between;
}

.filters input,
.filters button {
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 16px;
  flex: 1;
  min-width: 150px;
}

.filters button {
  background-color: #4caf50;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.filters button:hover {
  background-color: #388e3c;
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

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-top: 20px;
}

.pagination button {
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #4caf50;
  color: white;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s ease;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.pagination button:hover:not(:disabled) {
  background-color: #388e3c;
}

.pagination span {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}
</style>