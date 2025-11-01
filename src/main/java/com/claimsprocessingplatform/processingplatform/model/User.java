package com.claimsprocessingplatform.processingplatform.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Field("name")
    private String fullName;

    @Indexed(unique = true)
    private String email;

    private String phonenumber;

    // Constructor for testing
    public User(String id, String email, String fullName, String phonenumber) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phonenumber = phonenumber;
    }
}