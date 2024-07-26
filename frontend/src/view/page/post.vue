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
          

          <!-- replyBox -->
          <div
            v-clickoutside="hideReplyPostBtn"
            @click="inputFocus"
            class="my-reply mt20"
          >
            <el-avatar
              class="header-img"
              :size="40"
              :src="avatar"
            ></el-avatar>
            <div class="reply-info">
              <input
              v-model="replyInput"
                tabindex="0"
                contenteditable="true"
                id="replyInput"
                spellcheck="false"
                placeholder="Please enter comment..."
                class="reply-input"
                @focus="showReplyPostBtn"
              />

              <div class="reply-btn-box" v-show="btnShow">
                <el-button
                  class="reply-btn"
                  size="medium"
                  @click="sendCommentToPost()"
                  type="primary"
                  >Send Comment</el-button
                >
              </div>
            </div>
          </div>


           <!-- First Level Comment -->
          <div
            v-for="comment in comments"
            :key="comment.id"
            class="author-title reply-father"
          >
            <el-avatar
              class="header-img"
              :size="40"
              :src="comment.avatar"
            ></el-avatar>
            <div class="author-info">
              <span class="author-name">{{ comment.authorName }}</span>
              <span class="author">{{ comment.content }}</span>
              <span class="author-time">{{ comment.createTime }}</span>
            </div>


            <div class="icon-btn"></div>
            <el-badge :value="comment.likes" class="item">
              <el-button
                type="primary"
                icon="el-icon-edit"
                round
                @click="likes(comment.id)">
                Like
              </el-button>
            </el-badge>

            <el-button
              type="primary"
              icon="el-icon-edit"
              round
              @click="callbox(comment.id)"
              >Reply
            </el-button>

            <!--message box for reply-->
            <el-dialog
                  title="Reply to Comment"
                  :visible.sync= dialogVisible
                  width="30%"
                >
                  <!-- intput box -->
                  <el-input
                    v-model="inputValue"
                    placeholder="Please enter comment..."
                  ></el-input>

                  <!-- buttons in input box -->
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">Cancel</el-button>
                    <el-button
                      type="primary"
                      @click="sendCommentToComment(inputValue, commentId)"
                      >Confirm</el-button
                    >
                  </span>
                </el-dialog>

            <!-- Second Level Comment -->
            <div class="reply-box">
              <div
                v-for="reply in comment.replyComments"
                :key="reply.id"
                class="author-title"
              >
                <el-avatar
                  class="header-img"
                  :size="40"
                  :src="reply.avatar"
                ></el-avatar>
                <div class="author-info">
                  <span class="author-name">{{ reply.authorName }}</span>
                  <span class="author">{{ reply.content }}</span> 
                  <span class="author-time">{{ reply.createTime }}</span>
                </div>
                <el-badge :value="comment.likes" class="item">
                  <el-button
                    type="primary"
                    icon="el-icon-edit"
                    round
                    @click="likes(reply.id)"
                    >Like</el-button
                  >
                </el-badge>

                <el-button
                  type="primary"
                  icon="el-icon-edit"
                  round
                  @click="callbox(comment.id)"
                  >Reply</el-button
                >

                               
              </div>
            </div>


            

          </div>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import { getPostbyId } from "@/api/post";
import {
  getComments,
  getStatus,
  replyComment,
  like,
  unlike,
} from "@/api/comment";
import { getDate } from "@/utils/date";

const clickoutside = {
  bind(el, binding, vnode) {
    function documentHandler(e) {
      if (el.contains(e.target)) {
        return false;
      }
      if (binding.expression) {
        binding.value(e);
      }
    }
    el.vueClickOutside = documentHandler;
    document.addEventListener("click", documentHandler);
  },
  update() {},
  unbind(el, binding) {
    document.removeEventListener("click", el.vueClickOutside);
    delete el.vueClickOutside;
  },
};

export default {
  data() {
    return {
      avatar: "",
      post: {},
      comments: {},
      replyComments: {},
      section: "",
      btnShow: "false",
      commentId: -1,
      parentCommentId: -1,
      dialogVisible: false,
      inputValue: "",
      replyInput: "",
    };
  },
  directives: { clickoutside },

  created() {
    this.getPost();
    this.getComments();
    this.setAvatar();
  },

  methods: {

    //true meaning not logged in
    loginInterceptor(){
      let loginStatus = (this.$store.getters.token == 'test_template_token' || this.$store.getters.token == null)
      if(this.$store.getters.token == null)
      alert("Please login first")
      return loginStatus
    },

    callbox(id) {
      let loginStatus = this.loginInterceptor()
      if(loginStatus == false){
      console.log(id)
      this.commentId = id;
      this.dialogVisible = true;
      }
    },

    inputFocus() {
      var replyInput = document.getElementById("replyInput");
      replyInput.style.padding = "8px 8px";
      replyInput.style.border = "2px solid #409EFF";
      replyInput.focus();
    },

    //to show reply button
    showReplyPostBtn() {
      this.btnShow = true;
    },

    //to hide reply button
    hideReplyPostBtn() {
      this.btnShow = false;
      replyInput.style.padding = "10px";
      replyInput.style.border = "none";
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

    //back to discovery page
    back() {
      this.$router.back();
    },

    likes(commentId) {

    let loginStatus = this.loginInterceptor()
    if(loginStatus == true){
      return
    }

      new Promise((resolve, reject) => {
        getStatus(commentId)
          .then((res) => {
            let status = res.data.status;
            if (status == 1) {
              unlike(commentId);
            } else {
              like(commentId);
            }
            resolve();
          })
          .catch((err) => {
            reject(err);
            return "Error in like or unlike";
          });
      });
    },

    //send comment to post
    sendCommentToPost() {
      let loginStatus = this.loginInterceptor()
      if(loginStatus == true){
        return
      }
      let content = document.getElementById("replyInput").value;
      if (content == "") {
        alert("cannot send empty comment");
        return;
      }
      let createTime = getDate();
      replyComment({
        postId: this.post.id,
        content: content,
        createTime: createTime,
      }).then(() => {
        this.replyInput = "";
        this.inputValue = "";
        this.dialogVisible = false;
        this.getComments();
      }).catch((err) => {
        console.log(err)
      });
    },

    //send comment to comment; params: content, parentCommentId, for both replies to first level comment and second level comment, parentCommentId will be the same first level comment
    sendCommentToComment(content, parentCommentId) {
      if (content == "") {
        alert("cannot send empty comment");
        return;
      }
      let createTime = getDate();
      replyComment({
        postId: this.post.id,
        parentCommentId: parentCommentId,
        content: content,
        createTime: createTime,
      }).then(() => {
        this.inputValue = "";
        this.dialogVisible = false;
        this.getComments();
      }).catch((err) => {
        console.log(err)
      });

    },
  },
};
</script>

<style scoped>
  .title {
    padding: 5px 10px;
    background-color: #e8f4ff;
    border-color: #d1e9ff;
    color: #1890ff;
    height: 54px;
    line-height: 46px;
    border-radius: 4px;
    display: inline-block;
  }
  .mt20 {
    margin-top: 20px;
  }
  .f24 {
    font-size: 24px;
  }
  .comment {
    font-family: PingFang SC, HarmonyOS_Regular, Helvetica Neue, Microsoft YaHei,
      sans-serif;
  }
  .my-reply {
    padding: 10px;
    background-color: #fafbfc;
  }
  .my-reply .header-img {
    display: inline-block;
    vertical-align: top;
  }
  .my-reply .reply-info {
    display: inline-block;
    margin-left: 5px;
    width: 90%;
  }
  @media screen and (max-width: 1200px) {
    .my-reply .reply-info {
      width: 80%;
    }
  }
  .my-reply .reply-info .reply-input {
    min-height: 20px;
    line-height: 22px;
    padding: 10px 10px;
    color: #666;
    background-color: #fff;
    border-radius: 5px;
    width: 1800px
  }
  .my-reply .reply-info .reply-input:empty:before {
    content: attr(placeholder);
  }
  .my-reply .reply-info .reply-input:focus:before {
    content: none;
  }
  .my-reply .reply-info .reply-input:focus {
    padding: 8px 8px;
    border: 2px solid #409eff;
    box-shadow: none;
    outline: none;
  }
  /* .reply-info>div .reply-input:focus{
      border: 2px solid #409EFF;
  } */
  .my-reply .reply-btn-box {
    height: 25px;
    margin: 10px 0;
  }
  .my-reply .reply-btn-box .reply-btn {
    position: relative;
    float: right;
    margin-right: 15px;
  }
  .my-comment-reply {
    margin-left: 50px;
  }
  .my-comment-reply .reply-input {
    width: flex;
  }
  .author-title:not(:last-child) {
    border-bottom: 1px solid rgba(178, 186, 194, 0.3);
  }
  .author-title {
    padding: 10px;
  }
  .author-title .header-img {
    display: inline-block;
    vertical-align: top;
  }
  .author-title .author-info {
    display: inline-block;
    margin-left: 5px;
    width: 60%;
    height: 40px;
    line-height: 20px;
  }
  /* .author-title .author-info > span {
    display: block;
    cursor: pointer;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  } */
  .author-title .author-info .author-name {
    color: #303133;
    font-size: 18px;
    font-weight: 500;
  }
  .reply-box .talk-box {
    color: #606266;
  }
  .reply-box .talk-box span {
    color: #6298ce;
  }
  .author-title .author-info .author-time {
    font-size: 14px;
  }
  .author-time {
    color: #606266;
  }
  .author-title .icon-btn {
    width: 30%;
    padding: 0 !important;
    float: right;
    position: absolute;
  }
  @media screen and (max-width: 1200px) {
    .author-title .icon-btn {
      width: 20%;
      padding: 7px;
    }
  }
  .author-title .icon-btn > span {
    cursor: pointer;
  }
  .author-title .icon-btn .iconfont {
    margin: 0 5px;
  }
  .author-title .talk-box {
    margin: 0 50px;
  }
  .author-title .talk-box > p {
    margin: 0;
  }
  .author-title .talk-box .reply {
    font-size: 16px;
    color: #606266;
  }
  
  .author-title .reply-box {
    margin: 10px 0 0 50px;
    background-color: #efefef;
  }
  
  .author-name {
    padding: 0 6px;
    display: inline-block !important;
  }
  .author {

    border-radius: 2px;
    display: inline-block;
    width: 500px;
    text-align: center;
    color: #000000;
  }
</style>

