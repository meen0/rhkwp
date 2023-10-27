package com.ohgiraffers.springdatajpa.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString@NoArgsConstructor
public class CategoryDTO {
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;
}
