package com.suryansh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditModel {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPost;
}
