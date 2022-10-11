package com.example.libms.service.impl;

import com.example.libms.entity.Book;
import com.example.libms.importer.ExcelImporter;
import com.example.libms.repository.BookRepository;
import com.example.libms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    public void importExcelBook(MultipartFile file) {
        try {
            List<Book> listBooks = ExcelImporter.convertExcelToListOfProduct(file.getInputStream());
            this.bookRepository.saveAll(listBooks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }
}
