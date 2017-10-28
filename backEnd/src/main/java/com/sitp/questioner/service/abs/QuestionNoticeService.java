package com.sitp.questioner.service.abs;

import com.sitp.questioner.entity.Answer;
import com.sitp.questioner.entity.QuestionNotice;

import java.util.List;

/**
 * Created by qi on 2017/10/26.
 */
public interface QuestionNoticeService {
     void createNoticeAfterAnswerQuestion(Answer answer);

     void markAsHasRead(Long questionNoticeId);

     List<QuestionNotice> getUserNotReadNotice(Long accountId);
}
