package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrganizationShowResponse {
    private ResultJsonElement result;

    @Data
    public class ResultJsonElement{
        Integer package_count;
    }
}
