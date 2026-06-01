package com.project.library_management.serviceImpl;

import com.project.library_management.entity.IssueRecord;
import com.project.library_management.entity.IssueStatus;
import com.project.library_management.repository.IssueRecordReposiitory;
import com.project.library_management.service.IssueRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueRecordImpl implements IssueRecordService {
    private final IssueRecordReposiitory issueRecordReposiitory;

    public IssueRecordImpl(IssueRecordReposiitory issueRecordReposiitory) {
        this.issueRecordReposiitory = issueRecordReposiitory;
    }

    @Override
    public List<IssueRecord> findIssuedBook() {
        return issueRecordReposiitory.findByStatus(IssueStatus.ISSUED);
    }
}
