/**
 * Created by qi on 2017/10/16.
 */
const common = {
  state: {
    inQuestionListPage: false,
    questionType: null
  },
  mutations: {
    LEAVE_QUESTIONLIST_PAGE: (state) => {
      state.inQuestionListPage = false
    },
    ENTER_QUESTIONLIST_PAGE: (state) => {
      state.inQuestionListPage = true
    },
    INIT_QUESTION_TYPE: (state, questionType) => {
      state.questionType = questionType
    }
  },
  actions: {
    enter_questionList_page ({commit}) {
      commit('ENTER_QUESTIONLIST_PAGE')
    },
    leave_questionList_page ({commit}) {
      commit('LEAVE_QUESTIONLIST_PAGE')
    },
    init_question_type ({commit}, questionType) {
      commit('INIT_QUESTION_TYPE', questionType)
    }
  }
}
export default common
