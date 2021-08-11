<template>
  <div class="content-wrapper">
    <div class="submenu-title">클래스 목록 조회</div>
    <div class="class-by-permission">
      <el-button-group>
        <el-button>전체</el-button>
        <el-button>승인</el-button>
        <el-button>미승인</el-button>
        <el-button>거절</el-button>
      </el-button-group>
    </div>
    <div style="border: solid 0.5px; margin-top: 13px; margin-bottom: 10px"></div>
    <div class="finishedclass-card-section">
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
import { reactive } from "@vue/reactivity";
import { useStore } from "vuex";
import { onMounted } from "@vue/runtime-core";

export default {
  name: "AdminClass",

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
        .dispatch("root/getAdminClassList", {
          permission: "000",
          token: "Bearer " + localStorage.getItem("jwt-auth-token")
        })
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
        .catch(function(err) {
          alert(err.response);
        });
    };

    return {
      state,
      getClassList
    };
  }
}
</script>
