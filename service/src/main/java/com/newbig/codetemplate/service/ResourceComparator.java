package com.newbig.codetemplate.service;


import com.newbig.codetemplate.vo.ResourceTreeVo;

import java.util.Comparator;

public class ResourceComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        ResourceTreeVo vo1 = (ResourceTreeVo) o1;
        ResourceTreeVo vo2 = (ResourceTreeVo) o2;
        if (vo1.getSort() > vo2.getSort()) {
            return 1;
        } else if (vo1.getId() > vo2.getId()) {
            return 1;
        } else {
            return -1;
        }
    }
}
