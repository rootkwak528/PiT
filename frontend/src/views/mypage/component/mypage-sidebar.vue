<template>
  <el-row class="tac">
    <el-col :span="6">
      <el-menu
        default-active="1"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        active-text-color="#00C0D4"
        router="true"
      >
        <el-submenu index="1">
          <template #title>
            <i class="el-icon-notebook-1"></i>
            <span>내 클래스</span>
          </template>
          <el-menu-item-group>
            <el-menu-item index="/rgstClass">수강중 클래스</el-menu-item>
            <el-menu-item index="1-2">수강완료 클래스</el-menu-item>
            <el-menu-item index="1-3">녹화된 수업</el-menu-item>
            <el-menu-item index="1-4">피드백</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-menu-item index="2">
          <i class="el-icon-star-on"></i>
          <span>찜한 클래스</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-user"></i>
          <span>내 정보</span>
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      userType: "002"
    };
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
