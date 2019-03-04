package it.sevenbits.practice.repository;

import it.sevenbits.practice.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleItemsRepository implements ItemsRepository {
    private List<Item> items = new ArrayList<>();
    private long idCounter = 0;

    @Override
    public List<Item> getAllItems() {
        return Collections.unmodifiableList(items);
    }
    @Override
    public Item create(Item item) {
        Item newItem = new Item(getNextID(), item.getName());
        items.add(newItem);
        return newItem;
    }
    private long getNextID(){
        return ++idCounter; //it's very simplified version of id generator
    }
}
