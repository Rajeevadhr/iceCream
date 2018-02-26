package com.packageA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by rajeeva on 2/20/18.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping(value = "/save")
    public @ResponseBody String addNewItem (@RequestBody Item item) {
        itemRepository.save(item);
        return "Successfully saved";
    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam("itemName") String itemName,
                          @RequestParam("itemPrice") String itemPrice){

        Item newItem = new Item();
        ItemController itemController = new ItemController();

        newItem.setItemName(itemName);
        newItem.setPrice(Float.parseFloat(itemPrice));
        try {
            itemRepository.save(newItem);
            return "addItem";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(path="/all")
    public Iterable<Item> getAllItems (Model model) {
        return itemRepository.findAll();
    }

    @GetMapping(path="/showItem")
    public String showItem(@RequestParam(value = "itemId") String itemId, Model model) {
        Item item = itemRepository.findItemById(Integer.parseInt(itemId));

        model.addAttribute("itemId", item.getId());
        model.addAttribute("itemName", item.getItemName());
        model.addAttribute("itemPrice", item.getPrice());

        return "showItem";
    }

    @PutMapping(value = "/editPrice")
    public @ResponseBody String editPrice (@RequestParam("id") int id,
                                           @RequestParam("name") String itemName,
                                           @RequestParam("price") float price) {
        Item item = new Item();

        item.setId(id);
        item.setItemName(itemName);
        item.setPrice(price);
        itemRepository.save(item);
        return "Successfully edited";
    }

    @DeleteMapping(value = "/deleteItem")
    public @ResponseBody String deleteItem (@RequestParam("id") int id) {
        Item item = new Item();

        item.setId(id);
        try {
            itemRepository.delete(item);
            return "deleteItem";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/selectItem", method = RequestMethod.GET)
    public @ResponseBody List<Item> selectItem (@RequestParam("itemName") String itemName) {
        return itemRepository.findByItemName(itemName);
    }

}
