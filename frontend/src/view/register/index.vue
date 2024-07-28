
<template>
  <div>
    <el-form
      :model="registerForm"
      status-icon
      :rules="registerRules"
      ref="loginForm"
      label-width="100px"
      class="register-form"
    >
      <el-form-item label="Email" prop="email">
        <el-input v-model.number="registerForm.email"></el-input>
      </el-form-item>
      <el-form-item label="Password" prop="password">
        <el-input
          type="password"
          v-model="registerForm.password"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="Confirm Password" prop="confirmPassword">
        <el-input
          type="password"
          v-model="registerForm.confirmPassword"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="Username" prop="name">
        <el-input
          type="username"
          v-model="registerForm.name"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm()">Enter</el-button>
        <el-button @click="resetForm()">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
  
  
  
  <script>
export default {
  name: "register",
  data() {
    const checkEmail = (rule, value, callback) => {
      if (!value) {
        callback(new Error("The email cannot be empty"));
      } else {
        callback();
      }
    };

    const checkPassword = (rule, value, callback) => {
      if (value == "") {
        callback(new Error("The password cannot be empty"));
      } else {
        callback();
      }
    };

    const confirmPassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("Confirm passowrd cannot be empty"));
      } else if (value != this.registerForm.password) {
        callback(
          new Error("This confirmed password is different from password")
        );
      } else {
        callback();
      }
    };

    const checkName = (rule, value, callback) => {
      if (value == "") {
        callback(new Error("The username cannot be empty"));
      } else {
        callback();
      }
    };

    return {
      registerForm: {
        email: "",
        password: "",
        confirmPassword: "",
        name: "",
      },

      registerRules: {
        email: [{ required: true, validator: checkEmail, trigger: "blur" }],
        password: [
          { required: true, validator: checkPassword, trigger: "blur" },
        ],
        confirmPassword: [
          { required: true, validator: confirmPassword, trigger: "blur" },
        ],
        name: [{ required: true, validator: checkName, trigger: "blur" }],
      },
    };
  },

  methods: {
    submitForm() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store
            .dispatch("user/register", this.registerForm)
            .then(() => {
              alert("You have successfully registered a NUSurf account");
              this.email = this.registerForm.email;
              this.password = this.registerForm.password;
              this.$store.dispatch("user/login", { email: this.email, password: this.password }).then(() => {
                this.$router.push({path: "/discovery"});
              });
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

    resetForm() {
      this.$nextTick(() => {
        this.$refs['loginForm'].resetFields();
      })
    },
  },
};
</script>