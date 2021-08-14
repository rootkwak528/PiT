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
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "ClassSection",
  data() {
    return {};
  },
  setup() {
    const store = useStore();
    const state = reactive({
      list: computed(() => store.getters["root/getClassList"])
    });

    return {
      state,
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
