<template>
  <div class="player-container">
    <video id="myVideo" class="video-js" >
      <source :src="m3u8" type="application/x-mpegURL" >
    </video>
  </div>
</template>

<script>
    export default {
        name: "Player",
      data () {
        return {
          m3u8:''
        }
      },
      created() {
        let route = this.$route.query;
        this.m3u8 = route.m3u8;
      },
      computed: {

      },
      mounted() {
        this.initVideo();
      },
      methods: {
        initVideo() {
          //初始化视频方法
          let myPlayer = this.$video(myVideo, {
            //确定播放器是否具有用户可以与之交互的控件。没有控件，启动视频播放的唯一方法是使用autoplay属性或通过Player API。
            controls: true,
            //自动播放属性,muted:静音播放
            autoplay: "muted",
            //建议浏览器是否应在<video>加载元素后立即开始下载视频数据。
            preload: "auto",
            //设置视频播放器的显示宽度（以像素为单位）
            width: "970px",
            //设置视频播放器的显示高度（以像素为单位）
            height: "600px"
          });
        }
      },

    }
</script>

<style scoped>
</style>
