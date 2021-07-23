import Vue from 'vue'
import { mapActions } from 'vuex'
import App from './App.vue'
import router from './router'
import store from './store'

import jQuery from 'jquery'

import VueMoment from 'vue-moment'
Vue.use(VueMoment)

window.jQuery = jQuery
window.$ = jQuery

Vue.config.productionTip = false

export const eventBus = new Vue({
  methods:{
    userIsSelected(user){
      this.$emit('userIsSelected', user)
    },

    opntIsSelected(opnt){
      this.$emit('opntIsSelected', opnt)
    },

    reportIsSelected(report){
      this.$emit('reportIsSelected', report)
    },

    messageIsReceived(message){
      this.$emit('messageIsReceived', message)
    }

  }
})

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
