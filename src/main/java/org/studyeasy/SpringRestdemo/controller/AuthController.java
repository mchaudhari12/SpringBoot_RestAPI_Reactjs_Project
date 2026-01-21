package org.studyeasy.SpringRestdemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.studyeasy.SpringRestdemo.model.Account;
import org.studyeasy.SpringRestdemo.payload.auth.AccountDTO;
import org.studyeasy.SpringRestdemo.payload.auth.AccountViewDTO;
import org.studyeasy.SpringRestdemo.payload.auth.ProfileDTO;
import org.studyeasy.SpringRestdemo.payload.auth.TokenDTO;
import org.studyeasy.SpringRestdemo.payload.auth.UserLoginDTO;
import org.studyeasy.SpringRestdemo.service.AccountService;
import org.studyeasy.SpringRestdemo.service.TokenService;
import org.studyeasy.SpringRestdemo.util.constant.AccountError;
import org.studyeasy.SpringRestdemo.util.constant.AccountSuccess;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "Controller for Account management")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AccountService accountService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService,
            AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.accountService = accountService;
    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDTO> token(@Valid @RequestBody UserLoginDTO userLogin) throws AuthenticationException{
        try {
            Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
            return ResponseEntity.ok(new TokenDTO(tokenService.generateToken(authentication)));

        } catch (Exception e) {
            log.debug(AccountError.TOKEN_GENERATION_ERROR.toString()+": "+e.getMessage());
            return new ResponseEntity<>(new TokenDTO(null),HttpStatus.BAD_REQUEST);
        }
    }  
     
        @PostMapping(value = "/user/add",produces = "application/json")
        @ResponseStatus(HttpStatus.CREATED)
        @ApiResponse(responseCode = "400", description = "Please enter a valid email and password length between 6 to 20 character")
        @ApiResponse(responseCode = "201",description = "Account Created")
        @Operation(summary = "Add a New User")
        public ResponseEntity<String> addUser(@Valid @RequestBody AccountDTO accountDTO){
        try {
            Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());
        accountService.save(account);

        return ResponseEntity.ok(AccountSuccess.ACCOUNT_ADDED.toString());

        } catch (Exception e) {
            log.debug(AccountError.ADD_ACCOUNT_ERROR.toString()+": "+e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
     
    }
 
          
        @GetMapping(value = "/users",produces = "application/json")
        @ApiResponse(responseCode = "200",description = "Account User List")
        @ApiResponse(responseCode = "401", description = "Token Missing")
        @ApiResponse(responseCode = "403", description = "Token Error")
        @Operation(summary = "List of Users")
        @SecurityRequirement(name = "manish-chaudhari")
        public List<AccountViewDTO> users(){
            List<AccountViewDTO> accounts = new ArrayList<>();
            for(Account account :accountService.findAllUser() ){
                accounts.add(new AccountViewDTO(account.getId(),account.getEmail(),account.getAuthorities()));
            }
            return accounts;
        }

        @GetMapping(value = "/profile",produces = "application/json")
        @ApiResponse(responseCode = "200",description = "Account User List")
        @ApiResponse(responseCode = "401", description = "Token Missing")
        @ApiResponse(responseCode = "403", description = "Token Error")
        @Operation(summary = "View Profile")
        @SecurityRequirement(name = "manish-chaudhari")
        public ProfileDTO profile(Authentication authentication){
           String email = authentication.getName();
           Optional<Account> optionalAccount = accountService.findByEmailAccount(email);
           if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            ProfileDTO profileDTO = new ProfileDTO(account.getId(),account.getEmail(),account.getAuthorities());
            return profileDTO;
           }
           return null;
        }
        
}
