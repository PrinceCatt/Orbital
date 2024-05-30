
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
import axios from 'axios';

  export default {

    data() {

      var checkEmail = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('Email cannot be empty'));
        }
      };
      var checkPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('password cannot be empty'));
        }
      };

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

    mounted:function() {
        axios.post("/user/login",{
            email: this.loginForm.email,
            password: this.loginForm.email,
        }).then(function(response){
          console.log(response)
        })
      },


    methods: {
      submitForm() {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {

            this.loading = true
            this.$store.dispatch('user/login',this.loginForm)
            .then(() => {
              this.$router.push({path: '/discover'})

            })
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>