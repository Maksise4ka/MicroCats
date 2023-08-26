package com.maksise4ka.microcats.cats;

import com.maksise4ka.microcats.cats.services.CatService;
import com.maksise4ka.microcats.contracts.CatDto;
import com.maksise4ka.microcats.contracts.cats.requests.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "cats", groupId = "cats-listeners")
public class CatsListener {

    private final CatService catService;

    @KafkaHandler
    public void changeName(ChangeCatName command) {
        catService.changeName(command.catId(), command.newName(), command.requesterUsername());

        log.info("Called changeName method");
    }

    @KafkaHandler
    public void changeBirthdate(ChangeCatBirthdate command) {
        catService.changeBirthdate(command.catId(), command.birthdate(), command.requesterUsername());

        log.info("Called changeBirthdate method");
    }

    @KafkaHandler
    @SendTo
    public CatDto get(GetCat command) {
        CatDto cat = catService.get(command.catId(), command.requesterUsername());
        log.info("Called get method");

        return cat;
    }

    @KafkaHandler
    @SendTo
    public CatDto[] getAll(GetAllCats command) {
        CatDto[] cats = catService.getAll(command.requesterUsername()).toArray(new CatDto[0]);
        log.info("Called getAll method");

        return cats;
    }

    @KafkaHandler
    public void delete(DeleteCat command) {
        catService.delete(command.catId(), command.requesterUsername());

        log.info("Called delete method");
    }

    @KafkaHandler
    @SendTo
    public CatDto[] filter(FilterCats command) {
        CatDto[] cats = catService.filter(
                command.colors(),
                command.breeds(),
                command.requesterUsername()).toArray(new CatDto[0]);
        log.info("Called filter method");

        return cats;
    }

    @KafkaHandler(isDefault = true)
    public void object(Object object) {
        log.warn("Unknown type" + object);
    }
}
