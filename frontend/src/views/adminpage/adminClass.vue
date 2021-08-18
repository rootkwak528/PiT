<template>
  <div class="content-wrapper">
    <div class="submenu-title">클래스 목록 조회</div>
    <div class="class-by-permission">
      <el-button-group>
        <el-button v-bind:class="{'non-active': !state.pAll, 'active': state.pAll }" @click="clickPermissionAll">전체</el-button>
        <el-button v-bind:class="{'non-active': !state.pGranted, 'active': state.pGranted }" @click="clickPermissionGranted">승인</el-button>
        <el-button v-bind:class="{'non-active': !state.pNotGranted, 'active': state.pNotGranted }" @click="clickPermissionNotGranted">미승인</el-button>
        <el-button v-bind:class="{'non-active': !state.pDenied, 'active': state.pDenied }" @click="clickPermissionDenied">거절</el-button>
      </el-button-group>
      <span style="padding-left: 20px">{{ state.list.length }} 개의 클래스가 조회되었습니다.</span>
    </div>
    <div class="class-card-wrapper">
      <el-card
        shadow="none"
        class="class-card"
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
          :to="`/adminClassDetail?classNo=${classItem.classNo}`"
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
                <div class="admin-class-title">{{ classItem.classTitle }}</div>
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
      list: [],
      permission: "000",
      pAll: true,
      pGranted: false,
      pNotGranted: false,
      pDenied: false,
    });

    onMounted(() => {
      getClassList();
    });

    const clickPermissionAll = function() {
      state.permission = "000";
      state.pAll = true;
      state.pGranted = false;
      state.pNotGranted = false;
      state.pDenied = false;
      getClassList();
    }
    const clickPermissionGranted = function() {
      state.permission = "001";
      state.pAll = false;
      state.pGranted = true;
      state.pNotGranted = false;
      state.pDenied = false;
      getClassList();
    }
    const clickPermissionNotGranted = function() {
      state.permission = "002";
      state.pAll = false;
      state.pGranted = false;
      state.pNotGranted = true;
      state.pDenied = false;
      getClassList();
    }
    const clickPermissionDenied = function() {
      state.permission = "003";
      state.pAll = false;
      state.pGranted = false;
      state.pNotGranted = false;
      state.pDenied = true;
      getClassList();
    }

    const getClassList = function() {
      store
        .dispatch("root/getAdminClassList", {
          permission: state.permission,
          token: "Bearer " + localStorage.getItem("jwt-auth-token")
        })
        .then(function(result) {
          state.list = result.data;

          const today = new Date();

          var i = 0;
          while (i < result.data.length) {
            // 날짜 지난 클래스 삭제
            var dateArr = result.data[i].classEndDate.split("-");
            var endMonth = parseInt(dateArr[1]);
            var endDate = parseInt(dateArr[2]);

            if (endMonth <= today.getMonth() + 1 && endDate < today.getDate()) {
              result.data.splice(i, 1);
              i--;
            }
            i++;
          }

          for (i = 0; i < state.list.length; i++) {
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
          }
        })
        .catch(function(err) {
          alert(err.response);
        });
    };

    return {
      state,
      getClassList,
      clickPermissionAll,
      clickPermissionGranted,
      clickPermissionNotGranted,
      clickPermissionDenied
    };
  }
}
</script>

<style>
.non-active {
  background-color: white;
  color: #00C0D4;
}
.active {
  background-color: #00C0D4;
  color: white;
}
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

.class-by-permission {
  border-bottom: solid 1.2px #ebeef5;
  margin-bottom: 20px;
  padding-bottom: 10px;
}

@media (max-width: 576px) {
  .content-wrapper {
    margin-left: 0px !important;
  }
}
.admin-class-title{
  font-weight: bold;
  font-size: 18px;
  word-break: break-all;
  margin-top: 6px;
}

</style>
