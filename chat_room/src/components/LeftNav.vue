<template>
  <div class="left_nav" id="left_nav">
    <el-menu
      :default-active="menukey"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      @select="handleSelect"
    >
      <el-menu-item index="1">
        <i class="el-icon-chat-dot-round"></i>
        <span slot="title">聊天</span>
      </el-menu-item>

      <el-menu-item index="2">
        <i class="el-icon-tickets"></i>
        <span slot="title">个人信息</span>
      </el-menu-item>

      <el-submenu index="9">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>设置</span>
        </template>
        <el-menu-item index="3">
          <i class="el-icon-setting"></i>
          <span slot="title">个人设置</span>
        </el-menu-item>
        <el-menu-item index="4">
          <i class="el-icon-setting"></i>
          <span slot="title">系统设置</span>
        </el-menu-item>
      </el-submenu>

      <el-menu-item index="5">
        <i class="el-icon-receiving"></i>
        <span slot="title">系统日志</span>
      </el-menu-item>

      <el-menu-item index="6">
        <i class="el-icon-help"></i>
        <span slot="title">帮助</span>
      </el-menu-item>

      <el-menu-item index="7">
        <i class="el-icon-wind-power"></i>
        <span slot="title">关于</span>
      </el-menu-item>

      <el-menu-item index="8">
        <i class="el-icon-warning-outline"></i>
        <span slot="title">注销</span>
      </el-menu-item>
    </el-menu>

    <div class="panel">
      <p>
        <i class="el-icon-s-flag"></i>
        <span>Welcome</span>
      </p>
    </div>
  </div>
</template>


<script>
export default {
  name: "LeftNav",
  props: {
    menukey: String
  },
  data() {
    return {};
  },
  methods: {
    handleOpen(key, keyPath) {
    },
    handleClose(key, keyPath) {
    },
    handleSelect(key, keyPath) {
      this.$emit("handleSelect", { key: key, keyPath: keyPath });
    }
  }
};
</script>

<style scoped>
.left_nav {
  min-height: 100%;
}
.el-menu-item,
.el-submenu {
  text-align: left;
  border-bottom: 1px solid #f3f3f3;
}
.panel {
  width: 200px;
  height: 50px;
  padding: 12px;
  text-align: left;
  background-color: #fff;
  margin: 0 auto;
  margin-top: 20px;
  border: 1px solid #dddddd;
}
</style>