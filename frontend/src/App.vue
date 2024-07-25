<template>
  <div id="app">
    <Home></Home>
  </div>
</template>

<script>
import Login from "./view/login/index.vue";
import Home from "./view/homepage";
export default {
  name: "App",
  components: {
    Login,
    Home,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}
</style>

 <!--Main page, to load homepage-->