package org.hyn.jpademo.repository;

import org.hyn.jpademo.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
