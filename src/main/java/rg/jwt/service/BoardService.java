package rg.jwt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rg.jwt.mapper.BoardMapper;

@Service
@RequiredArgsConstructor
public class BoardService {
 
	private final BoardMapper boardMapper;

    public List<String> selectBoardList() {
        return boardMapper.selectBoardList();
    }
}