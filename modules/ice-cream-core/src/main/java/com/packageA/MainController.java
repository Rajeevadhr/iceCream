package com.packageA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the main controlling methods
 *
 * Created by rajeeva on 2/20/18.
 */
@Controller
public class MainController {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * loads the home page of the site
     * @return file name of the home page
     */
    @GetMapping("/")
    public String showMainPage(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,Model model) {
        if (keyword.isEmpty()) {
            model.addAttribute("itemList", itemRepository.findAll());
            return "welcome";
        } else {
            model.addAttribute("itemList", itemRepository.findItemByItemNameContains(keyword));
            return "welcome";
        }
    }

    /**
     * method related to the invoice page
     * @param model
     * @return
     */
    @GetMapping("/invoice")
    public String showInvoicePage(Model model) {
        model.addAttribute("itemList", itemRepository.findAll());
        return "invoice";
    }

    @GetMapping("/receipt")
    public String printReceipt(@RequestParam("items") String[] itemIdArray,Model model) {
        int itemId;
        List<Item> itemList = new ArrayList<>();
        Item item;
        float total = 0;

        for (int i = 0; i < itemIdArray.length; i++) {
            itemId = Integer.parseInt(itemIdArray[i]);
            item = itemRepository.findItemById(itemId);
            itemList.add(item);
            total += item.getPrice();
        }
        model.addAttribute("itemList", itemList);
        model.addAttribute("total", total);
        return "receipt";
    }

    /**
     * Adds an item to the database
     * This method is only used in test cases
     * @param item object of the item that needs to be saved
     * @return
     */
    @PostMapping(value = "/save")
    public @ResponseBody String addNewItem (@RequestBody Item item) {
        try {
            itemRepository.save(item);
            return "Successfully saved";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds an item to the database
     * @param itemName
     * @param itemPrice
     * @return
     */
    @PostMapping("/addItem")
    public String addItem(@RequestParam("itemName") String itemName,
                          @RequestParam("itemPrice") String itemPrice){

        Item newItem = new Item();

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

    /**
     * Loads all the items from the database
     * @param model
     * @return
     */
    @GetMapping(path="/all")
    public String getAllItems (Model model) {
        try {
            model.addAttribute("itemList",itemRepository.findAll());
            return "showAll";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds an item using its item id
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping(path="/showItem")
    public String showItem(@RequestParam(value = "itemId") String itemId, Model model) {
        try {
            Item item = itemRepository.findItemById(Integer.parseInt(itemId));
            model.addAttribute("itemId", item.getId());
            model.addAttribute("itemName", item.getItemName());
            model.addAttribute("itemPrice", item.getPrice());
            return "showItem";
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an item in the database
     * @param id
     * @param itemName
     * @param price
     * @return
     */
    @PutMapping(value = "/editPrice")
    public @ResponseBody String editPrice (@RequestParam("id") int id,
                                           @RequestParam("name") String itemName,
                                           @RequestParam("price") float price) {
        Item item = new Item();

        item.setId(id);
        item.setItemName(itemName);
        item.setPrice(price);
        try {
            itemRepository.save(item);
            return "Successfully edited";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes an item from the database using its id
     * @param id
     * @return
     */
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

    /**
     * Finds an item using its name
     * @param itemName
     * @param model
     * @return
     */
    @RequestMapping(value = "/findItem", method = RequestMethod.GET)
    public String selectItem (@RequestParam("itemName") String itemName, Model model) {
        try {
            Item item = itemRepository.findByItemName(itemName);
            model.addAttribute(item);
            return "findItem";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
