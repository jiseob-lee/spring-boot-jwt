package rg.jwt.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import rg.jwt.util.DateTimeUtil;

@Entity
@Table(name="board_article",
	uniqueConstraints={@UniqueConstraint(columnNames={"board_article_idx"})})
public class BoardArticle {
	/*
	board_article_idx
	board_idx
	subject
	content
	hit_count
	date_created
	user_id_created
	date_modified
	user_id_modified
	delete_yn
	open_yn
	subject_eng
	content_eng
	*/
	
	private int boardArticleIdx;
	private int boardIdx;
	private String subject;
	private String content;
	private int hitCount;
	
	@Column(name = "date_created", columnDefinition="DATETIME")
	//@Temporal(TemporalType.TIMESTAMP)
	private Timestamp dateCreated;
	
	private String userIdCreated;
	
	@Column(name = "date_modified", columnDefinition="DATETIME")
	//@Temporal(TemporalType.TIMESTAMP)
	private Timestamp dateModified;
	
	private String userIdModified;
	private char deleteYn;
	private char openYn;
	private String subjectEng;
	private String contentEng;
	
	private Board board;
	
	@Id
	@Column(name="board_article_idx", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getBoardArticleIdx() {
		return boardArticleIdx;
	}
	public void setBoardArticleIdx(int boardArticleIdx) {
		this.boardArticleIdx = boardArticleIdx;
	}
	
	@Column(name="board_idx", nullable=false)
	public int getBoardIdx() {
		return boardIdx;
	}
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}
	
	@Column(name="subject", nullable=false)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="hit_count", nullable=false)
	public int getHitCount() {
		return hitCount;
	}
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	
	@Column(name="user_id_created", nullable=false)
	public String getUserIdCreated() {
		return userIdCreated;
	}
	public void setUserIdCreated(String userIdCreated) {
		this.userIdCreated = userIdCreated;
	}
	
	@Column(name="user_id_modified")
	public String getUserIdModified() {
		return userIdModified;
	}
	public void setUserIdModified(String userIdModified) {
		this.userIdModified = userIdModified;
	}
	
	@Column(name="delete_yn")
	public char getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(char deleteYn) {
		this.deleteYn = deleteYn;
	}
	
	@Column(name="open_yn")
	public char getOpenYn() {
		return openYn;
	}
	public void setOpenYn(char openYn) {
		this.openYn = openYn;
	}
	
	@Column(name="subject_eng")
	public String getSubjectEng() {
		return subjectEng;
	}
	public void setSubjectEng(String subjectEng) {
		this.subjectEng = subjectEng;
	}
	
	@Column(name="content_eng")
	public String getContentEng() {
		return contentEng;
	}
	public void setContentEng(String contentEng) {
		this.contentEng = contentEng;
	}

	@ManyToOne
	//@JoinTable(name="board", 
		//joinColumns = @JoinColumn(name="board_idx"),
		//foreignKey = @ForeignKey(name = "board_idx"), 
	    //inverseJoinColumns = @JoinColumn(name = "board_idx"),
	    //inverseForeignKey = @ForeignKey(name = "board_idx")
	//)
	@JoinColumn(name="board_idx", insertable = false, updatable = false)
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}

	@Column(name="date_created")
	public String getDateCreated() {
		return DateTimeUtil.returnTimestamp(dateCreated);
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = DateTimeUtil.parseTimestamp(dateCreated);
	}

	@Column(name="date_modified")
	public String getDateModified() {
		return DateTimeUtil.returnTimestamp(dateModified);
	}
	public void setDateModified(String dateModified) {
		this.dateModified = DateTimeUtil.parseTimestamp(dateModified);
	}
}
