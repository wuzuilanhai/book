package com.biubiu.web;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * Created by Haibiao.Zhang on 2019-03-28 10:44
 */
@Getter
@Setter
@EqualsAndHashCode
public class PagedResponse<T extends Collection> extends Response<T> {

    private Integer pageIndex;

    private Integer pageSize;

    private Integer total;

    public PagedResponse() {
        super();
    }

    public PagedResponse(Long code, String message) {
        super(code, message);
    }

    public PagedResponse(Integer pageIndex, Integer pageSize, Integer total) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
    }

    public PagedResponse(Long code, Integer pageIndex, Integer pageSize, Integer total) {
        super(code);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
    }

    public PagedResponse(Long code, String message, Integer pageIndex, Integer pageSize, Integer total) {
        super(code, message);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
    }

    public PagedResponse(T payload, Integer pageIndex, Integer pageSize, Integer total) {
        super(payload);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
    }

    public PagedResponse(Long code, String message, T payload, Integer pageIndex, Integer pageSize, Integer total) {
        super(code, message, payload);
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
    }

}
