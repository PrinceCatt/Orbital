
<script>

export function open() {
        this.$confirm('Are you sure to logout?', {
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
          type: 'warning'
        }).then(() => {
            this.$store.dispatch('user/logout').then(() => {
                this.$router.push({path: '/discovery'})
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

</script>