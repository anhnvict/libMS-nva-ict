package com.example.libms.service;

import com.example.libms.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    void importExcelBook(MultipartFile file);
    List<Book> getAllBooks();
}
