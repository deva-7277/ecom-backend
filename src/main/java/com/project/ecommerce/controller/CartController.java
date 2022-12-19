package com.project.ecommerce.controller;

import com.project.ecommerce.common.ApiResponse;
import com.project.ecommerce.dto.cart.AddToCartDto;
import com.project.ecommerce.dto.cart.CartDto;
import com.project.ecommerce.exceptions.AuthenticationFailException;
import com.project.ecommerce.exceptions.CustomException;
import com.project.ecommerce.model.User;
import com.project.ecommerce.service.AuthenticationService;
import com.project.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDato,
                                                    @RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDato, user);

        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {

        authenticationService.authenticate(token);


        User user = authenticationService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItems(@PathVariable("cartItemId") Integer itemId,
                                                       @RequestParam("token") String token) throws AuthenticationFailException, CustomException {
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);

        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }
}
