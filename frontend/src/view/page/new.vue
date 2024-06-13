
<template>
<div>

  
    <el-input placeholder="Post Title"   v-model="title" class="input-with-select">
      <el-select v-model="select" slot="prepend" placeholder="Section">
        <el-option label="ACGN" value="ACGN"></el-option>
        <el-option label="Music" value="Music"></el-option>
        <el-button @click="submit()"> Post </el-button>
       
      </el-select>
      <el-button slot="append" icon="el-icon-search"></el-button>
    </el-input>

    <el-input placeholder="Post Body"  type="textarea" :autosize="{ minRows: 1, maxRows: 100}" v-model="body">
    </el-input>
<el-button @click="submit()"> Post </el-button>
    

</div>
    
  </template>


  <style>
    .el-select .el-input {
      width: 130px;
    }
    .input-with-select .el-input-group__prepend {
      background-color: #fff;
    }
  </style>
  
  <script>
import { newPost } from '@/api/post';

  export default {
    data() {
      return {
        title: '',
        body: '',
        select: ''
      }
    },
    methods:{
      submit(){
        if(this.$store.state.user.name == ""){
            alert('Please login first')
            this.$router.push({path: '/login'})
      }
      else{
        const currentTime = Date.now()
        newPost({title: this.title, body: this.body, section: this.select, time: currentTime})
    }
    }
  }
}
  </script>