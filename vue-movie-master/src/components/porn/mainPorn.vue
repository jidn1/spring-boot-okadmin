<template>
  <div class="wrap">
    <div class="left">
      <div class="leftCard">
        <div class="top"><span>视频分类</span></div>
        <div class="bottom">
          <ul>
            <li v-for="(item,index) in list"  :key="index" @click="searchType(item)">{{item}}</li>
          </ul>
        </div>
      </div>
    </div>
    <div class="right">
      <porn-list
        :videoType="videoType"
      ></porn-list>
    </div>

  </div>
</template>

<script>
  import pornList from './PornList';
  export default {
    components: {
      pornList,
    },
    data () {
      return {
        list: [],
        videoType:'',
      }
    },
    mounted(){
      this.loadType();
    },
    methods: {
      loadType() {
        this.$ajax({
          method: 'post',
          url: '/pornHub/type',
          data: {}
        }).then(res => {
          if (res.data.success) {
            this.list = JSON.parse(res.data.data);
          }
        })
      },
      searchType(type){
        this.videoType = type;
      }
    }
  }
</script>

<style scoped>
  .wrap{
    width:100%;
    display:flex;
  }
  .left{
    width:185px;
    margin-right:30px;
    padding:19px 0;
  }
  .leftCard{
    width:180px;
    margin:0 auto;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
  }
  .leftCard .top{
    padding: 18px 20px;
    border-bottom: 1px solid #ebeef5;
    box-sizing: border-box;
    color: #303133;
    display:flex;
    justify-content: space-between;
  }
  .leftCard .top span:nth-of-type(1){
    font-size:15px;
  }
  .leftCard .top span:nth-of-type(2){
    font-size:12px;
    color: #409eff;
  }
  .right{
    flex:1;
  }
  .leftCard  ul{
    list-style: none;
    padding:0 20px;
  }
  .leftCard  li{
    font-size: 14px;
    height:40px;
    line-height:40px;
    cursor: pointer;
  }
  .leftCard  li:hover{
    font-size: 14px;
    height:40px;
    line-height:40px;
    cursor: pointer;
    background-color:#9D9D9D; /* 改变背景色*/
    color:#ffff00; /* 改变文字颜色*/
  }
</style>
