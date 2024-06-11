

const getDefaultState = () => {
    return {
      id: '',
      title: '',
      body: '',
      author: '',
      time: '',
    }
  }

const state = getDefaultState()

const mutations = {
    RESET_STATE: (state) => {
        Object.assign(state, getDefaultState())
      },
      SET_ID: (state, id) => {
        state.id = id
      },
      SET_TITLE: (state, title) => {
        state.title = title
      },
      SET_AUTHOR: (state, author) => {
        state.author = author
      },
      SET_BODY: (state, body) => {
        state.body = body
      },
      SET_TIME: (state, time) => {
        state.time = time
  }
}

const actions = {

    getPost({ commit }, id) {
        return new Promise((resolve, reject) => {
            getPost({ id:id }).then(response =>{
                const { post }= response
                commit('SET_TITLE', post.title)
                commit('SET_AUTHOR', post.author)
                commit('SET_BODY', post.body)
                commit('SET_TIME', post.time)
                resolve()
            }).catch(error => {
                reject(error)
                return('loading error') // for debug
          })
        }) 
    },

}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
