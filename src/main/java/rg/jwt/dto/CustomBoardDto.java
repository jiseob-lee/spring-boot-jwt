package rg.jwt.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rg.jwt.entity.Board;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@JsonInclude(Include.NON_NULL)
public class CustomBoardDto extends Board {

	private int boardIdx;
	private String boardName;
	
	private Timestamp dateCreated;
	
	private String userIdCreated;
	
	private Timestamp dateModified;
	
	private String userIdModified;
	private String boardNameEn;
	
}
