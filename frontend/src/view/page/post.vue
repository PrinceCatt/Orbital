<template>
  <div>
    <div class="demo-type">
  
        <div class="book">
    <div class="item1"></div>
    <div class="item2"></div>
  </div>
    <div>
      <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
    </div>

  </div>
    <h3>{{ post.author}}</h3>
    <h3>{{ post.content}}</h3>
    <h3>{{ post.time}}</h3>
    <el-button @click="back()">Back</el-button>
  </div>
</template>

<script>
import { getPostbyId, getComments } from "@/api/post";

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
            this.comment = res.data.comment;
            resolve(this.posts);
          })
          .catch((err) => {
            reject(err);
            return "Error in loading Post";
          });
      });
    },

    back() {
      this.$router.push({path: `/discovery/page/${this.section}`});
    },
  },
};
</script>

<style>

.book {
  display: flex;
  flex-direction: column;
  width: 400px;
  height: 300px;
  margin: 20px;
  border: 1px dotted red;
}
.item1 {
  height: 100px;
  width: 150px;
  background-color: burlywood;
}
.item2 {
  height: 100px;
  width: 150px;
  background-color: skyblue;
}

</style>