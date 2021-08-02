import { createWebHistory, createRouter } from "vue-router";
import Main from "@/views/main/main.vue";
import Event from "@/views/event.vue";
import SearchResult from "@/views/search-result";
import ClassDetail from "@/views/classdetail/class-detail";
import MyClass from "@/views/mypage/myclass";
import RegisterClass from "@/views/mypage/registerClass";

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main
  },
  {
    path: "/event",
    name: "Event",
    component: Event
  },
  {
    path: "/searchresult",
    name: "SearchResult",
    component: SearchResult
  },
  {
    path: "/classdetail",
    name: "ClassDetail",
    component: ClassDetail
  },
  {
    path: "/myclass",
    name: "MyClass",
    component: MyClass
  },
  {
    path: "/rgstClass",
    name: "RegisterClass",
    component: RegisterClass
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
