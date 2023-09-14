package com.jac.Springbootlibrary.controller;


import com.jac.Springbootlibrary.entity.AddBookRequest;
import com.jac.Springbootlibrary.service.AdminService;
import com.jac.Springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    // inject AdminService class
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    // add book api
    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value="Authorization") String token,
                         @RequestBody AddBookRequest addBookRequest) throws Exception {
        // extract user type from the token
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        //if not role of Admin, can not use this function
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        //call postBook method in adminService class to save the book from the request to our database
        adminService.postBook(addBookRequest);
    }
}
