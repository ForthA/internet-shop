import { defineStore } from 'pinia'
import { authService } from '@/service/authService'

export const useAuth = defineStore('auth', {
  state: () => ({
    auth: false,
    user: {}
  }),
  getters: {
  },
  actions: {
    async registration(name, surname, email, password){
      try{
        const resp = await authService.registration(name, surname, email, password);
        console.log(resp);
      } catch(e){
        console.log(e.message);
      }
    }
  },
})