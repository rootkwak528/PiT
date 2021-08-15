<template>
  <div class="">

  </div>
</template>
<script>
import { reactive } from '@vue/reactivity';
import { useStore } from 'vuex'
export default {
  name: "EventDetailView",
  props:{
    eventNo: String
  },
  setup(props) {
    const store = useStore();
    const state = reactive({

    })

    store.dispatch("root/getEventDetail", {eventNo: props.eventNo})
    .then(function(result){
      alert(" 이벤트 상세 정보 조회 성공 ");

    })
    .catch(function(err) {
      alert(err.response.data.message);
    })
  }
}
</script>
<style>

</style>
