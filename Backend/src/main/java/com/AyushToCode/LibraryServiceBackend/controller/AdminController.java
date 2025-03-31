package com.AyushToCode.LibraryServiceBackend.controller;


import com.AyushToCode.LibraryServiceBackend.requestmodels.AddBookRequest;
import com.AyushToCode.LibraryServiceBackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQuantity(@AuthenticationPrincipal Jwt jwt,
                                     @RequestParam Long bookId) throws Exception {
        ArrayList<String> admin_role = new ArrayList<>();
        Map<String, Object> realm_access = jwt.getClaim("realm_access");
        if (realm_access != null && realm_access.containsKey("roles")) {
            admin_role =  (ArrayList<String>)realm_access.get("roles");
        }
        if (!admin_role.getFirst().equals("ADMIN")) throw new RuntimeException("Administration Page only");
        adminService.increaseBookQuantity(bookId);
    }

    @PutMapping("/secure/decrease/book/quantity")
    public void decreaseBookQuantity(@AuthenticationPrincipal Jwt jwt,
                                     @RequestParam Long bookId) throws Exception {
        ArrayList<String> admin_role = new ArrayList<>();
        Map<String, Object> realm_access = jwt.getClaim("realm_access");
        if (realm_access != null && realm_access.containsKey("roles")) {
            admin_role =  (ArrayList<String>)realm_access.get("roles");
        }
        if (!admin_role.getFirst().equals("ADMIN")) throw new RuntimeException("Administration Page only");
        adminService.decreaseBookQuantity(bookId);
    }

    @PostMapping("/secure/add/book")
    public void postBook(@AuthenticationPrincipal Jwt jwt,
                         @RequestBody AddBookRequest addBookRequest) throws Exception {
        ArrayList<String> admin_role = new ArrayList<>();
        Map<String, Object> realm_access = jwt.getClaim("realm_access");
        if (realm_access != null && realm_access.containsKey("roles")) {
            admin_role =  (ArrayList<String>)realm_access.get("roles");
        }
        if (!admin_role.getFirst().equals("ADMIN")) throw new RuntimeException("Administration Page only");;
        adminService.postBook(addBookRequest);
    }

    @DeleteMapping("/secure/delete/book")
    public void deleteBook(@AuthenticationPrincipal Jwt jwt,
                           @RequestParam Long bookId) throws Exception {
        ArrayList<String> admin_role = new ArrayList<>();
        Map<String, Object> realm_access = jwt.getClaim("realm_access");
        if (realm_access != null && realm_access.containsKey("roles")) {
            admin_role =  (ArrayList<String>)realm_access.get("roles");
        }
        if (!admin_role.getFirst().equals("ADMIN")) throw new RuntimeException("Administration Page only");
        adminService.deleteBook(bookId);
    }

}












