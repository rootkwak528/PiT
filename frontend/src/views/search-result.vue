<template>
  <h1>입력한 검색어 : {{ this.$route.query.word }}</h1>
  <el-row style="margin-top: 20px">
    <el-col :span="6" v-for="o in imageURLs" :key="o">
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
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "SearchResult",
  data() {
    return {
      imageURLs: [
        {
          thumbnail:
            "https://cdn.class101.net/images/119a8385-66ea-471a-a4a2-bee3e519c4da/375xauto.webp",
          title: `홈트계의 끝판왕 '힙으뜸'`,
          price: `46,800`
        },
        {
          thumbnail:
            "http://laza.jalbum.net/Watermark%20Demo/slides/P8220329.jpg",
          title: `SNPE 바디리셋 프로젝트. 척추운동으로 밸런스 회복하기!`,
          price: `46,800`
        },
        {
          thumbnail:
            "https://cdn57.androidauthority.net/wp-content/uploads/2020/04/oneplus-8-pro-ultra-wide-sample-twitter-1.jpg",
          title: `천재 사업가 '대학생김머신'의 무엇이든 팔 수 있는 쇼핑몰 창업가이드`,
          price: `46,800`
        },
        {
          thumbnail:
            "https://cdn.mos.cms.futurecdn.net/aehTwFrsn44UTg9T3shPEi.jpg",
          title: `이성과 감성을 넘나들다. 시선을 사로잡는 브랜드 디자인`,
          price: `46,800`
        }
      ]
    };
  }
};
</script>

<style></style>
