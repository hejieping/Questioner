package com.sitp.questioner.service.abs;

import java.util.List;

import com.sitp.questioner.entity.Question;
import com.sitp.questioner.viewmodel.Preference;
import org.apache.mahout.cf.taste.common.TasteException;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/11/7 下午8:59
 */
public interface RecommendService {
    void browseRecord(Long accountid, Long questionId);
    List<Question> recommend(Long accountid,int questionSize) throws TasteException;
    List<Preference> getPreferences(Long accountId,int preferenceSize) throws TasteException;
    void refreshRecommendSystem();
}
