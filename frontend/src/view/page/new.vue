
<template>
  <div>
  
    
      <el-input placeholder="Post Title"   v-model="title" class="input-with-select">
        <el-select v-model="select" slot="prepend" placeholder="Section">
          <el-option label="ACGN-Anime" value="Anime"></el-option>
          <el-option label="ACGN-Comics" value="Comics"></el-option>
          <el-option label="ACGN-Gaming" value="Gaming"></el-option>
          <el-option label="ACGN-Light Novel" value="LightNovel"></el-option>

          <el-option label="Music-Chinese" value="Music-Chinese"></el-option>
          <el-option label="Music-English" value="Music-English"></el-option>
          <el-option label="Music-Japanese" value="Music-Japanese"></el-option>
          <el-option label="Music-Korean" value="Music-Korean"></el-option>
          <el-option label="Music-Spanish" value="Music-Spanish"></el-option>
          <el-option label="Music-Spanish" value="Music-Others"></el-option>
          
          <el-option label="NUS-Study" value="NUS-Study"></el-option>
          <el-option label="NUS-Relationship" value="NUS-Relationship"></el-option>
          <el-option label="NUS-Physical Games" value="NUS-Games"></el-option>
          <el-option label="NUS-Pals" value="NUS-Pals"></el-option>
         
        </el-select>
        <el-button slot="append" icon="el-icon-search"></el-button>
      </el-input>
  
      <el-input placeholder="Post Body"  type="textarea" :autosize="{ minRows: 1, maxRows: 100}" v-model="content">
      </el-input>
  <el-button @click="submit()"> Post </el-button>
      

  </div>
      
  </template>
    
  <script>
  
  import { newPost } from '@/api/post';
  import { getDate } from '@/utils/date';
  
    export default {
  
      data() {
        return {
          title: '',
          content: '',
          select: '',
          strDate: ""
        }
      },
  
      methods:{
        submit(){
        this.strDate = getDate()
          newPost({title: this.title, content: this.content, section: this.select, time: this.strDate}).then(() => {
            alert("You have successfully created a new post!")
            this.$router.push({path: '/my/posts'})
          })
        }
      }
    }
  </script>
  
  <style>
      .el-select .el-input {
        width: 130px;
      }
      .input-with-select .el-input-group__prepend {
        background-color: #fff;
      }
  </style>