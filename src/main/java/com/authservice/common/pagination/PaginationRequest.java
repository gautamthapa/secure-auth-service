package com.authservice.common.pagination;

import com.cfo.common.constants.CommonConstants;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

import static com.cfo.common.constants.CommonConstants.*;


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

    @Min(value = 1, message = CommonConstants.PAGE_SIZE_MIN_VALUE)
    private int pageNo = DEFAULT_PAGE_NO;

    @Min(value = 10, message = CommonConstants.PAGE_NO_MIN_VALUE)
    private int pageSize = DEFAULT_PAGE_SIZE;

    private String sortBy = DEFAULT_SORT_BY;

    private String sortDirection = DEFAULT_SORT_DIRECTION;

    private String searchValue = DEFAULT_SEARCH_VALUE;

    private String searchBy;

    private String filterBy;

    private String filterValue;

    private Map<String, Object> searchByKeyValues;

    private Map<String, Object> filterKeyValues;

    private boolean downloadFile;
    private String startDate;
    private String endDate;
    private String productName;
}
