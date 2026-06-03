package com.project.library_management.serviceImpl;

import com.project.library_management.dto.book.BookRequestDto;
import com.project.library_management.dto.issue.IssueRecordRequestDto;
import com.project.library_management.dto.issue.IssueRecordResponseDto;
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
import java.util.stream.Collectors;

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

//    Mapping

    //DTO TO Entity

    private  IssueRecord toEntity(IssueRecordRequestDto dto){
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Book not found"));

        IssueRecord issueRecord = new IssueRecord();

        issueRecord.setStudent(student);
        issueRecord.setBook(book);
        issueRecord.setIssueDate(dto.getIssueDate());
        issueRecord.setDueDate(dto.getDueDate());
        issueRecord.setStatus(IssueStatus.ISSUED);

        return issueRecord;

    }

    //Entity To Dto
    private IssueRecordResponseDto toDto(IssueRecord issueRecord) {

        IssueRecordResponseDto dto = new IssueRecordResponseDto();

        dto.setId(issueRecord.getId());
        dto.setStudentName(issueRecord.getStudent().getName());
        dto.setBookTitle(issueRecord.getBook().getTitle());
        dto.setIssueDate(issueRecord.getIssueDate());
        dto.setDueDate(issueRecord.getDueDate());
        dto.setReturnDate(issueRecord.getReturnDate());
        dto.setStatus(issueRecord.getStatus());
        long overdueDays = 0;

        if (issueRecord.getDueDate() != null &&
                issueRecord.getDueDate().isBefore(LocalDate.now()) &&
                issueRecord.getStatus() == IssueStatus.ISSUED) {

            overdueDays = java.time.temporal.ChronoUnit.DAYS.between(
                    issueRecord.getDueDate(),
                    LocalDate.now());
        }

        dto.setOverdueDays(overdueDays);

        return dto;
    }


    @Override
    public void save(IssueRecordRequestDto issueRecordRequestDto) {

        IssueRecord issueRecord = toEntity(issueRecordRequestDto);

        Book book = issueRecord.getBook();

        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Book not available");
        }

        book.setQuantity(book.getQuantity() - 1);

        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(7));
        issueRecord.setStatus(IssueStatus.ISSUED);

        bookRepository.save(book);
        issueRecordReposiitory.save(issueRecord);

    }

    @Override
    public List<IssueRecordResponseDto> findIssuedBook() {
        return issueRecordReposiitory
                .findByStatusAndDueDateGreaterThanEqual(
                        IssueStatus.ISSUED,
                        LocalDate.now()
                )
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueRecordResponseDto> findOverdueBooks() {

        return issueRecordReposiitory
                .findByStatusAndDueDateBefore(
                        IssueStatus.ISSUED,
                        LocalDate.now())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<IssueRecordResponseDto> findReturnedBook() {
        return issueRecordReposiitory.findByStatusNot(IssueStatus.ISSUED)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public IssueRecordResponseDto findById(Long id) {
         IssueRecord issueRecord = issueRecordReposiitory.findById(id)
                .orElseThrow(() -> new RuntimeException("IssueRecord not found: " + id));
         return toDto(issueRecord);
    }

    @Override
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



    @Override
    public List<IssueRecordResponseDto> searchIssuedByStudentName(String keyword) {
        return   issueRecordReposiitory
                .findByStatusAndStudent_NameContainingIgnoreCase(
                        IssueStatus.ISSUED,
                        keyword
                )
                .stream()
                .map(this :: toDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<IssueRecordResponseDto> searchNotIssuedByStudentName(String keyword) {
        return issueRecordReposiitory
                .findByStatusNotAndStudent_NameContainingIgnoreCase(
                        IssueStatus.ISSUED,
                        keyword
                )
                .stream()
                .map(this :: toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long countByStatus() {
        return issueRecordReposiitory.countStudentsWithIssuedBooks();
    }

    public Long countByStatus1(){
        return issueRecordReposiitory.countStudentsWithoutIssuedBooks();
    }

    @Override
    public List<IssueRecordResponseDto> searchOverdueByName(String keyword) {

        return issueRecordReposiitory
                .findByStatusAndDueDateBeforeAndStudent_NameContainingIgnoreCase(
                        IssueStatus.ISSUED,
                        LocalDate.now(),
                        keyword)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
