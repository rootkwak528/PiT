<template>
  <el-menu
    :default-active="pathname"
    class="el-menu-vertical-demo"
    @open="handleOpen"
    @close="handleClose"
    active-text-color="#00C0D4"
    router="true"
  >
    <el-submenu index="1">
      <template #title>
        <i class="el-icon-notebook-1"></i>
        <span>마이클래스</span>
      </template>
      <el-menu-item-group v-if="userTypeName == '수강생'">
        <el-menu-item index="rgstClass">수강중 클래스</el-menu-item>
        <el-menu-item index="finishedClass">수강완료 클래스</el-menu-item>
        <el-menu-item index="recordedVideo">녹화된 수업</el-menu-item>
        <el-menu-item index="1-4">피드백</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group v-if="userTypeName == '트레이너'">
        <el-menu-item index="rgstClass">수강중 클래스</el-menu-item>
        <el-menu-item index="finishedClass">수강완료 클래스</el-menu-item>
        <el-menu-item index="openClass">클래스 개설</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-menu-item index="2" v-if="userTypeName == '수강생'">
      <i class="el-icon-star-on"></i>
      <span>찜한 클래스</span>
    </el-menu-item>
    <el-menu-item index="2" v-if="userTypeName == '트레이너'">
      <i class="el-icon-star-on"></i>
      <span>피드백 등록</span>
    </el-menu-item>
    <el-menu-item index="myinfo">
      <i class="el-icon-user"></i>
      <span>마이페이지</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  data() {
    return {
      userType: "002"
    };
  },
  props: {
    pathname: String,
    userTypeName: String
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>

<style></style>
