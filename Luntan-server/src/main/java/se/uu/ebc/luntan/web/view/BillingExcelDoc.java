package se.uu.ebc.luntan.web.view;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.CellType;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.EconomyDocument;
import se.uu.ebc.luntan.entity.EconomyDocGrant;
import se.uu.ebc.luntan.entity.IndividualCourseCreditBasis;
import se.uu.ebc.luntan.entity.IndividualCourseRegistration;
import se.uu.ebc.luntan.entity.IndividualCourseTeacher;
import se.uu.ebc.luntan.enums.Department;

public class BillingExcelDoc extends AbstractXlsView
{
    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(
    		Map<String, Object> model,
            Workbook workbook,
            HttpServletRequest request,
            HttpServletResponse response)
    {
        //VARIABLES REQUIRED IN MODEL
        String sheetName = (String)model.get("sheetname");
        List<String> headers = (List<String>)model.get("headers");
        IndividualCourseCreditBasis bdoc = (IndividualCourseCreditBasis)model.get("bdoc");

        List<String> numericColumns = new ArrayList<String>();
        if (model.containsKey("numericcolumns"))
            numericColumns = (List<String>)model.get("numericcolumns");

        //BUILD DOC
        HSSFSheet sheet = (HSSFSheet)workbook.createSheet(sheetName.replace(":","_"));
        sheet.setDefaultColumnWidth((short) 12);
        int currentRow = 0;
        short currentColumn = 0;

		//SET GENERAL INFORMATION

		HSSFRow row; // = sheet.createRow(currentRow);
		HSSFCell cell; //= row.createCell(1);
		HSSFRichTextString text;

        //CREATE STYLE FOR HEADER
        HSSFCellStyle headerStyle = (HSSFCellStyle)workbook.createCellStyle();
        HSSFFont headerFont = (HSSFFont)workbook.createFont();
		headerFont.setBold(true);
		headerStyle.setFont(headerFont);

		CreationHelper ch = workbook.getCreationHelper();
    	HSSFCellStyle styleCurrencyFormat = (HSSFCellStyle)workbook.createCellStyle();
    	styleCurrencyFormat.setDataFormat(ch.createDataFormat().getFormat("# ##0 kr"));

    	HSSFCellStyle stylePercentFormat = (HSSFCellStyle)workbook.createCellStyle();
    	stylePercentFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("0%"));

    	HSSFCellStyle styleDecFormat = (HSSFCellStyle)workbook.createCellStyle();
    	styleDecFormat.setDataFormat(ch.createDataFormat().getFormat("0.0"));

    	HSSFCellStyle styleDateFormat = (HSSFCellStyle)workbook.createCellStyle();
    	styleDateFormat.setDataFormat(ch.createDataFormat().getFormat("yyyy-mm-dd"));


        //POPULATE HEADER COLUMNS
        HSSFRow headerRow = sheet.createRow(currentRow);
        for(String header:headers){
            text = new HSSFRichTextString(header);
            cell = headerRow.createCell(currentColumn);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(text);
            currentColumn++;
        }

        //POPULATE VALUE ROWS/COLUMNS
        currentRow++;//exclude header
        for(IndividualCourseRegistration reg: bdoc.getRegistrations()){
			for ( IndividualCourseTeacher teacher : reg.getSuperAndReader() ) { 
//				if (teacher.computeCreditFunds() > 0){

					row = sheet.createRow(currentRow);
					currentColumn = 0;
		
					cell = row.createCell(currentColumn++);
					cell.setCellType(CellType.NUMERIC);
					cell.setCellStyle(styleDateFormat);
					cell.setCellValue(reg.getRegistrationDate());
		
					cell = row.createCell(currentColumn++);
					cell.setCellType(CellType.NUMERIC);
					cell.setCellStyle(styleDateFormat);
					cell.setCellValue(reg.getStartDate());
		
					cell = row.createCell(currentColumn++);
					text = new HSSFRichTextString(reg.getCourseBag().getDesignation());
					cell.setCellValue(text);
		
					cell = row.createCell(currentColumn++);
					text = new HSSFRichTextString(reg.getStudentName());
					cell.setCellValue(text);
		
					cell = row.createCell(currentColumn++);
					text = new HSSFRichTextString(reg.getCoordinator().getName());
					cell.setCellValue(text);
	
					cell = row.createCell(currentColumn++);
					text = new HSSFRichTextString(teacher.getTeacherType().toString());
					cell.setCellValue(text);
	
					cell = row.createCell(currentColumn++);
					text = new HSSFRichTextString(teacher.getName());
					cell.setCellValue(text);
	
					cell = row.createCell(currentColumn++);
					text = new HSSFRichTextString(teacher.getFullDepartment());
					cell.setCellValue(text);
	
					cell = row.createCell(currentColumn++);
					cell.setCellType(CellType.NUMERIC);
					cell.setCellStyle(styleCurrencyFormat);
					cell.setCellValue(teacher.computeCreditFunds());
	
					cell = row.createCell(currentColumn++);
					text = new HSSFRichTextString(teacher.getNote());
					cell.setCellValue(text);
	

					currentRow++;
//				}
			}
        }

/* 
		currentRow++;

		for (EconomyDocGrant edg : edoc.getEconomyDocGrants()) {
			row = sheet.createRow(currentRow);
			currentColumn = 1;

            cell = row.createCell(currentColumn);
			text = new HSSFRichTextString(edg.getItemDesignation());
			cell.setCellValue(text);

			currentColumn = 14;
			for (Department dep : edoc.getAccountedDeptsSorted())
			{
				cell = row.createCell(currentColumn++);
				cell.setCellType(CellType.NUMERIC);
				cell.setCellStyle(styleCurrencyFormat);
				if (edg.getDistributedGrant().get(dep) != null) {
					cell.setCellValue(edg.getDistributedGrant().get(dep));
				}
			}

			currentRow++;
		}

		currentRow++;

		row = sheet.createRow(currentRow);
		currentColumn = 1;
        cell = row.createCell(currentColumn);
		text = new HSSFRichTextString("Justering för tidigare kurstillfällen i Ekonomidokumentet");
		cell.setCellValue(text);
		currentColumn = 14;
		for (Department dep : edoc.getAccountedDeptsSorted())
			{
				cell = row.createCell(currentColumn++);
				cell.setCellType(CellType.NUMERIC);
				cell.setCellStyle(styleCurrencyFormat);
				if (edoc.totalAdjustmentSum().get(dep) != null) {
					cell.setCellValue(edoc.totalAdjustmentSum().get(dep));
				}
			}
 */

    }
}
