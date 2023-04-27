package com.fastlms.admin.dto;

import com.fastlms.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    Long id;
    String categoryName;
    int sortValue;
    boolean usingYn; //사용여부

    public static List<CategoryDto> of (List<Category> categories) {
        if (categories != null) {
            List<CategoryDto> categoryDtoList = new ArrayList<>();
            for(Category x : categories) {
                categoryDtoList.add(of(x));
            }
            return categoryDtoList;
        }
        return null;
    }

    // 카테고리 DTO를 리턴하는 함수
    public static CategoryDto of(Category category) {

        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .sortValue(category.getSortValue())
                .usingYn(category.isUsingYn())
                .build();
    }

}
