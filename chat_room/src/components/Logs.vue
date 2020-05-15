<template>
  <div class="Logs" :style="{width:mainWidth+'px'}">
    <header>系统日志</header>

    <div class="panel">
      <table>
        <thead>
          <tr>
            <td>#</td>
            <td>用户名</td>
            <td>时间</td>
            <td>类型</td>
            <td>详情</td>
            <td>IP</td>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(log,index) in logs" v-bind:key="index">
            <td>{{(pageIndex-1)*pageSize + index+1}}</td>
            <td>{{log.userid}}</td>
            <td>{{log.time}}</td>
            <td>{{log.type}}</td>
            <td>{{log.detail}}</td>
            <td>{{log.ip}}</td>
          </tr>
        </tbody>
      </table>
      <el-pagination
        :page-size="5"
        :pager-count="11"
        layout="prev, pager, next"
        :total="total"
        @current-change="pageChange"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "Logs",
  data() {
    return {
      screenWidth: window.innerWidth,
      mainWidth: 0,
      leftWidth: 250,
      pageIndex: 1,
      pageSize: 5,
      total: 0,
      logs: []
    };
  },
  created() {
    this.mainWidth = window.innerWidth - this.leftWidth;
    this.loadLogs();
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
    loadLogs() {
      let params = {
        pageIndex: this.pageIndex,
        pageSize: this.pageSize
      };
      this.$axios.get("/log/logs", { params: params }).then(res => {
        console.log(res.data);
        this.logs = res.data.data.records;
        this.total = res.data.data.total;
      });
    },
    pageChange(e) {
      console.log(e);
      this.pageIndex = e;
      this.loadLogs();
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
.Logs {
  position: relative;
}

.Logs header {
  text-align: left;
  padding: 1.6rem;
  box-sizing: border-box;
  color: #0e90d2;
  font-weight: bolder;
  font-size: 1.6rem;
  line-height: 1.6rem;
}
.Logs .panel {
  text-align: center;
  border: 1px solid #ddd;
  padding: 1.6rem;
  margin: 15px;
  width: auto;
  position: relative;
}
.Logs .panel table {
  text-align: start;
  border-color: grey;
  font-size: medium;
  font-style: normal;
  white-space: normal;
  line-height: normal;
  display: table;
  box-sizing: border-box;
  display: table;
  width: 100%;
  margin-bottom: 1.6rem;
  border-spacing: 0;
  border-collapse: separate;
}
.Logs .panel thead {
  font-weight: bolder;
}
.Logs .panel table tr {
  box-sizing: border-box;
  min-width: 100%;
}

.Logs .panel table tr td {
  box-sizing: border-box;
  padding: 0.7rem;
  line-height: 1.6rem;
  font-size: 1.2rem;
}
.Logs .panel table tbody tr:nth-child(odd) {
  background-color: #f9f9f9;
}

.Logs .panel table tbody tr:nth-child(even) {
  background-color: #fff;
}
</style>