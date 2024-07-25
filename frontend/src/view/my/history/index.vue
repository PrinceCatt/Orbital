<template>
    <span>
        <el-button @click="add(1)"> Add
        </el-button>
    </span>
</template>

<script>
import { getHistory, addHistory } from '@/api/user';

export default {

data() {
    return {
        History: {},
    }
},

created(){
    this.getData()
},

methods : {
    getData(){
        new Promise((resolve, reject) => {
          getHistory().then(res => {
            this.History = res.data.history
            console.log(this.History)
            resolve()
          }).catch(err => {
            reject(err)
            return "Error in loading History";
          })
        })
    },

    add(postId){
        addHistory(postId)
    }

}

}


</script>