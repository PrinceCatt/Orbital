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


<div class="pagaination-tool" style="padding:20px 110px 70px">
  <div class="count-show">
      <span>
          Total: {{total}} posts and {{pages}} pages
      </span>
  </div>
  <el-pagination
    @current-change="handlePageChange"
    :page-size="pageSize"
    layout="prev, pager, next, jumper"
    :total="total"
    :pages="pages">
  </el-pagination>

</div>

  </div>
</template>

<script>

import {getPost} from '@/api/post'


export default {

  data(){
    return {
      post: {},
      posts: [],
      pageNum: 1, //当前页码，不传默认第一页
      pages: 0, //数据总页数
      pageSize: 10,
      total: 0
    }
  },
  
  created() {
    this.getData()
  },
  
  methods: {
    getData() {
      new Promise((resolve, reject) => {
        getPost({section: this.$route.params.id, pageNum:this.pageNum}).then(res => {
            this.posts = res.data.pageInfo.list
            this.total = res.data.pageInfo.total
            this.pages = res.data.pageInfo.pages
            this.pageSize = res.data.pageInfo.pageSize
            resolve(this.posts)
        }).catch(err => {
            reject(err)
            return "Error in loading MyPosts";
        })
      })
    },

    handlePageChange(val) {
      this.pageNum = val
      this.getData()
    },

    newPost(){
      this.$router.push({path: '/newpost'})
    },

    openPost(id){
      this.$router.push({path: `/post/${id}`})
    }
  },

  watch: {
    "$route": function() {
      this.getData();
    }
  }
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