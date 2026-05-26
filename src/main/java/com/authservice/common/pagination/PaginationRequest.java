package com.authservice.common.pagination;

import com.authservice.common.CommonConstants;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/***
 * PaginationRequest is used to hold the pagination related information.
 *
 * @author Gautam Thapa
 * @version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

    @Min(value = 1, message = CommonConstants.PAGE_NO_MIN_VALUE)
    private int pageNo = CommonConstants.DEFAULT_PAGE_NO;

    @Min(value = 10, message = CommonConstants.PAGE_SIZE_MIN_VALUE)
    private int pageSize = CommonConstants.DEFAULT_PAGE_SIZE;

    private String sortBy = CommonConstants.DEFAULT_SORT_BY;

    private String sortDirection = CommonConstants.DEFAULT_SORT_DIRECTION;

    private String searchValue = CommonConstants.DEFAULT_SEARCH_VALUE;

    private String searchBy;

    private String filterBy;

    private String filterValue;
}
