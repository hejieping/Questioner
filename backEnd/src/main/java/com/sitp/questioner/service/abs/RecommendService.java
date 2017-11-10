package com.sitp.questioner.service.abs;

import java.util.List;

import com.sitp.questioner.entity.Question;
import org.apache.mahout.cf.taste.common.TasteException;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/11/7 下午8:59
 */
public interface RecommendService {
    void broweRecord(Long accountid,Long questionId);
    List<Question> recommend(Long accountid) throws TasteException;

}
