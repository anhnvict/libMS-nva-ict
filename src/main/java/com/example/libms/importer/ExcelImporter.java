package com.example.libms.importer;

import com.example.libms.entity.Book;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelImporter {

    //check that file is of Excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }

    //convert excel to list of products

    public static List<Book> convertExcelToListOfProduct(InputStream is) {
        List<Book> listBooks = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("data");
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                Book book = new Book();
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    switch (cid) {
                        case 0:
                            book.setIsbn(cell.getStringCellValue());
                            break;
                        case 1:
                            book.setTitle(cell.getStringCellValue());
                            break;
                        case 2:
                            book.setAuthor(cell.getStringCellValue());
                            break;
                        case 3:
                            book.setCategory(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;

                }

                listBooks.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBooks;
    }
}
