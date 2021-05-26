<template>
  <div class="container">
    <main-slider class="container__images"></main-slider>
  </div>
</template>

<script>
import MainSlider from '../components/main/MainSlider.vue';

export default {
  components: {
    MainSlider,
  }
}
</script>

<style scoped>
.container {
  margin: 0 auto;
  padding: 60px 10px 0;
  width: 100%;
  max-width: 768px;
}
.container__images {
  width: 100%;
  height: 200px;
}
</style>