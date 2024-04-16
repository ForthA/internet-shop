<script setup>
import userLogo from "@/assets/user.svg";
import cartSvg from "@/assets/cart.svg"
import { ref } from 'vue'
import Popup from "./Popup.vue";

const isShowPopup = ref(false);

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
          </Popup>

        <div class="headerRightWrap">
          
          <router-link to="/войти" class="headerLogin">
            <p>войти</p>
            <img :src="userLogo" alt="logo">
          </router-link>

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

.headerCatalog{
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

.headerLogin img {
  margin-left: 0.3vw;
  width: 1.5vw;
}

.headerBasket{
  display: flex;
  align-items: center;
}

.headerBasket img{
  width: 1.5vw;
  margin: 0 0.5vw;
}

</style>
