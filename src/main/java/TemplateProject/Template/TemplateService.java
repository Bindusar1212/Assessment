package TemplateProject.Template;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.annotation.Resource;

@Service
public class TemplateService {

    @Autowired
    private DataRecordRepository dataRecordRepository; // This is your JPA repository for data records.

    public List<DataRecord> getAllRecords() {
        return dataRecordRepository.findAll();
    }

    public void processTemplate(MultipartFile file) {
    	 try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
             Sheet sheet = workbook.getSheetAt(0); // Assuming the data is on the first sheet

             for (Row row : sheet) {
                 DataRecord dataRecord = new DataRecord();
                 dataRecord.setField1(getStringValue(row.getCell(0)));
                 dataRecord.setField2(getStringValue(row.getCell(1)));
                 dataRecord.setField3(getStringValue(row.getCell(2)));
                 dataRecord.setField4(getStringValue(row.getCell(3)));
                 // Set other fields as needed

                 dataRecordRepository.save(dataRecord); // Save the record to the database
             }
         } catch (Exception e) {
             // Handle any exceptions
         }
    }
    
    private String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // Format numeric values as needed
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return null;
        }
    }

    public Resource generateTemplateFile() {
    	try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Data Template");
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("First Name");
            headerRow.createCell(1).setCellValue("Last Name");
            headerRow.createCell(2).setCellValue("Date of Birth");
            headerRow.createCell(3).setCellValue("City");
            // Add headers for other fields as needed

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] templateBytes = bos.toByteArray();
            Resource resource = (Resource) new InputStreamResource(new ByteArrayInputStream(templateBytes));
            return resource;
        } catch (Exception e) {
            // Handle any exceptions
            return null;
        }
    }
}