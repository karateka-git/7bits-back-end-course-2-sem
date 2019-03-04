package it.sevenbits.practice.controllers;


import it.sevenbits.practice.Item;
import it.sevenbits.practice.repository.ItemsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemsController {
    private final ItemsRepository itemsRepository;

    public ItemsController(ItemsRepository itemsRepository){
        this.itemsRepository = itemsRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Item> list() {
        return itemsRepository.getAllItems();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Item> create(@RequestBody Item newItem) {
        Item createdItem = itemsRepository.create(newItem);
        URI location = UriComponentsBuilder.fromPath("/items/")
                .path(String.valueOf(createdItem.getId()))
                .build().toUri();
        return ResponseEntity.created(location).body(createdItem);
    }
}

