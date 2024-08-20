package rg.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
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
