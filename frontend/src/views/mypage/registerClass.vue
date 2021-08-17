<template>
  <div class="content-wrapper" v-loading="classData.loading">
    <div class="submenu-title">수강중 클래스</div>
    <div v-if="!classData.isEmpty" class="card-content-container">
      <el-card
        v-for="(classItem, index) in classData.classList"
        :key="classItem"
        shadow="hover"
        class="registerclass-card"
        :id="'card-'+classItem.classNo"
      >
        <div class="card-image-wrapper" style="width: 100%;">
          <el-image
            :src="classItem.classThumbnail"
            fit="cover"
            style="vertical-align: middle; opacity: 0.4; width: 100%;"
          />
          <div style="position: absolute; padding: 18px" class="card-content d-flex flex-column justify-content-between">
            <div>
              <div class="tag">{{ classItem.classType }}</div>
              <div class="trainer-title" style="font-weight: bold">
                강사:
                <span class="trainer">{{ classItem.classTeacherName }}</span>
              </div>
              <div class="title">{{ classItem.classTitle }}</div>
              <!-- <div class="desc">{{ classItem.classDesc }}</div> -->
            </div>
            <div class="registerclass-card-bottom">
              <!-- PT룸 입장 버튼 -->
              <el-button
                v-if="classItem.userNo == userNo"
                icon="el-icon-s-home"
                class="btn-enter"
                @click="onClickPTRoomBtn"
                >PT룸 개설하기
              </el-button>

              <el-button
                v-else
                icon="el-icon-s-home"
                class="btn-enter"
                @click="onClickPTRoomBtn"
                >PT룸 입장하기
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
        <div class="card-calendar-wrapper d-none d-lg-block">
          <v-calendar
            :attributes="classData.dayList[index].dateAttrs"
            :min-date="classItem.classStartDate"
            :max-date="classItem.classEndDate"
          />
        </div>
      </el-card>
    </div>

    <div v-else>수강중인 클래스가 없어요!</div>
  </div>
</template>

<script>
import { ref, onUpdated } from "vue";
import { useStore, mapState } from "vuex";
import { reactive } from "@vue/reactivity";

export default {
  name: "RegisterClassTest",
  components: {
    // Calendar,
  },
  setup() {
    const store = useStore();
    const _resizeFlag = ref(false)  // 이벤트리스너 한번만 추가하기 위한 flag
    const classData = reactive({
      isEmpty: false,
      loading: true,
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
          //console.log(result.data);
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
            //console.log(tmpDayList);
            var tmpDateAttrs = [];
            var tmpDays = [];
            for (var j = 0; j < tmpDayList.length; j++) {
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
              //console.log(weekday);
              var tmp = "weekdays : " + weekday;
              //tmpDays.push(tmp);
            }

            //console.log(tmpDays);
            //classData.dates.push(tmpDays);
            //console.log(classData.dates);
            //classData.dates.pop;
            // var tmp = "dates : " + tmpDays;
            // tmpDateAttrs.push(tmp);
            // // tmp = `,dot: {color: "red"}`;
            // // tmpDateAttrs.push(tmp);
            // console.log(tmpDateAttrs);
          }
        })
        .catch(function(err) {
          classData.isEmpty = true;
          console.log(err);
        });
      classData.loading = false;
    };

    const resizeClassCard = function () {
      classData.classList.forEach(classItem => {
        const classCard = document.getElementById(`card-${classItem.classNo}`)

        // 마지막 월요일이 달력에 포함되어 있으면 270px, 아니면 240px
        const lastMonday = classCard.getElementsByClassName('on-bottom on-left')[0]
        const is270px = !lastMonday.classList.contains('is-not-in-month')

        const cardBodyDOM1 = classCard.getElementsByClassName('el-card__body')[0]
        const cardBodyDOM2 = classCard.getElementsByClassName('card-content')[0]
        const calendarDOM = classCard.getElementsByClassName('vc-container vc-blue')[0]

        cardBodyDOM1.style.height = is270px?'270px':'240px'
        cardBodyDOM2.style.height = is270px?'270px':'240px'
        calendarDOM.style.height = is270px?'270px':'240px'
      })
    }

    const addEventOnBtn = function () {
      // eventListener는 딱 한번만 추가되어야 한다.
      if (!_resizeFlag.value && classData.classList.length) {
        _resizeFlag.value = true
      } else {
        return
      }

      classData.classList.forEach(classItem => {
        const classCard = document.getElementById(`card-${classItem.classNo}`)
        
        const calendar = classCard.getElementsByClassName('card-calendar-wrapper')[0]
        calendar.addEventListener('transitionrun', async function() {
          // 애니메이션이 완료되면 리사이즈해야 해서 0.35초 대기
          await new Promise(r => setTimeout(r, 350))
          resizeClassCard()
        })
      })
    }

    getRegisterClassList();

    onUpdated(() => {
      resizeClassCard()
      addEventOnBtn()
    })

    return { 
      store, classData,
      getRegisterClassList, resizeClassCard
    };
  },

  computed: {
    ...mapState({
      userNickname: state => state.root.userNickname,
      userName: state => state.root.userName,
      userNo: state => state.root.userNo
    })
  },

  methods: {
    format(percentage) {
      return percentage === 100 ? "Full" : `${percentage}%`;
    },

    async onClickPTRoomBtn(event) {
      const trainerName = event.target.parentElement.parentElement.parentElement.querySelector(
        ".trainer"
      ).innerText;
      const classTitle = event.target.parentElement.parentElement.parentElement.querySelector(
        ".title"
      ).innerText;

      let sessionName;
      let classNo;
      let trainerNo;
      for (let i = 0; i < this.classData.classList.length; i++) {
        if (classTitle == this.classData.classList[i].classTitle) {
          classNo = this.classData.classList[i].classNo;
          trainerNo = this.classData.classList[i].userNo;
          this.$store.dispatch("root/getSessionName", { classNo }).then(res => {
            sessionName = res.data;
          });
          break;
        }
      }

      // 정확히 트레이너인지 판단하려면 userNo를 비교해야함.
      const nickname = this.userNickname;
      const userNo = this.userNo;
      const isTrainer = trainerNo == userNo;
      const redirectUrl = "https://i5a204.p.ssafy.io:5000/";
      let isAvail;

      // 피티룸 개설됐는지 확인
      await this.$store
        .dispatch("root/getSessionAvail", { classNo })
        .then(res => {
          isAvail = res.data ? true : false;
          console.log(res.data, isAvail);
        });

      // 수강생은 빈 피티룸에 입장 불가
      if (!isTrainer && !isAvail) {
        console.log(isTrainer, isAvail);
        alert("아직 PT룸이 개설되지 않았습니다.");
      } else {
        const targetWindow = window.open(redirectUrl);
        setTimeout(() => {
          targetWindow.postMessage(
            {
              sessionName,
              nickname,
              isTrainer,
              classNo,
              classTitle,
              token: localStorage.getItem("jwt-auth-token")
            },
            redirectUrl
          );

          // 트레이너가 피티룸 개설하면 입장 확인
          if (isTrainer) {
            this.$store
              .dispatch("root/enterSession", { classNo })
              .then(res => {
                console.log(res);
              })
              .catch(err => {
                console.warn(err);
              });
          }
        }, 1000);
      }
    }
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
  padding: 0px;
  /* background-image: url("https://cdn.class101.net/images/119a8385-66ea-471a-a4a2-bee3e519c4da/375xauto.webp"); */
}

.registerclass-card {
  /* background-size: cover;
  opacity: 0.5; */
  position: relative;
  margin-bottom: 10px;
  max-width: 800px;
  border-radius: 0.55rem !important;
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

/* 민영 수정 시작 */
.trainer-title {
  margin-left: 3px;
}

.vc-container.vc-blue {
/* .vc-pane-layout { */
  border-top-left-radius: 0% !important;
  border-bottom-left-radius: 0% !important;
  border: none;
  background-color: #f5f5f5;
}

@media (max-width: 1020px) {
  .card-calendar-wrapper,
  .vc-popover-content-wrapper,
  .vc-container.vc-blue,
  .vc-pane-container,
  .v-calendar {
    /* display: none !important; */
    display: none !important;
  }

  .el-card__body .card-calendar-wrapper {
    display: none !important;
  }
  .title {
    width: 90%;
  }
}

/* 민영 수정 종료 */

.card-calendar-wrapper > el-image__inner {
  object-fit: cover;
}

.tag {
  background-color: #00c0d4;
  width: 40px;
  text-align: center;
  color: white;
  border-radius: 0.5rem;
  font-size: 16px; /* 민영 수정 */
  margin-right: 5px;
  vertical-align: middle; /* 민영 수정 */
  margin-bottom: 10px;
}

.title {
  font-weight: bold;
  font-size: 18px;
  width: 350px;
  /* 민영 수정 시작 */
  word-break: break-all;
  margin-top: 6px;
  /* 민영 수정 끝 */
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

.card-content-container {
  margin-top: 10px;
}
</style>
