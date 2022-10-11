package com.example.libms.controller;

import com.example.libms.entity.Book;
import com.example.libms.importer.ExcelImporter;
import com.example.libms.service.BookService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/excel-upload")
    public ResponseEntity<?> exelUpload(@RequestParam("file") MultipartFile file) {
        if (ExcelImporter.checkExcelFormat(file)) {
            //true
            this.bookService.importExcelBook(file);
            return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllProduct() {
        List<Book> listBooks = this.bookService.getAllBooks();
        if (!listBooks.isEmpty()){
            return new ResponseEntity<>(listBooks, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/download-template")
    public ResponseEntity<InputStreamResource> downloadTemplate(@RequestParam String temp, HttpServletRequest request) throws IOException {
        HttpHeaders responseHeader = new HttpHeaders();
        try {
            File file = ResourceUtils.getFile("classpath:templates/bookImport.xlsx");
            byte[] data = FileUtils.readFileToByteArray(file);

            responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            responseHeader.set("Content-disposition", "attachment; filename=" + file.getName());
            responseHeader.setContentLength(data.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<InputStreamResource>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
