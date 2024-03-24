package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.interfaceadapters.presenters.dto.Dto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Presenter<T extends Serializable, D extends Dto> {

    D convert(T document);

    T convert(D dto) throws ValidationsException;

    default PagedResponse<D> convertDocuments(Page<T> page) {
        PagedResponse<D> paged = new PagedResponse<>();

        paged.setPage(new Pagination(page.getNumber(), page.getSize(), page.getTotalPages()));

        List<D> data = convertDocuments(page.get().toList());

        paged.setData(data);

        return paged;
    }

    default List<D> convertDocuments(List<T> documents) {
        if (documents == null) {
            return Collections.emptyList();
        }

        return documents.stream().map(this::convert).toList();
    }

    default List<T> convert(List<D> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }

        List<T> documents = new ArrayList<>();

        dtos.forEach(item -> {
            try {
                documents.add(convert(item));
            } catch (ValidationsException e) {
                throw new RuntimeException(e);
            }
        });

        return documents;
    }
}