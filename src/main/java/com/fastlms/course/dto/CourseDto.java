package com.fastlms.course.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseDto {
    Long id;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    LocalDateTime saleEndDt;
    LocalDateTime regDt; //등록일자
    LocalDateTime udtDt; //수정일자

    // 추가 컬럼
    long totalCount;
    long seq;


}
