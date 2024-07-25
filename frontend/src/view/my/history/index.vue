<template>
    
    <div>
        <el-table
        :data="posts"
        border
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}"
        empty-text="Oops, you haven't viewed any posts yet."
        @row-click="goTo"
        align="center"
        style="width: 70%;margin:auto">
        <el-table-column
          fixed
          prop="time"
          label="Time of last update"
          width="220">
        </el-table-column>
        <el-table-column
          prop="section"
          label="Section"
          width="150">
        </el-table-column>
        <el-table-column
          prop="title"
          label="Title"
          width="330">
        </el-table-column>
        <el-table-column
          prop="author"
          label="Author"
          width="120">
        </el-table-column>

      </el-table>
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
import { getHistory } from '@/api/user';

export default {

data() {
    return {
        post: {},
        posts: [],
        pageNum: 1, // default page number
        pages: 0, // total number of pages
        pageSize: 10,
        total: 0, // total number of posts
    }
},

created() {
    this.getData()
},

methods: {

    getData(){
        new Promise((resolve, reject) => {
          getHistory(this.pageNum).then(res => {
            this.posts = res.data.pageInfo.list
            this.total = res.data.pageInfo.total
            this.pages = res.data.pageInfo.pages
            this.pageSize = res.data.pageInfo.pageSize
            resolve()
          }).catch(err => {
            reject(err)
            return "Error in loading History";
          })
        })
    },

    handlePageChange(val) {
        this.pageNum = val
        this.getData()
    },

    goTo(row, column, cell, event){
        let postId = row.id
        this.$router.replace(`/post/${postId}`)
    },

}

}


</script>