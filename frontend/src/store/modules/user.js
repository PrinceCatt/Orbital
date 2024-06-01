import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'



const getDefaultState = () => {
    return {
      email: '',
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
    },
    SET_EMAIL: (state, email) => {
      state.email = email
    }
  }

const actions = {
    // user Login
    login({ commit }, userInfo) {
        const { email, password} = userInfo
        return new Promise((resolve, reject) => {
            login({ email: email, password: password }).then(response =>{
                const { data } = response
                commit('SET_TOKEN', data.token)
                setToken(data.token)
                resolve()
            }).catch(error => {
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
                
                const { email, name, avatar } = data
                commit('SET_EMAIL', email)
                commit('SET_NAME', name)
                commit('SET_AVATAR', avatar)
                console.log('userInfo set') // for debug
                resolve(data)
            }).catch(error => {
                reject(error)
            })
        })
    },

    // user Logout 
    logout({ commit, state }) {
        return new Promise((resolve, reject) => {
          logout(state.token).then(() => {
            removeToken() // remove token first
            commit('RESET_STATE')
            resolve()
          }).catch(error => {
            reject(error)
          })
        })
    },

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
