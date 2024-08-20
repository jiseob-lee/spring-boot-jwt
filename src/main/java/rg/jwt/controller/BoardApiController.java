package rg.jwt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.dto.CustomBoardArticleDto;
import rg.jwt.dto.CustomBoardDto;
import rg.jwt.entity.BoardArticle;
import rg.jwt.service.BoardArticleService;
import rg.jwt.service.BoardNameService;
import rg.jwt.service.BoardService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class BoardApiController {
	
	private final BoardArticleService boardArticleService;
	
	private final BoardNameService boardNameService;
	
	private final BoardService boardService;
	
	//@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_NORMAL')")
    @PostMapping("boardArticleList")
	//@PostMapping(value = "boardArticleList", consumes="application/json")
    public ResponseEntity<List<CustomBoardArticleDto>> getBoardArticleList(//HttpServletRequest request//, 
    		//@RequestParam Map<String, String> paramMap
    		//, 
    		@RequestBody Map<String, Object> input
    		) {

    	int boardNo = 0;
    	int currentPage = 1;
    	int pageArticleCount = 10;
    	
    	if (input != null) {
    		log.info("string : " + input.get("boardNo"));
    		if (input.get("boardNo") != null && !"".equals(input.get("boardNo"))) {
    			boardNo = Integer.parseInt(String.valueOf(input.get("boardNo")));
    		}
    		if (input.get("currentPage") != null && !"".equals(input.get("currentPage"))) {
    			currentPage = Integer.parseInt(String.valueOf(input.get("currentPage")));
    		}
    		if (input.get("pageArticleCount") != null && !"".equals(input.get("pageArticleCount"))) {
    			pageArticleCount = Integer.parseInt(String.valueOf(input.get("pageArticleCount")));
    		}
    	}
		
    	//log.info("boardNo : " + request.getParameter("boardNo"));
    	
    	
    	//Enumeration<String> paramEnum = request.getParameterNames();
    	
    	//while (paramEnum.hasMoreElements()) {
    		//log.info("paramIter2 : " + paramEnum.nextElement());
    	//}
    	
    	//Iterator<String> paramIter1 = paramEnum.asIterator();
    	//while (paramIter1.hasNext()) {
    		//log.info("paramIter1 : " + paramIter1.next());
    	//}
    	
    	//Map <String, String[]> param = request.getParameterMap();
    	//log.info("param : " + param);
    	
    	//Set<String> paramSet = param.keySet();
    	//Iterator<String> paramIter = paramSet.iterator();
    	//while (paramIter.hasNext()) {
    		//log.info("param iter : " + param.get(paramIter.next()));
    	//}
    	
		//log.info("paramMap : " + paramMap);
		//log.info("boardNo : " + boardNo);
		
    	if (boardNo == 0) {
    		return new ResponseEntity<List<CustomBoardArticleDto>>(new ArrayList<>(), HttpStatus.OK);
    	}
    	
    	//log.info("#### boardNo : " + boardNo);
    	//log.info("#### currentPage : " + currentPage);
    	//log.info("#### pageArticleCount : " + pageArticleCount);
    	
		List<CustomBoardArticleDto> articleList = boardArticleService.getBoardArticleList(boardNo, currentPage, pageArticleCount);
		
		//log.info("#### size : " + articleList.size());
		
		//if (articleList != null && articleList.size() > 0) {
			//for (int i=0; i < articleList.size(); i++) {
				//CustomBoardArticleDto article = articleList.get(i);
				//log.info("Subject : " + article.getSubject());
			//}
		//}
		
		return new ResponseEntity<List<CustomBoardArticleDto>>(articleList, HttpStatus.OK);
		
    }

	
	@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_NORMAL')")
	//@PreAuthorize("permitAll")
    @GetMapping("boardNameList")
	//@PostMapping(value = "boardArticleList", consumes="application/json")
    public ResponseEntity<List<CustomBoardDto>> getBoardNameList() {
		
		List<CustomBoardDto> boardList = boardNameService.getBoardNameList();
		
		//log.info("size : " + boardList.size());
		
		//if (boardList != null && boardList.size() > 0) {
			//for (int i=0; i < boardList.size(); i++) {
				//CustomBoardDto board = boardList.get(i);
				//log.info("boardName : " + board.getBoardName());
			//}
		//}
		
		return new ResponseEntity<List<CustomBoardDto>>(boardList, HttpStatus.OK);
		
    }
	

	//@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_NORMAL')")
	@PreAuthorize("permitAll")
    @GetMapping("boardTotalCount")
	//@PostMapping(value = "boardArticleList", consumes="application/json")
    public ResponseEntity<Long> getBoardTotalCount(HttpServletRequest request) {
		
		Long boardTotalCount = 0L;
		
		String boardNo = request.getParameter("boardNo");
		if (boardNo == null || "".equals(boardNo) || "undefined".equals(boardNo) || "null".equals(boardNo)) {
			return new ResponseEntity<Long>(boardTotalCount, HttpStatus.OK);
		}
		
		boardTotalCount = boardArticleService.getBoardTotalCount(Integer.parseInt(boardNo));
		
		//log.info("boardTotalCount : " + boardTotalCount);
		
		return new ResponseEntity<Long>(boardTotalCount, HttpStatus.OK);
		
    }
	

	//@PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_NORMAL')")
	@PreAuthorize("permitAll")
    @GetMapping("getBoardContent")
	//@PostMapping(value = "boardArticleList", consumes="application/json")
    public ResponseEntity<BoardArticle> getBoardContent(HttpServletRequest request) {
		
		BoardArticle boardArticle = null;
		
		String articleId = request.getParameter("articleId");
		
		if (articleId == null || "".equals(articleId) || "undefined".equals(articleId) || "null".equals(articleId)) {
			return new ResponseEntity<BoardArticle>(boardArticle, HttpStatus.OK);
		}
		
		boardArticle = boardArticleService.getBoardContent(Integer.parseInt(articleId));
		
		return new ResponseEntity<BoardArticle>(boardArticle, HttpStatus.OK);
		
    }

	@PreAuthorize("permitAll")
    @GetMapping("getBoardList")
	//@PostMapping(value = "boardArticleList", consumes="application/json")
    public ResponseEntity<List<String>> getBoardList(HttpServletRequest request) {
		
		List<String> boardList = boardService.selectBoardList();
		
		return new ResponseEntity<List<String>>(boardList, HttpStatus.OK);
		
    }
	
}
