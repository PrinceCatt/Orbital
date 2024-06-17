<template>
    
  <div>
      <h3>{{ this.$route.params.id }}</h3>
      <el-button @click="newPost()"> New Post </el-button>

<el-row>
<el-col :span="24">
  <div class="grid-content bg-purple-dark">
  

    <el-table
        :data="posts"
        border: true
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}"
        :key="key"
        empty-text="Oops, you don't have any posts yet."
        align="left"
        style="width: 60%;margin:auto">
        <el-table-column
          prop="title"
          label="Title"
          width="700">
        </el-table-column>
        <el-table-column
          prop="author"
          label="Author"
          width="160">
        </el-table-column>
        <el-table-column
          prop="time"
          label="Last updated at"
          width="200">
        </el-table-column>
      </el-table>


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
      total: 0,
      key: 0
    }
  },
  
  created() {
    this.getData()
  },
  
  methods: {
    getData() {
      new Promise((resolve, reject) => {
        getPost(this.$route.params.id, this.pageNum).then(res => {
            this.posts = res.data.pageInfo.list
            this.total = res.data.pageInfo.total
            this.pages = res.data.pageInfo.pages
            this.pageSize = res.data.pageInfo.pageSize
            this.key = Math.random()

            console.log(this.key)
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
    "$route": function(){
      getPost(this.$route.params.id, this.pageNum)
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