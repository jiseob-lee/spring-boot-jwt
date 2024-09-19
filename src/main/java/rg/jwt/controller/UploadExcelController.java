package rg.jwt.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class UploadExcelController {
	
	private static String imageUploadPath = "E:/uploads/excels/";
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_NORMAL')")
	//@PreAuthorize("permitAll")
    @PostMapping("uploadExcel")
	//@PostMapping(value = "boardArticleList", consumes="application/json")
    public ResponseEntity<Object> uploadExcel(HttpServletRequest request) {
		
		JSONObject sheetsJsonObject = new JSONObject();
		
		//JSONArray jsonArray = new JSONArray();
		
		Workbook workbook = null;
		//Sheet sheet = null;
		InputStream inputStream = null;
		
		String serverFileName = getServerFileName();

		
		log.info("#########################################################");
		log.info("serverFileName : " + serverFileName);
		log.info("fileUploadPath : " + imageUploadPath);
		
		String uploadedFileName = "";
		String uploadedFileExt = "";
		
        try {
		    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		    Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		    MultipartFile multipartFile = null;
		    
		    if (!iterator.hasNext()) {
		    	return null;
		    }
		    
		    while (iterator.hasNext()) {
		    	
		        multipartFile = multipartHttpServletRequest.getFile(iterator.next());
		        uploadedFileName = multipartFile.getOriginalFilename();
		        
		        if (uploadedFileName.lastIndexOf(".") == -1) {
		        	return null;
		        } else {
		        	uploadedFileExt = uploadedFileName.substring(uploadedFileName.lastIndexOf(".") + 1).toLowerCase();
		        	//attachmentDTO.setAttachmentName(uploadedFileName.substring(0, uploadedFileName.lastIndexOf(".")));
		        	//attachmentDTO.setAttachmentExt(uploadedFileExt);
		        	//attachmentDTO.setAttachmentSize(multipartFile.getSize());
		        }
		        
		        if (multipartFile.isEmpty() == false) {
		        	log.info("------------- file start -------------");
		        	log.info("name : " + multipartFile.getName());
		        	log.info("filename : " + multipartFile.getOriginalFilename());
		        	log.info("size : " + multipartFile.getSize());
		        	log.info("ext : " + uploadedFileExt);
		        	log.info("-------------- file end --------------\n");
		        }
		        
		        String fileNew = null;
		        
		        /*
		        if ("4".equals(attachmentDTO.getBoardIdx())) {
		        	fileNew = customerUploadPath + serverFileName + "." + uploadedFileExt;
		        } else if ("img".equals(attachmentDTO.getBoardIdx())) {
		        	File directory = new File(imageUploadPath + serverFileName.substring(0, 4));
		        	if (!directory.exists()) {
		        		directory.mkdir();
						try {
							Runtime.getRuntime()
							  .exec(String.format("chmod o+x %s", directory));
						} catch (IOException e) {
							e.printStackTrace();
						}
		        	}
		        	*/
		        	fileNew = imageUploadPath + serverFileName.substring(0, 4) + "/" + serverFileName + "." + uploadedFileExt;
		        	
		        	String dirNew = imageUploadPath + serverFileName.substring(0, 4);
		        	File dirNewFile = new File(dirNew);
		        	if (!dirNewFile.exists()) {
		        		dirNewFile.mkdirs();
		        	}
		        	
		        /*
		        } else {
		        	File directory = new File(fileUploadPath + serverFileName.substring(0, 4));
		        	if (!directory.exists()) {
		        		directory.mkdir();
		        	}
		        	fileNew = fileUploadPath + serverFileName.substring(0, 4) + "/" + serverFileName + "." + uploadedFileExt;
		        }
		        */
		        
		        multipartFile.transferTo(new File(fileNew));
		        
		        /*
		        if (!fileUploadPath.startsWith("E:/")) {
					try {
						Runtime.getRuntime()
						  .exec(String.format("chmod o+r %s", fileNew));
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		        */
				
		        inputStream = new FileInputStream(fileNew);
		        
		        if ("xlsx".equals(uploadedFileExt)) {
		        	workbook = new XSSFWorkbook(inputStream);
		        } else if ("xls".equals(uploadedFileExt)) {
		        	workbook = new HSSFWorkbook(inputStream);
		        }
		        
		        log.info("NumberOfSheets : " + workbook.getNumberOfSheets());
		        //sheet = workbook.getSheetAt(0);
		        
		        /*
		        Row headerRow = sheet.getRow(0);
		        List<String> headers = new ArrayList<>();
		        for (Cell cell : headerRow) {
		            headers.add(cell.toString());
		            log.info(cell.toString());
		        }
		        jsonArray.put(headers);
		        
		        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
		            Row row = sheet.getRow(i);
		            List<String> rowData = new ArrayList<>();
		            for (Cell cell : row) {
		                rowData.add(cell.toString());
		            }
		            jsonArray.put(rowData);
		        }
		        */

		        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

		            JSONArray sheetArray = new JSONArray();
		            ArrayList<String> columnNames = new ArrayList<String>();
		            Sheet sheet = workbook.getSheetAt(i);
		            Iterator<Row> sheetIterator = sheet.iterator();

		            while (sheetIterator.hasNext()) {

		                Row currentRow = sheetIterator.next();
		                JSONObject jsonObject = new JSONObject();

		                if (currentRow.getRowNum() != 0) {

		                    for (int j = 0; j < columnNames.size(); j++) {

		                        if (currentRow.getCell(j) != null) {
		                            if (currentRow.getCell(j).getCellType() == CellType.STRING) {
		                                jsonObject.put(columnNames.get(j), currentRow.getCell(j).getStringCellValue());
		                            } else if (currentRow.getCell(j).getCellType() == CellType.NUMERIC) {
		                                jsonObject.put(columnNames.get(j), currentRow.getCell(j).getNumericCellValue());
		                            } else if (currentRow.getCell(j).getCellType() == CellType.BOOLEAN) {
		                                jsonObject.put(columnNames.get(j), currentRow.getCell(j).getBooleanCellValue());
		                            } else if (currentRow.getCell(j).getCellType() == CellType.BLANK) {
		                                jsonObject.put(columnNames.get(j), "");
		                            }
		                        } else {
		                            jsonObject.append(columnNames.get(j), "");
		                        }

		                    }

		                    log.info("jsonObject : " + jsonObject.toString());
		                    
		                    sheetArray.put(jsonObject);

		                } else {
		                    // store column names
		                    for (int k = 0; k < currentRow.getPhysicalNumberOfCells(); k++) {
		                        columnNames.add(currentRow.getCell(k).getStringCellValue());
		                    }
		                    
		                    log.info("columnNames : " + columnNames.toString());
		                }

		            }

		            sheetsJsonObject.put(workbook.getSheetName(i), sheetArray);

		        }

		        //return sheetsJsonObject;
		        
		    }
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        log.info("sheetsJsonObject : " + sheetsJsonObject.toString());
        
        Map<String, Object> mapping = null;
        
        try {
        	mapping = new ObjectMapper().readValue(sheetsJsonObject.toString(), HashMap.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
		//return new ResponseEntity<JSONObject>(sheetsJsonObject, HttpStatus.OK);
		return ResponseEntity.status(HttpStatus.OK).body(mapping);
    }

	private String getServerFileName() {
		UUID uuid = UUID.randomUUID();
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		return timeStamp + "-" + uuid;
	}
	
}
