<template>
  <el-card class="summary-card">
    <template #header>
      <div class="card-header">
        <div class="title">{{ state.form.classTitle }}</div>
      </div>
    </template>
    <div style="">
      <div class="subtitle">구매정보</div>
      <el-tag size="mini" color="#BEEDED">8월 8일부터 수강 가능</el-tag>
      <div class="info-content">
        <div class="info-content-child" style="width: 30%">
          <div><i class="el-icon-folder" /> 대분류</div>
          <div><i class="el-icon-goods" /> 준비물</div>
          <div><i class="el-icon-star-off" /> 레벨</div>
          <div><i class="el-icon-goods" /> 기간</div>
          <div><i class="el-icon-star-off" /> 수업 수</div>
        </div>
        <div class="info-content-child" style="margin-left: 15px">
          <div>{{ state.form.classTypeName }}</div>
          <div>{{ state.form.classMaterial }}</div>
          <div>{{ state.form.classLevelName }}</div>
          <div>3달</div>
          <div>{{ state.form.classTcnt }}</div>
        </div>
      </div>
      <el-divider />
      <div class="info-content">
        <div class="info-content-child" style="width: 30%">
          <div><i class="el-icon-coin" /> 금액</div>
        </div>
        <div class="info-content-child" style="margin-left: 15px">
          <div>총 {{ state.form.classPrice }}원</div>
        </div>
      </div>
      <div class="price-per-month">
        <div></div>
        <div>월 {{ state.form.classPricePerMonth }}원</div>
      </div>
      <button class="btn-submit" @click="clickRegister">신청하기</button>
      <button class="btn-favo" @click="clickClassLikes">찜하기</button>
    </div>
  </el-card>
</template>

<script>
import { useStore } from "vuex";
import { reactive, computed } from "vue";

export default {
  name: "ClassSummary",
  props: {
    classNo: String
  },
  setup(props) {
    const store = useStore();

    const state = reactive({
      form: {
        classTitle: "",
        classStartDate: "",
        classEndDate: "",
        classTypeName: "",
        classMaterial: "",
        classLevelName: "",
        classTcnt: "",
        classPrice: "",
        classPricePerMonth: "",
        userTypeName: "",
        isLogined: computed(() => store.getters["root/getIsLogined"]),
        userType: computed(() => store.getters["root/getUserType"]),
        classPricePerMonth: ""
      },
      isInUserLikes: false,
      userLikesClassList: computed(
        () => store.getters["root/getUserLikesClassList"]
      )
    });

    console.log("state.userLikesClassList : " + state.userLikesClassList);
    console.log(
      "getters.userLikesClassList : " +
        store.getters["root/getUserLikesClassList"]
    );

    store
      .dispatch("root/getClassDetail", { classNo: props.classNo })
      .then(function(result) {
        state.form.classTitle = result.data.classTitle;
        state.form.classStartDate = result.data.classStartDate;
        state.form.classEndDate = result.data.classEndDate;
        state.form.classTypeName = result.data.classTypeName;
        state.form.classMaterial = result.data.classMaterial;
        state.form.classLevelName = result.data.classLevelName;
        state.form.classTcnt = result.data.classTcnt;
        state.form.classPrice = result.data.classPrice;

        var startMonth = parseInt(result.data.classStartDate.split("-")[1]);
        var endMonth = parseInt(result.data.classEndDate.split("-")[1]);

        var tmp =
          Math.floor(
            result.data.classPrice / (endMonth - startMonth + 1) / 10
          ) * 10;

        state.form.classPricePerMonth = tmp;
      })
      .catch(function(err) {
        console.log(err);
      });

    const clickRegister = function() {
      if (!state.form.isLogined) {
        alert("로그인해주세요.");
      } else {
        if (state.form.userType === "수강생") {
          // 여기에 클래스 신청하기 로직 추가하자.
          var flag = true;
          store
            .dispatch("root/getRegisterClassList")
            .then(function(result) {
              var classList = result.data;
              for (var i = 0; i < classList.length; i++) {
                if (classList[i].classNo == props.classNo) {
                  alert("이미 수강중인 클래스입니다.");
                  flag = false;
                  break;
                }
              }
            })
            .catch(function(err) {
              console.log(err.response);
            });

          if (flag) {
            store
              .dispatch("root/enrollClass", { classNo: props.classNo })
              .then(function(result) {
                alert("신청이 완료되었습니다.");
              })
              .catch(function(err) {
                console.log(err.response);
              });
          }
        } else {
          alert("클래스는 수강생만 신청할 수 있습니다.");
        }
      }
    };

    const clickClassLikes = function() {
      store
        .dispatch("root/registerClassLikes", {
          classNo: props.classNo
        })
        .then(function() {
          alert("찜 목록에 추가 되었습니다.");
        })
        .catch(function(err) {
          alert(err.response.data.message);
          console.log(err);
        });
    };
    return {
      state,
      clickRegister,
      clickClassLikes
    };
  }
};
</script>

<style>
.summary-card {
  height: 600px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.info-content {
  display: flex;
  font-size: 15px;
  margin-top: 20px;
}

.info-content-child > * {
  margin-bottom: 8px;
}

.title {
  font-size: large;
  font-weight: bold;
}

.subtitle {
  font-weight: bold;
}

.price-per-month {
  color: #f35747;
  font-weight: bold;
  font-size: 20px;
  margin-top: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.btn-submit {
  margin-top: 30px;
  background-color: #ef7764;
  width: 100%;
  color: white;
  border: none;
  border-radius: 0.5rem;
  height: 40px;
  font-size: 18px;
  font-weight: bold;
}
.btn-favo {
  margin-top: 10px;
  background-color: rgb(239, 201, 128);
  width: 100%;
  color: white;
  border: none;
  border-radius: 0.5rem;
  height: 40px;
  font-size: 18px;
  font-weight: bold;
}
</style>
