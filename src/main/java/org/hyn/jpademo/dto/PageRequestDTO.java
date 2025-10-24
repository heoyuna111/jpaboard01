package org.hyn.jpademo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int page=1;
    @Builder.Default
    private int size=3;

    private String link;
    private String type;
    private String keyword;

    public String[] getTypes(){
        if(type==null || type.isEmpty()){
            return null;
        }
        return type.split(""); //twc=>tupes[0]="t", types[1]="w", ...
    }

    public Pageable getPageable(String...props){
        return PageRequest.of(this.page-1, size, Sort.by(props).descending());
    }
//    jpa는 0번부터 인식.
//    내가 전달하는 페이지는 실제 1부터 시작하므로, 그 값을 맞추기 위해 현재 페이지-1을 한 값을 전달함

    public String getLink(){
        if(link==null){
            StringBuilder builder=new StringBuilder();
            builder.append("page="+this.page);
            builder.append("&size="+this.size);
            if(type!=null && type.length()>0){
                builder.append("&type="+type);
            }
            if(keyword!=null && keyword.length()>0){
                builder.append("&keyword="+keyword);
            }
            link=builder.toString();
        }
        return link;
    }
}
