package com.project.ecommerce.service;

import com.project.ecommerce.dto.cart.AddToCartDto;
import com.project.ecommerce.dto.cart.CartDto;
import com.project.ecommerce.dto.cart.CartItemDto;
import com.project.ecommerce.exceptions.CustomException;
import com.project.ecommerce.model.Cart;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.User;
import com.project.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    public void addToCart(AddToCartDto addToCartDto, User user){
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        cartRepository.save(cart);

    }

    public CartDto listCartItems(User user){
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for(Cart cart : cartList){
            CartItemDto cartItemDto = new CartItemDto();
            totalCost+=cartItemDto.getQuantity()*cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }
        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) throws CustomException {
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);
        Cart cart = optionalCart.get();
        if(optionalCart.isEmpty()){
            throw new CustomException("cart item id is invalid: "+cartItemId);
        }
        cartRepository.delete(cart);
    }
}
