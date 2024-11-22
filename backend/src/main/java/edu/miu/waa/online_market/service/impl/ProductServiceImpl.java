package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.*;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import edu.miu.waa.online_market.entity.dto.UserDto;
import edu.miu.waa.online_market.repo.OrderItemRepo;
import edu.miu.waa.online_market.repo.ProductRepo;
import edu.miu.waa.online_market.service.CategoryService;
import edu.miu.waa.online_market.service.ProductService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.CurrentUser;
import edu.miu.waa.online_market.util.ListMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    final private ProductRepo productRepo;
    final private CategoryService categoryService;
    final private UserService userService;
    final private ListMapper listMapper;
    final private ModelMapper modelMapper;
    final private OrderItemRepo orderItemRepo;


    @Override
    public void createProduct(ProductDto p){
        Product product = modelMapper.map(p, Product.class);
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        Category  category = categoryService.findById(p.getCategoryId());
        product.setUser(user);
        product.setCategory(category);
        productRepo.save(product);
    }

    @Override
    public Page<Product> findByFilters(
            Long categoryId,
            Float ratingGt,
            Float ratingLt,
            Float priceGt,
            Float priceLt,
            Pageable pageable
            )
        {
        return productRepo.findByFilters(categoryId,
                 ratingGt,
                 ratingLt,
                 priceGt,
                 priceLt,
                pageable);
    }

    @Override
    public List<Product> listProducts(){
        return (List<Product>) listMapper.mapList(productRepo.findAll(), new Product());
    }

    @Override
    public Product getProduct(Long id){
        Product product = productRepo.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        UserDto userDto = modelMapper.map( product.getUser(), UserDto.class);
        product.setUser(modelMapper.map(userDto, User.class));

        return productRepo.findById(id).orElse(null);
    }

    @Override
    public ProductDto getProductDto(Long id){
        return modelMapper.map(productRepo.findById(id).orElse(null), ProductDto.class);
    }

    @Override
    public void updateProduct(Long id, int quantity){
        Product product = getProduct(id);
        product.setQuantity(quantity);
        productRepo.save(product);
    }

    @Override
    public  void deleteProduct(Long id){
        productRepo.deleteById(id);
    }

    @Override
    public List<Review> findReviewsByProduct(Long id){
        return productRepo.findReviewsByProductId(id);
    }

    @Override
    public Boolean subQuantity(Long productId, int quantity){
        Product product = getProduct(productId);
        if(quantity <= product.getQuantity()){
            product.setQuantity(product.getQuantity() - quantity);
        }else{
            return false;
        }
        productRepo.save(product);
        return true;
    }

    @Override
    public List<Product> getProductsBySellerId(long sellerId, Integer stockQty){
        return productRepo.findProductsBySellerId(sellerId, stockQty);
    }

    @Override
    public void sellerDeleteProduct(long id) {
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        if (user == null || !user.getRole().equals(Role.SELLER)) {
            // TODO: make custom exception
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (orderItemRepo.countByProductId(id) > 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        productRepo.deleteById(id);
    }
}
