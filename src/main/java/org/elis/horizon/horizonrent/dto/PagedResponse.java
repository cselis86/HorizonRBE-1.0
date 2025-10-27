package org.elis.horizon.horizonrent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean first;
    private boolean last;

    public static <T> PagedResponse<T> of(List<T> content, long totalElements,
                                           int page, int size) {
        PagedResponse<T> response = new PagedResponse<>();
        response.content = content;
        response.totalElements = totalElements;
        response.pageSize = size;
        response.currentPage = page;
        response.totalPages = (int) Math.ceil((double) totalElements / size);
        response.first = page == 0;
        response.last = page >= response.totalPages - 1;
        return response;
    }
}