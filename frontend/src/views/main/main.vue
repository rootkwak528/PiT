<template>
  <div
    class="block"
    style="margin-top: 30px; width: 100vw; margin-left: calc(-50vw + 50%);"
  >
    <el-carousel class="main-carousel" trigger="click" height="550px" interval="5000">
      <el-carousel-item v-for="item in imageURLs" :key="item" height="500px">
        <el-image :src="item" style="width: 100%" />
      </el-carousel-item>
    </el-carousel>
  </div>
  <h3 style="margin-top: 72px">인기 클래스</h3>
  <class-section/>
  <!-- <h3 style="margin-top: 72px">이벤트</h3>
  <class-section/> -->
</template>
<style>
@import "https://unpkg.com/element-plus/lib/theme-chalk/index.css";
@import "./main.css";
@import "../../common/css/common.css";
@import "../../common/css/element-plus.css";
</style>
<script>
import MainSidebar from "./components/main-sidebar";
import ClassSection from "./components/class-section.vue";

export default {
  name: "Main",
  components: {
    MainSidebar,
    ClassSection
  },
  data() {
    return {
      imageURLs: [
        "https://images.unsplash.com/photo-1604431696980-07e518647bec?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1055&q=80",
        "https://images.unsplash.com/photo-1571019613914-85f342c6a11e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1050&q=80",
        "https://images.unsplash.com/photo-1519505907962-0a6cb0167c73?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1050&q=80",
        "https://images.unsplash.com/photo-1600618528240-fb9fc964b853?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"
      ],
      searchDialogOpen: false
    };
  }
};
</script>


<style scoped>
@media (max-width: 825px) {
  .main-carousel {
    display: none;
  }
}
</style>
