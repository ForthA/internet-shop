<script setup>

import Header from '@/components/Header.vue';
import { postService } from '@/service/productService';

import { onMounted, ref } from 'vue';

const catalog = ref([]);
// const isLoading = ref(true);
// const error = ref(null);

async function fetchCatalog() {
  try {
    const resp = await fetch('http://localhost:7070/catalog');
    const data = await resp.json();
    catalog.value = data;
    console.log(data);
  }
  catch (e) {
    console.log(e);
  }
}


onMounted(fetchCatalog)

</script>

<template>
  <main>
    <Header />
  </main>
</template>
