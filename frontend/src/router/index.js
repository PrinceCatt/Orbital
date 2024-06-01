import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/view/login";
import Logout from "@/view/logout";
import Profile from '@/view/my/profile'

Vue.use(VueRouter)

const router = new VueRouter({
    routes:[
        {path:'/profile', component: Profile},
        {path:'/login',  component: Login},
        {path:'/logout', component: Logout},
    ]
})

export default router