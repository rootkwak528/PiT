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
          <div class="classList" v-if="!selectedClassid">
            <div class="submenu-title">녹화된 영상</div>
            <div class="recordedvideo-card-section">
              <el-table
                :data="classContent"
                style="width: 80%; font-size: 17px;"
                @row-click="mvVideoList"
              >
                <el-table-column prop="title" label="클래스명">
                </el-table-column>
                <el-table-column
                  prop="teacherName"
                  label="강사명"
                  width="100"
                  fixed="right"
                >
                </el-table-column>
              </el-table>
            </div>
          </div>
          <div class="videoList" v-else>
            <el-page-header @back="goBack" :content="selectedTitle">
              <!-- <video-player ref="videoPlayer" :options="playerOptions">
              </video-player> -->
            </el-page-header>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// import Calendar from "v-calendar";
import MypageSidebar from "./component/mypage-sidebar.vue";
// import VideoPlayer from "vue-video-player";
// import VueCoreVideoPlayer from "vue-core-video-player";
//import "video.js/dist/video-js.css";

//import { videoPlayer } from "vue-video-player";

export default {
  name: "RegisterClass",
  components: {
    // Calendar,
    MypageSidebar
    //videoPlayer
    // VideoPlayer
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
          classid: `3333`,
          teacherName: "김메추리알",
          classStartDate: "2021-08-08",
          classEndDate: "2021-11-08"
        },
        {
          thumbnail:
            "http://laza.jalbum.net/Watermark%20Demo/slides/P8220329.jpg",
          title: `SNPE 바디리셋 프로젝트. 척추운동으로 밸런스 회복하기!`,
          classid: `4444`,
          teacherName: "김훈제란",
          classStartDate: "2021-08-08",
          classEndDate: "2021-11-08"
        }
      ],
      selectedClassid: null,
      selectedTitle: null,
      playerOptions: {
        height: "360",
        autoplay: false,
        sources: [
          {
            // mp4
            // type: 'video/mp4',
            // src: 'http://vjs.zencdn.net/v/oceans.mp4',
            // flv
            type: "video/x-flv",
            src:
              "https://www.youtube.com/watch?v=eT3c9NDEjWo&ab_channel=webtro1"
          }
        ],
        language: "zh-CN",
        techOrder: ["flash"],
        poster:
          "https://surmon-china.github.io/vue-quill-editor/static/images/surmon-6.jpg"
      },
      imageUrl: ""
    };
  },
  methods: {
    mvVideoList(prop) {
      console.log(prop.classid);
      this.selectedClassid = prop.classid;
      this.selectedTitle = prop.title;
    },
    goBack() {
      this.selectedClassid = null;
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("Avatar picture must be JPG format!");
      }
      if (!isLt2M) {
        this.$message.error("Avatar picture size can not exceed 2MB!");
      }
      return isJPG && isLt2M;
    }
  }
};
</script>

<style>
.recordedvideo-card-section {
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
