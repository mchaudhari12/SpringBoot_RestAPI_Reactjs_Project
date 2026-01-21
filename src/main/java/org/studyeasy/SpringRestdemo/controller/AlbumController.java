package org.studyeasy.SpringRestdemo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.studyeasy.SpringRestdemo.model.Account;
import org.studyeasy.SpringRestdemo.model.Album;
import org.studyeasy.SpringRestdemo.payload.auth.albumPayload.AlbumPayloadDTO;
import org.studyeasy.SpringRestdemo.payload.auth.albumPayload.AlbumViewDTO;
import org.studyeasy.SpringRestdemo.service.AccountService;
import org.studyeasy.SpringRestdemo.service.AlbumService;
import org.studyeasy.SpringRestdemo.util.constant.AlbumUtil.AlbumError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/album")
@Tag(name = "Album Controller",description = "Controller fr album and photo management")
@Slf4j
public class AlbumController {
    
     @Autowired
    private AccountService accountService;

    @Autowired
    private AlbumService albumService;

    @PostMapping(value = "/add",produces ="application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Please add valid name a description")
    @ApiResponse(responseCode = "201", description = "Account added")
    @Operation(summary = "Add an Album")
    @SecurityRequirement(name = "manish-chaudhari")
    public ResponseEntity<AlbumViewDTO> addAlbum(@Valid @RequestBody AlbumPayloadDTO AlbumPayloadDTO,Authentication authentication){
        
        try{
            Album album = new Album();
            album.setName(AlbumPayloadDTO.getName());
            album.setDescription(AlbumPayloadDTO.getDescription());
            String email = authentication.getName();
            Optional<Account> optionalAccount = accountService.findByEmailAccount(email);
            Account account = optionalAccount.get();
            album.setAccount(account);
            album = albumService.save(album);
            AlbumViewDTO albumViewDTO = new AlbumViewDTO(album.getId(),album.getName(),album.getDescription());
            return ResponseEntity.ok(albumViewDTO);
        }
        catch(Exception e){
            log.debug(AlbumError.ADD_ALBUM_ERROR.toString() + ": "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(value = "/", produces = "Application/json")
    @ApiResponse(responseCode = "200", description = "List Of Album")
    @ApiResponse(responseCode = "401", description = "Token Missing")
    @ApiResponse(responseCode = "403", description = "Token Error")
    @Operation(summary = "List Of Album")
    @SecurityRequirement(name = "manish-chaudhari")
    public List<AlbumViewDTO> albums(Authentication authentication){
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmailAccount(email);
        Account account = optionalAccount.get();
        List<AlbumViewDTO> albums = new ArrayList<>();
        for(Album album : albumService.findByAccountId(account.getId())){
            albums.add(new AlbumViewDTO(album.getId(), album.getName(), album.getDescription()));
        }
        return albums;
    }

    @PostMapping(value = "/photos",consumes = {"multipart/form-data"})
    @Operation(summary = "Uplaod Photo into album")
    @SecurityRequirement(name = "manish-chaudhari")
    public List<String> photos(@RequestPart(required = true) MultipartFile[] files){
        List<String> fileName = new ArrayList<>();
        Arrays.asList(files).stream().forEach(file -> {fileName.add(file.getOriginalFilename());            
        });
        return fileName;

    }
}
