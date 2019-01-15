import { createCourse, fetchList } from '@/api/course'

const course = {
  state: {
    list: ''
  },

  mutations: {
    SET_CODE: (state, list) => {
      state.list = list
    }
  },

  actions: {
    // 创建课程
    CreateCourse({ commit }, courseInfo) {
      return new Promise((resolve, reject) => {
        createCourse(courseInfo).then(response => {
          const data = response.content
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },
    FetchList({ commit }) {
      return new Promise((resolve, reject) => {
        fetchList().then(response => {
          const data = response.content
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default course
