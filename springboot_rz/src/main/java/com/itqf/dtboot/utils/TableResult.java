package com.itqf.dtboot.utils;

import java.util.List;

public class TableResult {
    //{total:800,rows:[{},{}]}
    private  long total;
    private List<?> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
