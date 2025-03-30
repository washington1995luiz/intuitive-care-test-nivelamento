import { createRouter, createWebHistory } from 'vue-router';
import Home from './views/Home.vue';
import PlanList from './views/PlanList.vue';
import PlanDetails from './views/PlanDetails.vue';
import TopExpensesYear from './views/TopExpensesYear.vue';
import TopExpensesQuarter from './views/TopExpensesQuarter.vue';
import FinancialStatements from './views/FinancialStatements.vue';

const routes = [
  { path: '/', component: Home },
  { path: '/plans', component: PlanList }, // Rota para "Planos de Saúde"
  { path: '/plan/:id', component: PlanDetails, props: true },
  { path: '/top-expenses-year', component: TopExpensesYear },
  { path: '/top-expenses-quarter', component: TopExpensesQuarter },
  { path: '/financial-statements', component: FinancialStatements }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;