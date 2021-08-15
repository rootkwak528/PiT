<template>
  <div class="content-wrapper">
    {{this.$route.query.eventNo}}
  </div>
</template>

<script>
import { useStore } from "vuex";
import { reactive } from "vue";
import { ElMessage } from "element-plus";

export default {
  name: "EventDetail",
  setup(){
    const store = useStore();
    const state = reactive({
      list : [],
      // eventImage: "",
      // eventContent: "",
      // eventTitle: "",
      // eventStartTime: "",       // 시작일
      // eventEndTime: "",         // 종료일
      // eventRegDate: ""          // 등록일
    });

    let eventNo = document.querySelector(".content-wrapper");
    console.log(eventNo);
    console.log(document.querySelector("#content-wrapper"));

    store
      .dispatch("root/getEventInfoList")
      .then(function(result) {
        state.list = result.data;
      })
      .catch(function(err){
        ElMessage({
          message: err.response.data.message,
          type: "error"
        })
      })


    return{
      state
    }
  }
}
</script>
<style>

</style>
