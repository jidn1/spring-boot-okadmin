import axios from 'axios'
import qs from 'qs'
import Vue from 'vue'
import cryptoJS from "@/utils/crypto";
import router from '@/router/index'
// import { Loading } from 'element-ui';

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
//默认请求头  并 指向原型
//
axios.defaults.baseURL = process.env.BASE_API;
axios.defaults.withCredentials = true; // 让ajax携带cookie
Vue.prototype.path = process.env.BASE_API;

// let loading = null;
let loadingOpenCount = 0;
//获取cookie
function getCookie(c_name) {
  if (document.cookie.length>0)
  {
    var c_start=document.cookie.indexOf(c_name + "=");
    if (c_start!=-1)
    {
      c_start=c_start + c_name.length+1
      var c_end=document.cookie.indexOf(";",c_start);
      if (c_end==-1) c_end=document.cookie.length;
      return unescape(document.cookie.substring(c_start,c_end))
    }
  }
  return ""
}
//设置coolie
function setCookie(c_name,value,day){
  var exdate = new Date();
  exdate.setTime(exdate.getTime() + day*24*60*60*1000);
  document.cookie= c_name + "=" + value +((day==null) ? "" : ";expires="+exdate.toGMTString());
}
//删除cookie
function delCookie(c_name){
  var exp = new Date();
  exp.setTime(exp.getTime() - 1);
  var cval=getCookie(c_name);
  if(cval!=null) {
    document.cookie= c_name + "="+cval+";" +
      "expires="+exp.toGMTString();
  }
}
var token = getCookie('token');
axios.interceptors.request.use(function (config) {

  if (sessionStorage.getItem('token')) {
    config.headers.common['token'] = sessionStorage.getItem('token');
  }
  else{
    if(token != ''){
      config.headers.common['token'] = token
    }
  }
  if (config.method === 'post') {
    config.data = qs.stringify(config.data);
  }
  if (config.method === 'get') {
    config.url = config.url + "?" + qs.stringify(config.data);
  }
  if (config.method === 'upload') {
    config.method = 'post';
  }
  return config
},
  function (error) {
  return Promise.reject(error)
});

axios.interceptors.response.use(
  response => {
    var list = [];
    list = JSON.parse(cryptoJS.decrypt(response.data.obj));
    response.data.data = list;
    return response;
  },
  error => {
    if(error.response == undefined){
      return {data: {success: false, msg: '请求异常'}};
    }else{
      return checkStatus(error.response);
    }

    if (error.response && error.response.data) {
      return Promise.reject(error.response.data)
    }
  });

// 检查状态
function checkStatus(response) {
  let data = {data: {}};
  data.data['success'] = false;
  switch (response.status) {
    case 400:
      data.data['msg'] = '请求错误'
      break

    case 401:
      data.data['msg'] = '未授权，请登录'
      break

    case 403:
      data.data['msg'] = '拒绝访问'
      break

    case 404:
      data.data['msg'] = '请求地址出错'
      break

    case 408:
      data.data['msg'] = '请求超时'
      break

    case 500:
      data.data['msg'] = '服务器内部错误'
      break

    case 501:
      data.data['msg'] = '服务未实现'
      break

    case 502:
      data.data['msg'] = '网关错误'
      break

    case 503:
      data.data['msg'] = '服务不可用'
      break

    case 504:
      data.data['msg'] = '网关超时'
      break

    case 505:
      data.data['msg'] = 'HTTP版本不受支持'
      break

    default:
      data.data['msg'] = '异常请求';
  }
  return data;
}
