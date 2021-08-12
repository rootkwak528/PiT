<template>
  <div class="content-wrapper">
    <div class="submenu-title">수강중 클래스</div>
    <el-card
      v-for="(classItem, index) in classData.classList"
      :key="classItem"
      shadow="hover"
      class="registerclass-card"
    >
      <div class="card-image-wrapper">
        <el-image
          :src="classItem.classThumbnail"
          fit="cover"
          style="vertical-align: middle; opacity: 0.4; width: 100%;"
        />
        <div style="position: absolute; padding: 18px">
          <div style="display: flex">
            <div class="tag">{{ classItem.classType }}</div>
            <div style="font-weight: bold">
              강사:
              <span class="trainer">{{ classItem.classTeacherName }}</span>
            </div>
          </div>
          <div class="title">{{ classItem.classTitle }}</div>
          <!-- <div class="desc">{{ classItem.classDesc }}</div> -->
          <div class="registerclass-card-bottom">
            <el-button
              icon="el-icon-s-home"
              class="btn-enter"
              @click="joinSession"
              >입장하기
            </el-button>
            <el-progress
              :text-inside="true"
              :stroke-width="24"
              :percentage="100"
              status="success"
            ></el-progress>
          </div>
        </div>
      </div>
      <div class="card-calendar-wrapper">
        <v-calendar
          :attributes="classData.dayList[index].dateAttrs"
          :min-date="classItem.classStartDate"
          :max-date="classItem.classEndDate"
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import { onMounted } from "@vue/runtime-core";
import { useStore } from "vuex";
import { reactive } from "@vue/reactivity";

export default {
  name: "RegisterClassTest",
  components: {
    // Calendar,
  },
  methods: {
    format(percentage) {
      return percentage === 100 ? "Full" : `${percentage}%`;
    },

    joinSession(event) {
      const trainerName = event.target.parentElement.parentElement.parentElement.querySelector(
        ".trainer"
      ).innerText;
      const sessionName = this.hashCode(trainerName);
      const nickname = "abc";
      const redirectUrl = "https://i5a204.p.ssafy.io:5000/";

      const targetWindow = window.open(redirectUrl);
      setTimeout(function() {
        targetWindow.postMessage({ sessionName, nickname }, redirectUrl);
      }, 500);
    },

    hashCode(str) {
      var hash = 0,
        i,
        chr;
      if (str.length === 0) return hash;
      for (i = 0; i < str.length; i++) {
        chr = str.charCodeAt(i);
        hash = (hash << 5) - hash + chr;
        hash |= 0;
      }
      return hash;
    }
  },
  setup() {
    const store = useStore();
    onMounted(() => {
      getRegisterClassList();
    });

    const classData = reactive({
      classList: [],
      dates: [],
      dateAttrs: [],
      dayList: [
        {
          dateAttrs: [
            {
              highlight: true,
              dates: [{ weekdays: 0 }, { weekdays: 3 }]
            }
          ]
        },
        {
          dateAttrs: [
            {
              highlight: true,
              dates: [{ weekdays: 0 }, { weekdays: 3 }]
            }
          ]
        },
        {
          dateAttrs: [
            {
              highlight: true,
              dates: [{ weekdays: 6 }]
            }
          ]
        },
        {
          dateAttrs: [
            {
              highlight: true,
              dates: [{ weekdays: 1 }, { weekdays: 3 }, { weekdays: 5 }]
            }
          ]
        }
      ]
    });

    const getRegisterClassList = function() {
      store
        .dispatch("root/getRegisterClassList")
        .then(function(result) {
          // console.log(result);
          console.log(result.data);
          classData.classList = result.data;
          for (var i = 0; i < classData.classList.length; i++) {
            if (classData.classList[i].classType == "001") {
              //PT
              classData.classList[i].classType = "PT";
            } else if (classData.classList[i].classType == "002") {
              //요가
              classData.classList[i].classType = "요가";
            } else if (classData.classList[i].classType == "003") {
              //필라테스
              classData.classList[i].classType = "필라테스";
            } else {
              //기타
              classData.classList[i].classType = "기타";
            }

            var tmpDayList = classData.classList[i].classDay; // 요일 리스트
            console.log(tmpDayList);
            var tmpDateAttrs = [];
            var tmpDays = [];
            for (var j = 0; j < tmpDayList.length; j++) {
              console.log(tmpDayList.charAt(j));
              var weekday = 0;
              switch (tmpDayList.charAt(j)) {
                case "월":
                  weekday = 0;
                  break;
                case "화":
                  weekday = 1;
                  break;
                case "수":
                  weekday = 2;
                  break;
                case "목":
                  weekday = 3;
                  break;
                case "금":
                  weekday = 4;
                  break;
                case "토":
                  weekday = 5;
                  break;
                case "일":
                  weekday = 6;
                  break;
              }
              console.log(weekday);
              var tmp = "weekdays : " + weekday;
              tmpDays.push(tmp);
            }

            console.log(tmpDays);
            classData.dates.push(tmpDays);
            console.log(classData.dates);
            classData.dates.pop;
            // var tmp = "dates : " + tmpDays;
            // tmpDateAttrs.push(tmp);
            // // tmp = `,dot: {color: "red"}`;
            // // tmpDateAttrs.push(tmp);
            // console.log(tmpDateAttrs);
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    };

    return { getRegisterClassList, classData };
  }
};
</script>

<style>
.submenu-title {
  font-weight: bold;
  font-size: 20px;
  height: 50px;
  line-height: 50px;
}

.content-wrapper {
  vertical-align: middle;
  margin-left: 40px;
}

.registerclass-card > .el-card__body {
  display: flex;
  height: 240px;
  padding: 0px;
  /* background-image: url("https://cdn.class101.net/images/119a8385-66ea-471a-a4a2-bee3e519c4da/375xauto.webp"); */
}

.registerclass-card {
  /* background-size: cover;
  opacity: 0.5; */
  position: relative;
  margin-bottom: 10px;
  max-width: 800px;
}

.card-image-wrapper {
  display: flex;
  width: 80%;
}

.card-calendar-wrapper {
  text-align: center;
  /* height: 200px;
  width: 250px; */
}

.card-calendar-wrapper > el-image__inner {
  object-fit: cover;
}

.tag {
  background-color: #00c0d4;
  width: 40px;
  text-align: center;
  color: white;
  border-radius: 0.5rem;
  font-size: 13px;
  margin-right: 5px;
}

.title {
  font-weight: bold;
  font-size: 18px;
}

.registerclass-card-bottom {
  margin-top: 60px;
  display: flex;
}

.btn-enter {
  color: white;
  background-color: #00c0d4;
  border: none;
  border-radius: 0.8rem;
  padding: 10px;
}
</style>
