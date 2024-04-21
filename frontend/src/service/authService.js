import $api from './api'

export class authService {
  static async registration(name, surname, email, password) {
    const {data} =  $api.post(`/registration`, {
      params: {
        name,
        surname,
        email,
        password
      }
    });

    return data
  }

  static async login(username, password) {
    return $api.post(`/login`, {
      params: {
        username,
        password
      }
    });
  }
}