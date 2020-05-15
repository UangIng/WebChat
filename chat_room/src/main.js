import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import qs from 'qs';
import axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css';

import { Pagination, Button, Menu, Submenu, MenuItem, MenuItemGroup, Row, Col, Message, Form, FormItem, Input, Select, Option, Upload } from 'element-ui';

axios.defaults.withCredentials = true;
// axios.defaults.timeout = 5000 // 请求超时
// axios.defaults.baseURL = '/api/'  // api 即上面 vue.config.js 中配置的地址
axios.defaults.baseURL = 'http://localhost:9009/api/'

Vue.prototype.$axios = axios;
Vue.prototype.$qs = qs;
Vue.prototype.$wsServer = "ws://localhost:9009/ws"

Vue.config.productionTip = false

Vue.use(Row)
Vue.use(Col)
Vue.use(Menu)
Vue.use(Upload)
Vue.use(Button)
Vue.use(Submenu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Pagination)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Select)
Vue.use(Option)

Vue.prototype.$message = Message;


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')


//请求发送前拦截
axios.interceptors.request.use((request) => {

  if (request["Content-Type"] == "multipart/form-data") {
    return request
  } else {
    let REQUEST_DATA = request.data
    //统一进行qs模块转换
    request.data = qs.stringify(REQUEST_DATA)
    //再发送给服务器
    return request;
  }


}, function (error) {
  return Promise.reject(error);
});

//请求返回后拦截
axios.interceptors.response.use((response) => {
  //处理异常，-1服务器未知异常，可以添加其他异常状态
  console.log(response)
  if (response.data.code === -1) {
    // 待完善
    if (response.data.message != null) {
      Message({
        message: response.data.message,
        type: 'error'
      })
    } else {
      Message({
        message: '服务器繁忙',
        type: 'error'
      })
      router.push({
        path: "/Login"
      })
    }
    // 正常返回
  } else if (response.data.code === 0) {
    return response;
  }
}, function (error) {
  return Promise.reject(error);
});