
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

    <el-input placeholder="Post Body"  type="textarea" :autosize="{ minRows: 1, maxRows: 100}" v-model="content">
    </el-input>
<el-button @click="submit()"> Post </el-button>
    

</div>
    
</template>
  
<script>

import { newPost } from '@/api/post';

  export default {

    data() {
      return {
        title: '',
        content: '',
        select: ''
      }
    },

    methods:{
      submit(){
        var date = new Date();
    var year = date.getFullYear(); //月份从0~11，所以加一
    let month = date.getMonth();
    console.log("month",month);
    var dateArr = [
        date.getMonth() + 1,
        date.getDate(),
        date.getHours(),
        date.getMinutes(),
        date.getSeconds(),
    ];

    for (var i = 0; i < dateArr.length; i++) {
        if (dateArr[i] >= 1 && dateArr[i] <= 9) {
            dateArr[i] = "0" + dateArr[i];
        }
    }

    var strDate =
        year +
        "/" +
        dateArr[0] +
        "/" +
        dateArr[1] +
        " " +
        dateArr[2] +
        ":" +
        dateArr[3] +
        ":" +
        dateArr[4];


      console.log("strDate",strDate);
        newPost({title: this.title, content: this.content, section: this.select, time: strDate})
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