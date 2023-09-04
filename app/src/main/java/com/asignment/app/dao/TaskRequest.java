package com.asignment.app.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskRequest {

    @JsonProperty
    private String title;
    @JsonProperty
    private String description;
    @JsonProperty
    private boolean completed;
//    @JsonProperty
//    private long user_id;
    @JsonProperty
    private LocalDateTime createdAt;
}
