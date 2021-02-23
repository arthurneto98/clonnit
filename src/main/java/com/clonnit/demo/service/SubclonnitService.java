package com.clonnit.demo.service;

import com.clonnit.demo.dto.SubclonnitDto;
import com.clonnit.demo.model.Subclonnit;
import com.clonnit.demo.repository.SubclonnitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubclonnitService {
    private final SubclonnitRepository subclonnitRepository;
    private final DtoService dtoService;

    @Transactional
    public SubclonnitDto saveSubclonnit(SubclonnitDto dto) {
        Subclonnit subclonnit = dtoService.mapDtoToSubclonnit(dto);

        subclonnitRepository.save(subclonnit);
        dto.setId(subclonnit.getId());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<SubclonnitDto> subclonnitList() {
        return subclonnitRepository.findAll().stream().map(dtoService::mapSubclonnitToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubclonnitDto getSubclonnit(Integer id) {
        Optional<Subclonnit> subclonnit = subclonnitRepository.findById(id);
        return subclonnit.map(dtoService::mapSubclonnitToDto).orElse(null);
    }
}