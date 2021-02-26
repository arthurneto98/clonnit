package com.clonnit.demo.controller;

import com.clonnit.demo.dto.SubclonnitDto;
import com.clonnit.demo.service.SubclonnitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/subclonnit")
@AllArgsConstructor
@Slf4j
public class SubclonnitController {
    private final SubclonnitService subclonnitService;

    @PostMapping
    public ResponseEntity<SubclonnitDto> create(@RequestBody SubclonnitDto subclonnit) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subclonnitService.saveSubclonnit(subclonnit));
    }

    @GetMapping
    public ResponseEntity<List<SubclonnitDto>> list() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subclonnitService.listSubclonnit());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubclonnitDto> getById(@PathVariable Integer id) {
        SubclonnitDto dto = subclonnitService.getSubclonnit(id);
        return dto != null ?
                ResponseEntity.status(HttpStatus.OK).body(dto) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}