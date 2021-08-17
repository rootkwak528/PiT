<template>
  <div class="submenu-title" style="margin-left: 10px">클래스 상세</div>
  <admin-gallery :classNo="this.$route.query.classNo" style="margin-bottom: 40px;"/>
  <div>
    <admin-class-content :classNo="this.$route.query.classNo" />
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

  setup(props){
  }
};
</script>

<style>
.content {
  display: flex;
  justify-content: space-between;
}
</style>
