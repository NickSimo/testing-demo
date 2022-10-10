package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrganizationListResponse {
    private List<String> result;
}
