<template>
  <div>
    <el-col :span="6"
      ><div class="grid-content bg-purple"></div>
      <h1>Email:</h1>
      <h3>{{ email }}</h3></el-col
    >

    <el-col :span="6"
      ><div class="grid-content bg-purple"></div>
      <h1>Username:</h1>
      <h3>{{ name }}</h3>
      <el-button type="text" @click="changeName">changeName</el-button></el-col
    >

    <el-col :span="6"
      ><div class="grid-content bg-purple"></div>
      <div class="block" v-for="fit in fits" :key="fit">
        <h1>Avatar:</h1>
        <el-image
          style="width: 300px; height: 300px"
          :src="avatar"
          :fit="fit"
        ></el-image></div
    ></el-col>

    <el-col :span="6"
      ><div class="grid-content bg-purple"></div>
      <h3>Change Avatar</h3>
      <el-upload
        class="avatar-uploader"
        name="avatar"
        action="http://114.55.89.49:8088/user/updateAvatar"
        :show-file-list="false"
        :on-success="handleAvatarUpload"
        :before-upload="beforeAvatarUpload"
        :headers="{ 'X-Token': token }"
      >
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <i v-else class="el-icon-plus avatar-uploader-icon"></i> </el-upload
    ></el-col>
  </div>
</template>

<script>
export default {

  created() {
    this.$store.dispatch('user/getInfo', this.$store.state.user.token).then(() => {
      this.name = this.$store.state.user.name
      this.email = this.$store.state.user.email
      this.avatar = this.$store.state.user.avatar

    }).catch((err) => {
      console.log(err)
    })
  },

  data() {
    return {
      imageUrl: "",
      email: "",
      avatar: "",
      name: "",
      fits: ["fill"],
      token: this.$store.state.user.token,
    };
  },

  created() {
    this.getInfo();
  },

  methods: {
    getInfo() {
      this.$store.dispatch('user/getInfo', this.$store.state.user.token).then(() => {
        this.email = this.$store.state.user.email;
        this.name = this.$store.state.user.name;
        this.avatar = this.$store.state.user.avatar;
      })
    },

    handleAvatarUpload(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      this.getInfo();
      this.imageUrl = "";
    },

    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },

    changeName() {
      this.$prompt("Please enter new username", "Notice", {
        confirmButtonText: "confirm",
        cancelButtonText: "cancel",
      })
        .then(({ value }) => {
          this.$store
            .dispatch("user/updateName", value)
            .then(() => {
              this.$message({
                type: "success",
                message: "Your new username is: " + value,
              })
              this.getInfo();
            })
            .catch(() => {
              this.$message({
                type: "warning",
                message: "Update failed",
              });
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "cancelled",
          });
        });
    },
  },
};
</script>

<style lang="less" scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
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
