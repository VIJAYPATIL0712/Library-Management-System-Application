package com.project.library_management.serviceImpl;

import com.project.library_management.entity.Book;
import com.project.library_management.entity.IssueRecord;
import com.project.library_management.entity.IssueStatus;
import com.project.library_management.entity.Student;
import com.project.library_management.repository.BookRepository;
import com.project.library_management.repository.IssueRecordReposiitory;
import com.project.library_management.repository.StudentRepository;
import com.project.library_management.service.IssueRecordService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IssueRecordImpl implements IssueRecordService {
    private final IssueRecordReposiitory issueRecordReposiitory;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public IssueRecordImpl(IssueRecordReposiitory issueRecordReposiitory, BookRepository bookRepository, StudentRepository studentRepository) {
        this.issueRecordReposiitory = issueRecordReposiitory;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void save(IssueRecord issueRecord) {

        Book book = bookRepository.findById(issueRecord.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Student student = studentRepository.findById(issueRecord.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 🔥 CHECK STOCK
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book not available");
        }

        // 🔥 REDUCE QUANTITY
        book.setQuantity(book.getQuantity() - 1);
        // 🔥 SET ISSUE DETAILS
        issueRecord.setBook(book);
        issueRecord.setStudent(student);
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(7));
        issueRecord.setStatus(IssueStatus.ISSUED);

        // save both
        bookRepository.save(book);
        issueRecordReposiitory.save(issueRecord);

    }

    @Override
    public List<IssueRecord> findIssuedBook() {
        return issueRecordReposiitory.findByStatus(IssueStatus.ISSUED);
    }

    @Override
    public List<IssueRecord> findReturnedBook() {
        return issueRecordReposiitory.findByStatusNot(IssueStatus.ISSUED);
    }

    @Override
    public IssueRecord findById(Long id) {
        return issueRecordReposiitory.findById(id)
                .orElseThrow(() -> new RuntimeException("IssueRecord not found: " + id));
    }

    @Transactional
    public void updateStatus(Long id, IssueStatus status) {

        IssueRecord record = issueRecordReposiitory.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        Book book = record.getBook();

        record.setStatus(status);
        record.setReturnDate(LocalDate.now());

        // 🔥 QUANTITY LOGIC
        if (status == IssueStatus.RETURNED) {
            book.setQuantity(book.getQuantity() + 1);
        }

        // LOST / DAMAGED → no increment

        bookRepository.save(book);
        issueRecordReposiitory.save(record);
    }

//    @Override
//    public List<IssueRecord> findByStudent_NameAndStatusNot(String name, IssueStatus status) {
//        return issueRecordReposiitory.findByStudent_NameAndStatusNot(name , status);
//    }

    @Override
    public List<IssueRecord> searchIssuedByStudentName(String keyword) {
        return issueRecordReposiitory
                .findByStatusAndStudent_NameContainingIgnoreCase(
                        IssueStatus.ISSUED,
                        keyword
                );
    }

    @Override
    public List<IssueRecord> searchNotIssuedByStudentName(String keyword) {
        return issueRecordReposiitory
                .findByStatusNotAndStudent_NameContainingIgnoreCase(
                        IssueStatus.ISSUED,
                        keyword
                );
    }

//    @Override
//    public void saveReturned(IssueRecord existingRecord) {
//
//        issueRecordReposiitory.save(existingRecord);
//    }
}
