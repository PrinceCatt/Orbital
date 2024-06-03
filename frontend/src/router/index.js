import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/view/login";
import Logout from "@/view/logout";
import Profile from '@/view/my/profile'
import Register from '@/view/register'

Vue.use(VueRouter)

const router = new VueRouter({
    routes:[
        {path:'/register', component: Register},
        {path:'/profile', component: Profile},
        {path:'/login',  component: Login},
        {path:'/logout', component: Logout}
    ]
})

export default router