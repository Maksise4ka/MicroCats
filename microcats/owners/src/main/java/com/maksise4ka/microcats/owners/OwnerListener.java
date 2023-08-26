package com.maksise4ka.microcats.owners;

import com.maksise4ka.microcats.contracts.CatDto;
import com.maksise4ka.microcats.contracts.OwnerDto;
import com.maksise4ka.microcats.contracts.owners.requests.*;
import com.maksise4ka.microcats.owners.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@KafkaListener(topics = "owners", groupId = "multi")
public class OwnerListener {
    private final OwnerService ownerService;

    @Autowired
    public OwnerListener(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @KafkaHandler
    @SendTo
    public OwnerDto createOwner(CreateOwner command) {
        OwnerDto owner = ownerService.create(
                command.name(),
                command.birthdate(),
                command.username(),
                command.password()
        );
        log.info("Created owner: ");

        return owner;
    }

    @KafkaHandler
    @SendTo
    public CatDto petCat(PetCat command) {
        CatDto cat = ownerService.petCat(
                command.name(),
                command.birthdate(),
                command.breed(),
                command.color(),
                command.ownerId(),
                command.requesterUsername()
        );
        log.info("Pet cat: ");

        return cat;
    }

    @KafkaHandler
    public void changeName(ChangeName command) {
        ownerService.changeName(command.ownerId(), command.name(), command.requesterUsername());

        log.info("Change Name: ");
    }

    @KafkaHandler
    public void changeBirthdate(ChangeBirthdate command) {
        ownerService.changeBirthdate(command.ownerId(), command.birthdate(), command.requesterUsername());

        log.info("Change Birthdate: ");
    }

    @KafkaHandler
    @SendTo
    public OwnerDto get(GetOwner command) {
        OwnerDto owner = ownerService.get(command.ownerId(), command.requesterUsername());
        log.info("Get owner: ");

        return owner;
    }

    @KafkaHandler
    @SendTo
    public OwnerDto[] getAll(GetAll command) {
        OwnerDto[] owners = ownerService.getAll(command.requesterUsername()).toArray(new OwnerDto[0]);
        log.info("Get All Owners: " + owners.length);

        return owners;
    }

    @KafkaHandler
    public void delete(DeleteOwner command) {
        ownerService.delete(command.ownerId(), command.requesterUsername());

        log.info("Delete Owner: ");
    }

    @KafkaHandler(isDefault = true)
    public void object(Object object) {
        log.warn("Unknown type" + object);
    }
}
