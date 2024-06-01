import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import axios from 'axios'
import router from './router'
import store from './store'
import Vuex from 'vuex'
import jsimport Cookies from 'js-cookie'
-Cookies


axios.defaults.baseURL = "http://localhost:8088"
Vue.prototype.$http = axios

Vue.config.productionTip = false
Vue.use(ElementUI);

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')