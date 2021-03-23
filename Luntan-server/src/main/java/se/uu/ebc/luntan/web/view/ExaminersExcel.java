package se.uu.ebc.luntan.web.view;

import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
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
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.uu.ebc.ldap.Staff;
import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.entity.Examiner;
import se.uu.ebc.luntan.entity.ExaminersList;
import se.uu.ebc.luntan.enums.Department;

public class ExaminersExcel extends AbstractXlsView
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
        ExaminersList el = (ExaminersList)model.get("exList");
        List<Examiner> examiners = (List<Examiner>)model.get("examiners");
        Map<String, Staff> staff = (Map<String, Staff>)model.get("staffMap");
        Map<Course,List<Examiner>> em = (Map<Course,List<Examiner>>)model.get("exMap");
        Map<String, String> mm = (Map<String, String>)model.get("matchMap");

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
        for(Course course: em.keySet()){
            row = sheet.createRow(currentRow);
        	currentColumn = 0;

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(course.getCode());                
			cell.setCellValue(text);                    

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(course.getSeName()+", "+course.getCredits()+" hp");                
			cell.setCellValue(text);                    

			String examinerNames = em.get(course).stream()
        		.map( ex -> staff.get(ex.getExaminer()).getName() )
        		.collect( Collectors.joining( ", " ) );

			cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(examinerNames);                
			cell.setCellValue(text);  
                  
			cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString( mm.get(course.getCode()) );                
//			text = new HSSFRichTextString( course.getCode() );                
			cell.setCellValue(text);  

			currentRow++;
        }


    }
}