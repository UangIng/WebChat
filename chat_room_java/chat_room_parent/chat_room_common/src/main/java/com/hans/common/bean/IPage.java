package com.hans.common.bean;

import java.util.List;

public class IPage<T> {

    private List<T> dataList;

    private Integer totalCount;

    public static int formatOffset(int offset, int limit) {
        offset = ((offset - 1) * limit);
        return offset;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
