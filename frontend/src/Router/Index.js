import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/view/login";
import Logout from "@/view/logout";
import Nav from "@/components/Navigation/Nav.vue";

Vue.use(VueRouter)

const router = new VueRouter({
    routes:[
        {path:'/',  redirect: "nav"},
        {path:'/nav',  component: Nav},
        {path:'/login',  component: Login},
        {path:'/logout', component: Logout}
    ]
})

export default router