package com.fastlms.course.mapper;

import com.fastlms.course.dto.CourseDto;
import com.fastlms.course.model.CourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    long selectListCount(CourseParam parameter);
    List<CourseDto> selectList(CourseParam parameter);
}
