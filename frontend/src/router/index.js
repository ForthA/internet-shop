import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/views/Home.vue'
import Basket from '@/views/Basket.vue'
import Login from '@/views/Login.vue'
import Registration from '@/views/Registration.vue'

const routes = [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/корзина',
      name: 'Basket',
      component: Basket
    },
    {
      path: '/войти',
      name: 'Login',
      component: Login
    },
    {
      path: '/регистрация',
      name: 'Registration',
      component: Registration
    },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
