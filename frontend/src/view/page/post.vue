<template>
  <div>
  
  <div class="book">

    <div class="item1"><h3>{{ post.author}}</h3><h3>{{ post.time}}</h3></div>
    <div class="item2"><h3>{{ post.content}}</h3></div>

  </div>


  <div><el-button @click="back()">Back</el-button></div>

</div>


</template>

<script>
import { getPostbyId } from "@/api/post";

export default {
  data() {
    return {
      post: {},
      comment: {},
    };
  },

  created() {
    this.getData();
  },

  methods: {
    //get post by id
    getData() {
      new Promise((resolve, reject) => {
        getPostbyId(this.$route.params.id)
          .then((res) => {
            this.post = res.data.post;
            resolve(this.posts);
          })
          .catch((err) => {
            reject(err);
            return "Error in loading Post";
          });
      });
    },

    //get comments by post id
    getComment() {
      new Promise((resolve, reject) => {
        getComment(this.$route.params.id)
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
      this.$router.push({ path: "/discovery" });
    },
  },
};
</script>

<style>

.book {
  display: flex;
  flex-direction: column;
  width: 1600px;
  height: 300px;
  margin: 20px;
  border: 1px dotted rgb(12, 10, 10);
}
.item1 {
  height: 100px;
  width: 1000px;
  background-color: burlywood;
}
.item2 {
  height: 100px;
  width: 150px;
  background-color: skyblue;
}

</style>