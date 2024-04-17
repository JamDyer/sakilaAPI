package com.example.sakilaAPI.dto.output;

import lombok.Data;

import java.util.List;

@Data
public class ActorFilterOutput {
    private List<ActorOutput> actors;
    private int size;
    private int page;
    private long totalElements;
    private int totalPages;
}
