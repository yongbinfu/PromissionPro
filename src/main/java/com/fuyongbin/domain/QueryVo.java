package com.fuyongbin.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class QueryVo {
    private Integer page;
    private Integer rows;
    private String keyword;
}
