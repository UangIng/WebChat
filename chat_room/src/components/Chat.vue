<template>
  <div class="chat">
    <el-row type="flex">
      <el-col class="chat_col" :style="{width:mainWidth*0.8+'px'}">
        <div class="chat_area">
          <ul>
            <li v-for="(record,index) in chatRecord" v-bind:key="index">
              <p class="notice" v-if="record.type=='NOTICE'">
                <i class="el-icon-warning-outline" />
                [admin]加入聊天室,当前在线人数为1位
              </p>
              <div class="message" v-if="record.type=='MESSAGE' && record.from!=userid">
                <!-- <div class="message" v-if="record.type=='MESSAGE' "> -->
                <div class="img">
                  <img ：:src="imageUrl+'?userid='+record.from" />
                </div>
                <div class="content">
                  <header>
                    {{record.from}}
                    {{record.time}}
                    <span class="left_arrow"></span>
                  </header>
                  <div>
                    <p>{{record.message}}</p>
                  </div>
                </div>
              </div>

              <div class="message" v-if="record.type=='MESSAGE' && record.from==userid">
                <div class="content">
                  <header>
                    {{record.from}}
                    {{record.time}}
                    <span class="right_arrow"></span>
                  </header>
                  <div>
                    <p>{{record.message}}</p>
                  </div>
                </div>
                <div class="img right">
                  <img :src="imageUrl +'?userid='+ record.from" />
                </div>
              </div>
            </li>
          </ul>
        </div>
        <div class="chat_input">
          <textarea rows="5" placeholder="这里输入你想发送的信息..." v-model="message"></textarea>
        </div>
        <div class="buttons">
          <button @click="openConnection">
            <i class="el-icon-link" />
            <span>连接</span>
          </button>

          <button @click="closeConnection">
            <i class="el-icon-close" />
            <span>断开</span>
          </button>

          <button @click="checkConnention">
            <i class="el-icon-connection" />
            <span>检查</span>
          </button>
          <button @click="clearArea">
            <i class="el-icon-brush" />
            <span>清屏</span>
          </button>
          <button @click="send">
            <i class="el-icon-chat-dot-round" />
            <span>发送</span>
          </button>
        </div>
      </el-col>
      <el-col :style="{width:mainWidth*0.2+'px'}">
        <div class="online_list_title">
          <h3>在线列表[{{userList.length}}]</h3>
        </div>
        <ul class="online_list">
          <li v-for="(user,index) in userList" v-bind:key="index">{{user}}</li>
        </ul>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// import { createSocket, sendWSPush } from "@/utils/ws";

export default {
  name: "Chat",
  components: {},
  data() {
    return {
      screenWidth: window.innerWidth,
      mainWidth: 0,
      leftWidth: 250,
      timer: false,
      userid: sessionStorage.getItem("userid"),
      message: "",
      Socket: null,
      chatRecord: [],
      userList: [],
      imageUrl: this.$axios.defaults.baseURL + "user/getImgByUser"
    };
  },
  created() {
    this.mainWidth = window.innerWidth - this.leftWidth;
    this.openConnection();
  },
  mounted() {
    let that = this;
    window.onresize = () => {
      return (() => {
        window.screenWidth = window.innerWidth;
        that.screenWidth = window.screenWidth;
      })();
    };
  },
  methods: {
    send() {
      if (!this.message) {
        this.$message({
          message: "你不必惜字如金",
          type: "warning"
        });
        return;
      }
      let msg = {
        content: this.message,
        from: this.userid,
        to: null
      };
      this.Socket.send(JSON.stringify(msg));
      this.message = "";
    },
    openConnection() {
      if (this.Socket == null) {
        this.Socket = new WebSocket(this.$wsServer);
        this.Socket.onopen = e => {
          this.$message({
            message: "成功建立连接",
            type: "success"
          });
        };
        this.Socket.onmessage = e => {
          let data = JSON.parse(e.data);
          console.log(data);
          this.chatRecord.push(data);
          if (data.data != null) {
            this.userList = data.data;
          }
        };
        this.Socket.onclose = e => {
          this.$message("连接已关闭");
        };
        this.Socket.onerror = e => {
          this.$message.error("服务器繁忙");
          this.$router.push({
            path: "/Login"
          });
        };
      } else {
        this.$message({
          message: "已连接",
          type: "success"
        });
      }
    },
    closeConnection() {
      if (this.Socket != null) {
        this.Socket.close();
        this.Socket = null;
      } else {
        this.$message({
          message: "连接未开启",
          type: "warning"
        });
      }
    },
    checkConnention() {
      console.log(this.Socket);
      if (this.Socket && this.Socket.readyState == 1) {
        this.$message({
          message: "连接正常",
          type: "success"
        });
      } else {
        this.$message.error("连接异常");
      }
    },
    clearArea() {
      this.chatRecord = [];
    }
  },
  watch: {
    screenWidth(val) {
      if (!this.timer) {
        this.mainWidth = val - this.leftWidth;
        this.timer = true;
        setTimeout(() => {
          this.timer = false;
        }, 100);
      }
    }
  },
  destroyed() {
    this.closeConnection();
  }
};
</script>

<style scoped>
.chat {
  height: 510px;
  position: relative;
}
.chat .chat_col {
  overflow: hidden;
  min-height: 100%;
}
.chat .chat_col .chat_area {
  height: 510px;
  overflow-y: scroll;
  resize: vertical;
}
.chat .chat_col .chat_area ul {
  list-style: none;
}
.chat .chat_col .chat_area ul li {
  display: block;
  margin-block-end: 1rem;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  position: relative;
}
.chat .chat_col .chat_area ul li .notice {
  color: #5eb95e;
}

.chat .chat_col .chat_area ul li .message {
  position: relative;
}

.chat .chat_col .chat_area ul li .img {
  position: absolute;
}
.chat .chat_col .chat_area ul li .right {
  right: 0;
  top: 0;
}

.chat .chat_col .chat_area ul li .content {
  border: 1px solid #0e90d2;
  border-radius: 0;
  position: relative;
  margin: 0 64px 1rem 64px;
}
.chat .chat_col .chat_area ul li .content header {
  display: block;
  text-align: left;
  padding: 10px 15px;
  position: relative;
  color: #999;
  background-color: #f8f8f8;
  border-left: 1px solid #0e90d2;
  word-wrap: break-word;
  word-break: normal;
}

.chat .chat_col .chat_area ul li .message .left_arrow {
  position: absolute;
  width: 0;
  height: 0;
  border: 8px solid;
  border-color: transparent #0e90d2 transparent transparent;
  top: 4px;
  left: -17px;
}

.chat .chat_col .chat_area ul li .message .right_arrow {
  position: absolute;
  width: 0;
  height: 0;
  border: 8px solid;
  border-color: transparent transparent transparent #0e90d2;
  top: 4px;
  right: -17px;
}
.chat .chat_col .chat_area ul li .content p {
  display: block;
  padding: 15px;
  text-align: left;
  word-wrap: break-word;
  word-break: normal;
}
.chat .chat_col .chat_area ul li div img {
  border: 1px solid #dedede;
  width: 48px;
  height: 48px;
}

.chat .chat_col .chat_input {
  position: relative;
}
.chat .chat_input textarea {
  box-sizing: border-box;
  resize: vertical;
  width: 100%;
  height: 120px;
  display: block;
  font-size: 1.6rem;
  padding: 0.5rem;
  line-height: 1.2;
  color: #555;
  border: 1px solid #ccc;
  background-color: #fff;
  border-radius: 0;
  text-rendering: optimizeLegibility;
  -webkit-font-smoothing: antialiased;
}

.chat .chat_col .buttons {
  float: right;
  margin-top: 1rem;
}
.chat .chat_col .buttons button {
  float: right;
  border: 1px solid #c1c1c1;
  background-color: #e6e6e6;
  padding: 0.5rem 1rem;
  cursor: pointer;
  outline: none;
}
.chat .chat_col .buttons button:hover {
  background-color: #d4d4d4;
}
.chat .chat_col .buttons button:focus {
  outline: 0;
}
.online_list_title {
  box-sizing: border-box;
  line-height: 1rem;
  font-size: 1rem;
  font-weight: 400;
  color: #444;
  background-color: #f5f5f5;
  border-color: #ddd;
  text-align: left;
  padding: 0.6rem 1.25rem;
  border-bottom: 2px solid #dddddd;
}
.online_list_title ul {
  list-style: none;
}
.online_list li {
  background-color: #fff;
  padding: 0.8rem 0.2rem;
  display: block;
  border: 1px solid #dedede;
  border-width: 1px 0;
  box-sizing: border-box;
}
</style>