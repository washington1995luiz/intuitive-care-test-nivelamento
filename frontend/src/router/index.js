import { createRouter, createWebHistory } from 'vue-router';
import PlanList from '../views/PlanList.vue';
import PlanDetails from '/src/views/PlanDetails.vue';

const routes = [
  { path: '/', name: 'PlanList', component: PlanList },
  { path: '/plan/:id', name: 'PlanDetails', component: PlanDetails, props: true }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;