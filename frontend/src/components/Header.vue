<script setup>
import userLogo from "@/assets/user.svg";
import cartSvg from "@/assets/cart.svg"
import { ref } from 'vue'
import Popup from "./Popup.vue";
import CatalogList from "./CatalogList.vue";
import { useAuth } from "@/store/AuthStore";

const isShowPopup = ref(false);
const auth = useAuth();

const showPopup = () => {
  isShowPopup.value = true;
}

const closePopup = () => {
  isShowPopup.value = false;
}


</script>

<template>
  <header>
    <div class="container">
      <div class="headerContent">

        <div @click="showPopup" class="headerCatalog">Каталог</div>

        <Popup :show="isShowPopup" @closePopup="closePopup">
          <CatalogList />
        </Popup>

        <div class="headerRightWrap">

          <div class="headerLogin" v-if="!auth.isAuth">
            <router-link to="/войти">
              <div @click="auth.logout" class="headerLoginBody">
                <p>войти</p>
                <img :src="userLogo" alt="logo">
              </div>
            </router-link>
          </div>

          <div class="headerLogin" v-else>

            <router-link to="/войти">
              <p @click="auth.logout">выйти</p>
            </router-link>

            <img :src="userLogo" alt="logo">
            <p class="headerUser">{{ auth.user }}</p>

          </div>

          <router-link to="/корзина" class="headerBasket">
            <p>корзина</p>
            <img :src="cartSvg" alt="cart">
            <p>(0)</p>
          </router-link>

        </div>

      </div>
    </div>
  </header>
</template>

<style scoped>
.headerContent {
  font-size: 1vw;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1.5vw;
  border-bottom: 0.1vw solid gray;
}

.headerCatalog {
  cursor: pointer;
}

.headerRightWrap {
  display: flex;
  align-items: center;
}

.headerLogin {
  margin-right: 4vw;
  display: flex;
  align-items: center;
}

.headerLoginBody{
  display: flex;
  align-items: center;
}

.headerLogin img {
  margin-left: 0.3vw;
  width: 1.5vw;
}

.headerUser{
  margin-left: 0.4vw;
  font-weight: 600;
  color: blue;
}

.headerBasket {
  display: flex;
  align-items: center;
}

.headerBasket img {
  width: 1.5vw;
  margin: 0 0.5vw;
}
</style>
