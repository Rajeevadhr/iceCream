package com.packageA;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
    /**
     * Search an item using its name
     * @param itemName
     * @return
     */
    Item findByItemName(String itemName);

    /**
     * Search an item using its id
     * @param id
     * @return
     */
    Item findItemById(int id);

    /**
     * Finds an item by name which contains a keyword
     * @param keyword
     * @return
     */
    List<Item> findItemsByItemNameContains(String keyword, Pageable pageable);

}
