package com.fastlms.admin.mapper;

import com.fastlms.admin.dto.MemberDto;
import com.fastlms.admin.model.MemberParam;
import com.fastlms.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    long selectListCount(MemberParam parameter);
    List<MemberDto> selectList(MemberParam parameter);
}
