package com.kinoved.common.telegram.dtos;

import com.kinoved.common.enums.SortField;
import com.kinoved.common.enums.SortOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortPreferenceDto {
    private SortField sortField;
    private SortOrder sortOrder;
}
