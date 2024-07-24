<template>
    <el-dialog title="private chat"
    :visible.sync="privateChatVisible" width="70%"
    @close="handleClose">
      <div class="user-info">
        <el-avatar
        class="header-img"
        :size="40"
        :src="otherUser.avatar"
        ></el-avatar>
        <div class="user-info">
          <span class="user-name">{{ otherUser.name }}</span>
        </div>
      </div>
      <hr>

<div class="chat-history">
 <div v-for="(socketMsg) in messages" :key="socketMsg.id">
  <!--Showing other's messages-->
  <div v-if="socketMsg.toUid == uid" class="message-other">
    <span style="font-size: 8px">{{otherUser.name}}  {{socketMsg.createTime}} </span>
    <p style="margin: 0px;margin-top: 4px;">{{socketMsg.msg}}</p>
  </div>
  <!--Showing my messages-->
  <div v-else class="message-me">
    <span style="font-size: 8px;">{{socketMsg.createTime}}   {{name}}</span>
    <p style="margin: 0px;margin-top: 4px;">{{socketMsg.msg}}</p>
  </div>
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
    {{item}}
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
import { getMessages } from "@/api/chat";

  export default {
    name: "privateChat",

    computed: {
      messages: {
        get: function(){
          return this.tempMessages;
        }
      }
    },

    data(){
      return{
        privateChatVisible:false,
        otherUser: {},
        otherAvatar: "",
        otherName: "",
        otherUid: null,
        user: {},
        uid: null,
        name: "",
        avatar: "",

        text: "",
        emojiShow: false,
        faceList: [],
        getBrowString: "",

        tempMessages: [],
        socketMsg: {},
        msg: ""
      }
    },

    created() {
      this.loadEmojis();
    },
    
    methods:{

      scrollChat() {
        var container = document.querySelector('.chat-history')
        if (container.scrollHeight > 400) {
          container.scrollTop = container.scrollHeight
        }
      },

      getHistory(toUid){
        new Promise((resolve, reject) => {
        getMessages(toUid)
          .then((res) => {
            this.tempMessages = res.data.messages
            resolve();
          })
          .catch((err) => {
            reject(err);
            return "Error in loading History";
          });
        });
      },

      init(data){
        this.privateChatVisible=true
        this.otherUser = data
        this.name = this.$store.state.user.name
        this.uid = this.$store.state.user.id

        this.getHistory(this.otherUser.id)
      },

      loadEmojis() {
        for (let i in appData) {
          this.faceList.push(appData[i].char);
        }
      },

      getBrow(index) {
        for (let i in this.faceList) {
          if (index == i) {
            this.getBrowString = this.faceList[index];
            this.text += this.getBrowString;
          }
        }
        this.emojiShow = false;
      },

      submitMessage() {
        this.$parent.text = this.text
        this.$parent.send()
        this.text = ""
      },

      handleClose() {
        this.$parent.toUid = null
      }
    }
  }
</script>

<style scoped>
.chat-history {
  width: 100%;
  height: 400px;
  border-bottom: 1px solid #8a8282;
  overflow: auto;
}
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
}
  ul {
    display: flex;
    flex-wrap: wrap;
    padding: 10px;
  }
    li {
      cursor: pointer;
      width: 10%;
      font-size: 26px;
      list-style: none;
      text-align: center;
    }
.submit-btn {
  margin: 0 15px 10px 0;
  float: right;
}
.message-other{
  width: 98%;
  text-align: left;
}
.message-me{
  width: 98%;
  text-align: right;
}

</style>

