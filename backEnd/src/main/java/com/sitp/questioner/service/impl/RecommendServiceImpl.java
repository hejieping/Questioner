package com.sitp.questioner.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sitp.questioner.constants.QuestionerConstants;
import com.sitp.questioner.entity.BrowseHistory;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.entity.QuestionType;
import com.sitp.questioner.repository.BrowseHistoryRepository;
import com.sitp.questioner.repository.QuestionRepository;
import com.sitp.questioner.repository.QuestionTypeRepository;
import com.sitp.questioner.service.abs.RecommendService;
import com.sitp.questioner.util.PageableBuilder;
import com.sitp.questioner.viewmodel.Preference;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);
    private static DataModel dataModel;
    private static UserSimilarity similarity;
    private static UserNeighborhood neighborhood;
    private static UserBasedRecommender recommender;
    private static MysqlDataSource mysqlDataSource;
    static {
        refresh();
    }
    @Autowired
    private BrowseHistoryRepository browseHistoryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    public static Random random = new Random();
    @Override
    public void browseRecord(Long accountid, Long questionId) {
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
    public List<Question> recommend(Long accountId,int questionSize) throws TasteException {

        List<BrowseHistory> browseHistoryList = getBrowseHistory(accountId);
        List<Question> questionList = Lists.newArrayList();
        for(int i = 0; i < browseHistoryList.size()&&i < questionSize; i++){
            questionList.add(getRandomQuestionByType(browseHistoryList.get(i).getItemid()));
        }
        return questionList;
    }

    @Override
    public List<Preference> getPreferences(Long accountId, int preferenceSize) throws TasteException {
        // here it will use machine learning Algorithm to predict the user preference, so it'll cost much time,
        // in the future the machine learning Algorithm can be skipped here for it's to slow
     //   List<BrowseHistory> browseHistoryList = getBrowseHistory(accountId);
        // now we don't want to use recommend data just to get user preference
        List<BrowseHistory> browseHistoryList = getBrowseHistory(accountId, false);
        List<Long> typeIdList ;
        List<Preference> preferenceList;
        typeIdList = browseHistoryList.stream().limit(preferenceSize).map(BrowseHistory::getItemid).collect(Collectors.toList());
        List<QuestionType> questionTypeList = questionTypeRepository.findByIdIn(typeIdList);
        Map<Long,QuestionType> questionTypeMap = Maps.newHashMap();
        for(QuestionType questionType : questionTypeList){
            questionTypeMap.put(questionType.getId(),questionType);
        }
        preferenceList = browseHistoryList.stream().limit(preferenceSize).map((browseHistory) -> {
            Preference preference = new Preference();
            preference.setPreferenceValue(browseHistory.getPreference());
            preference.setSubject(questionTypeMap.get(browseHistory.getItemid()).getSubject());
            preference.setCourse(questionTypeMap.get(browseHistory.getItemid()).getCourse());
            return preference;
        }).collect(Collectors.toList());
        return preferenceList;
    }

    @Override
    public void refreshRecommendSystem() {
        refresh();
    }

    private List<BrowseHistory> getBrowseHistory(Long accountId) throws TasteException {
        return getBrowseHistory(accountId, true);
    }


    private List<BrowseHistory> getBrowseHistory(Long accountId, boolean withRecommend) throws TasteException {

        List<RecommendedItem> recommendations = Lists.newArrayList();
        if (withRecommend) {
            try {
                //算法只给出用户从未浏览过的问题类型的预估喜欢程度
                recommendations = recommender.recommend(accountId, QuestionerConstants.RECOMMEND_MAX_NUMBER);
            }catch (NoSuchUserException e){
                logger.error("Can't recommend question");
            }
        }
        //将预估喜欢程度和已有的喜欢程度和在一起，排序，取出喜欢程度高的作为问题推荐
        List<BrowseHistory> browseHistoryList = browseHistoryRepository.findByUserid(accountId);
        for(RecommendedItem recommendedItem : recommendations){
            BrowseHistory browseHistory = new BrowseHistory();
            browseHistory.setUserid(accountId);
            browseHistory.setItemid(recommendedItem.getItemID());
            browseHistory.setPreference((double) recommendedItem.getValue());
            browseHistoryList.add(browseHistory);
        }
        browseHistoryList.sort(Comparator.comparing(BrowseHistory::getPreference).reversed());
        return browseHistoryList;
    }

    /**
     * 获取指定类型的一个随机问题
     * @param typeid
     * @return
     */
    private Question getRandomQuestionByType(Long typeid){
        List<Long> questionIdList = questionRepository.getAllQuestionIdByQuestionTypeId(typeid);
        Long questionId = questionIdList.get(random.nextInt(questionIdList.size()));
        return questionRepository.findOne(questionId);
    }
    public static void refresh(){
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(QuestionerConstants.URL);
        mysqlDataSource.setUser(QuestionerConstants.ACCOUNT);
        mysqlDataSource.setPassword(QuestionerConstants.PWD);
        //执行推荐算法
        dataModel = new MySQLJDBCDataModel(mysqlDataSource,QuestionerConstants.RECOMMEND_TABLE_NAME,QuestionerConstants.USER_ID,QuestionerConstants.ITEM_ID,QuestionerConstants.PREFERENCE,QuestionerConstants.TIMESTAMP);

        similarity = null;
        try {
            similarity = new PearsonCorrelationSimilarity(dataModel);
        } catch (TasteException e) {
            e.printStackTrace();
        }
        neighborhood = new ThresholdUserNeighborhood(QuestionerConstants.THRESHOLD, similarity, dataModel);
        recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
    }

}
