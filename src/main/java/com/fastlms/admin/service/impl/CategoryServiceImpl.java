package com.fastlms.admin.service.impl;

import com.fastlms.admin.dto.CategoryDto;
import com.fastlms.admin.entity.Category;
import com.fastlms.admin.model.CategoryInput;
import com.fastlms.admin.repository.CategoryRepository;
import com.fastlms.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }

    @Override
    public List<CategoryDto> list() {
        List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc());
        return CategoryDto.of(categories);
        //return categoryRepository.findAllOrderBySortValueDesc().map(CategoryDto::of).orElse(null);
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
    public boolean update(CategoryInput parameter) {
        Optional<Category> optionalCategory = categoryRepository.findById(parameter.getId());

        if( optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            category.setCategoryName(parameter.getCategoryName());
            category.setSortValue(parameter.getSortValue());
            category.setUsingYn(parameter.isUsingYn());
            categoryRepository.save(category);
        }

        return true;
    }

    @Override
    public boolean del(long id) {
        categoryRepository.deleteById(id);
        return true;
    }
}
