<template>
  <div class="name">
    {{ username }}
  </div>
  <div>
    <el-row class="tac">
      <el-col :span="6">
        <mypage-sidebar />
      </el-col>
      <el-col :span="18">
        <div class="content-wrapper">
          <div class="submenu-title">수강중 클래스</div>
          <el-card
            v-for="o in classContent"
            :key="o"
            shadow="hover"
            class="registerclass-card"
          >
            <div class="card-image-wrapper">
              <el-image
                :src="o.thumbnail"
                :fit="cover"
                style="vertical-align: middle; opacity: 0.4; width: 100%;"
              />
              <div style="position: absolute; padding: 18px">
                <div style="display: flex">
                  <div class="tag">요가</div>
                  <div style="font-weight: bold">강사: {{ o.teacherName }}</div>
                </div>
                <div class="title">{{ o.title }}</div>
                <div class="desc">{{ o.description }}</div>
                <div class="registerclass-card-bottom">
                  <el-button icon="el-icon-s-home" class="btn-enter"
                    >입장하기</el-button
                  >
                  <el-progress
                    :text-inside="true"
                    :stroke-width="24"
                    :percentage="100"
                    status="success"
                  ></el-progress>
                </div>
              </div>
            </div>
            <div class="card-calendar-wrapper">
              <el-image
                :src="require(`@/assets/images/calendar.png`)"
                fit="cover"
                style=""
              />
              <!-- <Calendar /> -->
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// import Calendar from "v-calendar";
import MypageSidebar from "./component/mypage-sidebar.vue";

export default {
  name: "RegisterClass",
  components: {
    // Calendar,
    MypageSidebar
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
  }
};
</script>

<style>
.submenu-title {
  font-weight: bold;
  font-size: 20px;
  height: 50px;
  line-height: 50px;
}

.content-wrapper {
  vertical-align: middle;
  margin-left: 40px;
}

.registerclass-card > .el-card__body {
  display: flex;
  height: 200px;
  padding: 0px;
  /* background-image: url("https://cdn.class101.net/images/119a8385-66ea-471a-a4a2-bee3e519c4da/375xauto.webp"); */
}

.registerclass-card {
  /* background-size: cover;
  opacity: 0.5; */
  position: relative;
  margin-bottom: 10px;
  max-width: 800px;
}

.card-image-wrapper {
  display: flex;
  width: 80%;
}

.card-calendar-wrapper {
  text-align: center;
  width: 250px;
}

.card-calendar-wrapper > el-image__inner {
  object-fit: cover;
}

.tag {
  background-color: #00c0d4;
  width: 40px;
  text-align: center;
  color: white;
  border-radius: 0.5rem;
  font-size: 13px;
  margin-right: 5px;
}

.title {
  font-weight: bold;
  font-size: 18px;
}

.registerclass-card-bottom {
  margin-top: 60px;
  display: flex;
}

.btn-enter {
  color: white;
  background-color: #00c0d4;
  border: none;
  border-radius: 0.8rem;
  padding: 10px;
}
</style>
