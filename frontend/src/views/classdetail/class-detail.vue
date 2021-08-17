<template>
  <gallery :classNo="this.$route.query.classNo" style="margin-bottom: 40px;" />
  <div class="content">
    <class-content
      :classNo="this.$route.query.classNo"
      style="min-width: 100px; width: 68%; margin-right: 20px"
    />
    <class-summary
      :classNo="this.$route.query.classNo"
      style="min-width: 250px; width: 30%;"
    />
  </div>
</template>

<script>
import Gallery from "./components/gallery.vue";
import ClassContent from "./components/class-content.vue";
import ClassSummary from "./components/class-summary.vue";

export default {
  name: "ClassDetail",
  components: {
    Gallery,
    ClassContent,
    ClassSummary
  },
  data() {
    return {};
  }
};
</script>

<style>
.content {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  /* flex-wrap: wrap; */
}
</style>
