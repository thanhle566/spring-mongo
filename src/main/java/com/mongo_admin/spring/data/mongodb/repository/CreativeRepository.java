package com.mongo_admin.spring.data.mongodb.repository;

import com.mongo_admin.spring.data.mongodb.model.Creative;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CreativeRepository extends MongoRepository<Creative, String> {
  List<Creative> findByNameContaining(String title);
}
