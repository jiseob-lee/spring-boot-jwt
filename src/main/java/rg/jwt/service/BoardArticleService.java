package rg.jwt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.dto.CustomBoardArticleDto;
import rg.jwt.entity.Board;
import rg.jwt.entity.BoardArticle;
import rg.jwt.util.HibernateUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardArticleService {

	private final ModelMapper modelMapper;
	
	@Transactional
    public List<CustomBoardArticleDto> getBoardArticleList(int boardNo, int currentPage, int pageArticleCount) {
    	

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		//Session session = sessionFactory.openSession();

		session.beginTransaction();

		/*
		// Select all records at table of Employee
		Query query = session.createQuery("FROM Employee");

		List<Employee> empList = query.list();

		for (Employee emp: empList) {
			System.out.println(emp);
		}

		// Select specific record at table of Employee
		query = session.createQuery("FROM Employee WHERE empId = :id");
		query.setParameter("id", 1);
		Employee employee = (Employee) query.uniqueResult();
		System.out.println(employee);
		*/

		// Inner join between Employee and Address
		//Query query = session.createQuery("select c.boardArticleIdx, d.boardIdx from BoardArticle c INNER JOIN Board d ON c.boardIdx = d.boardIdx", BoardArticle.class);
		
		Query<Object[]> query = session.createQuery("from BoardArticle c INNER JOIN Board d ON c.boardIdx = d.boardIdx where c.boardIdx = :boardIdx", Object[].class);
		
		query.setParameter("boardIdx", boardNo);
		
		//log.info("#### currentPage : " + currentPage); 
		//log.info("#### pageArticleCount : " + pageArticleCount);
		
		query.setFirstResult((currentPage - 1) * pageArticleCount);
		query.setMaxResults(pageArticleCount);
		
		List<Object[]> addrList = query.getResultList();
		
		List<CustomBoardArticleDto> articleList = new ArrayList<>();
		
		for (Object[] arr: addrList) {
			//System.out.println(Arrays.toString(arr));
			BoardArticle boardArticle = (BoardArticle)arr[0];
			Board board = (Board)arr[1];
			//System.out.println(board.getBoardName() + ", " + boardArticle.getSubject() + ", " + boardArticle.getDateCreated());
			
			CustomBoardArticleDto customBoardArticle = modelMapper.map(boardArticle, CustomBoardArticleDto.class);

			//log.info("boardArticle.getSubject : " + boardArticle.getSubject());
			//log.info("customBoardArticle.getSubject : " + customBoardArticle.getSubject());
			//log.info("boardArticle.getBoardName : " + boardArticle.getBoard().getBoardName());
			//log.info("customBoardArticle.getBoardName : " + customBoardArticle.getBoard().getBoardName());
			//log.info("boardArticle.getDateCreated : " + boardArticle.getDateCreated());
			//log.info("customBoardArticle.getDateCreated : " + customBoardArticle.getDateCreated());
			//log.info("customBoardArticle.getDateModified : " + customBoardArticle.getDateModified());
			
			//log.info("제목 : " + customBoardArticle.getSubject());
			
			articleList.add(customBoardArticle);
		}

		session.getTransaction().commit();

		//HibernateUtil.getSessionFactory().close();
		
		//log.info("size : " + articleList.size());
		
		return articleList;
		
    }

	
	@Transactional
    public Long getBoardTotalCount(int boardNo) {
    	

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		//Session session = sessionFactory.openSession();

		session.beginTransaction();

		Query<Long> query = session.createQuery("select count(*) from BoardArticle c where c.boardIdx = :boardIdx", Long.class);
		
		query.setParameter("boardIdx", boardNo);
		
		query.setFirstResult(0);
		query.setMaxResults(10);
		
		Long boardtotalCount = query.getSingleResult();
		
		session.getTransaction().commit();

		//HibernateUtil.getSessionFactory().close();
		
		log.info("boardtotalCount : " + boardtotalCount);
		
		return boardtotalCount;
		
    }
	
	
	@Transactional
    public BoardArticle getBoardContent(int articleId) {
    	

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		//Session session = sessionFactory.openSession();

		session.beginTransaction();

		Query<BoardArticle> query = session.createQuery("from BoardArticle c where c.boardArticleIdx = :boardArticleIdx", BoardArticle.class);
		
		query.setParameter("boardArticleIdx", articleId);
		
		//query.setFirstResult(0);
		//query.setMaxResults(10);
		
		BoardArticle boardArticle = query.getSingleResult();
		
		session.getTransaction().commit();

		//HibernateUtil.getSessionFactory().close();
		
		//log.info("boardtotalCount : " + boardtotalCount);
		
		return boardArticle;
		
    }

}
