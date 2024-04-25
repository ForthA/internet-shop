import { defineStore } from "pinia";
import { authService } from "@/service/authService";

export const useAuth = defineStore("auth", {
  state: () => ({
    errorReg: "",
    errorLogin: "",
    isAuth: false,
    isLoading: true,
    user: {},
  }),
  getters: {},
  actions: {
    async registration({ name, surname, email, password }) {
      try {
        const data = await authService.registration(
          name,
          surname,
          email,
          password
        );
        this.user = data.username;
        this.isAuth = true;
        this.errorReg = "";
      } catch (e) {
        this.errorReg = e.message;
      }
    },

    async login({ username, password }) {
      try {
        const data = await authService.login(username, password);
        this.user = data.username;
        this.isAuth = true;
        this.errorLogin = "";
      } catch (e) {
        this.errorLogin = e.message;
      } finally {
        this.isLoading = false;
      }
    },
    
    logout() {
      this.user = {};
      this.isAuth = false;
      this.errorLogin = "";
      this.errorReg = "";
    }
  },
  persist: true,
});
