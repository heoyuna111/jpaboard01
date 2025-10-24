package org.hyn.jpademo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="item")
@Getter
@Setter
@ToString
@Builder
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String itemNm;

    @Column(nullable = false, name = "price")
    @ColumnDefault(value = "1000")
    private int price;

//    @Column(nullable = false)
//    @ColumnDefault(value = "10")

    @Column(columnDefinition = "int default 10 not null")
    private int stockNumber;
    @Lob
    @Column(nullable = false)
    private String itemDetail;

    @Enumerated(EnumType.STRING)//ordinal:0,1,2 / string:판매중, 판매완료, 입고대기
    private ItemSellStatus itemSellStatus;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regTime;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

//    @Transient
    private String memo;
    @Transient
    private String remark;

}
