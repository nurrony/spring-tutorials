package info.nurrony.tutorials.spring.overriderest.dto;

import java.util.List;

public record Page<T>(Integer offset, Integer limit, Integer totalResult, List<T> items) {

    public static final int RESULTS_PER_PAGE = 5;

    public Page {
        if (limit == null || limit == 0)
            limit = RESULTS_PER_PAGE;
    }

    public Integer totalPage() {
        return ((totalResult - 1) / limit) + 1;
    }

    public List<T> items() {
        return items;
    }
}
