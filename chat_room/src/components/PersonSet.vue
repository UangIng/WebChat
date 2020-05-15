<template>
  <div class="PersonSet" :style="{width:mainWidth+'px'}">
    <header>个人设置</header>
    <el-menu mode="horizontal" @select="handleSelect" :default-active="menuIndex">
      <el-menu-item index="1">处理中心</el-menu-item>
      <el-menu-item index="2">修改头像</el-menu-item>
      <el-menu-item index="3">修改密码</el-menu-item>
    </el-menu>

    <transition>
      <div class="panel" v-if="menuIndex==1">
        <el-form ref="form" :model="user" label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="user.userid" disabled></el-input>
          </el-form-item>

          <el-form-item label="昵称">
            <el-input v-model="user.nickname"></el-input>
          </el-form-item>

          <el-form-item label="性别" style="text-align:left;">
            <el-input v-model="user.sex==1?'男':'女'" disabled></el-input>
            <!-- <el-select v-model="user.sex==1?'男':'女'" placeholder="请选择">
              <el-option label="男" value="1" disabled=""></el-option>
              <el-option label="女" value="0" disabled=""></el-option>
            </el-select>-->
          </el-form-item>

          <el-form-item label="年龄">
            <el-input v-model="user.age"></el-input>
          </el-form-item>

          <el-form-item label="个性签名">
            <el-input type="textarea" v-model="user.profile"></el-input>
          </el-form-item>

          <el-button type="primary" @click="updateUser">提交</el-button>
        </el-form>
      </div>

      <div class="panel" v-if="menuIndex==2">
        <div class="header-img">
          <el-upload
            class="avatar-uploader"
            :action="action"
            :show-file-list="false"
            :multiple="false"
            :auto-upload="false"
            :limit="1"
            :on-change="selectFile"
          >
            <img v-if="image" :src="image" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <div style="margin-top:15px;">
          <el-button type="primary" @click="uploadFile">上传头像</el-button>
        </div>
      </div>

      <div class="panel" v-if="menuIndex==3">
        <el-form ref="form" label-width="80px">
          <el-form-item label="原密码">
            <el-input v-model="old_password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="新密码">
            <el-input v-model="new_password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="再次输入">
            <el-input v-model="sure_password" type="password"></el-input>
          </el-form-item>
          <el-button type="primary" @click="updatePassword">提交</el-button>
        </el-form>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: "PersonSet",
  data() {
    return {
      screenWidth: window.innerWidth,
      mainWidth: 0,
      leftWidth: 250,
      user: {
        userid: "",
        nickname: "",
        sex: "",
        age: "",
        profile: ""
      },
      menuIndex: "3",
      action: this.$axios.defaults.baseURL + "user/upload/",
      imageUrl: this.$axios.defaults.baseURL + "user/rawImgByUUID",
      image: "",
      file: "",

      old_password: "",
      new_password: "",
      sure_password: ""
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
        console.log(res.data.data);
        this.user = res.data.data;
        this.image = this.imageUrl + "?fullName=" + this.user.profilehead;
      });
    },
    updateUser() {
      this.$axios.post("/user/update", { user: this.user }).then(res => {
        this.$message({
          message: "提交成功",
          type: "success"
        });
      });
    },
    updatePassword() {
      if (!this.old_password || !this.new_password || !this.sure_password) {
        this.$messgae({
          message: "密码不能为空",
          type: "warning"
        });
      }
      if (this.new_password != this.sure_password) {
        this.$message({
          message: "两次输入密码不一致",
          type: "waening"
        });
      }

      let params = {
        oldPassword: this.old_password,
        newPassword: this.new_password,
        surePassword: this.sure_password
      };
      this.$axios.post("/user/updatePassword", params).then(res => {
        if (res) {
          this.$message({
            message: "修改成功",
            type: "success"
          });
          this.old_password = "";
          this.new_password = "";
          this.sure_password = "";
        }
      });
    },
    handleSelect(key) {
      this.menuIndex = key;
    },
    selectFile(file, filelist) {
      console.log(file);
      this.image = URL.createObjectURL(file.raw);
      this.file = file.raw;
    },
    uploadFile(e) {
      let formData = new FormData();
      formData.append("uploadFile", this.file);
      this.$axios
        .post("/user/upload", formData, {
          "Content-Type": "multipart/form-data"
        })
        .then(res => {
          this.$message({
            message: "上传成功",
            type: "success"
          });
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
.PersonSet {
  position: relative;
}

.PersonSet header {
  text-align: left;
  padding: 1.6rem;
  box-sizing: border-box;
  color: #0e90d2;
  font-weight: bolder;
  font-size: 1.6rem;
  line-height: 1.6rem;
}
.PersonSet .panel {
  text-align: center;
  border: 1px solid #ddd;
  padding: 1.6rem;
  margin: 15px;
  width: auto;
  position: relative;
}

.PersonSet .panel-leave-active,
.PersonSet .panel-enter-active {
  transition: opacity 0.5s;
}

.PersonSet .panel .header-img {
  border: 1px solid #d9d9d9;
  margin: 0 auto;
  text-align: center;
  width: 178px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>