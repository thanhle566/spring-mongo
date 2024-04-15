package com.mongo_admin.spring.data.mongodb.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "creative")
@Getter
@Setter
@Builder
public class Creative {

    @Id
    private String id;

    private String name;
    private String title;
    private String description;
}
