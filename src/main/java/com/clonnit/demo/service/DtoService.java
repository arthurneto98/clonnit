package com.clonnit.demo.service;

import com.clonnit.demo.dto.SubclonnitDto;
import com.clonnit.demo.model.Subclonnit;

public class DtoService {
//    AuthResponseDto
//    LoginRequestDto
//    RegisterRequestDto

    public SubclonnitDto mapSubclonnitToDto(Subclonnit subclonnit) {
        return SubclonnitDto.builder()
                .id(subclonnit.getId())
                .name(subclonnit.getName())
                .description(subclonnit.getDescription())
                .postNumber(subclonnit.getPostList().size())
                .build();
    }

    public Subclonnit mapDtoToSubclonnit(SubclonnitDto dto) {
        return Subclonnit.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
