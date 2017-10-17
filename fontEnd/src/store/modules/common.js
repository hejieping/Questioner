/**
 * Created by qi on 2017/10/16.
 */
const common = {
  state: {
    inQuestionListPage: false
  },
  mutations: {
    LEAVE_QUESTIONLIST_PAGE: (state) => {
      state.inQuestionListPage = false
    },
    ENTER_QUESTIONLIST_PAGE: (state) => {
      state.inQuestionListPage = true
    }
  },
  actions: {
    enter_questionList_page ({commit}) {
      commit('ENTER_QUESTIONLIST_PAGE')
    },
    leave_questionList_page ({commit}) {
      commit('LEAVE_QUESTIONLIST_PAGE')
    }
  }
}
export default common
