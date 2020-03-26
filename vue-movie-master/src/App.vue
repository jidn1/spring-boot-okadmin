<template>
  <div id="app">
    <router-view name="header"/>
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    <router-view name="footer"></router-view>
  </div>
</template>

<script>
  export default {
    name: 'App',
    data() {
      return {}
    },
    mounted() {

      //判断手机端
      var PC = this.IsPC();

    },
    methods: {
      IsPC() {
        var userAgentInfo = navigator.userAgent;
        // "iPad"   "iPod"
        var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone",];

        var flag = true;

        for (var v = 0; v < Agents.length; v++) {
          if (userAgentInfo.indexOf(Agents[v]) > 0) {
            flag = false;

            break;
          }
        }
        return flag;
      }


    }
  }
</script>

<style>
  a {
    color: #888;
  }

  .canvas {
    display: flex;
    justify-content: center;
  }

  .spinner {
    animation: spinner 1s linear infinite;
    border: solid 1.5em #4DB6AC;
    border-right: solid 1.5em transparent;
    border-left: solid 1.5em transparent;
    border-radius: 100%;
    width: 0;
    height: 0;
  }

  @keyframes spinner {
    0% {
      transform: rotate(0deg);
    }
    50% {
      transform: rotate(60deg)
    }
    100% {
      transform: rotate(360deg);
    }
  }

  .container {
    margin-bottom: 15px;
  }
</style>
