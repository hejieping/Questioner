/**
 * Created by qi on 2017/10/11.
 */
import fetch from '@/utils/fetch'

export function raiseQuestion (questionTitle, questionContent, questionContentTxt) {
  const data = {
    questionTitle: questionTitle,
    questionContent: questionContent,
    questionContentTxt: questionContentTxt
  }
  return fetch({
    url: '/question',
    method: 'post',
    data
  })
}
