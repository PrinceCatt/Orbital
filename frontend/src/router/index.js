import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/view/login";
import Profile from '@/view/my/profile'
import Register from '@/view/register'
import Discovery from '@/view/discovery'


Vue.use(VueRouter)

const router = new VueRouter({
    routes:[
        {path:'/', redirect: '/discovery'},
        {path:'/register', component: Register},
        {path:'/profile', component: Profile},
        {path:'/login',  component: Login},
        {path:'/discovery', component: Discovery},
    ]
})

export default router