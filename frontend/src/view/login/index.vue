
<template>
  <div>
    <el-form :model="loginForm" status-icon :rules="loginRules" ref="loginForm" label-width="100px" class="login-form">
    <el-form-item label="Email" prop="email">
    <el-input v-model.number="loginForm.email"></el-input>
  </el-form-item>
  <el-form-item label="Password" prop="password">
    <el-input type="password" v-model="loginForm.password" autocomplete="off"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submitForm()">Enter</el-button>
    <el-button @click="resetForm('loginForm')">Reset</el-button>
  </el-form-item>
</el-form>
  </div>

</template>



<script>

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




    methods: {
      submitForm() {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            console.log('222') // for debug
            this.loading = true
            this.$store.dispatch('user/login',this.loginForm).then(() => {
              console.log('333') // for debug
              this.$router.push({path: '/discover'})
              this.loading = false
            }).catch(() => {
              console.log('dispatch failed') // for debug
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