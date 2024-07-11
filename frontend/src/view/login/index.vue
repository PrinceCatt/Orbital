
<template>
  <div>
    <el-form
      :model="loginForm"
      status-icon
      :rules="loginRules"
      ref="loginForm"
      label-width="100px"
      class="login-form"
    >
      <el-form-item label="Email" prop="email">
        <el-input v-model.number="loginForm.email"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input
          type="password"
          v-model="loginForm.password"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm()">Enter</el-button>
        <el-button @click="resetForm('loginForm')">Reset</el-button>
        <el-button @click="register()">Register</el-button>
      </el-form-item>
    </el-form>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    const checkEmail = (rule, value, callback) => {
      if (!value) {
        callback(new Error("The email cannot be empty"));
      } else {
        callback();
      }
    };

    const checkPassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("The password cannot be empty"));
      } else {
        callback();
      }
    };

    return {
      loginForm: {
        email: "",
        password: "",
      },

      loginRules: {
        email: [{ required: true, validator: checkEmail, trigger: "blur" }],
        password: [
          { required: true, validator: checkPassword, trigger: "blur" },
        ],
      },
    };
  },

  methods: {
    submitForm() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store
            .dispatch("user/login", this.loginForm)
            .then(() => {
              this.$router.push({ path: "/discovery" });
              location.reload("");
              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    register() {
      this.$router.push({ path: "register" });
    },
  },
};
</script>