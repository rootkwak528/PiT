<template>
  <div>
    <div class="content-wrapper">
      <div class="classList" v-if="state.selectedClassid == null">
        <div class="submenu-title">녹화된 영상</div>
        <div class="recordedvideo-card-section">
          <el-table
            :data="state.classList"
            style="width: 80%; font-size: 17px;"
            @row-click="mvVideoList"
          >
            <el-table-column prop="classTitle" label="클래스명">
            </el-table-column>
            <el-table-column
              prop="classTeacherName"
              label="강사명"
              width="100"
              fixed="right"
            >
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="videoList" v-else>
        <el-page-header @back="goBack" :content="state.selectedTitle">
          <!-- <video-player ref="videoPlayer" :options="playerOptions">
          </video-player> -->
        </el-page-header>
        <video-player :options="videoOptions" />
      </div>
    </div>
  </div>
</template>

<script>
import "video.js/dist/video-js.min.css";
import "video.js/dist/video.min.js";
import VideoPlayer from "./components/videoplayer.vue";
import { reactive } from "@vue/reactivity";
import { useStore } from "vuex";

export default {
  name: "recordedVideoTest",
  components: {
    VideoPlayer
  },
  setup() {
    const store = useStore();

    const state = reactive({
      selectedClassid: null,
      selectedTitle: null,
      classList: []
    });

    // 수강중 클래스
    store
      .dispatch("root/getRegisterClassList")
      .then(function(result) {
        state.classList = result.data;
      })
      .catch(function(err) {
        console.log(err);
      });

    // 수강완료 클래스
    store
      .dispatch("root/getFinishedClassList")
      .then(function(result) {
        for (var i = 0; i < result.data.length; i++)
          state.classList.push(result.data[i]);
      })
      .catch(function(err) {
        //alert(err.response );
        console.log(err.response);
      });

    const mvVideoList = function(prop) {
      //console.log(prop.classid);
      state.selectedClassid = prop.classNo;
      state.selectedTitle = prop.classTitle;
    };
    const goBack = function() {
      state.selectedClassid = null;
    };

    return { mvVideoList, goBack, state };
  },
  data() {
    return {
      videoOptions: {
        autoplay: true,
        controls: true,
        sources: [
          {
            src:
              "https://i5a204.p.ssafy.io/openvidu/recordings/ses_O9H9QaNtfm/str_CAM_SG84_con_ATmh8cnZdE.webm",
            type: "video/webm"
          }
        ]
      },
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
      ]
    };
  },
  methods: {}
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
