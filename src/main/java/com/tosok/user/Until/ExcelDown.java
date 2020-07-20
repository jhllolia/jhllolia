package com.tosok.user.Until;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tosok.user.VO.PayVO;
import com.tosok.user.VO.VisitCountVO;

public class ExcelDown {
	public void Download(String keyword, List<VisitCountVO> list, HttpServletResponse response) throws IOException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");	// Date
		Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet(keyword);							// Sheet Name
	    CellStyle headStyle = wb.createCellStyle();						// Cell Style

	    Row row = null;
	    Cell cell = null;
	    int rowNo = 0;

	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);

	    headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());	//	 배경색은 노란색입니다.
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);					//	 배경색 패턴
	    headStyle.setAlignment(HorizontalAlignment.CENTER); 						//	 데이터는 가운데 정렬합니다.

	    CellStyle bodyStyle = wb.createCellStyle();									//	 데이터용 경계 스타일 테두리만 지정

	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);

	    row = sheet.createRow(rowNo++);

	    cell = row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("번호");

	    cell = row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("방문 IP");

	    cell = row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("방문 시간");
	    
	    cell = row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("등록 기기");

	    for(VisitCountVO vo : list) {
	        Date date = vo.getVisit_time(); 			// Date
			String visit_Time = format1.format(date);	// Date Format

	    	row = sheet.createRow(rowNo++);

	    	sheet.autoSizeColumn(rowNo);				// Column Width Size
	    	sheet.setColumnWidth(rowNo, (sheet.getColumnWidth(rowNo)) + 512);

	        cell = row.createCell(0);					// Num
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(vo.getVisit_id());

	        cell = row.createCell(1);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(vo.getVisit_ip());

	        cell = row.createCell(2);					// Time
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(visit_Time);

	        cell = row.createCell(3);					// AGENT
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(vo.getVisit_agent());
	    }

	    response.setContentType("ms-vnd/excel");												// 컨텐츠 타입
	    response.setHeader("Content-Disposition", "attachment;filename=" + keyword + ".xls");	// 파일명 지정

	    wb.write(response.getOutputStream());													// 엑셀 출력
	    wb.close();
	}

	public void PaymentExcel(List<PayVO> list, HttpServletResponse response) throws IOException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");	// Date
		String keyword = "Payment";

		Workbook wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet(keyword);							// Sheet Name
	    CellStyle headStyle = wb.createCellStyle();						// Cell Style

	    Row row = null;
	    Cell cell = null;
	    int rowNo = 0;

	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);

	    headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());	//	 배경색은 노란색입니다.
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);					//	 배경색 패턴
	    headStyle.setAlignment(HorizontalAlignment.CENTER); 						//	 데이터는 가운데 정렬합니다.

	    CellStyle bodyStyle = wb.createCellStyle();									//	 데이터용 경계 스타일 테두리만 지정

	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);

	    row = sheet.createRow(rowNo++);

	    cell = row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("주문번호");

	    cell = row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("주문자");

	    cell = row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("주문자 번호");
	    
	    cell = row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("배송 주소");
	    
	    cell = row.createCell(4);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("결제 제품");
	    
	    cell = row.createCell(5);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("제품 개수");
	    
	    cell = row.createCell(6);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("해당 결제 가격");

	    cell = row.createCell(7);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("배송비");

	    cell = row.createCell(8);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("배송번호");

	    cell = row.createCell(9);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("결제 시간");

	    cell = row.createCell(10);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("상태");

	    for(PayVO vo : list) {
	        Date date = vo.getPAID_DATE();

	        String ship = "";
			String paid_time = format1.format(date);	// Date Format
			String ship_num = vo.getPRODUCT_SHIPPING_NUM();
			String ship_courier = vo.getPRODUCT_SHIPPING_COURIER();
	        String status = vo.getPAID_STATUS();

	    	row = sheet.createRow(rowNo++);

	    	sheet.autoSizeColumn(rowNo);				// Column Width Size
	    	sheet.setColumnWidth(rowNo, (sheet.getColumnWidth(rowNo)) + 512);

	        cell = row.createCell(0);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(vo.getORDER_NUM());

	        cell = row.createCell(1);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(vo.getBUYER_NAME());

	        cell = row.createCell(2);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(vo.getBUYER_TEL());

	        cell = row.createCell(3);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue("(" + vo.getBUYER_POSTCODE() + ") " + vo.getBUYER_ADDR());

	        cell = row.createCell(4);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(vo.getPRODUCT_NAME());

	        cell = row.createCell(5);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(vo.getPRODUCT_QTY() + " 개");

	        cell = row.createCell(6);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue("" + vo.getPRODUCT_PRICE() + " 원");

	        cell = row.createCell(7);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue("" + vo.getPRODUCT_SHIPPING_COST() + " 원");

	        cell = row.createCell(8);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue("" + ship_num + " =>" + ship_courier + "");

	        cell = row.createCell(9);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(paid_time);

			if(status.equals("paid")) {
				status = "결제 완료";
			} else if(status.equals("cancelled")) {
				status = "결제 취소";
			} else if(status.equals("shipping_waiting")) {
				status = "상품준비중";
			} else if(status.equals("shipping_delivery")) {
				status = "배송중";
			} else if(status.equals("shipping_success")) {
				status = "배송완료";
			} else if(status.equals("shipping_return")) {
				status = "반품요청";
			} else if(status.equals("shipping_exchange")) {
				status = "교환요청";
			}

	        cell = row.createCell(10);					// IP
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(status);
	    }

	    response.setContentType("ms-vnd/excel");												// 컨텐츠 타입
	    response.setHeader("Content-Disposition", "attachment;filename=" + keyword + ".xls");	// 파일명 지정

	    wb.write(response.getOutputStream());													// 엑셀 출력
	    wb.close();
	}
}
