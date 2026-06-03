package com.project.library_management.service;

import com.project.library_management.dto.issue.IssueRecordRequestDto;
import com.project.library_management.dto.issue.IssueRecordResponseDto;
import com.project.library_management.entity.IssueRecord;
import com.project.library_management.entity.IssueStatus;

import java.util.List;
import java.util.Optional;

public interface IssueRecordService {

     void save(IssueRecordRequestDto issueRecordRequestDto) ;

    List<IssueRecordResponseDto> findIssuedBook();

    List<IssueRecordResponseDto> findReturnedBook();

    IssueRecordResponseDto findById(Long id);


    void updateStatus(Long id, IssueStatus issueStatus);



    List<IssueRecordResponseDto> searchIssuedByStudentName(String keyword);

    List<IssueRecordResponseDto> searchNotIssuedByStudentName(String keyword);


    Long countByStatus();

    Long countByStatus1();
}