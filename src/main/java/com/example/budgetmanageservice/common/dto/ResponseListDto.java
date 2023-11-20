package com.example.budgetmanageservice.common.dto;

import com.example.budgetmanageservice.common.paging.PagingUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ResponseListDto {

    private PagingUtil pagingUtil;
}
