<template>
  <div>
    <h3>{{ $route.params.id }}</h3>
    <el-button @click="newPost()"> New Post </el-button>

    <el-row>
      <el-col :span="24"
        ><div class="grid-content bg-purple-dark">
          <ul>
            <li v-for="post in posts" :key="post.id">
              <h3>{{ post.title }}</h3>
            </li>
          </ul>
          <el-pagination background layout="prev, pager, next" :total="100">
          </el-pagination></div
      ></el-col>
    </el-row>
  </div>
</template>

<script>
import { getPost } from "@/api/post";

export default {
  props: ["posts","pageId"],

  mounted() {
    new Promise((resolve, reject) => {
      getPost(this.$route.params.id, pageId)
        .then((response) => {
          posts = response;
          resolve();
        })
        .catch((error) => {
          reject(error);
          return "Error in loading posts";
        });
    });
  },
  methods: {
    open(sectionId, postId) {},

    newPost() {
      this.$router.push({ path: "/newpost" });
    },
  },
};
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
</style>

