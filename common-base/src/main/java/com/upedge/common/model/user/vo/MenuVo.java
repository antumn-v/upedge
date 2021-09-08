package com.upedge.common.model.user.vo;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by jiaqi on 2020/11/9.
 */
@Data
public class MenuVo implements Comparable<MenuVo> {
    private Long id;

    private String title;

    private String name;

    private String url;

    private Boolean leaf;

    private Long parentId;

    private String menuPath;

    private Integer seq;

    private Boolean createPerm;

    private List<MenuVo> children;


    @Override
    public int compareTo(@NotNull MenuVo o) {
        return (int) (seq-o.getSeq());
    }
}
