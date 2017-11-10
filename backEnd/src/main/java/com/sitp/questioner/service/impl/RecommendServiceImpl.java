package com.sitp.questioner.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sitp.questioner.constants.QuestionerConstants;
import com.sitp.questioner.entity.BrowseHistory;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.repository.BrowseHistoryRepository;
import com.sitp.questioner.repository.QuestionRepository;
import com.sitp.questioner.service.abs.RecommendService;
import com.sitp.questioner.util.PageableBuilder;
import org.apache.mahout.cf.taste.common.NoSuchUserException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/11/7 下午8:59
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private BrowseHistoryRepository browseHistoryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    private MysqlDataSource mysqlDataSource;
    public static Random random = new Random();
    @Override
    public void broweRecord(Long accountid, Long questionId) {
        //查询问题的类型id
        Long questionTypeId = questionRepository.findOne(questionId).getQuestionType().getId();
        //记录浏览
        BrowseHistory browseHistory = browseHistoryRepository.findByUseridAndItemid(accountid,questionTypeId);
        if(browseHistory == null){
            browseHistory = new BrowseHistory();
            browseHistory.setUserid(accountid);
            browseHistory.setItemid(questionTypeId);
            browseHistory.setPreference(1D);
        }
        else {
            browseHistory.setPreference(browseHistory.getPreference()+1);
        }
        browseHistoryRepository.save(browseHistory);
    }

    @Override
    public List<Question> recommend(Long accountid) throws TasteException {
        if(mysqlDataSource == null){
            mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setUrl(QuestionerConstants.URL);
            mysqlDataSource.setUser(QuestionerConstants.ACCOUNT);
            mysqlDataSource.setPassword(QuestionerConstants.PWD);
        }
        //执行推荐算法
        DataModel dataModel = new MySQLJDBCDataModel(mysqlDataSource,QuestionerConstants.RECOMMEND_TABLE_NAME,QuestionerConstants.USER_ID,QuestionerConstants.ITEM_ID,QuestionerConstants.PREFERENCE,QuestionerConstants.TIMESTAMP);
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(QuestionerConstants.THRESHOLD, similarity, dataModel);
        UserBasedRecommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
        List<RecommendedItem> recommendations = null;
        try {
            //算法只给出用户从未浏览过的问题类型的预估喜欢程度
            recommendations = recommender.recommend(accountid, QuestionerConstants.RECOMMEND_MAX_NUMBER);
        }catch (NoSuchUserException e){
            recommendations = Lists.newArrayList();
        }
        //将预估喜欢程度和已有的喜欢程度和在一起，排序，取出喜欢程度高的作为问题推荐
        List<BrowseHistory> browseHistoryList = browseHistoryRepository.findByUserid(accountid);
        for(RecommendedItem recommendedItem : recommendations){
            BrowseHistory browseHistory = new BrowseHistory();
            browseHistory.setUserid(accountid);
            browseHistory.setItemid(recommendedItem.getItemID());
            browseHistory.setPreference(new Double(recommendedItem.getValue()));
            browseHistoryList.add(browseHistory);
        }
        Collections.sort(browseHistoryList, new Comparator<BrowseHistory>() {
            @Override
            public int compare(BrowseHistory o1, BrowseHistory o2) {
                return o1.getPreference().compareTo(o2.getPreference());
            }
        });

        List<Question> questionList = Lists.newArrayList();
        for(int i = 0; i < browseHistoryList.size()&&i < QuestionerConstants.RECOMMEND_SIZE; i++){
            questionList.add(getRandomQuestionByType(browseHistoryList.get(i).getItemid()));
        }
        return questionList;
    }

    /**
     * 获取指定类型的一个随机问题
     * @param typeid
     * @return
     */
    private Question getRandomQuestionByType(Long typeid){
        Pageable pageable = new PageableBuilder().setCurrentPage(0)
            .setPageSize(10000).setSortParam("id")
            .setDirection(Sort.Direction.DESC).buildPage();
        List<Question> questionList = questionRepository.getQuestionByPageAndType(typeid,pageable).getContent();
        return questionList.get(random.nextInt(questionList.size()));
    }

}
