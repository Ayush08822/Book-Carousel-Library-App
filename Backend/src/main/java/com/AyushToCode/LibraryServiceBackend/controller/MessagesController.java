package com.AyushToCode.LibraryServiceBackend.controller;


import com.AyushToCode.LibraryServiceBackend.entity.Message;
import com.AyushToCode.LibraryServiceBackend.requestmodels.AdminQuestionRequest;
import com.AyushToCode.LibraryServiceBackend.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private final MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@AuthenticationPrincipal Jwt jwt,
                            @RequestBody Message messageRequest) throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        System.out.println(userEmail);
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        messagesService.postMessage(messageRequest, userEmail);
    }

    @PutMapping("/secure/admin/message")
    public void putMessage(@AuthenticationPrincipal Jwt jwt,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        String userEmail = jwt.getClaimAsString("email");
        System.out.println(userEmail);
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        String admin_role ="";
        Map<String , Object> realm_access = jwt.getClaim("realm_access");
        if(realm_access != null && realm_access.containsKey("roles")){
            admin_role = (String) realm_access.get("roles");
        }
        if(!admin_role.equals("ADMIN")) throw new RuntimeException("Administration Page only");
        messagesService.putMessage(adminQuestionRequest, userEmail);
    }

}














