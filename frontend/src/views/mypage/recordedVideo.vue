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
        <div class="content-wrapper classlist">
          <div class="submenu-title">녹화된 영상</div>
          <div class="recordedvideo-card-section">
            <el-table
              :data="classContent"
              style="width: 80%; font-size: 17px;"
              @click="mvVideoList"
            >
              <el-table-column prop="title" label="클래스명"> </el-table-column>
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
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    mvVideoList(prop) {
      console.log(prop);
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
