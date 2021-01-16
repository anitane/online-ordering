package com.onlineordering.endpoint;

import com.onlineordering.domain.Item;
import com.onlineordering.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemEndpoint {

    ItemRepository itemRepository;

    @Autowired
    public ItemEndpoint(ItemRepository itemRepository) {this.itemRepository=itemRepository;}

    @GetMapping("/create")
    public List<Item> createItem() {
        Item item = new Item();
        item.setId(1);
        item.setName("Tost");
        item.setDescription("Cheese with Gils secret sauce");
        item.setPrice(35.5);
        item.setAvailable(0);
        itemRepository.save(item);
        item.setId(2);
        item.setName("Soup of the day");
        item.setDescription("every day - different soup");
        item.setPrice(15.0);
        item.setAvailable(0);
        itemRepository.save(item);
        item.setId(3);
        item.setName("Steak Burger");
        item.setDescription("onion, cheese , tomato , sauce");
        item.setPrice(55.0);
        item.setAvailable(0);
        itemRepository.save(item);
        item.setId(4);
        item.setName("French fries");
        item.setDescription("best fries you ever had");
        item.setPrice(15.0);
        item.setAvailable(0);
        itemRepository.save(item);
        item.setId(5);
        item.setName("Green salad");
        item.setDescription("lettuce,cucumber,cabbage");
        item.setPrice(25.5);
        item.setAvailable(0);
        itemRepository.save(item);
        return itemRepository.findAll();
    }

    @GetMapping("")
    public List<Item> getAllItem() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findItemById(@PathVariable(value = "id") Long itemId){
        Item item =itemRepository.
                findById(itemId).orElse(null);
        return ResponseEntity.ok().body(item);
    }
}
