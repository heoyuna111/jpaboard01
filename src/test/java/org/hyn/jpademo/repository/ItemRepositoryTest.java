package org.hyn.jpademo.repository;

import lombok.extern.log4j.Log4j2;
import org.hyn.jpademo.domain.Item;
import org.hyn.jpademo.domain.ItemSellStatus;
import org.hyn.jpademo.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void insertItem(){
        Item item = Item.builder()
                .itemNm("연필")
                .itemDetail("4B")
                .itemSellStatus(ItemSellStatus.판매중)
                .price(200)
                .stockNumber(100)
                .build();
        itemRepository.save(item);
    }
    @Test
    public void findbyid(){
        Optional<Item> item = itemRepository.findById(1L);
        log.info(item);
    }

    @Test
    public void updateItem(){
        Item item = itemRepository.findById(1L).get();
        item.setPrice(900);
        itemRepository.save(item);
    }

    @Test
    public void deleteItem(){
//        findBy
        itemRepository.deleteById(1L);
    }

}