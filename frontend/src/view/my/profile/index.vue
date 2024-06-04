<template>
    <div>

  <el-col :span="6"><div class="grid-content bg-purple"></div><h1>Email:</h1>
        <h3>{{email}}</h3></el-col>

  <el-col :span="6"><div class="grid-content bg-purple"></div><h1>Username:</h1>
    <h3>{{name}}</h3>
    <el-button type="text" @click="changeName">changeName</el-button></el-col>

  <el-col :span="6"><div class="grid-content bg-purple"></div>  <div class="block" v-for="fit in fits" :key="fit">
    <h1>Avatar:</h1>
    <el-image
      style="width: 300px; height: 300px"
      :src= "avatar"
      :fit="fit"></el-image>
  </div></el-col>
  
  <el-col :span="6"><div class="grid-content bg-purple"></div><h3>Change Avatar</h3><el-upload
  class="avatar-uploader"
  action="https://jsonplaceholder.typicode.com/posts/"
  :show-file-list="false"
  :on-success="handleAvatarSuccess"
  :before-upload="beforeAvatarUpload">
  <img v-if="imageUrl" :src="imageUrl" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload></el-col>

    </div>
</template>

<script>

  export default {
    data() {
      return {
        imageUrl: '',
        email: this.$store.state.user.email,
        avatar: this.$store.state.user.avatar,
        name: this.$store.state.user.name,
        fits: ['fill'],
      }
    },

    methods: {
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
        this.$store.dispatch('/user/updateAvatar', file)
      },

      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },

      changeName(){
            this.$prompt('Please enter new username', 'Notice', {
          confirmButtonText: 'confirm',
          cancelButtonText: 'cancel',
        }).then(({ value }) => {


          console.log('Trying dispatch action') // for debug

          
          this.$store.dispatch('user/update', value).then(() => {
            this.$message({
            type: 'success',
            message: 'Your new username is: ' + value,
          });


          }).catch(() => {
            this.$message({
              type: 'warning',
              message: 'Update failed',
            })
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'cancelled'
          });       
        });
    },

  }
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }


  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
