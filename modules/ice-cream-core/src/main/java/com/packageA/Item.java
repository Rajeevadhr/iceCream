package com.packageA;

import javax.persistence.*;

/**
 * Tells Hibernate to make a table out of this class
 *
 */
@Entity
@Table(name="item")
public class Item {
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="item_name")
    private String itemName;

    @Column(name="item_price")
    private float price;

    public void setId(int id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public float getPrice() {
        return price;
    }
}