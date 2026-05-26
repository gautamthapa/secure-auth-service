package com.authservice.common.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FilterBy {
    private Double productMinPrice;
    private Double productMaxPrice;
    private List<Long> brandIds;
    private List<Long> categoryIds;
    private List<Long> subCategoryIds;
    private List<String> sizes;
    private List<String> colorNames;
    private List<String> genders;
}
