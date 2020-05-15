<template>
  <div class="header">
    <div class="admin_dropdowm_toggle">
      <div class="admin_name" v-on:click="show_drop_down=true">
        admin
        <span class="admin-icon-caret-down"></span>
      </div>

      <transition class="admin-dropdown-content">
        <ul class="admin-dropdown-content" v-show="show_drop_down">
          <li>
            <img />
            资料
          </li>
          <li>
            <img />
            设置
          </li>
          <li>
            <img />
            注销
          </li>
        </ul>
      </transition>
    </div>
  </div>
</template>


<script>
export default {
  name: "HeaderNav",
  data() {
    return {
      show_drop_down: false
    };
  },
  created() {},
  mounted() {
    document.addEventListener("click", e => {
      if (
        e.target.className != "admin-dropdown-content" &&
        e.target.className != "admin_name"
      ) {
        this.show_drop_down = false;
      }
    });
  }
};
</script>


<style scoped>
.header {
  width: 100%;
  height: 50px;
  line-height: 50px;
  background-color: #f8f8f8;
  font-size: 14px;
  border-top: 1px solid #dddddd;
  border-bottom: 2px solid #dddddd;
}
.header .admin_dropdowm_toggle {
  width: 70px;
  margin-right: 10px;
  padding: 0 10px;
  color: #666;
  text-align: center;
  float: right;
  line-height: 50px;
  position: relative;
}
.header .admin_dropdowm_toggle:hover {
  background-color: #eeeeee;
}
.header .admin_dropdowm_toggle .admin_name {
  cursor: pointer;
}

.header .admin_dropdowm_toggle .admin-icon-caret-down {
  display: inline-block;
  font-style: normal;
  width: 0;
  height: 0;
  border-width: 4px;
  border-style: solid;
  border-color: #666666 transparent transparent transparent;
}
.header .admin_dropdowm_toggle .admin-dropdown-content {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1000;
  list-style: none;
  margin: 9px 0 0;
  padding: 5px 0;
  text-align: left;
  line-height: 1.6;
  float: left;
  background-color: #fff;
  border: 1px solid #ddd;
  color: #333333;
}

.header .admin_dropdowm_toggle .admin-dropdown-content-leave-active,
.header .admin_dropdowm_toggle .admin-dropdown-content-enter-active {
  transition: opacity 0.5s;
}

.header .admin_dropdowm_toggle .admin-dropdown-content li {
  cursor: pointer;
  padding: 6px 20px;
}

.header .admin_dropdowm_toggle .admin-dropdown-content li:hover {
  background-color: #eeeeee;
}
</style>