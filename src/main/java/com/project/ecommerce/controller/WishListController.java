package com.project.ecommerce.controller;


import com.project.ecommerce.common.ApiResponse;
import com.project.ecommerce.dto.ProductDto;
import com.project.ecommerce.exceptions.AuthenticationFailException;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import com.project.ecommerce.model.WishList;
import com.project.ecommerce.service.AuthenticationService;
import com.project.ecommerce.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    WishListService wishListService;

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) throws AuthenticationFailException {

//        Authenticate the token firstly
        authenticationService.authenticate(token);

//        finding the user
        User user = authenticationService.getUser(token);
        List<ProductDto> productDtos = wishListService.getWishListForUser(user);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        WishList wishList = new WishList(user, product);
        wishListService.createWishList(wishList);
        ApiResponse apiResponse = new ApiResponse(true, "added to wishlist");;
        return  new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }
}
