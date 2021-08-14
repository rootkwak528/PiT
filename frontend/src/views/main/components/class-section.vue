<template>
  <div class="class-card-wrapper">
    <el-card
      shadow="none"
      v-for="classItem in state.list"
      :key="classItem"
      :body-style="{
        padding: '0px',
        height: '400px',
        width: '300px'
      }"
      style="margin: 5px"
    >
      <router-link
        :to="`/classdetail?classNo=${classItem.classNo}`"
        style="text-decoration: none; color: inherit;"
      >
        <el-image
          :src="classItem.classThumbnail"
          fit="cover"
          style="width: 300px; height: 200px;"
        />
        <div class="class-card-content">
          <div>
            <div style="height: 80px;">
              <div>
                {{ classItem.classTeacherName }}
              </div>
              <div class="title">{{ classItem.classTitle }}</div>
            </div>
            <div class="class-card-tag">
              <el-tag size="mini" color="#BEEDED">
                {{ classItem.classStartDate }} ~
                {{ classItem.classEndDate }}
              </el-tag>
            </div>
          </div>
          <div class="class-card-content-bottom">
            월 {{ classItem.classPrice }}원
          </div>
        </div>
      </router-link>
    </el-card>
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

          for (var i = 0; i < state.list.length; i++) {
            var startMonth = parseInt(
              result.data[i].classStartDate.split("-")[1]
            );
            var endMonth = parseInt(result.data[i].classEndDate.split("-")[1]);
            var tmp =
              Math.floor(
                result.data[i].classPrice / (endMonth - startMonth + 1) / 10
              ) * 10;

            result.data[i].classPrice = tmp;
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
.class-card-wrapper {
  display: flex;
  flex-wrap: wrap;
}

.class-card-content {
  height: 170px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 10px;
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
}
</style>
