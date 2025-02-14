package com.clonnit.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubclonnitDto {
    private Integer id;
    private String name;
    private String description;
    private Integer postNumber;
    private Integer userId;
}