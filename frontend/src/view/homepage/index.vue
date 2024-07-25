<template>
  <div>
  <el-menu
    :default-active="activeIndex2"
    class="el-menu-demo"
    mode="horizontal"
    @select="handleSelect"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b">

     <!--top bar-->
    
    <el-menu-item index="1">
    <el-button type="text" @click="open">{{Login}}</el-button>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">My</template>
      <el-menu-item index="2-1">
        <el-button type="text" @click="myProfile">Profile</el-button>
      </el-menu-item>
      <el-menu-item index="2-2">
        <el-button type="text" @click="myPosts">Posts</el-button>
      </el-menu-item>
      <el-menu-item index="2-3">
        <el-button type="text" @click="myHistory">History</el-button>
      </el-menu-item>
      <el-menu-item index="2-4">Favorite</el-menu-item>
      <el-submenu index="2-5">
        <template slot="title">Message</template>
        <el-menu-item index="2-4-1">@me</el-menu-item>
        <el-menu-item index="2-4-2">comment me</el-menu-item>
        <el-menu-item index="2-4-3">Liked me</el-menu-item>
      </el-submenu>
    </el-submenu>
    <el-menu-item index="3">
      
      <el-button type="text" @click="discovery()">Discovery</el-button>

    </el-menu-item>
    <el-menu-item index="4">
      
      <el-button type="text" @click="openChat">Chat</el-button>

      </el-menu-item>
  </el-menu>
  <hr>
  <router-view></router-view>
  </div>
      </template>
  
  <script>

    export default {

      data() {
        return {
          activeIndex: '1',
          activeIndex2: '1',
          Login: "",
        };
      },

      created() {
        if (this.$store.getters.token == 'test_template_token' || this.$store.getters.token == null) {
            this.Login = "Login"
          } else {
            this.Login = "Logout"
          }
      },

      methods: {

        //focus on selection
        handleSelect(key, keyPath) {
        console.log(key, keyPath);
        },

        //open my profile
        myProfile(){
          if (this.$store.getters.token == 'test_template_token' || this.$store.getters.token == null) {
            alert("Please login first")
            this.openLogin()
          } else {
            this.$router.push({path: '/my/profile'})
          }
        },

        //open my posts
        myPosts(){
          this.$router.push({path: '/my/posts'})
        },

        //open my history
        myHistory(){
          this.$router.push({path: '/my/history'})
        },

        //alternating between login and logout and call
        open(){
          if (this.$store.getters.token == 'test_template_token' || this.$store.getters.token == null) {
            this.openLogin()
          } else {
            this.openLogout()
          }
        },

        // For user to login
        openLogin(){
          this.$router.push({path: '/login'})
        },

        // For user to confirm logout
        openLogout() {
        this.$confirm('Are you sure to logout?', {
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
          type: 'warning'
        }).then(() => {
            this.$store.dispatch('user/logout').then(() => {
              this.$router.push({path: '/discovery'})
              location.replace("")
          this.$message({
            type: 'success',
            message: 'successfully logout',
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'logout failed',
          })
        });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'logout canceled'
          });          
        });
      },

      //go to discovery which is actually useless
        discovery(){
          this.$router.push({path: '/discovery'})
        },

        //go to chat
        openChat(){
          this.$router.push({path: '/chat'})
        }

      }, 

    }      
  </script>