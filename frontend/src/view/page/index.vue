<template>
    
  <div>
      <h3>{{ $route.params.id }}</h3>
      <el-button @click="newPost()"> New Post </el-button>

<el-row>
<el-col :span="24">
  <div class="grid-content bg-purple-dark">
  
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div style="display: flex">
        <div><h3><el-button @click="openPost(post.id)"> 
          {{post.title}}
        </el-button></h3></div>
        <div class="white-space"></div>
        <div><h3>{{post.time}}</h3></div>
      </div>

    </li>
  </ul>

</div>
</el-col>
</el-row>


<ul class="page f16 tc mt30">
<li>
<span v-if="page > 1"><b @click="page--,pageClick()">上一页</b></span>
<span v-if="page == 1">上一页</span>
<span v-for="index in pageAll" :key="index" :class="{'active':page == index}" @click="goPage(index)">{{index}}</span>
<span v-if="page!=pageAll"><b @click="page++,pageClick()">下一页</b></span>
<span v-if="page == pageAll">下一页</span>
</li>
<li>共{{pageAll}}页</li>
<li>到<input type="text" class="int02" v-model="jumpPage"> 页<input type="button" class="bt03" value="确定" @click="goPage(jumpPage)"></li>
</ul>

  </div>
</template>

<script>

import {getPost} from '@/api/post'


export default {

  data(){
    return {
      post: {},
      posts: [],
      page: 1,//当前页码，不传默认第一页
      pageAll:"", //数据总页数
      jumpPage:"",//跳转页码
      pageNum: {default: 1}

    }
  },
  
  created() {
    new Promise((resolve, reject) => {
      getPost({section: this.$route.params.id, pageNum: 1})
        .then(res => {
          this.posts = res.data.pageInfo.list;
          resolve(this.posts);
        })
        .catch(err => {
          reject(err);
          return "Error in loading posts";
        });
    })
  },
  
  methods: {
    getPostAlt(event){
      return new Promise((resolve, reject) => {
        console.log(this.$route.params.id)
      getPost({section: this.$route.params.id, pageNum: event})
        .then(res => {
          this.posts = res.data.pageInfo.list;
          resolve(this.posts);
        })
        .catch(err => {
          reject(err);
          return "Error in loading posts";
        });
    }).catch((err) => {
      console.log(err)
    });
 
    },
    
    //on click of next/before button
    pageClick(){       
      this.getPostAlt(this.page);
      window.scrollTo(0,500) //scroll to top
    },

    //go to page
    goPage(event){
      if(event != this.page && event != ""){
        console.log(event)
        this.page = event
        this.getPostAlt(this.page)
        window.scrollTo(0,500) //scroll to top
      }
    },

      newPost(){
        this.$router.push({path: '/newpost'})
      },

      openPost(id){
      this.$router.push({path: `/post/${id}`})
    }
    },



  computed: {
    pagesAll: function(){
        // Start
        var leftNum = 1;
        // End
        var rightNum = this.pageAll;
        // Page number array
        var pageArray = [];
        // Number of page numbers shown, better be odd number
        var showNum = 5;
      
        var centerNum = Math.ceil(showNum/2);
        // 判断分析当总页数超过showNum展示页数时，需要判断分页情况
        //1、在最左边第一个或者在centerNum的前面
        //2、在中间
        //3、最右边最后一个
        if(this.pageAll >= showNum){	
            // 中间centerNum时左右都加上（centerNum-1)
            if(this.page > centerNum && this.page < this.pageAll - (centerNum - 1)){
                //Number() to ensure int instead of string
                leftNum = Number(this.page) - (centerNum - 1)
                rightNum = Number(this.page) + (centerNum - 1)
            }else{
              //最左边或者在showNum的中间
              if(this.page <= centerNum){
                leftNum = 1
                rightNum = showNum
                // 最右边时结束是总条数，开始是showNum减去1
              }else{
                rightNum = this.pageAll
                leftNum = this.pageAll - (showNum - 1)
              }
           }
        }
        while (leftNum <= rightNum){
            pageArray.push(leftNum)
            leftNum ++
	    }
        return pageArray
    }
  },
}



</script>

<style lang="less" scoped>
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
.white-space {
  margin-right: 100px;
}
</style>