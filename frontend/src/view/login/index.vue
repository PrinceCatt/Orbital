
<template>
  <div>
    <el-form :model="loginForm" status-icon :rules="loginRules" ref="loginForm" label-width="100px" class="login-form">
    <el-form-item label="Email" prop="email">
    <el-input 
    v-model.number="loginForm.email"
    name="username"
    ></el-input>
  </el-form-item>

  <el-form-item label="Password" prop="password">
    <el-input 
    type="password" 
    name="password"
    v-model="loginForm.password" 
    autocomplete="off"
    ></el-input>
  </el-form-item>

  <el-form-item>
    <el-button type="primary" @click="submitForm(loginForm)">Enter</el-button>
    <el-button @click="resetForm('loginForm')">Reset</el-button>
  </el-form-item>
</el-form>
  </div>

</template>



<script>
<<<<<<< HEAD:frontend/src/view/login/Login.vue
=======

>>>>>>> 5eb54b1f6cacb7e16859ad3a6e4373899a2c4e40:frontend/src/view/login/index.vue
  export default {
    name: 'Login',
    data() {

      const checkEmail = (rule, value, callback) => {
        if (!value) {
          callback(new Error('The email cannot be empty'));
        } else {
          callback()
        }
      }

      const checkPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('The password cannot be empty'));
        } else {
          callback()
        }
      }

      return {
        loginForm: {
          email: '',
          password: '',
        },

        loginRules: {
          email: [
            { required: true, validator: checkEmail, trigger: 'blur' }
          ],
          password: [
            { required: true, validator: checkPassword, trigger: 'blur' }
          ],
        }
      };
    },

<<<<<<< HEAD:frontend/src/view/login/Login.vue
    mounted:function() {
      console.log("1111")
      },
=======

>>>>>>> 5eb54b1f6cacb7e16859ad3a6e4373899a2c4e40:frontend/src/view/login/index.vue


    methods: {
      submitForm(loginForm) {
        console.log("11111111111111")
        this.$refs.loginForm.validate((valid) => {
<<<<<<< HEAD:frontend/src/view/login/Login.vue
          console.log("11111111111111")
=======
          console.log("111")
>>>>>>> 5eb54b1f6cacb7e16859ad3a6e4373899a2c4e40:frontend/src/view/login/index.vue
          if (valid) {
            console.log('222')
            this.loading = true
            this.$store.dispatch('user/login',this.loginForm)
            console.log("11111111111111")
            .then(() => {
              console.log('333')
              this.$router.push({path: '/discover'})
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false
          }
        })
      },

      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>