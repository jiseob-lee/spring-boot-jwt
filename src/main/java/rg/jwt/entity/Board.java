package rg.jwt.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="board",
	uniqueConstraints={@UniqueConstraint(columnNames={"board_idx"})})
public class Board {
	/*
	board_idx
	board_name
	date_created
	user_id_created
	date_modified
	user_id_modified
	board_name_en
	*/
	
	private int boardIdx;
	private String boardName;
	
	//@Temporal(TemporalType.TIMESTAMP)
	private Timestamp dateCreated;
	
	private String userIdCreated;
	
	//@Temporal(TemporalType.TIMESTAMP)
	private Timestamp dateModified;
	
	private String userIdModified;
	private String boardNameEn;
	
	@Id
	@Column(name="board_idx", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	
	@Column(name="board_name", length=100, nullable=false)
	public String getBoardName() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
	@Column(name="user_id_created", length=30, nullable=false)
	public String getUserIdCreated() {
		return userIdCreated;
	}
	public void setUserIdCreated(String userIdCreated) {
		this.userIdCreated = userIdCreated;
	}
	
	@Column(name="user_id_modified", length=30)
	public String getUserIdModified() {
		return userIdModified;
	}
	public void setUserIdModified(String userIdModified) {
		this.userIdModified = userIdModified;
	}
	
	@Column(name="board_name_en", length=300)
	public String getBoardNameEn() {
		return boardNameEn;
	}
	public void setBoardNameEn(String boardNameEn) {
		this.boardNameEn = boardNameEn;
	}
	
	@Column(name="date_created")
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	@Column(name="date_modified")
	public Timestamp getDateModified() {
		return dateModified;
	}
	public void setDateModified(Timestamp dateModified) {
		this.dateModified = dateModified;
	}
	
	
}
