<template>
  <div class="container">
    <div class="canvas" v-show="loading">
      <div class="spinner"></div>
    </div>
    <h2>{{title}}</h2>
    <div class="row">
      <div class="col-md-2 text-center" v-for="(item,index) in list" :key="index" @click="toPlayer(item.m3u8)" >
        <!--<img src="../../assets/images/img01.jpg">-->
        <img :src="item.picture">
        <div class="title">{{item.name}}</div>
        <div class="title">{{item.last}}</div>
      </div>
    </div>

    <div class="page">
      <el-pagination background
                     :pageSizes="pageParam.pageSizes"
                     :pageSize="pageParam.pageSize"
                     :currentPage="pageParam.pageCurrent"
                     :total="pageParam.total"
                     @current-change="handleCurrentChange">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import '@/assets/elementui/index.css'
  import { Message } from 'element-ui'

  export default {
    props: {
      videoType: String
    },
    data () {
      return {
        pageParam: {
          pageSizes: [10, 16, 24, 32, 40],
          count: 0,
          total: 10,
          pageCurrent: 1, // 初始化为第一页
          pageSize: 18
        },
        loading: true,
        title: '',
        list: [],
        pornType:'',
      }
    },
    mounted(){
      this.loadVideoList();
    },
    watch: {
      // 监听币种 并 交易方式
      videoType: function(curVal, oldVal) {
        if (curVal != "") {
          this.pornType = curVal;
         this.loadVideoList();
        }
      },
    },
    methods: {
      loadVideoList(){
        this.loading = true;
        this.$ajax({
          method:'post',
          url:'/pornHub/list',
          data:{
            'limit':this.pageParam.pageSize,
            'page':this.pageParam.pageCurrent,
            'type':this.pornType,
          }
        }).then(res=>{
          if(res.data.success){
            this.list = res.data.data;
            this.pageParam.total = res.data.count;
            this.loading = false;
          }
        })

      },
      //分页
      handleCurrentChange(val) {
        this.pageParam.pageCurrent= Number(`${val}`)
        this.loadVideoList();
      },

      toPlayer(m3u8){
        this.$router.push({
          name: "player",
          query: {
            m3u8:m3u8
          }
        });
      }
    }
  }
</script>

<style scoped>
  img {
    width: 100%;
    height: 230px;
    vertical-align: middle;
  }
  .row > div {
    margin-bottom: 20px;
  }
  .title {
    height: 20px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .container{
    width:100%;
  }
</style>
