
<template>
  
  <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="Email" prop="email">
    <el-input v-model.number="ruleForm.age"></el-input>
  </el-form-item>
  <el-form-item label="Password" prop="password">
    <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submitForm('ruleForm')">Enter</el-button>
    <el-button @click="resetForm('ruleForm')">Reset</el-button>
  </el-form-item>
</el-form>
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
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('password cannot be empty'));
        }
      };


      return {
        ruleForm: {
          email: '',
          password: '',
        },

        rules: {
          email: [
            { validator: checkEmail, trigger: 'blur' }
          ],
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
        }
      }
    },

    mounted:function(){
        axios.post("https://localhost:8088/user/login",{
          params:{
            email: this.email,
            password: this.password,
          }
        }).then(function(response){
          console.log(response)
        })
    },

    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
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