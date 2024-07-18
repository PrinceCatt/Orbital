<template>
    <el-dialog titel="private chat"
    :visible.sync="privateChatVisible" width="70%">
      <div class="user-info">
        <el-avatar
        class="header-img"
        :size="40"
        :src="user.avatar"
        ></el-avatar>
        <div class="user-info">
          <span class="user-name">{{ user.name }}</span>
        </div>
      </div>

      <el-popover
            placement="bottom"
            title="标题"
            width="500"
            height="700"
            trigger="click"
            v-model="emojiShow"
          >
            <el-button slot="reference">😀</el-button>
            <div class="browBox">
              <ul>
                <li
                  v-for="(item, index) in faceList"
                  :key="index"
                  @click="getBrow(index)"
                >
                  {{ item }}
                </li>
              </ul>
            </div>
  </el-popover>
   <el-button
            class="submit-btn"
            type="primary"
            @click="submitMessage"
            :disabled="text == ''"
            >send
   </el-button>
   <el-input
            :rows="5"
            type="textarea"
            placeholder="请输入内容"
            @keyup.enter.native="submitMessage"
            v-model="text"
          >
    </el-input>

      <hr>

    </el-dialog>
  </template>
    
<script>
const appData = require("@/assets/images/emoji.json");

  export default {
    name: "privateChat",

    data(){
      return{
        privateChatVisible:false,
        user: {},
        avatar: "",
        name: "",
        toUid: null,

        text: "",
        emojiShow: false,
        faceList: [],
        getBrowString: "",

      }
    },

    created() {
      this.loadEmojis();
    },
    
    methods:{

      init(data){
        this.privateChatVisible=true
        this.user = data
        console.log(data); // for debug
      },

      loadEmojis() {
      for (let i in appData) {
        this.faceList.push(appData[i].char);
      }
    },
     // 获取用户点击之后的标签 ，存放到输入框内
      getBrow(index) {
        for (let i in this.faceList) {
          if (index == i) {
            this.getBrowString = this.faceList[index];
            this.text += this.getBrowString;
          }
        }
        this.emojiShow = false;
      },
      submitMessage(){
        this.$parent.text = this.text
        this.$parent.send()
        this.text = ""
      }
    }
  }
</script>

<style scoped>

.item-test{
  border: 1px solid black;
  padding: 7px;
  cursor: pointer;
  float: left;
  width: 300px;
  height:214px;
  margin-right: 20px;
}
.browBox {
  width: 100%;
  height: 200px;
  background: #e6e6e6;
  position: absolute;
  z-index: 100;
  bottom: 0px;
  overflow: scroll;
  ul {
    display: flex;
    flex-wrap: wrap;
    padding: 10px;
    li {
      cursor: pointer;
      width: 10%;
      font-size: 26px;
      list-style: none;
      text-align: center;
    }
  }
}
.submit-btn {
  margin: 0 15px 10px 0;
  float: right;
}

</style>

