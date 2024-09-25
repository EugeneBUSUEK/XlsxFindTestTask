package com.example.xlsxserachntesttask.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

@RestController
public class FileController {

    @Operation(summary = "Возвращает N-ое максимальное число из файла")
    @GetMapping("/max-number")
    public Integer getNthMaxNumber(@RequestParam String filePath, @RequestParam int n) throws IOException {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                int number = (int) row.getCell(0).getNumericCellValue();

                if (minHeap.size() < n) {
                    minHeap.add(number);
                } else if (number > minHeap.peek()) {
                    minHeap.poll();
                    minHeap.add(number);
                }
            }
        }

        return minHeap.peek();
    }
}
