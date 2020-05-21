<template>
  <div class="all">
    <transition name="window">
      <div class="window" v-if="show">
        <div class="top">
          <img src="../assets/icon/close.png" alt="图片加载失败" v-on:click="show =!show" />
        </div>
        <div class="main">
          <div class="head">
            <img src="../assets/icon/head.png" />
          </div>

          <div class="form_item">
            <input
              type="text"
              placeholder="请输入账号"
              :style="user_class"
              name="userid"
              v-model="userid"
            />
          </div>
          <div class="form_item">
            <input
              type="password"
              placeholder="请输入密码"
              :style="password_class"
              name="password"
              v-model="password"
            />
          </div>
          <div class="signin">
            <input type="submit" id="submit" value="Login" @click="login" />
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: "Login",
  components: {},
  data() {
    return {
      show: true,
      user_class: {
        backgroundImage: "url(" + require("../assets/icon/user.png") + ")",
        backgroundSize: "1em 2rem",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "1rem 1rem"
      },
      password_class: {
        backgroundImage: "url(" + require("../assets/icon/password.png") + ")",
        backgroundSize: "1em 2rem",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "1rem 1rem"
      },
      userid: "admin",
      password: "admin"
    };
  },
  methods: {
    login(e) {
      let params = {
        userid: this.userid,
        password: this.password
      };
      // 密码在实际项目中请使用加密传输
      this.$axios.post("/user/login", params).then(res => {
        sessionStorage.setItem("userid", res.data.data.userid);
        console.log(sessionStorage.getItem("userid"));
        this.$router.push({
          path: "/Room"
        });
      });
    }
  }
};
</script>

<style scoped>
/* body start */
.all {
  width: 100%;
  height: 100%;
  position: absolute;
  background-image: linear-gradient(#e98259, #8fb0af);
}
.window {
  background: #2b2b36;
  position: relative;
  width: 30%;
  margin: 10% auto 0 auto;
  text-align: center;
  overflow: hidden;
  border-radius: 15px;
  -webkit-border-radius: 15px;
  -moz-border-radius: 15px;
  -o-border-radius: 15px;
}
.window-leave-active,
.window-enter-active {
  transition: opacity 0.5s;
}

.window-enter,
.window-leave-to {
  opacity: 0;
}
.window .top {
  height: 80px;
  widows: 100%;
  background-color: #23232e;
  display: flex;
  align-items: center;
}
.window .top img {
  width: 20px;
  height: 20px;
  margin-left: 15px;
  cursor: pointer;
}

.window .main {
  background-color: #2b2b36;
}
.window .main .head {
  padding-top: 20px;
}

.window .main input[type="text"] {
  width: 70%;
  padding: 1rem 2rem 1rem 3rem;
  color: #9199aa;
  font-size: 18px;
  outline: none;
  border: none;
  font-weight: 100;
  border-bottom: 1px solid#484856;
  margin-top: 2em;
  background-color: #2b2b36;
  line-height: 2rem;
}

.window .main input[type="password"] {
  width: 70%;
  padding: 1rem 2rem 1rem 3rem;
  color: #9199aa;
  font-size: 18px;
  outline: none;
  border: none;
  font-weight: 100;
  border-bottom: 1px solid#484856;
  margin-top: 2em;
  background-color: #2b2b36;
  line-height: 2rem;
}

.window .main input[type="submit"] {
  font-size: 30px;
  color: #fff;
  outline: none;
  border: none;
  background: #3ea751;
  width: 100%;
  padding: 18px 0;
  border-bottom-left-radius: 15px;
  -webkit-border-bottom-left-radius: 15px;
  -moz-border-bottom-left-radius: 15px;
  -o-border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
  -webkit-border-bottom-right-radius: 15px;
  -moz-border-bottom-right-radius: 15px;
  -o-border-bottom-right-radius: 15px;
  cursor: pointer;
}
.window .main input[type="submit"]:hover {
  background: #ff2775;
  border-bottom-left-radius: 15px;
  -webkit-border-bottom-left-radius: 15px;
  -moz-border-bottom-left-radius: 15px;
  -o-border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
  -webkit-border-bottom-right-radius: 15px;
  -moz-border-bottom-right-radius: 15px;
  -o-border-bottom-right-radius: 15px;
  transition: 1s all;
  -webkit-transition: 1s all;
  -moz-transition: 1s all;
  -o-transition: 1s all;
}
/* body end */

/*--start-responsive-design--*/
@media (max-width: 1440px) {
  body {
    min-height: 811px;
  }
}
@media (max-width: 1366px) {
  body {
    min-height: 768px;
  }
}
@media (max-width: 1280px) {
  body {
    min-height: 711px;
  }
}
@media (max-width: 1024px) {
  .window {
    width: 37%;
  }
  h1 {
    padding-top: 2em;
  }
  body {
    min-height: 675px;
  }
}
@media (max-width: 768px) {
  .window {
    width: 50%;
    margin: 12% auto 0 auto;
  }
  body {
    min-height: 929px;
  }
}
@media (max-width: 640px) {
  .window {
    width: 60%;
    margin: 20% auto 0 auto;
  }
}
@media (max-width: 480px) {
  .window {
    width: 80%;
  }
}
@media (max-width: 320px) {
  input[type="text"] {
    width: 62%;
    padding: 1.2em 2em 0.5em 2.5em;
    font-size: 17px;
    margin-top: 0.5em;
  }
  input[type="password"] {
    width: 62%;
    padding: 1.2em 2em 0.5em 2.5em;
    font-size: 17px;
    margin-top: 0.5em;
    margin-bottom: 2em;
  }
  .close {
    left: 16px;
    top: 13px;
  }
  body {
    min-height: 504px;
  }
  input[type="submit"] {
    font-size: 28px;
    padding: 10px 0;
  }
}
/*--end-responsive-design--*/
</style>
