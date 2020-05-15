<template>
  <div class="About" :style="{width:mainWidth+'px'}">
    <header>关于</header>
    <el-menu mode="horizontal" @select="handleSelect" :default-active="menuIndex">
      <el-menu-item index="1">所用技术</el-menu-item>
      <el-menu-item index="2">获取源码</el-menu-item>
      <el-menu-item index="3">关于作者</el-menu-item>
    </el-menu>

      <div class="panel" v-if="menuIndex==1">
        <p>该项目所使用的框架主要包括：springboot+mybatis-plus</p>
        <p>通讯使用的是websocket</p>
        <p>数据库使用的mysql8.0</p>
        <p>前端采用vue4.0 + webpack + UI框架element-ui</p>
        <p>项目中还有许多地方可以进行优化的，比如：用户登陆管理上我们可以用上shiro/security的安全框架、密码安全校验、websocket指定发送等等。</p>
        <p>作者提供一个设计思想、大致实现方式供各位同学学习参考，发现不对的地方欢迎指正</p>
      </div>

      <div class="panel" v-if="menuIndex==2">
        <p><a href="https://github.com/UangIng/WebChat.git">https://github.com/UangIng/WebChat.git</a></p>
      </div>

      <div class="panel" v-if="menuIndex==3">
        <p>欢迎访问我的个人博客：<a href="https://blog.csdn.net/UangIng">https://blog.csdn.net/UangIng</a></p>
        <p>虽然没什么好看的</p>
      </div>

  </div>
</template>


<script>
export default {
  name: "About",
  data() {
    return {
      screenWidth: window.innerWidth,
      mainWidth: 0,
      leftWidth: 250,
      menuIndex: "1"
    };
  },
  created() {
    this.mainWidth = window.innerWidth - this.leftWidth;
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
    handleSelect(key) {
      this.menuIndex = key;
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
  }
};
</script>

<style scoped>
.About {
  position: relative;
}

.About header {
  text-align: left;
  padding: 1.6rem;
  box-sizing: border-box;
  color: #0e90d2;
  font-weight: bolder;
  font-size: 1.6rem;
  line-height: 1.6rem;
}

.About .panel {
  text-align: center;
  border: 1px solid #ddd;
  padding: 1.6rem;
  margin: 15px;
  width: auto;
  position: relative;
}
.About .panel p {
  text-align: left;
  padding: 15px 0;
}
</style>