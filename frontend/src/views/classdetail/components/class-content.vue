<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="클래스 설명" name="classDescript">
      <div>
        <h2>클래스 설명</h2>
        <el-divider />
        <h3>{{ classInfo.title }}</h3>
        <p>{{ classInfo.description }}</p>
        <el-divider />
        <h3>커리큘럼</h3>
        <pre>{{ classInfo.curriculum }}</pre>
        <el-divider />
        <h3>커뮤니티</h3>
        <!-- <div class="rate">
          <div style="margin-right: 20px">수강생 별점</div>
          <el-rate
            v-model="value"
            disabled
            show-score
            text-color="#00C0D4"
            score-template="{value} / 5"
          >
          </el-rate>
        </div> -->
        <!-- <div> -->
        <div v-for="o in community" :key="o">
          <el-card class="box-card">
            <div class="rate">
              <div style="margin-right: 20px">{{ o.userid }}</div>
              <!-- <el-rate
                  v-model="o.rate"
                  disabled
                  show-score
                  text-color="#00C0D4"
                  score-template="{value} / 5"
                >
                </el-rate> -->
              <div>{{ o.reviewCreateDate }}</div>
            </div>
            <div>
              {{ o.review }}
            </div>
          </el-card>
        </div>
        <!-- </div> -->
      </div>
    </el-tab-pane>
    <el-tab-pane label="커리큘럼" name="curriculum"
      ><h3>커리큘럼</h3>
      <pre>{{ classInfo.curriculum }}</pre></el-tab-pane
    >
    <el-tab-pane label="커뮤니티" name="review"
      ><h3>커뮤니티</h3>
      <!-- <div class="rate">
        <div style="margin-right: 20px">수강생 별점</div>
        <el-rate
          v-model="value"
          disabled
          show-score
          text-color="#00C0D4"
          score-template="{value} / 5"
        >
        </el-rate>
      </div> -->
      <div>
        <div v-for="o in community" :key="o">
          <el-card class="box-card">
            <div class="rate">
              <div style="margin-right: 20px">{{ o.userid }}</div>
              <!-- <el-rate
                  v-model="o.rate"
                  disabled
                  show-score
                  text-color="#00C0D4"
                  score-template="{value} / 5"
                >
                </el-rate> -->
              <div>{{ o.reviewCreateDate }}</div>
            </div>
            <div>
              {{ o.review }}
            </div>
          </el-card>
        </div>
      </div></el-tab-pane
    >
  </el-tabs>
</template>

<script>
export default {
  name: "ClassContent",
  data() {
    return {
      activeName: "classDescript",
      classInfo: {
        title: "달심쌤과 함께 퇴근 후 요가퐈이야",
        description:
          "퇴근 이후에 코어 근육 강화를 위한 요가 수업입니다. 늦은 저녁 시간에 강의가 진행됩니다.",
        curriculum: `1주차 : 수강생의 상태 파악
2주차 : ~~
3주차 : ~~
4주차 : ~~

5주차 : ~~
6주차 : ~~
7주차 : ~~
8주차 : ~~

9주차 : ~~
10주차 : ~~
11주차 : ~~
12주차 : ~~`
      },
      value: 3.7,
      community: [
        {
          userid: "id123",
          reviewCreateDate: "2021-07-29",
          review: `정말 좋아요! 시간도 정말 적절하고 강사님께서 배려심 넘치세요!!
맨날 앉아있기만 해서 허리가 정말 안좋았는데 달심쌤 수업 듣고 자세도 훨씬 좋아졌어요! 강력추천 드립니다!!`
        },
        {
          userid: "id222",
          reviewCreateDate: "2021-08-01",
          review: `정말 좋아요! 시간도 정말 적절하고 강사님께서 배려심 넘치세요!!
맨날 앉아있기만 해서 허리가 정말 안좋았는데 달심쌤 수업 듣고 자세도 훨씬 좋아졌어요! 강력추천 드립니다!!`
        },
        {
          userid: "id333",
          reviewCreateDate: "2021-08-01",
          review: `정말 좋아요! 시간도 정말 적절하고 강사님께서 배려심 넘치세요!!
맨날 앉아있기만 해서 허리가 정말 안좋았는데 달심쌤 수업 듣고 자세도 훨씬 좋아졌어요! 강력추천 드립니다!!`
        }
      ]
    };
  },
  methods: {
    handleClick(tab, event) {
      //console.log(tab, event);
    }
  }
};
</script>

<style>
.rate {
  display: flex;
  margin-bottom: 20px;
  /* justify-content: space-between; */
}

.text {
  font-size: 14px;
}

.box-card {
  margin-top: 7px;
}
</style>
