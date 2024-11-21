package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.*;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import edu.miu.waa.online_market.repo.CategoryRepo;
import edu.miu.waa.online_market.repo.ProductRepo;
import edu.miu.waa.online_market.service.CategoryService;
import edu.miu.waa.online_market.service.LoggerService;
import edu.miu.waa.online_market.service.ProductService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.CurrentUser;
import edu.miu.waa.online_market.util.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    final private ProductRepo productRepo;
    final private CategoryService categoryService;
    final private UserService userService;
    final private ListMapper listMapper;
    final private ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, CategoryService categoryService, UserService userService,
                              ListMapper listMapper, ModelMapper modelMapper) {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
        this.userService = userService;
        this.listMapper = listMapper;
        this.modelMapper = modelMapper;

    }

    @Override
    public void createProduct(Product product){
        User user = userService.findByUsername(CurrentUser.getCurrentUser());
        product.setUser(user);
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
        return productRepo.findByFilters( categoryId,
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
    public List<Product> getProductsBySellerId(long sellerId){
        return productRepo.findProductsBySellerId(sellerId);
    }
}
