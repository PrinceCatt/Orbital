<template>
  <div>
    Online Users:
    <br>
    <br>
    <div class="online-user-list" v-for="onlineUser in onlineUsers" :key="onlineUser.id"> 
        <el-avatar
        class="header-img"
        @click.native="handlePrivateChat(onlineUser)"
        :size="40"
        :src="onlineUser.avatar">
        </el-avatar>
    <div class="author-info">
      <span class="author-name">{{ onlineUser.name }}</span>
    </div>
      </div>
      <br>
      <private-chat v-if="Visible" ref="private"></private-chat>
      <br>
      Message: <el-input v-model="text" 
      type="textarea"
      :rows="2"
      maxlength="1000" show-word-limit>z
        </el-input>
      <button @click="send()">Send</button>
      <br>
      <br>
      <div id="message">{{data}}</div>
    </div>
</template>

<script>
import { getDate } from '@/utils/date';
import privateChat from './private.vue';

export default {
  components: { privateChat },

  data() {
      return {
          text: "",
          data: "",
          websocket: null,
          uid: null,

          socketMsg: {},
          toUid: null,
          date: "",
          type: null,

          onlineUsers: [],
          message: {},

          Visible: false,
      }
  },

  created() {
    this.connectWebSocket()
    this.$store.dispatch('user/getInfo', this.$store.state.user.token).then(() => {
      this.uid = this.$store.state.user.id
    }).catch((err) => {
      console.log(err)
    })
  },

  beforeDestroy() {
      this.onbeforeunload()
  },

  methods: {
    setOnlineUsers(onlineUsers) {
      this.onlineUsers = onlineUsers
    },

    handlePrivateChat(onlineUser) {
      if (onlineUser.id != this.uid) {
        onlineUser.hideBadge = true
        this.Visible = true
        this.toUid = onlineUser.id
        this.$nextTick(() => {
          this.$refs.private.init(onlineUser)
        })
      } else {
        this.$message({
          type:'warning',
          message:"You cannot text yourself."
        })
      }
    },

    handlePrivateMessage(message) {
      if (this.toUid == message.fromUid || this.toUid == message.toUid) {
        this.$refs.private.tempMessages.push(message)
      } else {
        this.$message({
          type: 'info',
          message: "You have received a new message from " + message.fromUser
        })
      }
    },

    connectWebSocket() {
        if ('WebSocket' in window) {
          var token = this.$store.state.user.token
          this.websocket = new WebSocket('ws://114.55.89.49:8088/ws', [token])
          this.initWebSocket()
        } else { alert('Current browser does not support websocket') }
    },
      
  initWebSocket() {
    //连接错误
    this.websocket.onerror = this.setErrorMessage

    // //连接成功
    this.websocket.onopen = this.setOnopenMessage

    //收到消息的回调
    this.websocket.onmessage = this.setOnmessageMessage

    //连接关闭的回调
    this.websocket.onclose = this.setOncloseMessage

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = this.onbeforeunload
  },
  setErrorMessage() {
    this.setMessageInnerHTML("An error occurred during WebSocket connection")
  },
  setOnopenMessage() {
    this.setMessageInnerHTML("Chat server connection success")
  },
  setOnmessageMessage(event) {
    var json = JSON.parse(event.data)
    if (json.type == 0) {
      this.setMessageInnerHTML(json.msg)
    } else if (json.type == 1) {
      this.handlePrivateMessage(json)
    } else {
      this.setOnlineUsers(json.onlineUsers)
    }
  },
  setOncloseMessage() {
    console.log("WebSocket connection closed")
  },
  onbeforeunload() {
    this.closeWebSocket();
  },

  //websocket reflect msg
  setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML += innerHTML + '<br/>';
  },

  //websocket发送消息
  send() {
    this.date = getDate()
    var message = this.text
    var socketMsg = {msg: message, toUid: this.toUid, createTime: this.date}
    if (this.toUid !== null) {
      // private msg
      socketMsg.type = 1
    } else {
      // group msg
      socketMsg.type = 0
    }
    this.websocket.send(JSON.stringify(socketMsg))
    this.text = ''
  },
  closeWebSocket() {
    this.websocket.close()
    this.connected = false
    console.log("WebSocket closed")
  }
}
}
</script>

<style>
#message{
  margin-top:40px;
  border:1px solid gray;
  padding:20px;
}
.el-textarea {
  width: 400px;
}
</style>