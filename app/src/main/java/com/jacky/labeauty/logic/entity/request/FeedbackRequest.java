package com.jacky.labeauty.logic.entity.request;

public class FeedbackRequest {

//        "content": "登录不了", // 反馈内容
//        "category": "功能异常", // 反馈类别
//        "contact": "13500000000" // 选填。联系方式

    private String content;
    private String category;
    private String contact;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
