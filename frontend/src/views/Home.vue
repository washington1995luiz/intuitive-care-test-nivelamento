<template>
  <div class="home-container">
    <h1 class="title">Bem-vindo ao Sistema de Operadoras de Saúde</h1>
    <div class="navigation">
      <button @click="$router.push('/plans')">Planos de Saúde</button>
      <button @click="$router.push('/top-expenses-year')">Top 10 Despesas - Último Ano</button>
      <button @click="$router.push('/top-expenses-quarter')">Top 10 Despesas - Último Trimestre</button>
      <button @click="$router.push('/financial-statements')">Lista Completa de Despesas</button> <!-- Novo botão -->
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home'
};
</script>

<style>
.home-container {
  max-width: 800px;
  margin: 50px auto;
  text-align: center;
  font-family: 'Roboto', Arial, sans-serif;
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #4caf50;
  margin-bottom: 30px;
}

.navigation {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.navigation button {
  padding: 15px;
  font-size: 16px;
  font-weight: bold;
  color: white;
  background-color: #4caf50;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.navigation button:hover {
  background-color: #388e3c;
}
</style>