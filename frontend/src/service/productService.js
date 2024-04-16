import $api from './api'

export class postService {
  static async getCatalog() {
    return $api.get(`/catalog`);
  }
}