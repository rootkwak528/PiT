<template>
  <div class="content-wrapper">
    <div class="submenu-title">찜한 클래스</div>
    <div v-if="state.list.length == 0">찜한 클래스가 없어요!</div>
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
  </div>

</template>

<script>
import { useStore } from 'vuex'
import { reactive } from "@vue/reactivity";

export default {
  name: "FavoriteClass",
  setup() {
    const store = useStore();
    const state = reactive({
      list: []
    });

    // 찜한 클래스 목록 조회하기 함수
    const getClassLikesList = function() {
      store
        .dispatch("root/getClassLikesList")
        .then(function(result) {
          state.list = result.data;

          for (var i = 0; i < state.list.length; i++) {
            var startMonth = parseInt(
              result.data[i].classStartDate.split("-")[1]
            );
            var endMonth = parseInt(result.data[i].classEndDate.split("-")[1]);
            result.data[i].classPrice = Math.ceil(
              result.data[i].classPrice / (endMonth - startMonth + 1)
            );
          }
        })
        .catch(function(err){
          alert(err.response);
        })
    }

    return {
      state,
      getClassLikesList
    }
  }
}
</script>
<style>

</style>
