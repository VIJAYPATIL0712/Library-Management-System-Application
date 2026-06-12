package com.project.library_management.repository;

import com.project.library_management.entity.IssueRecord;
import com.project.library_management.entity.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface IssueRecordReposiitory extends JpaRepository<IssueRecord , Long> {



      List<IssueRecord> findByStatus(IssueStatus issueStatus);

      List<IssueRecord> findByStatusNot(IssueStatus issueStatus);

      List<IssueRecord> findByStatusAndStudent_NameContainingIgnoreCase(IssueStatus issueStatus, String keyword);

      List<IssueRecord> findByStatusNotAndStudent_NameContainingIgnoreCase(IssueStatus status, String keyword);


      @Query("""
       SELECT COUNT(DISTINCT i.student.id)
       FROM IssueRecord i
       WHERE i.status = 'ISSUED'
       """)
      Long countStudentsWithIssuedBooks();

      @Query("""
       SELECT COUNT(DISTINCT i.student.id)
       FROM IssueRecord i
       WHERE i.status != 'ISSUED'
       """)
      Long countStudentsWithoutIssuedBooks();

      List<IssueRecord> findByDueDateBeforeAndStatus(
              LocalDate date,
              IssueStatus status);


      List<IssueRecord> findByStatusAndDueDateBefore(
              IssueStatus status,
              LocalDate date);

      List<IssueRecord> findByStatusAndDueDateGreaterThanEqual(
              IssueStatus status,
              LocalDate date);

      List<IssueRecord> findByStatusAndDueDateBeforeAndStudent_NameContainingIgnoreCase(
              IssueStatus status,
              LocalDate date,
              String keyword
      );

    boolean existsByBookId(Long id);
}
