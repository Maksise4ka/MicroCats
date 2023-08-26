package com.maksise4ka.microcats.cats;

import com.maksise4ka.microcats.cats.exceptions.CatServiceException;
import com.maksise4ka.microcats.cats.services.CatService;
import com.maksise4ka.microcats.contracts.CatDto;
import com.maksise4ka.microcats.contracts.friendship.requests.MakeFriends;
import com.maksise4ka.microcats.contracts.friendship.requests.RemoveFriendship;
import com.maksise4ka.microcats.contracts.friendship.requests.ShowFriends;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@KafkaListener(topics = "friendship", groupId = "friendship-listeners")
public class FriendshipListener {

    private final CatService catService;

    @KafkaHandler
    public void makeFriends(MakeFriends command) throws CatServiceException {
        catService.makeFriends(command.catId1(), command.catId2(), command.requesterUsername());

        log.info("Called makeFriends");
    }

    @KafkaHandler
    public void removeFriendship(RemoveFriendship command) throws CatServiceException {
        catService.removeFriendship(command.catId1(), command.catId2(), command.requesterUsername());

        log.info("Called removeFriendship");
    }

    @KafkaHandler
    @SendTo
    public CatDto[] showFriends(ShowFriends command) {
        CatDto[] cats = catService.showFriends(command.catId(), command.requesterUsername()).toArray(new CatDto[0]);
        log.info("Called showFriends");

        return cats;
    }

    @KafkaHandler(isDefault = true)
    public void object(Object object) {
        log.warn("Unknown type" + object);
    }
}
