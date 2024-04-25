import { defineStore } from "pinia";
import { productService } from "@/service/productService";

export const useProducts = defineStore("products", {
  state: () => ({
    products: [],
    isLoading: true,
    error: null
  }),
  getters: {},
  actions: {
    async fetchProduct(){
      try{
        const {data} = await productService.getProduct();
        this.products = data;
      }
      catch(e){
        this.error = e;
      } finally{
        this.isLoading = false;
      }
    }  
  },
});