<template>
  <div class="submenu-title" style="margin-left: 10px">클래스 상세</div>
  <admin-gallery style="margin-bottom: 40px;"/>
  <div>
    <admin-class-content :classNo="this.$route.query.classNo" />
    <!-- <admin-class-summary :classNo="this.$route.query.classNo" /> -->
  </div>
  <div class="permission-btn">
    <el-button>클래스 개설승인</el-button> <!-- -->
    <el-button>클래스 개설거절</el-button> <!-- -->
  </div>
</template>

<script>
// 어드민 클래스 컨텐트, 섬머리, 갤러리 임포트
import AdminClassContent from "@/views/adminpage/adminclassdetail/components/adminclass-content"
import AdminClassSummary from "@/views/adminpage/adminclassdetail/components/adminclass-summary"
import AdminGallery from "@/views/adminpage/adminclassdetail/components/admingallery"

export default {
  name: "AdminClassDetail",
  components: {
    // 컨텐트, 섬머리, 갤러리
    AdminClassContent,
    AdminClassSummary,
    AdminGallery
  },
  props: {
    permission: String
  },
  setup(props){
    // 클래스 승인 여부 변경 코드
    console.log(props.permission);
  }


};
</script>

<style>
.content {
  display: flex;
  justify-content: space-between;
}
.permission-btn {
  display: flex;
  margin-top: 25px;
  justify-content: center;
}
</style>
