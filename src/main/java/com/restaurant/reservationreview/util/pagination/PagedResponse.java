package com.restaurant.reservationreview.util.pagination;

import com.restaurant.reservationreview.util.dto.Dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PagedResponse<T extends Dto> {

    private Pagination page;

    private List<T> data;
}