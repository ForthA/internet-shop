import $api from './api'

export class productService {
  static async getCatalog() {
    return await $api.get(`/catalog`);
  }

  static async getProduct() {
    return await $api.get(`/product/list`);
  }
}