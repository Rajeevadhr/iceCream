/*
 * (C) Copyright 2010-2018 hSenid Mobile Solutions (Pvt) Limited.
 * All Rights Reserved.
 *
 * These materials are unpublished, proprietary, confidential source code of
 * hSenid Mobile Solutions (Pvt) Limited and constitute a TRADE SECRET
 * of hSenid Mobile Solutions (Pvt) Limited.
 *
 * hSenid Mobile Solutions (Pvt) Limited retains all title to and intellectual
 * property rights in these materials.
 */

package com.packageA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.log4j.Logger;

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

    private Logger logger = Logger.getLogger(MainController.class.getName());

    /**
     * loads the home page of the site
     * @return file name of the home page
     */
    @GetMapping("/")
    public String showWelcomePage (@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                               @RequestParam(value = "page", required = false, defaultValue = "0") String page,
                               Model model) {
        int numberOfRecords;
        int numberOfPages;
        int pageSize = 5;

        Pageable pageable = new PageRequest(Integer.parseInt(page), pageSize);
        try {
            List<Item> itemList = itemRepository.findItemsByItemNameContains(keyword, pageable);
            numberOfRecords = itemRepository.countByItemNameContains(keyword);
            numberOfPages = (int) Math.ceil(numberOfRecords/(float)pageSize);
            model.addAttribute("numberOfPages",numberOfPages);
            model.addAttribute("itemList", itemList);
            model.addAttribute("keyword", keyword);
            model.addAttribute("page", page);
        } catch (Exception e) {
            logger.error("This is an error message", new Exception("myException"));
        }
        logger.trace("This is a trace message");
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warning");
        return "welcome";
    }

    /**
     * method related to the invoice page
     * @param model
     * @return
     */
    @GetMapping("/invoice")
    public String showInvoicePage(Model model) {
        try {
            model.addAttribute("itemList", itemRepository.findAll());
        } catch (Exception e) {
            logger.error("Invoice error", e);
        }
        return "invoice";
    }

    /**
     * Method related to printing the receipt
     * @param itemIdArray
     * @param model
     * @return
     */
    @GetMapping("/receipt")
    public String printReceipt(@RequestParam("items") String[] itemIdArray,Model model) {
        int itemId;
        List<Item> itemList = new ArrayList<>();
        Item item;
        float total = 0;

        try {
            for (int i = 0; i < itemIdArray.length; i++) {
                itemId = Integer.parseInt(itemIdArray[i]);
                item = itemRepository.findItemById(itemId);
                itemList.add(item);
                total += item.getPrice();
            }
            model.addAttribute("itemList", itemList);
            model.addAttribute("total", total);
        } catch (Exception e) {
            logger.error("Receipt error",e);
        }
        return "receipt";
    }

    /**
     * Loads the main page
     * @return
     */
    @GetMapping("/main")
    public String showMainPage() {
        return "mainPage";
    }

    /**
     * Adds an item to the database
     * This method is only used in test cases
     * @param item object of the item that needs to be saved
     * @return
     */
    @PostMapping(value = "/save")
    public @ResponseBody String addNewItem (@RequestBody Item item) {
        itemRepository.save(item);
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
        itemRepository.save(newItem);
        return "addItem";
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
            logger.debug("Debug message of the page all");
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
        } catch (Exception e) {
            logger.error("Show item error",e);
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
