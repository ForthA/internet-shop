<script setup>
defineProps({
  id: Number,
  title: String,
  subCategories: Array,
  products: Array
})
</script>

<template>

    <p class="catalogTitle"> {{ title }}</p>
    <ul v-if="products.length !== 0" class="productList">
      <li 
        v-for="item in products" 
        :key="item.id"
        class="productName">
        {{ item.title }}
      </li>
    </ul>

  <li v-if="subCategories.length !== 0" class="catalogItem">
    <CatalogItem 
      v-for="catalogItem in subCategories" 
      :id="catalogItem.id" 
      :title="catalogItem.title"
      :key="catalogItem.id" 
      :subCategories="catalogItem.subCategories" 
      :products="catalogItem.products" 
    />
  </li>

</template>

<style>
  .catalogTitle{
    margin-bottom: 1vw;
    font-weight: 700;
  }
  .catalogItem{
    margin-left: 2vw;
    margin-bottom: 2vw;
  }

  .catalogItem:last-child{
    margin-bottom: 0;
  }

  .productList{
    margin-bottom: 1vw;
  }

  .productList:last-child{
    margin-bottom: 0;
  }

  .productName{
    color: blue;
    margin-left: 3vw;
    margin-bottom: 0.5vw;
  }

  .productName:last-child{
    margin-bottom: 0vw;
  }
</style>