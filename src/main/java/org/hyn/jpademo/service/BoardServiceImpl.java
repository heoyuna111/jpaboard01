//service 인터페이스의 디폴트는 구현 X
package org.hyn.jpademo.service;


import jdk.jfr.Label;
import lombok.extern.log4j.Log4j2;
import org.hyn.jpademo.domain.Board;
import org.hyn.jpademo.dto.BoardDTO;
import org.hyn.jpademo.dto.PageRequestDTO;
import org.hyn.jpademo.dto.PageResponseDTO;
import org.hyn.jpademo.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Long insertBoard(BoardDTO boardDTO) {
        Board board = dtoToEntity(boardDTO);
        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public List<BoardDTO> findAllBoards() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDTO> dtos = new ArrayList<>();
        for (Board board : boards) {
            dtos.add(entityToDTO(board));
        }
        return dtos;
    }

    @Override
    public BoardDTO findBoardById(Long bno, Integer mode) {
        //dto일 때 해야하는 작업
        Board board = boardRepository.findById(bno).orElse(null);//Optional
        if(mode==1){
            board.updateReadcount();
            boardRepository.save(board);
        }
        return entityToDTO(board); //entity로 된 board를 dto로 변환하여 전달
    }

    @Override
    public void updateBoard(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getBno()).orElse(null);
        board.change(boardDTO.getTitle(), boardDTO.getContent());
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Long bno) {
        boardRepository.deleteById(bno);
    }
//페이징처리
    @Override
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("bno");
//        Page<Board> result = boardRepository.findAll(pageable);
//        Page<Board> result = boardRepository.findKeyword(pageRequestDTO.getKeyword(),pageable);

        Page<Board> result = boardRepository.searchAll(
                pageRequestDTO.getTypes(),
                pageRequestDTO.getKeyword(),
                pageable);

        List<BoardDTO> dtoList = result.getContent().stream()
                .map(board -> entityToDTO(board))
                .collect(Collectors.toList());
        int total = (int)result.getTotalElements();

        PageResponseDTO<BoardDTO> responseDTO = PageResponseDTO.<BoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();

        return responseDTO;
    }

}
