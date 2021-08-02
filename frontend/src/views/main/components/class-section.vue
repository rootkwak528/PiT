<template>
  <el-row style="margin-top: 20px">
    <el-col :span="6" v-for="o in classContent" :key="o">
      <router-link
        :to="`/classdetail?classid=${o.classid}`"
        style="text-decoration: none; color: inherit;"
      >
        <el-card :body-style="{ padding: '0px' }">
          <img :src="o.thumbnail" class="image" />
          <div style="padding: 14px; height: 100px; text-overflow: ellipsis;">
            <p>{{ o.title }}</p>
            <div class="bottom">
              월 {{ o.price }}원 (5개월)<br />
              <el-button type="text" style="color: #00C0D4">선물하기</el-button>
            </div>
          </div>
        </el-card>
      </router-link>
    </el-col>
    <el-col :span="6" v-for="o in classContent" :key="o" @click="clickClass">
      <router-link
        :to="`/classdetail?classid=${o.classid}`"
        style="text-decoration: none; color: inherit;"
      >
        <el-card :body-style="{ padding: '0px' }">
          <img :src="o.thumbnail" class="image" />
          <div style="padding: 14px; height: 100px; text-overflow: ellipsis;">
            <p>{{ o.title }}</p>
            <div class="bottom">
              월 {{ o.price }}원 (5개월)<br />
              <el-button type="text" style="color: #00C0D4">선물하기</el-button>
            </div>
          </div>
        </el-card>
      </router-link>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "ClassSection",
  data() {
    return {
      classContent: [
        {
          thumbnail:
            "https://cdn.class101.net/images/119a8385-66ea-471a-a4a2-bee3e519c4da/375xauto.webp",
          title: `홈트계의 끝판왕 '힙으뜸'`,
          price: `46,800`,
          classid: `1111`
        },
        {
          thumbnail:
            "http://laza.jalbum.net/Watermark%20Demo/slides/P8220329.jpg",
          title: `SNPE 바디리셋 프로젝트. 척추운동으로 밸런스 회복하기!`,
          price: `46,800`,
          classid: `2222`
        },
        {
          thumbnail:
            "https://cdn57.androidauthority.net/wp-content/uploads/2020/04/oneplus-8-pro-ultra-wide-sample-twitter-1.jpg",
          title: `천재 사업가 '대학생김머신'의 무엇이든 팔 수 있는 쇼핑몰 창업가이드`,
          price: `46,800`,
          classid: `3333`
        },
        {
          thumbnail:
            "https://cdn.mos.cms.futurecdn.net/aehTwFrsn44UTg9T3shPEi.jpg",
          title: `이성과 감성을 넘나들다. 시선을 사로잡는 브랜드 디자인`,
          price: `46,800`,
          classid: `4444`
        }
      ]
    };
  },
  setup() {}
};
</script>

<style>
p {
  font-size: 15px;
  margin: 0;
  /* height: 50px; */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.bottom {
  font-size: 14px;
  width: 100%;
  height: 40px;
  margin-top: 20px;
  line-height: 12px;
  /* display: flex;
  justify-content: space-between;
  align-items: center;
  text-overflow: ellipsis;
  overflow: hidden; */
}

.image {
  width: 100%;
  height: 207px;
  display: block;
}
</style>
