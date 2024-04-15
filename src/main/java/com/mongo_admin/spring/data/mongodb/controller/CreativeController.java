package com.mongo_admin.spring.data.mongodb.controller;

import com.mongo_admin.spring.data.mongodb.model.Creative;
import com.mongo_admin.spring.data.mongodb.repository.CreativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;  
import java.util.Optional;

@RestController
@RequestMapping("/api/creative")
public class CreativeController {

  @Autowired
  CreativeRepository creativeRepository;

  @GetMapping("")
  public ResponseEntity<List<Creative>> getAllCreatives(@RequestParam(required = false) String name) {
    try {
      List<Creative> creatives = new ArrayList<>();

      if (name == null)
        creatives.addAll(creativeRepository.findAll());
      else
        creatives.addAll(creativeRepository.findByNameContaining(name));

      if (creatives.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(creatives, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Creative> getCreativeById(@PathVariable("id") String id) {
    return creativeRepository.findById(id)
            .map(creative -> new ResponseEntity<>(creative, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("")
  public ResponseEntity<Creative> createCreative(@RequestBody Creative creative) {
    try {
      Creative _creative = creativeRepository.save(Creative.builder()
              .name(creative.getName())
              .description(creative.getDescription())
              .title(creative.getTitle())
              .build());
      return new ResponseEntity<>(_creative, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Creative> updateCreative(@PathVariable("id") String id, @RequestBody Creative creative) {
    Optional<Creative> creativeData = creativeRepository.findById(id);

    if (creativeData.isPresent()) {
      Creative _creative = creativeData.get();
      _creative.setName(creative.getName());
      _creative.setTitle(creative.getTitle());
      _creative.setDescription(creative.getDescription());
      return new ResponseEntity<>(creativeRepository.save(_creative), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteCreative(@PathVariable("id") String id) {
    try {
      creativeRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("")
  public ResponseEntity<HttpStatus> deleteAllCreatives() {
    try {
      creativeRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
