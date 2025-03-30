<template>
  <div class="container">
    <h1 class="title">Planos de Saúde</h1>
    <div class="filters">
      <input v-model="filters.telefone" placeholder="Filtrar por Telefone" @keyup.enter="applyFilters" />
      <input 
        v-model="filters.cidade" 
        placeholder="Filtrar por Cidade" 
        @input="onCityInput" 
      />
      <input v-model="filters.ddd" placeholder="Filtrar por DDD" @keyup.enter="applyFilters" />
      <input v-model="filters.uf" placeholder="Filtrar por Estado (UF)" @keyup.enter="applyFilters" />
      <input 
        v-model="filters.modalidade" 
        placeholder="Filtrar por Modalidade" 
        @input="onModalityInput" 
      />
      <input v-model="filters.ansRegistration" placeholder="Filtrar por Registro ANS" @keyup.enter="applyFilters" /> <!-- Novo filtro -->
      <select v-model="sortOrder" @change="applyFilters">
        <option value="asc">Ordenar Ascendente</option>
        <option value="desc">Ordenar Descendente</option>
      </select>
      <button @click="applyFilters">Aplicar Filtros</button>
    </div>
    <table>
      <thead>
        <tr>
          <th>Nome Fantasia</th>
          <th>Razão Social</th>
          <th>Registro ANS</th>
          <th>Modalidade</th>
          <th>Cidade</th>
          <th>UF</th>
          <th>Telefone</th>
          <th>Email</th>
          <th>Data Registro</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="plan in filteredPlans" :key="plan.id" @click="goToPlanDetails(plan.id)" class="clickable-row">
          <td>{{ plan.nome_fantasia }}</td>
          <td>{{ plan.razao_social }}</td>
          <td>{{ plan.registro_ans }}</td>
          <td>{{ plan.modalidade }}</td>
          <td>{{ plan.cidade }}</td>
          <td>{{ plan.uf }}</td>
          <td>{{ plan.telefone || 'N/A' }}</td>
          <td>{{ plan.endereco_eletronico }}</td>
          <td>{{ plan.data_registro_ans }}</td>
        </tr>
      </tbody>
    </table>
    <!-- Botões de paginação -->
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
      plans: [],
      filters: {
        telefone: '',
        cidade: '',
        ddd: '',
        uf: '',
        modalidade: '',
        ansRegistration: '' // Novo filtro
      },
      sortOrder: 'asc',
      filteredPlans: [],
      typingTimeout: null,
      page: {
        number: 0,
        size: 5,
        totalPages: 0
      }
    };
  },
  mounted() {
    this.fetchPlans();
  },
  methods: {
    async fetchPlans() {
      try {
        const queryParams = new URLSearchParams({
          telephone: this.filters.telefone || '',
          city: this.filters.cidade || '',
          ddd: this.filters.ddd || '',
          state: this.filters.uf || '',
          modality: this.filters.modalidade || '',
          ansRegistration: this.filters.ansRegistration || '', // Adicionado o filtro de Registro ANS
          sort: `ansRegistrationDate,${this.sortOrder}`,
          page: this.page.number,
          size: this.page.size
        }).toString();

        const url = `http://localhost:8585/api/v1/health-plan-operators?${queryParams}`;

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
        this.plans = jsonData.content;
        this.filteredPlans = this.plans;

        // Atualiza os valores de paginação corretamente
        this.page.totalPages = jsonData.page.totalPages;
        this.page.number = jsonData.page.number;
      } catch (error) {
        console.error('Erro:', error);
      }
    },
    applyFilters() {
      this.page.number = 0; // Reinicia para a primeira página
      this.fetchPlans();
    },
    goToPlanDetails(id) {
      this.$router.push(`/plan/${id}`);
    },
    onCityInput() {
      if (this.filters.cidade.length >= 3) {
        clearTimeout(this.typingTimeout);
        this.typingTimeout = setTimeout(() => {
          this.page.number = 0;
          this.fetchPlans();
        }, 500);
      }
    },
    onModalityInput() {
      if (this.filters.modalidade.length >= 3) {
        clearTimeout(this.typingTimeout);
        this.typingTimeout = setTimeout(() => {
          this.page.number = 0;
          this.fetchPlans();
        }, 500);
      }
    },
    nextPage() {
      if (this.page.number < this.page.totalPages - 1) {
        this.page.number++;
        this.fetchPlans();
      }
    },
    previousPage() {
      if (this.page.number > 0) {
        this.page.number--;
        this.fetchPlans();
      }
    }
  }
};
</script>

<style>
/* Container principal */
.container {
  max-width: 1200px;
  margin: 20px auto;
  font-family: 'Roboto', Arial, sans-serif;
  background-color: #f9f9f9;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0px 6px 12px rgba(0, 0, 0, 0.15);
}

/* Título */
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

/* Filtros */
.filters {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-bottom: 25px;
  justify-content: space-between;
}

.filters input,
.filters select,
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

/* Tabela */
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

/* Efeito de hover */
.clickable-row {
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.clickable-row:hover {
  background-color: #c8e6c9;
}

/* Botões de paginação */
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

/* Responsividade */
@media (max-width: 768px) {
  .filters {
    flex-direction: column;
    gap: 10px;
  }

  .filters input,
  .filters select,
  .filters button {
    flex: none;
    width: 100%;
  }

  table {
    font-size: 14px;
  }
}

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
}

/* Informações do plano */
.plan-details p {
  font-size: 16px;
  margin: 10px 0;
  line-height: 1.6;
}

/* Botão de voltar */
.plan-details button {
  display: block;
  margin: 20px auto 0;
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

/* Mensagem de carregamento */
.plan-details div p {
  text-align: center;
  font-size: 18px;
  color: #666;
}
</style>