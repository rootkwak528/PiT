<template>
  <div class="name">
    {{ username }}
  </div>
  <div>
    <el-row class="tac">
      <el-col :span="6">
        <el-menu
          default-active="1-1"
          class="el-menu-vertical-demo"
          @open="open"
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
      <el-col span="6">
        <div>
          {{ selected }}
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import MypageSidebar from "./component/mypage-sidebar.vue";
export default {
  name: "MyClass",
  components: {
    MypageSidebar
  },

  data() {
    return {
      username: "홍길동",
      selectedMenu: 0,
      selected: ""
    };
  },
  setup() {
    const hah = function(index) {
      console.log(index + " selected");
      selected = index;
    };

    return { hah };
  }
};
</script>

<style>
.name {
  font-size: 30px;
  font-weight: bold;
  margin-top: 50px;
  margin-bottom: 50px;
}
</style>
