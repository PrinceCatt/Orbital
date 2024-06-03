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
      
    <el-button type="text" @click="open">Login/Logout</el-button>
  
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">My</template>
      <el-menu-item index="2-1">
        
        <el-button type="text" @click="myProfile">Profile</el-button>

      </el-menu-item>
      <el-menu-item index="2-2">History</el-menu-item>
      <el-menu-item index="2-3">Favorite</el-menu-item>
      <el-submenu index="2-4">
        <template slot="title">Message</template>
        <el-menu-item index="2-4-1">@me</el-menu-item>
        <el-menu-item index="2-4-2">comment me</el-menu-item>
        <el-menu-item index="2-4-3">Liked me</el-menu-item>
      </el-submenu>
    </el-submenu>
    <el-menu-item index="3">
      
      <el-button type="text" @click="register">Register</el-button>

    </el-menu-item>
    <el-menu-item index="4"><a href="https://www.ele.me" target="_blank">饿了么</a></el-menu-item>
  </el-menu>
  <hr>
  <router-view></router-view>
  </div>
      </template>
  
  <script>
import { register } from '@/api/user';
import router from '@/router';


    export default {
      methods: {
        handleSelect(key, keyPath) {
        console.log(key, keyPath);
        },

        myProfile(){
          if(this.$store.state.user.name == ""){
            alert('Please login first')
            this.openLogin();
          }
          else{ this.openProfile(); }
        },

        openProfile(){
          this.$router.push({path: '/profile'})
        },

        open(){
          if(this.$store.state.user.name == ""){
            this.openLogin();
          }
          else{
            this.openLogout();}
        },

        // For user to login
        openLogin(){
          this.$router.push({path: '/login'})
        },

        // For user to confirm logout
        openLogout() {
          this.$confirm('Are you sure to logout?', {
            confirmButtonText: 'Confirm',
            cancelButtonText: 'No',
            type: 'warning'
          }).then(() => {
              this.$store.dispatch('user/logout').then(() => {
            this.$message({
              type: 'success',
              message: 'successfully logout',
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: 'logoutfailed',
            })
          });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: 'logout canceled'
            });          
          });
        },

        register(){
          this.$router.push({path: '/register'})
        }
      }, 

  
  
      data() {
        return {
          activeIndex: '1',
          activeIndex2: '1'
        };
      },
    }      
  </script>