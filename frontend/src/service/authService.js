import { jwtDecode } from 'jwt-decode';
import $api from './api'

export class authService {
  static async registration(name, surname, email, password) {
    const {data} = await $api.post(`/registration`, {
        name,
        surname,
        email,
        password
    });
    localStorage.setItem('token', data)
    return jwtDecode(data)
  }

  static async login(username, password) {
    const {data} =  await $api.post(`/login`, {
        username,
        password
    });
    localStorage.setItem('token', data)
    return jwtDecode(data)
  }
}