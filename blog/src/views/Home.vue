<template>
  <div id="home">
    <el-menu
            router
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
      <el-menu-item index="show-blog">博客总览</el-menu-item>
      <el-menu-item index="add-blog">新增博客</el-menu-item>
    </el-menu>
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    data(){
      return {
          activeIndex: 'show-blog'
      }
    },
    methods: {
      handleSelect(key) {
        // this.activeIndex = key
      }
    },
    created(){
      if(this.$route.name == 'add-blog') {
        this.activeIndex = 'add-blog'
      }

    }
  }
</script>

<style scoped>
  #home{
    max-width: 800px;
    margin: 10px auto;
  }
</style>