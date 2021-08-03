<template>
  <div class="name">
    {{ username }}
  </div>
  <div>
    <el-row class="tac">
      <el-col :span="6">
        <el-menu
          default-active="finishedClass"
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
              <el-menu-item index="rgstClass">수강중 클래스</el-menu-item>
              <el-menu-item index="finishedClass">수강완료 클래스</el-menu-item>
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
      <el-col :span="18">
        <div class="content-wrapper">
          <div class="submenu-title">수강완료 클래스</div>
          <!-- <el-row :gutter="10"> -->
          <!-- <el-col :span="6" v-for="o in classContent" :key="o"> -->
          <div class="finishedclass-card-section">
            <el-card
              shadow="none"
              v-for="o in classContent"
              :key="o"
              :body-style="{
                padding: '0px',
                height: '400px',
                width: '300px'
              }"
              style="margin: 5px"
            >
              <el-image
                :src="o.thumbnail"
                fit="cover"
                style="width: 300px; height: 200px;"
              />
              <div class="finishedclass-card-content">
                <div>
                  <div>
                    {{ o.teacherName }}
                  </div>
                  <div class="title">{{ o.title }}</div>
                  <el-tag size="mini" color="#BEEDED"
                    >{{ o.classStartDate }} ~ {{ o.classEndDate }}</el-tag
                  >
                </div>
                <div class="finishedclass-card-content-bottom">
                  <el-button
                    icon="el-icon-video-play"
                    class="btn-enter"
                    style="width: 70%;"
                    >영상 다시보기</el-button
                  >
                </div>
              </div>
            </el-card>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import Calendar from "v-calendar";

export default {
  name: "RegisterClass",
  components: {
    Calendar
  },
  data() {
    return {
      classContent: [
        {
          thumbnail:
            "https://images.unsplash.com/photo-1604431696980-07e518647bec?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1055&q=80",
          title: `홈트계의 끝판왕 '힙으뜸'`,
          classid: `1111`,
          teacherName: "심으뜸",
          classStartDate: "2021-08-08",
          classEndDate: "2021-11-08"
        },
        {
          thumbnail:
            "http://laza.jalbum.net/Watermark%20Demo/slides/P8220329.jpg",
          title: `SNPE 바디리셋 프로젝트. 척추운동으로 밸런스 회복하기!`,
          classid: `2222`,
          teacherName: "김계란",
          classStartDate: "2021-08-08",
          classEndDate: "2021-11-08"
        },
        {
          thumbnail:
            "http://laza.jalbum.net/Watermark%20Demo/slides/P8220329.jpg",
          title: `SNPE 바디리셋 프로젝트. 척추운동으로 밸런스 회복하기!`,
          classid: `2222`,
          teacherName: "김계란",
          classStartDate: "2021-08-08",
          classEndDate: "2021-11-08"
        },
        {
          thumbnail:
            "http://laza.jalbum.net/Watermark%20Demo/slides/P8220329.jpg",
          title: `SNPE 바디리셋 프로젝트. 척추운동으로 밸런스 회복하기!`,
          classid: `2222`,
          teacherName: "김계란",
          classStartDate: "2021-08-08",
          classEndDate: "2021-11-08"
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

.finishedclass-card-section {
  display: flex;
  flex-wrap: wrap;
}

.finishedclass-card-content {
  height: 170px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px;
}

.finishedclass-card-content-bottom {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
</style>
