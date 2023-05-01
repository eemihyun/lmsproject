package com.fastlms.course.service.impl;

import com.fastlms.course.dto.CourseDto;
import com.fastlms.course.entity.Course;
import com.fastlms.course.mapper.CourseMapper;
import com.fastlms.course.model.CourseInput;
import com.fastlms.course.model.CourseParam;
import com.fastlms.course.repository.CourseRepository;
import com.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    @Override
    public boolean add(CourseInput parameter) {
        Course course = Course.builder()
                .categoryId(parameter.getCategoryId())
                .subject(parameter.getSubject())
                .summary(parameter.getSummary())
                .contents(parameter.getContents())
                .price(parameter.getPrice())
                .salePrice(parameter.getSalePrice())
                .keyword(parameter.getKeyword())
                .regDt(LocalDateTime.now())
                .build();

        courseRepository.save(course);
        return true;
    }

    @Override
    public boolean set(CourseInput parameter) {
        Optional<Course> optionalCourse = courseRepository.findById(parameter.getId());
        
        if( !optionalCourse.isPresent() ){
            return false; // 수정사항 없음
        }
        Course course = optionalCourse.get();
        course.setCategoryId(parameter.getCategoryId());
        course.setSubject(parameter.getSubject());
        course.setKeyword(parameter.getKeyword());
        course.setSummary(parameter.getSummary());
        course.setContents(parameter.getContents());
        course.setPrice(parameter.getPrice());
        course.setSalePrice(parameter.getSalePrice());
        course.setUdtDt(LocalDateTime.now());
        courseRepository.save(course);

        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam parameter) {
        long totalCount = courseMapper.selectListCount(parameter);

        List<CourseDto> list = courseMapper.selectList(parameter);

        if( !CollectionUtils.isEmpty(list) ) {
            int i=0;
            for(CourseDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() -i);
                i++;
            }
        }

        return list;
    }

    @Override
    public CourseDto getById(long id) {

        return courseRepository.findById(id).map(CourseDto::of).orElse(null);
    }


}
