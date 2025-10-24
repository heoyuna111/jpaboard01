package org.hyn.jpademo.service;

import org.hyn.jpademo.domain.Board;
import org.hyn.jpademo.dto.BoardDTO;
import org.hyn.jpademo.dto.PageRequestDTO;
import org.hyn.jpademo.dto.PageResponseDTO;

import java.util.List;

public interface BoardService {
    Long insertBoard(BoardDTO board);
    List<BoardDTO> findAllBoards();
    BoardDTO findBoardById(Long bno, Integer mode);
    void updateBoard(BoardDTO board);
    void deleteBoard(Long bno);

    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);



    default Board dtoToEntity(BoardDTO boardDTO){
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .author(boardDTO.getAuthor())
                .build();
        return board;
    }
    default BoardDTO entityToDTO(Board board){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .readcount(board.getReadcount())
                .regDate(board.getRegDate())
                .updateDate(board.getUpdateDate())
                .build();
        return boardDTO;
    }
}
