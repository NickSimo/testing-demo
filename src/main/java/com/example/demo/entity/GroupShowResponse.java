package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupShowResponse {
    private ResultJsonElement result;

    @Data
    public class ResultJsonElement{
        Integer package_count;
    }
}
