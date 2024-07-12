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

    <div v-for="comment in comments" :key="comment.id" class="author-title">
      <el-avatar
        class="author-avatar"
        :size="40"
        :src="comment.avatar"
      ></el-avatar>
      <div class="author-info">
        <span class="author-name">{{ comment.authorName }}</span>
        <span class="author-time">{{ comment.createTime }}</span>
      </div>
      
      <el-badge :value="comment.likes" class="item">
        <el-button type="primary" icon="el-icon-edit" round @click="likes(comment.id)">Like</el-button>  
      </el-badge>


        <el-button type="primary" icon="el-icon-edit" round @click="reply(comment.id)">Reply</el-button>  


      <hr />
      <span class="author-content">{{ comment.content }}</span>
    </div>
  </div>
</template>

<script>
import { getPostbyId} from "@/api/post";
import { getComments, getStatus, replyComment, like, unlike} from "@/api/comment";

export default {
  data() {
    return {
      post: {},
      comments: {},
      section: "",
    };
  },

  created() {
    this.getPost();
    this.getComments();
  },

  methods: {
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

    reply(commentId){
      replyComment(commentId)
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