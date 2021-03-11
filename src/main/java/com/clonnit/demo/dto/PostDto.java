package com.clonnit.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    private Integer id;
    private String title;
    private String url;
    private String content;
    private Integer voteCount;
    private String created;
    private Integer userId;
    private Integer subclonnitId;
}
