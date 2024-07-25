
<template>
    <div>
        Section: {{ section }}
        <br>
        Title: <el-input size="medium"
        style="size: 200px;"
        v-model="title"></el-input>

        <br>
        <br>
        
        Content: <el-input
        type="textarea"
        size="small"
        style="size: 80%;"
        :autosize="{ minRows: 1, maxRows: 100}"
        v-model="content">
        </el-input>

    <el-button @click="submit()"> Post </el-button>
        
    <div><el-button @click="back()">Cancel</el-button></div>
    </div>
        
    </template>
      
    <script>
    
    import { updatePost, getPostbyId } from '@/api/post';
    import { getDate } from '@/utils/date';
    
      export default {
    
        data() {
          return {
            title: '',
            content: '',
            section: '',
            strDate: "",
            post: {},
          }
        },

        created() {
            this.getPost()
        },
    
        methods:{

            getPost() {
                new Promise((resolve, reject) => {
                    getPostbyId(this.$route.params.postId).then(res => {
                        this.post = res.data.post
                        this.section = this.post.section
                        this.title = this.post.title
                        this.content = this.post.content
                        resolve(this.post)
                        }).catch(err => {
                        reject(err)
                        return "Error in loading original post";
                    })
                })
            },
            
            back() {
                this.$router.back();
            },

            submit(){
            this.strDate = getDate()
            if (this.title == this.post.title && this.content == this.post.content) {
                alert("This post is the same as before!")
            } else {
                updatePost({id: this.post.id, title: this.title, content: this.content, section: this.section, time: this.strDate}).then(() => {
                    alert("You have successfully updated the post!")
                    this.$router.push({path: '/my/posts'})
            })
            }
          }
        }
      }
    </script>
    
<style>
.el-textarea {
  width: 100%;
}
</style>