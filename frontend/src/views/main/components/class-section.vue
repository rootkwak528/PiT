<template>
  <!-- <el-row style="margin-top: 20px">
    <el-col :span="6" v-for="classItem in state.list" :key="classItem">
      <router-link
        :to="`/classdetail?classid=${classItem.classNo}`"
        style="text-decoration: none; color: inherit;"
      >
        <el-card :body-style="{ padding: '0px' }">
          <img :src="classItem.classThumbnail" class="image" />
          <div style="padding: 14px; height: 100px; text-overflow: ellipsis;">
            <p>{{ classItem.classTitle }}</p>
            <div class="bottom">
              월 {{ classItem.classPrice }}원 (5개월)<br />
              <el-button type="text" style="color: #00C0D4">선물하기</el-button>
            </div>
          </div>
        </el-card>
      </router-link>
    </el-col>
  </el-row> -->
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
      <el-image
        :src="classItem.classThumbnail"
        fit="cover"
        style="width: 300px; height: 200px;"
      />
      <div class="class-card-content">
        <div>
          <div>
            {{ classItem.teacherName }}
          </div>
          <div class="title">{{ classItem.classTitle }}</div>
          <el-tag size="mini" color="#BEEDED"
            >{{ classItem.classStartDate }} ~
            {{ classItem.classEndDate }}</el-tag
          >
        </div>
        <div class="finishedclass-card-content-bottom">
          월 {{ classItem.classPrice }}원
        </div>
      </div>
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
      list: [],
      pricePerMonth: []
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

            // state.pricePerMonth.push(

            // );
            result.data[i].classPrice = Math.ceil(
              result.data[i].classPrice / (endMonth - startMonth + 1)
            );
          }
          //console.log(state.pricePerMonth);
          //state.list.push(pricePerMonth);
        })
        .catch(function(err) {
          alert(err.response);
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
</style>
