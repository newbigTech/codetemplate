package com.newbig.codetemplate.service.helper;

import com.google.common.collect.Lists;
import com.newbig.codetemplate.common.utils.BeanCopyUtil;
import com.newbig.codetemplate.dal.model.SysResource;
import com.newbig.codetemplate.vo.ResourceTreeVo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResourceHelper {
    public static List<ResourceTreeVo> buildResourceTreeVo(List<SysResource> resources) {
        List<ResourceTreeVo> rootTrees = Lists.newArrayList();
        List<ResourceTreeVo> resourceTreeVos = Lists.newArrayList();
        for (SysResource resource : resources) {
            ResourceTreeVo resourceTreeVo = new ResourceTreeVo();
            BeanCopyUtil.copyProperties(resource, resourceTreeVo);
            resourceTreeVo.setChildren(Lists.newArrayList());
            resourceTreeVos.add(resourceTreeVo);
            if (0 == resourceTreeVo.getParentId()) {
                resourceTreeVo.setParentId(null);
            }
        }
        for (ResourceTreeVo resourceTreeVo : resourceTreeVos) {
            if (Objects.equals(1, resourceTreeVo.getLevel())) {
                rootTrees.add(resourceTreeVo);
            }
            for (ResourceTreeVo r : resourceTreeVos) {
                if (Objects.equals(r.getParentId(), resourceTreeVo.getId())) {
                    resourceTreeVo.getChildren().add(r);
                    Collections.sort(resourceTreeVo.getChildren(), new ResourceComparator());
                }
            }
        }
        Collections.sort(rootTrees, new ResourceComparator());

        return rootTrees;
    }
}
