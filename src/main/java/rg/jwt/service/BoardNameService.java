package rg.jwt.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import rg.jwt.dto.CustomBoardDto;
import rg.jwt.entity.Board;
import rg.jwt.util.HibernateUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardNameService {

	private final ModelMapper modelMapper;
	
	@Transactional
    public List<CustomBoardDto> getBoardNameList() {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		//Session session = sessionFactory.openSession();

		session.beginTransaction();

		Query<Board> query = session.createQuery("from Board", Board.class);
		
		List<Board> queryList = query.getResultList();
		
		List<CustomBoardDto> boardList = new ArrayList<>();
		
		for (Board obj: queryList) {
			Board board = obj;
			
			CustomBoardDto customBoard = modelMapper.map(board, CustomBoardDto.class);
			
			boardList.add(customBoard);
		}

		session.getTransaction().commit();

		//HibernateUtil.getSessionFactory().close();
		
		log.info("size : " + boardList.size());
		
		return boardList;
		
    }

}
