package com.ex.dataloader.excelReader;

import com.ex.models.entities.gl.GlBankBranches;
import com.ex.repositories.gl.GlBankBranchesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BankBranchesExcelReader {

    @Value("${bankBranches.excel.path}")
    private String excelPath;

    private final GlBankBranchesRepository glBankBranchesRepository;

    List<GlBankBranches> dataList = new ArrayList<>();
    public List<GlBankBranches> readExcel() throws IOException {


        FileInputStream file = new FileInputStream(excelPath);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);


        for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
            Row row = sheet.getRow(i);

            GlBankBranches branch = new GlBankBranches();
            branch.setBankId((row.getCell(0) != null ? Long.parseLong(row.getCell(0).getStringCellValue()) : 0));
            branch.setBranchId((row.getCell(1) != null ? Long.parseLong(row.getCell(1).getStringCellValue()) : 0));
            branch.setBranchArName((row.getCell(2) != null ? row.getCell(2).getStringCellValue() : ""));
            branch.setBranchEnName((row.getCell(3 )!= null ? row.getCell(3).getStringCellValue() : ""));
            branch.setBranchAddress(" ");
            branch.setIsActive(true);
            dataList.add(branch) ;
        }
        file.close();
        glBankBranchesRepository.saveAll(dataList);

        return  dataList ;
    }


}
