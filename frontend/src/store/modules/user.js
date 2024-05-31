import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'


const getDefaultState = () => {
    return {
      token: getToken(),
      name: '',
      avatar: '',
    }
  }

const state = getDefaultState()

const mutations = {
    RESET_STATE: (state) => {
      Object.assign(state, getDefaultState())
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    }
  }

const actions = {
    // user Login
    login({ commit }, userInfo) {
        const { email, password} = userInfo
        console.log('Till actions') // for debug
        return new Promise((resolve, reject) => {
            login({ email: email, password: password }).then(response =>{
                const { data } = response
                console.log('request success') // for debug
                commit('SET_TOKEN', data.token)
                setToken(data.token)
                resolve()
            }).catch(error => {
                console.log('request failed') // for debug
                reject(error)
            })
        }) 
    },

    // get User Info
    getInfo({ commit, state }){
        return new Promise((resolve, reject) => {
            getInfo(state.token).then(response => {
                const { data } = response

                if (!data) {
                    return reject('Verification failed, please Login again')
                }

                const { name, avatar } = data

                commit('SET_NAME', name)
                commit('SET_AVATAR', avatar)
                resolve(data)
            }).catch(error => {
                reject(error)
            })
        })
    },

    // user Logout （not done yet!）
    //logout({ commit, state }) {
    //    return new Promise
    //},

    // remove Token
    resetToken({ commit }) {
        return new Promise(resolve => {
            removeToken() // remove token first
            commit('RESET_STATE')
            resolve()
        })
    }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
