package com.clonnit.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private Integer parentCommentId;
    private ArrayList<CommentDto> childComments;
    private String userUsername;
    private String content;
    private LocalDateTime created;
}