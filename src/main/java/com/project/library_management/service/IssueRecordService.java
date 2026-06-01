package com.project.library_management.service;

import com.project.library_management.entity.IssueRecord;
import com.project.library_management.entity.IssueStatus;

import java.util.List;
import java.util.Optional;

public interface IssueRecordService {
     void save(IssueRecord issueRecord) ;

    List<IssueRecord> findIssuedBook();

    List<IssueRecord> findReturnedBook();

    IssueRecord findById(Long id);


    void updateStatus(Long id, IssueStatus issueStatus);



    List<IssueRecord> searchIssuedByStudentName(String keyword);

    List<IssueRecord> searchNotIssuedByStudentName(String keyword);
}