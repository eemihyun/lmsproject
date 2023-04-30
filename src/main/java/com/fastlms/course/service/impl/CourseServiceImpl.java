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

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    @Override
    public boolean add(CourseInput parameter) {
        Course course = Course.builder()
                .subject(parameter.getSubject())
                .regDt(LocalDateTime.now())
                .build();

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
}
