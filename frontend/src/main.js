import Vue from 'vue'
import { mapActions } from 'vuex'
import App from './App.vue'
import router from './router'
import store from './store'

Vue.config.productionTip = false

new Vue({
  methods: {
    ...mapActions(['account', 'getCurrentUserInfo']),
  },
  router,
  store,
  created: async function() {
    await this.getCurrentUserInfo();  // 페이지에 접속했을 때, 로그인한 유저라면 유저정보를 store에 저장한다.
    this.$mount('#app');
  },
  render: h => h(App),
});
