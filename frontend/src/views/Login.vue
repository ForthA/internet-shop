<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router'
import { useAuth } from '@/store/AuthStore';

const login = ref('');
const password = ref('');

const auth = useAuth();

const router = useRouter()


const handleSubmit = async () => {
  try {
    await auth.login({ username: login.value, password: password.value });
    if (auth.errorLogin.length === 0) {
      router.push({ path: '/' });
    }
  } catch (error) {
    console.log(error);
  }
}



</script>

<template>
  <div class="loginForm">
    <div class="loginBody">
      <p class="loginTitle">Вход в аккаунт</p>
      <input class="loginInput" type="text" placeholder="Логин" v-model.trim="login">
      <input class="loginInput" type="password" placeholder="Пароль" v-model.trim="password">
      <button class="loginBtn" @click="handleSubmit">Войти</button>
      <p class="loginToReg">
        Нет аккаунта?
        <router-link to="/регистрация">
          <span>Создать</span>
        </router-link>
      </p>
    </div>
    <router-link to="/" class="linkToHome">
      на главную
    </router-link>
  </div>
</template>

<style scoped>
.loginForm {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  background: #f8f8f8;
}

.loginTitle {
  font-weight: 700;
  font-size: 2vw;
  margin-bottom: 5vw;
}

.loginBody {
  background-color: #ffffff;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
  border-radius: 0.6vw;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 30vw;
  height: 30vw;
  margin: auto;
  padding: 2vw;
}

.loginInput {
  width: 100%;
  margin-bottom: 2vw;
  font-size: 1.5vw;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
  padding: 1vw;
}

.loginInput:focus {
  outline: 0.1vw solid rgb(58, 42, 204);
}

.loginBtn {
  background-color: rgb(107, 160, 230);
  color: #ffffff;
  font-size: 1.5vw;
  border-radius: 0.6vw;
  padding: 0.5vw;
  margin-bottom: 3vw;
}

.loginToReg {
  align-self: flex-end;
  font-size: 1vw;
}

.loginToReg span {
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