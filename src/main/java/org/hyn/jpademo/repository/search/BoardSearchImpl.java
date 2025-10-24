package org.hyn.jpademo.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.hyn.jpademo.domain.Board;
import org.hyn.jpademo.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard qboard = QBoard.board;
        JPQLQuery<Board> query=from(qboard);
        BooleanBuilder builder=new BooleanBuilder();
        builder.or(qboard.title.containsIgnoreCase("10"));   //title like '%10%' or
        builder.or(qboard.content.containsIgnoreCase("10")); //content like '%10%'
        builder.or(qboard.author.containsIgnoreCase("10"));
        query.where(builder);   //where (title like '%10%' or content like '%10%' or author like '%10%') and bno>0 limit 0,5 -> title, content, author 중에 -10- 이 있는 값을 찾아라
        query.where(qboard.bno.gt(0));  //bno값이 0보다 크면 다 호출
        this.getQuerydsl().applyPagination(pageable, query);    //
        List<Board> list=query.fetch();
        long count=query.fetchCount();  //전체 레코드 수

        return new PageImpl<Board>(list,pageable,count);

    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard qboard = QBoard.board;
        JPQLQuery<Board> query=from(qboard);

        if(types!=null && types.length>0 && keyword!=null){
            BooleanBuilder builder=new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "t":
                        builder.or(qboard.title.containsIgnoreCase(keyword));
                        break;
                    case "c":
                        builder.or(qboard.content.containsIgnoreCase(keyword));
                        break;
                    case "w":
                        builder.or(qboard.author.containsIgnoreCase(keyword));
                        break;
                }
            }
            query.where(builder);
        }
        query.where(qboard.bno.gt(0));
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list=query.fetch();
        long count=query.fetchCount();
        return new PageImpl<>(list,pageable,count);
    }
}
