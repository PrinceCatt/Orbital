<template>
<div>
<div class="line"></div>
<el-menu
  :default-active="activeIndex2"
  class="el-menu-demo"
  mode="horizontal"
  @select="handleSelect"
  background-color="#545c64"
  text-color="#fff"
  active-text-color="#ffd04b">
  <el-menu-item index="1">
    
    <template>
  <el-button type="text" @click="open">Login</el-button>
</template>
    
  </el-menu-item>
  <el-submenu index="2">
    <template slot="title">My</template>

    
    <el-menu-item index="2-1"><el-button type="text" @click="profile">Profile

    </el-button></el-menu-item>
    <el-menu-item index="2-2">History</el-menu-item>
    <el-menu-item index="2-3">Favorite</el-menu-item>
    <el-submenu index="2-4">
      <template slot="title">Message</template>
      <el-menu-item index="2-4-1">@me</el-menu-item>
      <el-menu-item index="2-4-2">comment me</el-menu-item>
      <el-menu-item index="2-4-3">Liked me</el-menu-item>
    </el-submenu>
  </el-submenu>
  <el-menu-item index="3">消息中心</el-menu-item>
  <el-menu-item index="4"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item>
</el-menu>


<h3>11111</h3>
</div>
    </template>


<script>
  export default {
    data() {
      return {
        activeIndex: '1',
        activeIndex2: '1'
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
      },

      open(){
        console.log("pushed")
        this.$router.push({path: '/login'})
      },

      profile(){
        this.$router.push({path: '/profile'})
    }
  }
}
</script>