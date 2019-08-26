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

import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.uu.ebc.luntan.entity.CourseInstance;
import se.uu.ebc.luntan.entity.EconomyDocument;
import se.uu.ebc.luntan.enums.Department;

public class EconomyDocExcel extends AbstractXlsView
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
/* 
		HSSFRichTextString text = new HSSFRichTextString(sheetName);                
		cell.setCellValue(text);                    
		currentRow++;

		row = sheet.createRow(currentRow);		
		cell = row.createCell(1);
		text = new HSSFRichTextString(experiment.getLaborator().getFirstName()+" "+experiment.getLaborator().getLastName());
		cell.setCellValue(text);                    
		HSSFCellStyle cellStyle = ((HSSFWorkbook)workbook).createCellStyle();
		CreationHelper createHelper = ((HSSFWorkbook)workbook).getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));
		cell = row.createCell(2);
		cell.setCellStyle(cellStyle);		
		cell.setCellValue(experiment.getDate());                    
		currentRow++;

		row = sheet.createRow(currentRow);		
		cell = row.createCell(1);
		text = new HSSFRichTextString(experiment.getExperimentKind().getExperimentType());
		cell.setCellValue(text);                    
		cell = row.createCell(2);
		text = new HSSFRichTextString(experiment.getProtocol() == null ? "" : experiment.getProtocol().getDesignation());
		cell.setCellValue(text);                    
		currentRow++;
		currentRow++;
 */

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
			text = new HSSFRichTextString(ci.getDesignation());                
			cell.setCellValue(text);                    

            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(ci.getCourse().getCredits());

            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(ci.getModelStudentNumber());

            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(ci.getRegisteredStudents() == null ? 0 : ci.getRegisteredStudents());

            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(styleDecFormat);
            cell.setCellValue(ci.getModelStudentNumber()*ci.getCourse().getCredits()/60);

            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(ci.getFundingModel().getId());

            cell = row.createCell(currentColumn++);
			text = new HSSFRichTextString(ci.isFirstInstance() ? "X" : "");                
			cell.setCellValue(text);                    

            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(styleCurrencyFormat);
            cell.setCellValue(ci.computeCIGrant());

			for (Department dep : edoc.getAccountedDeptsSorted())
			{            
				cell = row.createCell(currentColumn++);
				cell.setCellType(CellType.NUMERIC);
				cell.setCellStyle(stylePercentFormat);
				cell.setCellValue(ci.explicitGrantDist().get(dep));
			}            
			for (Department dep : edoc.getAccountedDeptsSorted())
			{            
				cell = row.createCell(currentColumn++);
				cell.setCellType(CellType.NUMERIC);
				cell.setCellStyle(styleCurrencyFormat);
            	cell.setCellValue(ci.computeGrants().get(dep));
			}            
/* 
            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(stylePercentFormat);
            cell.setCellValue(ci.explicitGrantDist().get(Department.ICM));
            
            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(stylePercentFormat);
            cell.setCellValue(ci.explicitGrantDist().get(Department.IEG));

            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(stylePercentFormat);
            cell.setCellValue(ci.explicitGrantDist().get(Department.IOB));


            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(styleCurrencyFormat);
            cell.setCellValue(ci.computeGrants().get(Department.IBG));
            
            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(styleCurrencyFormat);
            cell.setCellValue(ci.computeGrants().get(Department.ICM));
            
            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(styleCurrencyFormat);
            cell.setCellValue(ci.computeGrants().get(Department.IEG));
            
            cell = row.createCell(currentColumn++);
            cell.setCellType(CellType.NUMERIC);
			cell.setCellStyle(styleCurrencyFormat);
            cell.setCellValue(ci.computeGrants().get(Department.IOB));
            
 */
            currentRow++;
        }

    }
}