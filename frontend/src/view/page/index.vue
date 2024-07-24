<template>
    
  <div>
      <h3>{{ $route.params.section }}</h3>
      <el-button @click="newPost()"> New Post </el-button>

<el-row>
<el-col :span="24">
  <div class="grid-content bg-purple-dark">
  
    <el-table
    :data="posts"
    border: true
    :header-cell-style="{'text-align':'center'}"
    :cell-style="{'text-align':'center'}"
    empty-text="Oops, there is no post for this section yet."
    @row-click="openPost"
    align="left"
    style="width: 100%;margin:auto">
    <el-table-column
    prop="id"
    label="Id"
    width="100">
    </el-table-column>
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
      pageNum: 1, // current page number
      pages: 0, // total number of pages
      pageSize: 10,
      total: 0 // total number of posts
    }
  },
  
  created() {
    this.getData()
  },
  
  methods: {
    getData() {
      new Promise((resolve, reject) => {
        getPost(this.$route.params.section, this.pageNum).then(res => {
            this.posts = res.data.pageInfo.list
            this.total = res.data.pageInfo.total
            this.pages = res.data.pageInfo.pages
            this.pageSize = res.data.pageInfo.pageSize
            resolve(this.posts)
        }).catch(err => {
            reject(err)
            return "Error in loading Posts";
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

    openPost(row, column, cell, event){
      let id = row.id
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