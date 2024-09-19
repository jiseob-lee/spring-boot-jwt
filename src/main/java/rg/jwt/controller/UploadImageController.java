package rg.jwt.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class UploadImageController {
	
	private static String imageUploadPath = "E:/uploads/images/";
	
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_NORMAL')")
	//@PreAuthorize("permitAll")
    @PostMapping("uploadImage")
	//@PostMapping(value = "boardArticleList", consumes="application/json")
    public ResponseEntity<String> uploadImage(HttpServletRequest request) {


		try {
			
			String hostname = InetAddress.getLocalHost().getHostName();
			
			if ("jisblee.me".equals(hostname)) {
				imageUploadPath = "/home/tomcat/uploads/images/";
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
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
		        
		        File targetFile = new File(fileNew);
		        multipartFile.transferTo(targetFile);
		        
		        targetFile.setReadable(true, false);
		        
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
				
		    }
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return new ResponseEntity<String>("success", HttpStatus.OK);
    }

	private String getServerFileName() {
		UUID uuid = UUID.randomUUID();
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		return timeStamp + "-" + uuid;
	}
	
}
