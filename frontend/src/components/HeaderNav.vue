<template>
  <div class="header-nav">
    <div class="header-nav__container">
      <router-link class="header-nav__logo" to="/">  
        <img alt="Logo" src="../assets/image/logo.png">
      </router-link>
      <div class="header-nav__search-box">
        <input type="text">
        <span class="material-icons">search</span>
      </div>
      <div v-if="isAuth" class="header-nav__items">
        <router-link to="/mypage" class="header-nav__item">My page</router-link>
        <a v-on:click="logout" class="header-nav__item">Logout</a>
      </div>
      <div v-else class="header-nav__items">
        <router-link to="/login" class="header-nav__item">Sign in</router-link>
        <router-link to="/join" class="header-nav__item">Sign up</router-link>
      </div>
      <span class="material-icons header-nav__abs header-nav__abs--left header--nav__list">reorder</span>
      <ul class="header-nav__abs header-nav__abs--right">
        <li>Cart</li>
        <li>Recently view</li>
        <li>Like</li>
      </ul>
    </div>
  </div>
</template>

<script>
// import axios from '@/api'
import { mapGetters, mapActions } from 'vuex'

export default {
  computed: {
    ...mapGetters(['isAuth']),
  },
  methods: {
    ...mapActions(['account', 'postLogout']),
    logout() {
      this.postLogout();
      alert('로그아웃 성공!');
    },
  }
}
</script>

<style scoped>
.header-nav {
  padding: 5px 20px;
  background-color: #f1f1f1;
}
.header-nav__container {
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  max-width: 968px;
  position: relative;
}
.header-nav__logo {
  height: 46px;
  width: 46px;
}
.header-nav__logo img {
  height: 100%;
  object-fit: contain;
}
.header-nav__search-box {
  display: flex;
  align-items: center;
  position: relative;
}
.header-nav__search-box input {
  width: 230px;
  height: 20px;
  border: none;
  border-radius: 10px;
  padding: 4px 30px 4px 14px;
}
.header-nav__search-box input:focus { outline: none; }
.header-nav__search-box span {
  position: absolute;
  right: 2px;
  cursor: pointer;
}
.header-nav__item {
  margin-right: 10px;
  cursor: pointer;
}
.header-nav__item:last-child { margin-right: 0; }
.header-nav__abs {
  position: absolute;
  top: 58px;
}
.header-nav__abs--left { left: 5px; }
.header--nav__list {
  font-size: 36px;
  cursor: pointer;
}
.header-nav__abs--right {
  right: 0;
  text-align: right;
}
</style>