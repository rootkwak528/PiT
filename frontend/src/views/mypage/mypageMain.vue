<template>
  <div class="name">
    {{ username }}
  </div>
  <div>
    <el-row class="tac">
      <el-col :span="6">
        <mypage-sidebar :pathname="pathname"/>
      </el-col>
      <el-col :span="18" style="min-height: 655px">
        <router-view />
      </el-col>
    </el-row>
  </div>

</template>

<script>
import MypageSidebar from "../mypage/sidebar/mypage-sidebar.vue";

export default {
  name: "MypageMain",
  components: {
    MypageSidebar,
  },
  data() {
    return {
      classContent: [
        {
          thumbnail:
            "https://images.unsplash.com/photo-1604431696980-07e518647bec?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1055&q=80",
          title: `홈트계의 끝판왕 '힙으뜸'`,
          price: `46,800`,
          classid: `1111`,
          teacherName: "심으뜸",
          description: `검증된 운동 유튜버의 업데이트 된 커리큘럼!`,
          percentage: 30
        },
        {
          thumbnail:
            "http://laza.jalbum.net/Watermark%20Demo/slides/P8220329.jpg",
          title: `SNPE 바디리셋 프로젝트. 척추운동으로 밸런스 회복하기!`,
          price: `46,800`,
          classid: `2222`,
          teacherName: "김계란",
          description: `수년 째 작심삼일 중인 당신을 위한 클래스`,
          percentage: 50
        }
      ]
    };
  },
  methods: {
    format(percentage) {
      return percentage === 100 ? "Full" : `${percentage}%`;
    }
  },
  computed: {
    pathname(){

      return(window.location.pathname.slice(1));
    }
  }
};
</script>
