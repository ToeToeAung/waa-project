package edu.miu.waa.online_market.service.impl;

import edu.miu.waa.online_market.entity.Category;
import edu.miu.waa.online_market.entity.Product;
import edu.miu.waa.online_market.entity.Review;
import edu.miu.waa.online_market.entity.User;
import edu.miu.waa.online_market.entity.dto.ProductDto;
import edu.miu.waa.online_market.repo.CategoryRepo;
import edu.miu.waa.online_market.repo.ProductRepo;
import edu.miu.waa.online_market.service.CategoryService;
import edu.miu.waa.online_market.service.ProductService;
import edu.miu.waa.online_market.service.UserService;
import edu.miu.waa.online_market.util.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void createProduct(ProductDto productdto, Long categoryId, Long userId){
        Category category = categoryService.findById(categoryId);

        User user = userService.findById(userId);
        Product newProduct = new Product(productdto.getDescription(), productdto.getQuantity(), productdto.getPrice(),
                category, user);
        productRepo.save(newProduct);
    }

    @Override
    public List<ProductDto> listProducts(){
        return (List<ProductDto>) listMapper.mapList(productRepo.findAll(), new ProductDto());
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
}
