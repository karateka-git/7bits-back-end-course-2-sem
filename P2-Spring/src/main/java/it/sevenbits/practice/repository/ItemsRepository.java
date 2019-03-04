package it.sevenbits.practice.repository;

import it.sevenbits.practice.Item;

import java.util.List;

public interface ItemsRepository {
    List<Item> getAllItems();
    Item create(Item item);
}
