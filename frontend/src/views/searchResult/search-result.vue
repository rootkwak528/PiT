<template>
  <h1>입력한 검색어 : {{ this.$route.query.word }}</h1>
  <search-class-section />
</template>

<script>
import SearchClassSection from "@/views/searchResult/components/search-class-section"

export default {
  name: "SearchResult",
  components: {
    SearchClassSection
  },

};
</script>

<style></style>
