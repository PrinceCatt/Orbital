<template>
    
    <div>
        <h3>Page{{ $route.params.id }}</h3>

        <el-button @click="open($route.params.id)"> Open </el-button>
    </div>
</template>


<script>

export default {

    methods: {
        open(id) {
            if(id == 1){
            alert("1")
            }
            else {
                alert("not 1")
            }
        }
      }
}
</script>
