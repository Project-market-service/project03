<template>
  <div class="container">
    <main-slider class="main__sliders" />
    <item-list class="main__item-list" :items="items"/>
  </div>
</template>

<script>
import MainSlider from '../components/main/MainSlider.vue';
import ItemList from '../components/ItemGridList.vue';

export default {
  components: {
    MainSlider,
    ItemList,
  },
  data: function() {
    return {
      items: [
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
        {
          name: '신발',
          cost: 2000,
          image: 'https://cdn.pixabay.com/photo/2014/10/27/19/18/baby-shoes-505471_1280.jpg',
        },
      ]
    }
  },
}
</script>

<style scoped>
.container {
  margin: 0 auto;
  padding: 60px 10px 0;
  width: 100%;
  max-width: 768px;
}
.main__sliders {
  width: 100%;
}
.main__item-list {
  margin-top: 30px;
}
</style>