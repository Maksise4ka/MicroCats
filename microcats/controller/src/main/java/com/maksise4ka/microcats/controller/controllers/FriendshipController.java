package com.maksise4ka.microcats.controller.controllers;

import com.maksise4ka.microcats.contracts.CatDto;
import com.maksise4ka.microcats.controller.dtos.friendship.*;
import com.maksise4ka.microcats.controller.kafka.KafkaService;
import com.maksise4ka.microcats.controller.mappers.FriendshipDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
@Slf4j
public class FriendshipController {

    private final KafkaService kafkaService;

    @PostMapping("/make-friends")
    @PreAuthorize("hasAuthority('friendship:create')")
    public ResponseEntity<?> makeFriends(
            @Valid @RequestBody MakeFriendsDto command,
            @AuthenticationPrincipal UserDetails user) {

        sendAsync(FriendshipDtoMapper.makeFriends(command, user.getUsername()), "method makeFriends");

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove-friendship")
    @PreAuthorize("hasAuthority('friendship:create')")
    public ResponseEntity<?> removeFriendship(
            @Valid @RequestBody RemoveFriendshipDto command,
            @AuthenticationPrincipal UserDetails user) {

        sendAsync(FriendshipDtoMapper.removeFriendship(command, user.getUsername()), "method removeFriendship");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/show-friends/{id}")
    @PreAuthorize("hasAuthority('friendship:read')")
    public ResponseEntity<CatDto[]> showFriends(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails user) {

        CatDto[] reply = sendSync(
                FriendshipDtoMapper.showFriends(id, user.getUsername()),
                CatDto[].class,
                "method showFriends");

        return ResponseEntity.ofNullable(reply);
    }

    private void sendAsync(Object command, String logMessage) {
        log.info("Called FriendshipController: " + logMessage);
        kafkaService.sendAsynchronously(command, "friendship");
    }

    private <T> T sendSync(Object command, Class<T> type, String logMessage) {
        log.info("Called FriendshipController: " + logMessage);

        return kafkaService.sendSynchronously(command, "friendship", type);
    }
}
