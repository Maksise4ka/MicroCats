package com.maksise4ka.microcats.controller.controllers;

import com.maksise4ka.microcats.contracts.CatDto;
import com.maksise4ka.microcats.contracts.OwnerDto;
import com.maksise4ka.microcats.contracts.owners.requests.PetCat;
import com.maksise4ka.microcats.controller.dtos.owners.*;
import com.maksise4ka.microcats.controller.kafka.KafkaService;
import com.maksise4ka.microcats.controller.mappers.OwnersDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
@Slf4j
public class OwnerController {
    private final PasswordEncoder encoder;

    private final KafkaService kafkaService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('users:create')")
    public ResponseEntity<OwnerDto> create(@Valid @RequestBody CreateOwnerDto createOwner) {

        OwnerDto reply = sendSync(
                OwnersDtoMapper.createOwner(createOwner, encoder),
                OwnerDto.class,
                "method create");

        return ResponseEntity.ofNullable(reply);
    }

    @PostMapping("/pet")
    @PreAuthorize("hasAuthority('cats:create')")
    public ResponseEntity<CatDto> petCat(
            @Valid @RequestBody PetCatDto command,
            @AuthenticationPrincipal UserDetails user) {

        PetCat request = OwnersDtoMapper.petCat(command, user.getUsername());
        CatDto reply = kafkaService.sendSynchronously(request, "owners", CatDto.class);

        return ResponseEntity.ofNullable(reply);
    }

    @PatchMapping("update/name/{id}")
    @PreAuthorize("hasAuthority('owners:update')")
    public ResponseEntity<?> changeName(
            @PathVariable Long id,
            @Valid @RequestBody ChangeNameDto command,
            @AuthenticationPrincipal UserDetails user) {

        sendAsync(OwnersDtoMapper.changeName(id, command, user.getUsername()), "method changeName");

        return ResponseEntity.ok().build();
    }

    @PatchMapping("update/birthdate/{id}")
    @PreAuthorize("hasAuthority('owners:update')")
    public ResponseEntity<?> changeBirthdate(
            @PathVariable Long id,
            @Valid @RequestBody ChangeBirthdateDto command,
            @AuthenticationPrincipal UserDetails user) {

        sendAsync(OwnersDtoMapper.changeBirthdate(id, command, user.getUsername()), "method ChangeBirthdate");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('owners:read')")
    public ResponseEntity<OwnerDto> get(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {

        OwnerDto reply = sendSync(OwnersDtoMapper.getOwner(id, user.getUsername()), OwnerDto.class, "method get");

        return ResponseEntity.ofNullable(reply);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('owners:read')")
    public ResponseEntity<OwnerDto[]> getAll(@AuthenticationPrincipal UserDetails user) {

        OwnerDto[] reply = sendSync(OwnersDtoMapper.getAll(user.getUsername()),OwnerDto[].class, "method getAll");

        return ResponseEntity.ofNullable(reply);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('owners:update')")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        sendAsync(OwnersDtoMapper.deleteOwner(id, user.getUsername()), "method delete");

        return ResponseEntity.ok().build();
    }

    private void sendAsync(Object command, String logMessage) {
        log.info("Called OwnerController: " + logMessage);
        kafkaService.sendAsynchronously(command, "owners");
    }

    private <T> T sendSync(Object command, Class<T> type, String logMessage) {
        log.info("Called OwnerController: " + logMessage);

        return kafkaService.sendSynchronously(command, "owners", type);
    }
}