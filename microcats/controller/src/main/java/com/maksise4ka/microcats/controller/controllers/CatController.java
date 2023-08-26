package com.maksise4ka.microcats.controller.controllers;

import com.maksise4ka.microcats.contracts.CatDto;
import com.maksise4ka.microcats.controller.dtos.cats.*;
import com.maksise4ka.microcats.controller.kafka.KafkaService;
import com.maksise4ka.microcats.controller.mappers.CatsDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cats")
@RequiredArgsConstructor
@Slf4j
public class CatController {

    private final KafkaService kafkaService;

    @PatchMapping("/update/name/{id}")
    @PreAuthorize("hasAuthority('cats:write')")
    public ResponseEntity<?> changeName(
            @PathVariable Long id,
            @Valid @RequestBody ChangeCatNameDto command,
            @AuthenticationPrincipal UserDetails user) {

        sendAsync(CatsDtoMapper.changeName(id, command, user.getUsername()), "method changeName");

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/birthdate/{id}")
    @PreAuthorize("hasAuthority('cats:write')")
    public ResponseEntity<?> changeBirthdate(
            @PathVariable Long id,
            @Valid @RequestBody ChangeCatBirthdateDto command,
            @AuthenticationPrincipal UserDetails user) {

        sendAsync(CatsDtoMapper.changeBirthdate(id, command, user.getUsername()), "method changeBirthdate");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('cats:read')")
    public ResponseEntity<CatDto> get(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {

       CatDto reply = sendSync(CatsDtoMapper.get(id, user.getUsername()), CatDto.class, "method get");

        return ResponseEntity.ofNullable(reply);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('cats:read')")
    public ResponseEntity<CatDto[]> getAll(@AuthenticationPrincipal UserDetails user) {

        CatDto[] reply = sendSync(CatsDtoMapper.getAll(user.getUsername()), CatDto[].class, "method getAll");

        return ResponseEntity.ofNullable(reply);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('cats:create')")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {

        sendAsync(CatsDtoMapper.delete(id, user.getUsername()), "method delete");

        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filter")
    @PreAuthorize("hasAuthority('cats:read')")
    public ResponseEntity<CatDto[]> filter(
            @RequestParam(required = false) ArrayList<String> colors,
            @RequestParam(required = false) ArrayList<String> breeds,
            @AuthenticationPrincipal UserDetails user) {

        CatDto[] reply = sendSync(
                CatsDtoMapper.filter(colors, breeds, user.getUsername()),
                CatDto[].class,
                "method filter");

        return ResponseEntity.ofNullable(reply);
    }

    private void sendAsync(Object command, String logMessage) {
        log.info("Called CatController: " + logMessage);
        kafkaService.sendAsynchronously(command, "cats");
    }

    private <T> T sendSync(Object command, Class<T> type, String logMessage) {
        log.info("Called CatController: " + logMessage);

        return kafkaService.sendSynchronously(command, "cats", type);
    }
}
