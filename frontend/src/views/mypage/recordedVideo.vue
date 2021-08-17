<template>
  <div>
    <div class="content-wrapper">
      <div class="classList" v-if="!state.selectedClassNo">
        <div class="submenu-title">녹화된 영상</div>
        <div class="recordedvideo-card-section">
          <el-table
            id="recordedvideo-card-content"
            :data="state.classList"
            style="width: 100%; font-size: 17px;"
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
        </el-page-header>
        <videos :classNo="state.selectedClassNo" />
      </div>
    </div>
  </div>
</template>

<script>
import "video.js/dist/video-js.min.css";
import "video.js/dist/video.min.js";
import videos from "./components/videos.vue";
import { reactive } from "@vue/reactivity";
import { useStore } from "vuex";
import { onMounted } from "vue";

export default {
  name: "recordedVideoTest",
  components: {
    videos
  },
  setup() {
    const store = useStore();

    const state = reactive({
      selectedClassNo: null,
      selectedTitle: null,
      classList: []
    });

    onMounted(() => {
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
          //console.log(result);
          for (var i = 0; i < result.data.length; i++)
            state.classList.push(result.data[i]);
        })
        .catch(function(err) {
          //alert(err.response);
          console.log(err);
        });
      //console.log(state.classList);
    });

    const mvVideoList = function(prop) {
      //console.log(prop.classid);
      state.selectedClassNo = prop.classNo;
      state.selectedTitle = prop.classTitle;
    };

    const goBack = function() {
      state.selectedClassNo = null;
    };

    return { mvVideoList, goBack, state };
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
