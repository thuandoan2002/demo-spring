package jmaster.io.project2.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PageDTO<T> {
    private int totalPages;
    private int totalElements;
    private List<T> contents;
}
