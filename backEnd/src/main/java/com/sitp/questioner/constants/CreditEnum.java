package com.sitp.questioner.constants;

/**
 * 增加声望值的枚举
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/10/23 上午10:27
 */
public enum CreditEnum {
    ACCEPT_ANSWER("acceptAnswer",15),
    FEED_BACK_GOOD("goodFeedBack",2),
    ANSWER_QUESTION("answerQuestion",1),
    FEED_BACK_BAD("badFeedBack",-1),
    ;
    private String type;
    private int creditValue;
    CreditEnum(String type,int creditValue){
        this.setCreditValue(creditValue);
        this.setType(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(int creditValue) {
        this.creditValue = creditValue;
    }


    public static CreditEnum typeOf(String type){
        if(type != null){
            for(CreditEnum creditEnum : CreditEnum.values()){
                if(creditEnum.getType().equals(type)){
                    return creditEnum;
                }
            }
        }
        return null;
    }
}
