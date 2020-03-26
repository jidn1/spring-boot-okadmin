<template>
    <div class="player playerWrap">
      <iframe class="player" :src="jx+src"></iframe>
    </div>
</template>

<script>
    export default {
        name: "moviePlayer",
      data () {
        return {
          jx:"https://jx.618g.com/?url=",
          src:''
        }
      },
      created() {
        let route = this.$route.query;
        this.src = route.src;
      },
      computed: {

      },
      mounted() {
      },
      methods: {

      },

    }
</script>

<style scoped>
.player{
  height: 600px;
  width: 1300px;
  margin:0 auto;
}
  .playerWrap{
    min-height:800px;
  }
</style>
