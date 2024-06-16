<template>
    
    <div>
        <h3>{{ $route.params.id }}</h3>
        <el-button @click="newPost()"> New Post </el-button>
        
        <hr>
        <el-table
        :data="posts"
        border
        :header-cell-style="{'text-align':'center'}"
        :cell-style="{'text-align':'center'}"
        empty-text="Oops, you don't have any posts yet."
        align="center"
        style="width: 60%;margin:auto">
        <el-table-column
          fixed
          prop="time"
          label="Time of last update"
          width="220">
        </el-table-column>
        <el-table-column
          prop="section"
          label="Section"
          width="160">
        </el-table-column>
        <el-table-column
          prop="title"
          label="Title"
          width="200">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="180">
          <template slot-scope="scope">
            <el-button type="text" size="midium">details</el-button>
            <el-button type="text" size="midium">update</el-button>
            <el-button @click="deletePost(scope.row)" type="text" size="midium">delete</el-button>
          </template>
        </el-table-column>
      </el-table>
  
  
    </div>
  </template>

  <script>
import {myPosts, deletePost} from '@/api/user'

  export default {

    data() {
        return {
            post: {},
            posts: [],
            pageNum: {default:1}
        }
    },

    created() {
        new Promise((resolve, reject) => {
            myPosts().then(res => {
                this.posts = res.data.pageInfo.list;
                resolve(this.posts)
            }).catch(err => {
                reject(err)
                return "Error in loading MyPosts";
            })
        })
    },

    methods: {
        deletePost(row) {
            this.$confirm('Are you sure to delete the post with title: ' + row.title, {
                confirmButtonText: 'Yes',
                cancelButtonText: 'No',
                type: 'warning'
            }).then(() => {
                this.handleDelete(row.id)
            }).catch(() => {
                this.$message({
                    type:'info',
                    message:'Deletion canceled'
                })
            })
        },

        handleDelete(postId) {
            deletePost(postId).then(() => {
                alert('Deletion success')
                location.reload("")
            }).catch(err => {
                console.log(err)
            })
        },
        
        newPost() {
            this.$router.push({path: '/newpost'})
        }
    }

  }
  </script>