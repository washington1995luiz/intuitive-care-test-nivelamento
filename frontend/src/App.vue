<template>
  <div class="app-container">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style>
/* Adicione estilos globais aqui, se necessário */
.app-container {
  font-family: 'Roboto', Arial, sans-serif;
  background-color: #f9f9f9;
  min-height: 100vh;
}
</style>
