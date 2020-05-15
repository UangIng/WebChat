<template>
  <div class="all">
    <!-- 头部 -->
    <HeaderNav></HeaderNav>
    <!-- 主要内容 -->
    <el-row class="main" type="flex" justify="space-between">
      <!-- 左边导航 -->
      <el-col class="left_col">
        <LeftNav @handleSelect="handleSelect" @menukey="menuIndex"></LeftNav>
      </el-col>
      <!-- 右边具体内容，不同menu，切换不同component -->
      <el-col>
        <Chat v-if="menuIndex==1"></Chat>
        <Person v-if="menuIndex==2"></Person>
        <PersonSet v-if="menuIndex==3"></PersonSet>
        <Logs v-if="menuIndex==5"></Logs>
        <About v-if="menuIndex==7"></About>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import LeftNav from "@/components/LeftNav.vue";
import Chat from "@/components/Chat.vue";
import Person from "@/components/Person.vue";
import PersonSet from "@/components/PersonSet.vue";
import Logs from "@/components/Logs.vue";
import About from "@/components/About.vue";

export default {
  name: "Room",
  components: {
    HeaderNav,
    LeftNav,
    Chat,
    Person,
    PersonSet,
    Logs,
    About
  },
  data() {
    return {
      menuIndex: 7
    };
  },
  methods: {
    handleSelect(e) {
      console.log(e);
      this.menuIndex = e.key;
    }
  }
};
</script>

<style scoped>
.all {
  position: fixed;
  min-height: 100%;
  min-width: 100%;
}
.main {
  min-height: 100%;
  position: absolute;
}
.main .left_col {
  min-width: 250px;
  min-height: 100%;
  background-color: #f3f3f3;
}
</style>