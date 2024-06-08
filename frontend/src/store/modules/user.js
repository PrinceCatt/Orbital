import { register, login, logout, getInfo, updateName, updateAvatar } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
// import defaultAvatar from '@/assets/defaultAvatar'



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
                return('Entered wrong email or password')
          })
        }) 
    },


    register({ commit }, userInfo) {
      const { email, password, name} = userInfo
      return new Promise((resolve, reject) => {
          register({ email: email, password: password , name: name}).then(response =>{
              commit('SET_NAME', name)
              resolve()
          }).catch(error => {
              reject(error)
              return('Invalid')
              
        })
      }) 
    },

    updateName({ commit }, name){
      return new Promise((resolve,reject) => {
        updateName(name).then(response =>{
          commit('SET_NAME', name)
          resolve()
      }).catch(error => {
        reject(error)
        return('invalid change')
      })
      })
    },

    updateAvatar({ commit }, avatar){
      return new Promise((resolve,reject) => {
        updateAvatar(avatar).then(response =>{
          commit('SET_AVATAR', avatar)
          resolve()
      }).catch(error => {
        reject(error)
        return('invalid change')
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
                if(avatar != null){
                  commit('SET_AVATAR', avatar)
                }
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
