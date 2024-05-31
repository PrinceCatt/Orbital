<template>
<el-container style="height: 500px; border: 1px solid #eee">
  <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
    <el-menu :default-openeds="['1', '3']">
      <el-submenu index="1">
        <template slot="title"><i class="el-icon-message"></i>User</template>
        <el-menu-item-group>
            <el-button type="text" @click="open">点击打开 Message Box</el-button>
          <el-menu-item index="1-1">    <router-link to="/discover">NUS today</router-link>
    <router-link to="/login">Login</router-link>
    <el-button type="text" @click="logout()">Logout</el-button>
    <router-view></router-view></el-menu-item>
        </el-menu-item-group>
      </el-submenu>
        
     

    </el-menu>
  </el-aside>
  
  <el-container>
    <el-header style="text-align: right; font-size: 12px">
      <el-dropdown>
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>查看</el-dropdown-item>
          <el-dropdown-item>新增</el-dropdown-item>
          <el-dropdown-item>删除</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span>王小虎</span>
    </el-header>
    
    <el-main>
      <el-table :data="tableData">
        <el-table-column prop="date" label="日期" width="140">
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="120">
        </el-table-column>
        <el-table-column prop="address" label="地址">
        </el-table-column>
      </el-table>
    </el-main>
  </el-container>
</el-container>
    </template>

    <script>

    export default {
      methods: {
        // For user to confirm logout
        logout() {
        this.$confirm('Are you sure to logout?', {
          confirmButtonText: 'Yes',
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

        open() {
          this.$prompt('请输入邮箱', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
            inputErrorMessage: '邮箱格式不正确'
          }).then(({ value }) => {
            this.$message({
              type: 'success',
              message: '你的邮箱是: ' + value
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '取消输入'
            });       
          });
        }
      }
    }
  </script>