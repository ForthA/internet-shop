<script setup>

import { ref } from 'vue';
import { useAuth } from '@/store/AuthStore';

const name = ref('');
const surname = ref('');
const email = ref('');
const password = ref('')

const auth = useAuth();


</script>

<template>
  <div class="regForm">
    <div class="regBody">
      <p class="regTitle">Регистрация</p>
      <input class="regInput" type="text" placeholder="Имя" required v-model="name">
      <input class="regInput" type="text" placeholder="Фамилия" required v-model="surname">
      <input class="regInput" type="email" placeholder="Email" required v-model="email">
      <input class="regInput" type="password" placeholder="Пароль" required v-model="password">
      <button class="regBtn" @click="auth.registration(name, surname, email, password)">Зарегистрироваться</button>
      <p class="regToReg">
        Вернутся на
        <router-link to="/войти">
          <span>вход</span>
        </router-link>
      </p>
    </div>
    <router-link to="/" class="linkToHome">
      на главную
    </router-link>
  </div>
</template>

<style scoped>
.regForm {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  background: #f8f8f8;
}

.regTitle {
  font-weight: 700;
  font-size: 2vw;
  margin-bottom: 5vw;
}

.regBody {
  background-color: #ffffff;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
  border-radius: 0.6vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 30vw;
  height: 40vw;
  margin: auto;
  padding: 2vw;
}

.regInput {
  width: 100%;
  margin-bottom: 2vw;
  font-size: 1.5vw;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
  padding: 1vw;
}

.regInput:focus {
  outline: 0.1vw solid rgb(58, 42, 204);
}

.regBtn {
  background-color: rgb(107, 160, 230);
  color: #ffffff;
  font-size: 1.5vw;
  border-radius: 0.6vw;
  padding: 0.5vw;
  margin-bottom: 3vw;
}

.regToReg {
  align-self: flex-end;
  font-size: 1vw;
}

.regToReg span {
  cursor: pointer;
  color: blue;
}

.linkToHome {
  position: absolute;
  bottom: 2vw;
  left: 2vw;
  background-color: rgb(107, 160, 230);
  color: #ffffff;
  font-size: 1.5vw;
  border-radius: 0.6vw;
  padding: 0.5vw;
}
</style>