<template>
  <div class="person" :style="{width:mainWidth+'px'}">
    <header>个人信息</header>
    <div class="person_info">
      <div class="person_info_item">
        <b>昵称：</b>
        {{user.nickname}}
      </div>
      <div class="person_info_item" style="float:right;text-align:right;">
        <img :src="image" />
      </div>
      <div class="person_info_item">
        <b>性别：</b>
        {{user.sex==1?'男':'女'}}
      </div>
      <div class="person_info_item">
        <b>年龄：</b>
        {{user.age}}
      </div>
      <div class="person_info_item">
        <b>简介：</b>
        {{user.profile}}
      </div>
      <div class="person_info_item">
        <b>注册时间：</b>
        {{user.firsttime}}
      </div>
      <div class="person_info_item">
        <b>最后登陆：</b>
        {{user.lasttime}}
      </div>
      <div class="clear"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Person",
  data() {
    return {
      screenWidth: window.innerWidth,
      mainWidth: 0,
      leftWidth: 250,
      user: {},
      imageUrl: this.$axios.defaults.baseURL + "user/rawImgByUUID",
      image: ""
    };
  },
  created() {
    this.mainWidth = window.innerWidth - this.leftWidth;
    this.getUser();
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
    getUser() {
      this.$axios.get("/user/info").then(res => {
        console.log(res);
        this.user = res.data.data;
        this.image = this.imageUrl + "?fullName=" + this.user.profilehead;
      });
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
.person {
  position: relative;
}
.person header {
  text-align: left;
  padding: 1.6rem;
  box-sizing: border-box;
  color: #0e90d2;
  font-weight: bolder;
  font-size: 1.6rem;
  line-height: 1.6rem;
}

.person .person_info {
  padding: 15px;
  border: 1px solid #ddd;
  margin: 15px;
  color: #333;
  font-size: 1.2rem;
  text-align: left;
}

.person .person_info .person_info_item {
  width: auto;
}
.person .person_info .person_info_item img {
  width: 140px;
  height: 140px;
}
</style>