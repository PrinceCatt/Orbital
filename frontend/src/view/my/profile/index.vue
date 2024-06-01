<template>
    <div>
        <h1>Email:</h1>
        <h3>{{email}}</h3>
        <h1>Username:</h1>
        <el-button type="text" @click="changeName">changeName</el-button>
        <h1>Avatar:</h1>
        <div class="demo-image">
  <div class="block" v-for="fit in fits" :key="fit">
    <el-image
      style="width: 300px; height: 300px"
      :src= avatar
      :fit="fit"></el-image>
      <h3>Change Avatar</h3>
      <el-upload
  class="avatar-uploader"
  action="https://jsonplaceholder.typicode.com/posts/"
  :show-file-list="false"
  :on-success="handleAvatarSuccess"
  :before-upload="beforeAvatarUpload">
  <img v-if="imageUrl" :src="imageUrl" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>
  </div>
</div>
    </div>
</template>

<script>

  export default {
    data() {
      return {
        imageUrl: '',
        email: this.$store.state.avatar,
        avatar: this.$store.state.avatar,
        name: this.$store.state.name,
        fits: ['fill'],
      }
    },

    methods: {
        handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
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
          this.$message({
            type: 'success',
            message: 'Your new username is: ' + value,
          });
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
</style>
