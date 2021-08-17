<template>
  <div class="content-wrapper" v-loading="classData.loading">
    <div class="submenu-title">수강완료 클래스</div>
    <div v-if="!classData.isEmpty">
      <div class="finishedclass-card-section">
        <el-card
          shadow="none"
          v-for="classItem in classData.classList"
          :key="classItem"
          :body-style="{
            padding: '0px',
            height: '350px',
            width: '300px'
          }"
          style="margin: 5px"
        >
          <el-image
            :src="classItem.classThumbnail"
            fit="cover"
            style="width: 300px; height: 200px;"
          />
          <div class="finishedclass-card-content">
            <div>
              <div>
                {{ classItem.classTeacherName }}
              </div>
              <div class="title">{{ classItem.classTitle }}</div>
              <el-tag size="mini" color="#BEEDED"
                >{{ classItem.classStartDate }} ~
                {{ classItem.classEndDate }}</el-tag
              >
            </div>
          </div>
        </el-card>
      </div>
    </div>
    <div v-else>
      수강완료된 클래스가 없어요!
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { reactive, computed, onMounted } from "vue";
import { useStore } from "vuex";

export default {
  name: "FinishedClassTest",
  components: {
    // Calendar,
  },
  props: {
    userTypeName: String
  },
  setup(props) {
    const router = useRouter();
    const store = useStore();

    onMounted(() => {
      getFinishedClassList();
    });

    const classData = reactive({
      isEmpty: false,
      loading: true,
      classList: []
    });

    const getFinishedClassList = function() {
      store
        .dispatch("root/getFinishedClassList")
        .then(function(result) {
          //console.log(result);
          classData.classList = result.data;
        })
        .catch(function(err) {
          alert(err.response.data.message);
          classData.isEmpty = true;
          console.log(err);
        });
      classData.loading = false;
    };

    return { getFinishedClassList, classData };
  }
};
</script>

<style>
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
