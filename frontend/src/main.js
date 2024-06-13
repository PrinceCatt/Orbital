import Vue from 'vue'
import Vuex from 'vuex'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import axios from 'axios'
import router from './router'
import store from './store'

axios.defaults.baseURL = "http://localhost:8088"
Vue.prototype.$http = axios

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.prototype.$confirm = ElementUI.MessageBox.confirm
Vue.use(Vuex)

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')


// check whether the user have logged in before proceeding the target page
router.beforeEach(function(to, from, next) {
  if (to.meta.needLogin) {
    // need login
    if (store.getters.token != null && store.getters.token != 'test_template_token') {
      next();
    } else {
      alert("Please login first")
      next({
        name: "login"
      });
    }
  } else {
    // not need to login
    next();
  }
});