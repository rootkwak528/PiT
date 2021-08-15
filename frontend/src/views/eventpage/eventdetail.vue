<template>
  <div class="content-wrapper">
    <event-detail-view :eventNo="this.$route.query.eventNo"/>
  </div>
</template>

<script>
import EventDetailView from "@/views/eventpage/eventdetailview"

export default {
  name: "EventDetail",
  components:{
    EventDetailView
  }
}
</script>
<style>

</style>
