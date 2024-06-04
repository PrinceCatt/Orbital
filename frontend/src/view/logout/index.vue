<template>
    <el-button type="text" @click="open">Logout</el-button>
  </template>

<script>

export default {
    name: 'Logout',
    methods:{

        // For user to confirm logout
        open() {
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
            message: 'logout failed',
          })
        });

        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'logout canceled'
          });          
        });
      }
    },
}

</script>