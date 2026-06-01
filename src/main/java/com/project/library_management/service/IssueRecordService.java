package com.project.library_management.service;

import com.project.library_management.entity.IssueRecord;
import com.project.library_management.entity.IssueStatus;

import java.util.List;

public interface IssueRecordService {
    List<IssueRecord> findIssuedBook();
}