package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LicenseListResponse {
    private ArrayList<ResultJsonElement> result;

    @Data
    public class ResultJsonElement{
        String id;
        String title;
    }
}
