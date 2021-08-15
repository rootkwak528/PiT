<template>
  <el-menu
    class="el-menu-demo"
    mode="horizontal"
    active-text-color="#00C0D4"
    router="true"
  >
    <el-menu-item index="/">
      <!-- <router-link to="/main"> -->
      Home
      <!-- </router-link> -->
    </el-menu-item>
    <el-submenu index="2">
      <template #title>1:1</template>
      <el-menu-item index="2-1">PT</el-menu-item>
      <el-menu-item index="2-2">요가</el-menu-item>
      <el-menu-item index="2-3">필라테스</el-menu-item>
    </el-submenu>
    <el-submenu index="3">
      <template #title>1:N</template>
      <el-menu-item index="3-1">PT</el-menu-item>
      <el-menu-item index="3-2">요가</el-menu-item>
      <el-menu-item index="3-3">필라테스</el-menu-item>
    </el-submenu>
    <el-menu-item index="/event">이벤트</el-menu-item>
  </el-menu>
  <div class="line"></div>
</template>

<script>
export default {
  name: "Navbar",
  setup() {}
};
</script>
<style></style>
