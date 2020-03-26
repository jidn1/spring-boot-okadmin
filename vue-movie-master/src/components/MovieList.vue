<template>
  <div class="container">


    <div>
      <div class="canvas" v-show="loading">
        <div class="spinner"></div>
      </div>
      <h2>{{title}}</h2>
      <div class="row">
        <div class="col-md-2 text-center" v-for="(item,index) in list" :key="index" @click="toPlayer(item.movicePlayerUrl)" >
          <img :src="item.movicePictureUrl">
          <div class="title">{{item.moviceName}}</div>
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

  </div>
</template>

<script>
  import '@/assets/elementui/index.css'
  import { Message } from 'element-ui'

  export default {
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
      }
    },
    mounted(){
      this.loadMovieList();
    },
    methods: {
      loadMovieList(){
        this.loading = true;

        this.$ajax({
          method:'post',
          url:'/movie/list',
          data:{
            'limit':this.pageParam.pageSize,
            'page':this.pageParam.pageCurrent,
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
        this.loadMovieList();
      },

      toPlayer(movicePlayerUrl){
        this.$router.push({
          name: "movieplayer",
          query: {
            src:movicePlayerUrl
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


  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }
</style>
