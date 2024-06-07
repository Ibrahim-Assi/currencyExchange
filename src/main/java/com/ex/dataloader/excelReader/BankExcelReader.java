package com.ex.dataloader.excelReader;

import com.ex.models.entities.gl.GlBanks;
import com.ex.repositories.gl.GlBanksRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BankExcelReader {

    @Value("${bank.excel.path}")
    private String excelPath;

    @Autowired
    private GlBanksRepository  glBanksRepository;

    List<GlBanks> banks = new ArrayList<>();
    public List<GlBanks> readExcel() throws IOException {


        FileInputStream file = new FileInputStream(excelPath);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);


        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);
            GlBanks bank = new GlBanks();
            bank.setBankId((row.getCell(0) != null ? Long.valueOf(row.getCell(0).getStringCellValue()) : 0));
            bank.setBankArName((row.getCell(1) != null ? row.getCell(1).getStringCellValue() : ""));
            bank.setBankEnName((row.getCell(2 )!= null ? row.getCell(2).getStringCellValue() : ""));
             banks.add(bank) ;
        }
        file.close();
        glBanksRepository.saveAll(banks);
        return  banks ;
    }


}
