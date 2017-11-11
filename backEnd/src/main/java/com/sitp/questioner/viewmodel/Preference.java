package com.sitp.questioner.viewmodel;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/11/11 下午2:07
 */
public class Preference {
    private String subject;
    private String course;
    private Double preferenceValue;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getPreferenceValue() {
        return preferenceValue;
    }

    public void setPreferenceValue(Double preferenceValue) {
        this.preferenceValue = preferenceValue;
    }
}
