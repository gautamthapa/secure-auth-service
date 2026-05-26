package com.authservice.common.pagination;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchBy {
    private Long brandId;
    private Long categoryId;
    private Long subCategoryId;
    private Long productId;
    private String type;
}
