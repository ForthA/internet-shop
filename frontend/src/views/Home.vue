<script setup>

import Header from '@/components/Header.vue';
import ProductList from '@/components/ProductList.vue';
import { productService } from '@/service/productService';
import { onMounted, ref, provide } from 'vue';

const catalogList = ref([]);
const isLoading = ref(true);
const error = ref(null);


async function fetchCatalog() {
  try {
    const { data } = await productService.getCatalog();
    catalogList.value = data;
    console.log(data);
  }
  catch (e) {
    error.value = e;
  }
  finally{
    isLoading.value = false;
  }
}

provide('catalog', {
  catalogList,
  isLoading,
  error
})


onMounted(fetchCatalog)

</script>

<template>
  <main>
    <Header />
    <ProductList />
  </main>
</template>
