<template>
  <div>
      <br>
      <button @click="connectWebSocket()">连接到websocket</button>
      <br>
      频道号：<input type="text" v-model="toUser" />
      消息：<input type="text" v-model="text">
      <button @click="send()">发送消息</button>
      <br>
      <button @click="closeWebSocket()">关闭websocket连接</button>
      <br>
      <div id="message">{{data}}</div>
    </div>
</template>

<script>
import { getDate } from '@/utils/date';

export default {
  data() {
      return {
          text: "",
          data: "",
          websocket: null,

          socketMsg: {},
          toUser: "",
          date: "",
          type: 0,
      }
  },

  beforeDestroy() {
      this.onbeforeunload()
  },

  methods: {
      connectWebSocket() {
          if ('WebSocket' in window) {
            var token = this.$store.state.user.token
            this.websocket = new WebSocket('ws://localhost:8088/ws', [token])
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
    this.setMessageInnerHTML("WebSocket连接发生错误" + '   状态码：' + this.websocket.readyState)
  },
  setOnopenMessage() {
    this.setMessageInnerHTML("WebSocket连接成功" + '   状态码：' + this.websocket.readyState)
  },
  setOnmessageMessage(event) {
    this.setMessageInnerHTML(event.data)
  },
  setOncloseMessage() {
    this.setMessageInnerHTML("WebSocket连接关闭" + '   状态码：' + this.websocket.readyState)
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
    var toUser = this.toUser
    var socketMsg = {msg: message, toUser: toUser, createTime: this.date}
    if (toUser === '') {
      // group msg
      socketMsg.type = 0
    } else {
      // private msg
      socketMsg.type = 1
    }
    this.websocket.send(JSON.stringify(socketMsg))
    this.text = ''
  },
  closeWebSocket() {
    this.websocket.close()
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
</style>