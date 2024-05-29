import VueRouter from "vue-router";
import Vue from "vue";
import Login from "@/components/LoginAndRegister/Login.vue";
import Nav from "@/components/Navigation/Nav.vue";

Vue.use(VueRouter)

const router = new VueRouter({
    routes:[
        {path:'/',  redirect: "nav"},
        {path:'/nav',  component: Nav},
        {path:'/login',  component: Login},
    ]
})

export default router