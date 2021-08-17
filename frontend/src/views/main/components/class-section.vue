<template>
  <div class="class-section class-card-wrapper d-flex container row g-0 m-0">
    <div
      class="col-12 col-sm-6 col-md-4 col-xxl-3 p-1"
      v-for="(classItem, index) in state.list"
      :key="index"
    >
      <el-card
        shadow="none"
        class="class-card"
        :body-style="{
          padding: '0'
        }"
      >
        <router-link
          :to="`/classdetail?classNo=${classItem.classNo}`"
          style="text-decoration: none; color: inherit;"
        >
          <el-image
            :src="classItem.classThumbnail"
            fit="cover"
            style="width: 100%; height: 300px"
          />

          <!-- 클래스 카드 -->
          <div class="class-section class-card-content
                      d-flex flex-column justify-content-between">

            <!-- 강사, 제목 -->
            <div class="class-card-header">
              <div class="class-card-trainer">
                {{ classItem.classTeacherName }}
              </div>
              <div class="class-card-title">{{ classItem.classTitle }}</div>
            </div>

            <!-- 기간, 가격 -->
            <div class="class-date-price">
              <div class="class-card-tag">
                <el-tag size="mini" color="#BEEDED">
                  {{ classItem.classStartDate }} ~
                  {{ classItem.classEndDate }}
                </el-tag>
              </div>
              <div class="class-card-content-bottom">
                월 {{ classItem.classPrice }}원
              </div>
            </div>

          </div>
        </router-link>
      </el-card>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { useStore } from "vuex";
import { onMounted } from "@vue/runtime-core";

export default {
  name: "ClassSection",
  data() {
    return {};
  },
  setup() {
    const store = useStore();
    const state = reactive({
      list: []
    });

    onMounted(() => {
      getClassList();
    });

    const getClassList = function() {
      store
        .dispatch("root/getClassList")
        .then(function(result) {
          state.list = result.data;
          const today = new Date();

          for (var i = 0; i < state.list.length; i++) {
            // 월단위 가격 계산
            var startMonth = parseInt(
              result.data[i].classStartDate.split("-")[1]
            );
            var endMonth = parseInt(result.data[i].classEndDate.split("-")[1]);
            var tmp = (
              Math.floor(
                result.data[i].classPrice / (endMonth - startMonth + 1) / 100
              ) * 100
            ).toString();

            var left = tmp.slice(0, -3);
            var right = tmp.slice(-3, tmp.length);

            result.data[i].classPrice = left + "," + right;

            // 날짜 지난 클래스 삭제
            var classEndMonth = parseInt(
              result.data[i].classEndDate.split("-")[1]
            );
            var classEndDate = parseInt(
              result.data[i].classEndDate.split("-")[2]
            );

            if (
              classEndMonth <= today.getMonth() + 1 &&
              classEndDate < today.getDate()
            ) {
              state.list.splice(i, 1);
            }
          }
        })
        .catch(function(err) {
          alert(err.response);
          console.log(err);
        });
    };

    return {
      state,
      getClassList
    };
  }
};
</script>

<style>
.class-card:hover {
  box-shadow: 8px 8px 20px #ddd;
}

.class-section.class-card-wrapper {
  padding: 0;
  margin: 0;
  width: 100%;
  max-width: 100%;
}

.class-section.class-card-content {
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 15px;
}

.class-card-header {
  padding-bottom: 10px;
}

.class-card-trainer {
  font-size: 21px;
}

.class-card-title {
  font-weight: bold;
  font-size: 18px;
  margin-top: 6px;
}

.class-card-tag {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
}

.class-card-content-bottom {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  font-size: 20px;
  font-weight: bold;
  margin-top: 10px;
}
</style>
