<template>
  <gallery style="margin-bottom: 40px;" />
  <div class="content">
    <class-content style="width: 68%" />
    <class-summary :classid="this.$route.query.classid" />
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
  justify-content: space-between;
}
</style>
