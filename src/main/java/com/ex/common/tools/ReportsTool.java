package com.ex.common.tools;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsTool {
    public static ResponseEntity<Resource> generateReport(Map<String, Object> parameters, List<?> dataSource, String reportPath, String pdfName) throws IOException, JRException, FontFormatException {
        Map<String, Object> params = new HashMap<>();
        params.put("imgPath", "classpath:static/img/user.png");
        params.put("repDate", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        params.put("repTime", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
        if(parameters!=null){
            params.putAll(parameters);
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(ResourceUtils.getFile(reportPath).getAbsolutePath());
        JRBeanCollectionDataSource reportDataSource = new JRBeanCollectionDataSource(dataSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, reportDataSource);

        byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        ByteArrayResource resource = new ByteArrayResource(reportBytes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+pdfName+".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(reportBytes.length)
                .body(resource);
    }
}
