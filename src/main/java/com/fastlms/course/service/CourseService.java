package com.fastlms.course.service;

import com.fastlms.course.dto.CourseDto;
import com.fastlms.course.model.CourseInput;
import com.fastlms.course.model.CourseParam;

import java.util.List;

public interface CourseService {
    boolean add(CourseInput parameter); // 강좌 등록
    List<CourseDto> list(CourseParam parameter); // 강좌 리스트 
}
