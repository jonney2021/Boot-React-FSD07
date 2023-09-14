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

    // increase quantity of book
    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long bookId) throws Exception {
        // extract the usertype from token string
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        // if it's not the  role of Admin, throw an exception
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        // if it's the role of Admin, call the increaseBookQuantity() in the adminService
        adminService.increaseBookQuantity(bookId);
    }
    //decrease quantity of book
    @PutMapping("/secure/decrease/book/quantity")
    public void decreaseBookQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long bookId) throws Exception {
        // extract the usertype from token string
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        // if it's not the  role of Admin, throw an exception
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        // if it's the role of Admin, call the decreaseBookQuantity() in the adminService
        adminService.decreaseBookQuantity(bookId);
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

    @DeleteMapping("/secure/delete/book")
    public void deleteBook(@RequestHeader(value="Authorization") String token,
                           @RequestParam Long bookId) throws Exception {
        // extract user type from the token
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        //if not role of Admin, can not use this function
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        //if the role is authenticated, call the deleteBook() in adminService.
        adminService.deleteBook(bookId);
    }
}
