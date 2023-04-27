package com.fastlms.admin.service.impl;

import com.fastlms.admin.dto.CategoryDto;
import com.fastlms.admin.entity.Category;
import com.fastlms.admin.repository.CategoryRepository;
import com.fastlms.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> list() {
        List<Category> categories = categoryRepository.findAll();

        return CategoryDto.of(categories);
    }

    @Override
    public boolean add(String categoryName) {
        // 카테고리명 중복 체크

        Category category = Category.builder()
                .categoryName(categoryName)
                .usingYn(true)
                .sortValue(0)
                .build();
        categoryRepository.save(category);

        return true;
    }

    @Override
    public boolean update(CategoryDto parameter) {
        return false;
    }

    @Override
    public boolean del(long id) {
        return false;
    }
}
