<template>
  <div>
    <vueper-slides autoplay fade :touchable="false" :slide-ratio="1 / 2">
      <vueper-slide v-for="(slide, i) in slides" :key="i" :image="slide.image" />
    </vueper-slides>
  </div>
</template>

<script>
import { VueperSlides, VueperSlide } from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'

export default {
  components: { VueperSlides, VueperSlide },
  data: function() {
    return {
      slides: [
        { image: 'https://cdn.pixabay.com/photo/2020/07/24/21/58/lemon-5435158_1280.jpg' },
        { image: 'https://cdn.pixabay.com/photo/2020/01/11/13/22/freedom-4757533_1280.jpg' },
        { image: 'https://cdn.pixabay.com/photo/2020/02/22/11/09/anemone-4870275_1280.jpg' },
        
      ]
    };
  },
}
</script>

<style>
</style>