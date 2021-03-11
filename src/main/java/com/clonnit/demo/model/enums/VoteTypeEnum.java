package com.clonnit.demo.model.enums;

public enum VoteTypeEnum {
    UPVOTE,
    DOWNVOTE;

    private Integer value;
    static {
        UPVOTE.value = 1;
        DOWNVOTE.value = -1;
    }

    public Integer getValue() {
        return value;
    }
}
