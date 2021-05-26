<template>
  <div id="app">
    <div>
      <header-nav></header-nav>
      <router-view></router-view>
    </div>
    <sticky-icons></sticky-icons>
  </div>
</template>

<script>
import HeaderNav from './components/static/HeaderNav.vue';
import StickyIcons from './components/static/StickyIcons.vue';

export default {
  name: 'App',
  components: {
    HeaderNav,
    StickyIcons,
  }
}
</script>

<style src="./assets/css/reset.css"></style>
<style>
html, body {
  height: 100%;
}
#app {
  display: flex;
  flex-direction: column;
  height: 100%;
}
</style>
