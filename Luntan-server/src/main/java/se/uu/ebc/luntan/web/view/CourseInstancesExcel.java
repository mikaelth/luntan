package se.uu.ebc.luntan.web.view;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.CellType;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.uu.ebc.ldap.Staff;
import se.uu.ebc.luntan.entity.Course;
import se.uu.ebc.luntan.entity.Examiner;
import se.uu.ebc.luntan.entity.ExaminersList;
import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.EconomyDocument;
import se.uu.ebc.luntan.enums.Department;

public class CourseInstancesExcel extends AbstractXlsView
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
        EconomyDocument edoc = (EconomyDocument)model.get("edoc");
        Map<String, Staff> courseLeaderMap = (Map<String, Staff>)model.get("courseLeaderMap");
        Map<String, Staff> examinerMap = (Map<String, Staff>)model.get("examinerMap");

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
//    	styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("# ##0 kr"));
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
        for(CourseInstance ci: edoc.getCourseInstances()){
            row = sheet.createRow(currentRow);
        	currentColumn = 0;

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(ci.getCourse().getCourseGroup().displayName());                
			cell.setCellValue(text);                    

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(ci.getInstanceCode());                
			cell.setCellValue(text);                    

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(ci.getDesignation());                
			cell.setCellValue(text);                    

            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(ci.getCourse().getCredits());

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(ci.isFirstInstance() ? "X" : "");                
			cell.setCellValue(text);                    

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(courseLeaderMap.get(ci.getCourseLeader()).getNameAndContact());                
			cell.setCellValue(text);                    

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(examinerMap.containsKey(ci.getCourse()) ? examinerMap.get(ci.getCourse()).getNameAndContact() : "Missing");                
			cell.setCellValue(text);                    

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(ci.getNote());                
			cell.setCellValue(text);                    

			cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(ci.isSupplementary() ? "Tillagd som supplement " + ci.getCreationDate() : "");                
			cell.setCellValue(text);                    

            currentRow++;
        }

    }
}