package com.sitp.questioner.service.impl;

import com.sitp.questioner.config.NoticeWebSocket;
import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Answer;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.entity.QuestionNotice;
import com.sitp.questioner.repository.AccountRepository;
import com.sitp.questioner.repository.QuestionNoticeRepository;
import com.sitp.questioner.repository.QuestionRepository;
import com.sitp.questioner.service.abs.QuestionNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@Service
public class QuestionNoticeServiceImpl implements QuestionNoticeService{

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private QuestionNoticeRepository questionNoticeRepository;

    @Autowired
    private QuestionRepository questionRepository ;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public void createNoticeAfterAnswerQuestion(Answer answer) {
        Long questionId = answer.getQuestion().getId();
        Question question = questionRepository.findOne(questionId);

        Account questionPublisher = accountRepository.findOne(question.getPublisher().getId());
        List<Account> accounts = questionRepository.getQuestionFollowers(questionId);
        // first remove the question publisher to avoid send the notification for two times
        accounts = accounts.stream().filter(account -> !account.getId().equals(questionPublisher.getId())).collect(Collectors.toList());
        accounts.add(questionPublisher);

        Account answerUser = accountRepository.findOne(answer.getAccount().getId());
        answerUser.setRoles(new ArrayList<>());
        Account tempAccount = new Account();
        tempAccount.setUsername(answerUser.getUsername());

        Question tempQuestion = new Question();
        tempQuestion.setQuestionTitle(question.getQuestionTitle());
        tempQuestion.setId(questionId);

        accounts.forEach((account) -> executorService.submit(()->{
            QuestionNotice questionNotice = new QuestionNotice();
            questionNotice.setAnswer(answer);
            questionNotice.setAccount(account);
            questionNotice.setQuestion(question);
            questionNoticeRepository.save(questionNotice);
            questionNotice.setQuestion(tempQuestion);
            questionNotice.setAccount(null);
            questionNotice.getAnswer().setAccount(tempAccount);
            NoticeWebSocket.pushMessageToOneUser(account.getId().toString(), questionNotice);
        }));
    }


    @Override
    public void markAsHasRead(Long questionNoticeId) {
        QuestionNotice questionNotice = questionNoticeRepository.findOne(questionNoticeId);
        if (questionNotice != null) {
            questionNotice.setHasRead(true);
            questionNoticeRepository.save(questionNotice);
        }
    }

    @Override
    public List<QuestionNotice> getUserNotReadNotice(Long accountId) {
        return questionNoticeRepository.getByAccount_IdAndHasRead(accountId, false);
    }
}
