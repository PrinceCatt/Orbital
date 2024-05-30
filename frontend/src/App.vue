<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <router-link to="/discover">NUS today</router-link>
    <router-link to="/login">Login</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
import Login from './view/login/Login.vue'
import Nav from './components/Navigation/Nav.vue'
export default {
  name: 'App',
  components: {
    Login,
    Nav
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
