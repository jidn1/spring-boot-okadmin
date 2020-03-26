// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import VueVideoPlayer from 'vue-video-player'
import axios from 'axios'
import App from './App'
import router from './router'
import 'bootstrap/dist/css/bootstrap.css'
import '@/utils/axios';
import Video from 'video.js'
import 'video.js/dist/video-js.css'
import cryptoJS from "@/utils/crypto";

// 请求绑定Vue原型属性上面，方便使用
Vue.prototype.$ajax=axios;
Vue.prototype.$video = Video
Vue.prototype.cryptoJS = cryptoJS;
Vue.use(ElementUI);
Vue.use(VueVideoPlayer)
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
