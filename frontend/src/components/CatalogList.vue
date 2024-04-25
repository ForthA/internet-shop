<script setup>
import { inject } from 'vue'
const { catalogList, isLoading, error } = inject('catalog')
import CatalogItem from './CatalogItem.vue';
</script>
<template>

  <div v-if="isLoading">
    ...Загрузка
  </div>

  <div v-else-if="error">
    {{ error.message }}
  </div>

  <ul v-else class="catalogList">
    <CatalogItem v-for="catalogItem in catalogList" :id="catalogItem.id" :title="catalogItem.title"
      :key="catalogItem.id" :subCategories="catalogItem.subCategories" :products="catalogItem.products"/>
  </ul>

</template>
<style>

::-webkit-scrollbar {
  width: 10px;
  background: #f2f2f2; 
}

::-webkit-scrollbar-thumb {
  background: #888; 
  border-radius: 5px; 
}

.catalogList{
  min-height: 30vw;
  overflow:hidden;
  overflow-y:scroll;
}

</style>