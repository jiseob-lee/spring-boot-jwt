package rg.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rg.jwt.entity.BoardArticle;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@JsonInclude(Include.NON_NULL)
public class CustomBoardArticleDto extends BoardArticle {
	
	private int boardArticleIdx;
	
	private int boardIdx;
	
	private String subject;
	
	private String content;
	
	private int hitCount;
	
	private String dateCreated;
	
	private String userIdCreated;
	
	private String dateModified;
	
	private String userIdModified;
	
	private char deleteYn;
	
	private char openYn;
	
	private String subjectEng;
	
	private String contentEng;
	
	private String boardName;
	
	private String boardNameEn;
	
	
	
	public CustomBoardArticleDto(int boardIdx, String subject, String content, int hitCount, String dateCreated,
			String userIdCreated, String dateModified, String userIdModified, char deleteYn, char openYn,
			String subjectEng, String contentEng, String boardName, String boardNameEn) {
		super(boardIdx, subject, content, hitCount, dateCreated,
				userIdCreated, dateModified, userIdModified, deleteYn, openYn,
				subjectEng, contentEng);
		this.boardIdx = boardIdx;
		this.subject = subject;
		this.content = content;
		this.hitCount = hitCount;
		this.dateCreated = dateCreated;
		this.userIdCreated = userIdCreated;
		this.dateModified = dateModified;
		this.userIdModified = userIdModified;
		this.deleteYn = deleteYn;
		this.openYn = openYn;
		this.subjectEng = subjectEng;
		this.contentEng = contentEng;
		this.boardName = boardName;
		this.boardNameEn = boardNameEn;
	}
	
	
	public String getDateCreated() {
		if (dateCreated == null) {
			return "";
		}
		return dateCreated;
	}
	public String getDateModified() {
		if (dateModified == null) {
			return "";
		}
		return dateModified;
	}
}
