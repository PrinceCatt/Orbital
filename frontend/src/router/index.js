import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/view/login/login.vue";
import Logout from "@/view/logout";

Vue.use(VueRouter)

const router = new VueRouter({
    routes:[

        {path:'/login',  component: Login},
        {path:'/logout', component: Logout}
    ]
})

export default router