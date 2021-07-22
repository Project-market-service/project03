import Vue from "vue"
import VueRouter from "vue-router"
import MainView from "../views/MainView.vue"
import Login from "../views/Login.vue"
import MyPage from '@/views/MyPage.vue'
import Test from '@/views/Test.vue'
import ChatView from "../views/ChatView.vue"
import PostFile from "@/views/PostFile.vue"

Vue.use(VueRouter);

const router = new VueRouter({
    mode: "history",
    routes: [
        { path: "/", component: MainView },
        { path: "/login", component: Login },
        { path: "/mypage", component: MyPage },
        { path: "/test", component: Test },
        { path: "/chat", component: ChatView},
        { path: "/post", component: PostFile},
    ]
})

export default router