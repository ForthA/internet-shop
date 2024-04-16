<script setup>

import closeSVG from '@/assets/close.svg'

const props = defineProps({
  show: Boolean
})

const emit = defineEmits(['closePopup']);

</script>

<template>
  <Transition name="popup">
    <div v-if="show" class="popup-mask">
      <div class="popup-container" ref="popup">
        <div @click="emit('closePopup')" class="popup-close">
          <img :src="closeSVG" alt="close">
        </div>
        <slot></slot>
      </div>
    </div>
  </Transition>
</template>

<style>
.popup-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  transition: opacity 0.3s ease;
}

.popup-container {
  position: relative;
  width: 30vw;
  margin: auto;
  padding: 2vw;
  background-color: #fff;
  border-radius: 0.3vw;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
}

.popup-close{
  position: absolute;
  cursor: pointer;
  top: 0.3vw;
  right: 0.3vw;
  width: 2vw;
}

.popup-enter-from {
  opacity: 0;
}

.popup-leave-to {
  opacity: 0;
}

.popup-enter-from .popup-container,
.popup-leave-to .popup-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>
