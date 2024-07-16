<template>
  <div>
    <div class="book">
      <div class="item1">
        <h3>{{ post.author }}</h3>
        <h3>{{ post.time }}</h3>
      </div>
      <div class="item2">
        <h3>{{ post.content }}</h3>
      </div>
    </div>

    <div><el-button @click="back()">Back</el-button></div>



<el-card>
<el-row :gutter="10">
  <el-col :span="24">

<div class="comment"></div>
    

<div v-clickoutside="hideReplyPostBtn" @click ="inputFocus" class="my-comment mt20">
<el-avatar class="header-img" :size ="40" :src="this.$store.state.user.avatar"></el-avatar>
<div class="reply-info">
<input tabindex="0" contenteditable="true" id="replyInput" spellcheck="false" placeholder="Please enter comment..." class="reply-input" @focus="showReplyPostBtn">

<div class="reply-btn-box" v-show="btnShow">
  <el-button class="reply-btn" size="medium" @click="sendCommentToPost()" type="primary">Send Comment</el-button>
</div>
</div>
</div>


    <div v-for="comment in comments" :key="comment.id" class="author-title reply-father">
      <el-avatar class="author-avatar" :size="40" :src="comment.avatar"></el-avatar>
      <div class="author-info">
        <span class="author-name">{{ comment.authorName }}</span>
        <span class="author-time">{{ comment.createTime }}</span>
      </div>
      
      <el-badge :value="comment.likes" class="item">
        <el-button type="primary" icon="el-icon-edit" round @click="likes(comment.id)">Like</el-button>  
      </el-badge>


        <el-button type="primary" icon="el-icon-edit" round @click="callbox(comment.id)">Reply</el-button>  


      <span class="author-content">{{ comment.content }}</span>

      
      <div class="reply">
      <div v-for="reply in comment.replyComments" :key="reply.id" class="author-title">
        <el-avatar class="header-img" :size="40" :src="reply.avatar"></el-avatar>
        <div class="author-info">
          <span class="author-name">{{ reply.authorName }}</span>
          <span class="author-time">{{ reply.createTime }}</span>
        </div>
        <el-badge :value="comment.likes" class="item">
        <el-button type="primary" icon="el-icon-edit" round @click="likes(reply.id)">Like</el-button>  
      </el-badge>

        <el-button type="primary" icon="el-icon-edit" round @click="callbox(reply.id)">Reply</el-button>  

      <span class="author-content">{{ reply.content }}</span>

      

<el-dialog title="输入框" :visible.sync="dialogVisible" width="30%">
      <!-- 输入框 -->
      <el-input v-model="inputValue" placeholder="请输入内容"></el-input>
 
      <!-- 对话框的尾部（按钮等） -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="sendCommentToComment(inputValue)">确 定</el-button>
      </span>
    </el-dialog>


      </div>
    </div>
  </div>

  </el-col>
  </el-row>
  </el-card>
  </div>
</template>

<script>
import { getPostbyId} from "@/api/post";
import { getComments, getStatus, replyComment, like, unlike} from "@/api/comment";
import { getDate } from "@/utils/date";

const clickoutside = {
  bind(el, binding, vnode){
    function documentHandler(e){
      if(el.contains(e.target)){
        return false
      }
      if(binding.expression){
      binding.value(e)
    }
  }
  el.vueClickOutside = documentHandler;
  document.addEventListener("click", documentHandler)
},
update() {},
unbind(el,binding){
  document.removeEventListener("click",el.vueClickOutside)
  delete el.vueClickOutside
}
}

export default {
  data() {
    return {
      post: {},
      comments: {},
      replyComments: {},
      section: "",
      btnShow: "false",
      commenId: "",
      dialogVisible: false,
      inputValue: ""
    };
  },
  directives: { clickoutside },

  created() {
    this.getPost();
    this.getComments();
  },

  methods: {
    callbox(id){
      this.commenId = id
      this.dialogVisible = true
    },

    inputFocus() {
      var replyInput = document.getElementById("replyInput")
      replyInput.style.padding = "8px 8px"
      replyInput.style.border = "2px solid #409EFF"
      replyInput.focus()
    },

    //to show reply button
    showReplyPostBtn() {
      this.btnShow = true
    },

    //to hide reply button
    hideReplyPostBtn() {
      this.btnShow = false;
      replyInput.style.padding = "10px";
      replyInput.style.border = "none";
    },

    hideReplyCommentBox(){
      this.replyBoxShow = false;
      commentInput.style.padding = "10px";
      commentInput.style.border = "none";
    },

    //get post by id
    getPost() {
      new Promise((resolve, reject) => {
        getPostbyId(this.$route.params.id)
          .then((res) => {
            this.post = res.data.post;
            this.section = res.data.post.section;
            resolve(this.posts);
          })
          .catch((err) => {
            reject(err);
            return "Error in loading Post";
          });
      });
    },

    //get comments by post id
    getComments() {
      new Promise((resolve, reject) => {
        getComments(this.$route.params.id)
          .then((res) => {
            this.comments = res.data.comments;
            resolve(this.posts);
          })
          .catch((err) => {
            reject(err);
            return "Error in loading Post";
          });
      });
    },

    back() {
      this.$router.push({ path: `/discovery/page/${this.section}` });
    },

    likes(commentId){
      new Promise((resolve, reject) => {
        getStatus(commentId)
          .then((res) => {
            let status = res.data.status;
            if(status == 1){
              unlike(commentId)
            }
            else{
              like(commentId)
            }
            resolve();
          })
          .catch((err) => {
            reject(err);
            return "Error in like or unlike";
          });
      });
    },

    sendCommentToPost(){
      let content = document.getElementById('replyInput').value
      if(content == "") {
        alert("cannot send empty comment")
        return
      }
      let createTime = getDate()
      replyComment({postId: this.post.id, content: content, createTime: createTime})
    },

    sendCommentToComment(content, parentCommentId){

      console.log(content)
      if(content == "") {
        alert("cannot send empty comment")
        return
      }
      let createTime = getDate()
      replyComment({postId: this.post.id, parentCommentId: parentCommentId, content: content, createTime: createTime})
    
    }
  },
};
</script>

<style lang="stylus" scoped>
.author-title {
  line-height: 20px;
  margin-left: auto;
  margin-right: auto;
  width: 60%;
  background-color: #ff0;
  border-radius: 0px;
  padding: 10px;

  .author-avatar {
    display: inline-block;
    vertical-align: top;
  }

  .author-info {
    display: inline-block;
    margin-left: 5px;
    width: 60%;
    height: 40px;
    line-height: 20px;
  }

  .author-name {
    color: #000;
    font-size: 18px;
    font-weight: bold;
  }

  .author-time {
    font-size: 14px;
  }
}
</style>

