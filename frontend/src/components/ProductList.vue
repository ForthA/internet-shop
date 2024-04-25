<script setup>
  import { useProducts } from '@/store/ProductStore';

  import { onMounted } from 'vue';
  
  const products = useProducts();
  
  onMounted(products.fetchProduct)

</script>

<template>
  <div>
    
  </div>
</template>

<style>
  
</style>