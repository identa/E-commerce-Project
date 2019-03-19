package com.dac.spring.service.impl;

import com.dac.spring.constant.ShopConst;
import com.dac.spring.entity.*;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;
import com.dac.spring.model.req.ShopCreateProductRequest;
import com.dac.spring.model.req.ShopUpdateProductRequest;
import com.dac.spring.model.resp.*;
import com.dac.spring.repository.*;
import com.dac.spring.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductPaginationRepository productPaginationRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    CampaignPaginationRepository campaignPaginationRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public ServiceResult getInfoById(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity shop = employeeRepository.findByIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_SHOP).orElse(null);
        if (shop != null) {
            ShopGetInfoResponse response = new ShopGetInfoResponse(
                    shop.getId(),
                    shop.getFirstName(),
                    shop.getLastName(),
                    shop.getImageURL());
            result.setData(response);
            result.setMessage("Get info successfully");
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(ShopConst.SHOP_NOT_FOUND);
        }
        return result;
    }

    @Override
    public ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity shop = employeeRepository.findByIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_SHOP).orElse(null);
        if (shop != null) {
            if (firstName != null && lastName != null && password != null) {
                shop.setFirstName(firstName);
                shop.setLastName(lastName);
                shop.setPassword(encoder.encode(password));
                shop.setImageURL(imageURL);
                employeeRepository.save(shop);
                ShopUpdateInfoResponse response = new ShopUpdateInfoResponse(shop.getId(),
                        shop.getFirstName(),
                        shop.getLastName(),
                        shop.getImageURL());
                result.setMessage("Update info successfully");
                result.setData(response);
            } else {
                result.setMessage(ShopConst.EMPTY_FIELD);
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage(ShopConst.SHOP_NOT_FOUND);
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult paginateProductById(int id, int page, int size) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity shop = employeeRepository.findByIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_SHOP).orElse(null);
        if (shop != null) {
            Pageable info = PageRequest.of(page - 1, size, Sort.by("id").ascending());
            Page<ProductEntity> productList = productPaginationRepository.
                    findAllByDeletedAndShopId(info, false, id);
            boolean isProductListEmpty = productList.isEmpty();
            if (!isProductListEmpty) {
                int totalPages = productList.getTotalPages();
                List<ShopGetProductResponse> responses = new ArrayList<>();
                for (ProductEntity entity : productList) {
                    ShopGetProductResponse response = new ShopGetProductResponse(entity.getId(),
                            entity.getName(),
                            entity.getStatus().getName().name(),
                            entity.getDescription(),
                            entity.getQuantity(),
                            entity.getOriginalPrice(),
                            entity.getDiscount(),
                            entity.getView(),
                            entity.getProductImageURL(),
                            entity.getCategory().getId(),
                            entity.getShop().getId(),
                            entity.getCategory().getName(),
                            entity.getShop().getFirstName() + " " + entity.getShop().getLastName());
                    responses.add(response);
                }
                ShopPaginateProductByIdResponse response = new ShopPaginateProductByIdResponse(totalPages, responses);
                result.setMessage("Products are returned successfully");
                result.setData(response);
            } else {
                result.setMessage("Product list is empty");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(ShopConst.SHOP_NOT_FOUND);
        }
        return result;
    }

    @Override
    public ServiceResult createProduct(ShopCreateProductRequest request) {
        ServiceResult result = new ServiceResult();
        if (request.getName() != null && request.getStatus() != null) {
            boolean isStatusExist = Arrays.stream(StatusName.values()).anyMatch(t -> t.name().equals(request.getStatus()));
            if (isStatusExist) {
                if (isDiscountRight(request.getDiscount())) {
                    ProductEntity product = new ProductEntity(request.getName(),
                            statusRepository.findByName(StatusName.valueOf(request.getStatus())),
                            request.getDescription(),
                            request.getQuantity(),
                            request.getOriginalPrice(),
                            request.getDiscount(),
                            categoryRepository.findById(request.getCategoryID()).orElse(null),
                            employeeRepository.findById(request.getShopID()).orElse(null));
                    if (request.getProductImageURL() == null) product.setProductImageURL(ShopConst.DEFAULT_AVATAR);
                    else product.setProductImageURL(request.getProductImageURL());
                    productRepository.save(product);
                    ShopCreateProductResponse response = new ShopCreateProductResponse(product.getId(),
                            product.getName(),
                            product.getStatus().getName().name(),
                            product.getDescription(),
                            product.getQuantity(),
                            product.getOriginalPrice(),
                            product.getDiscount(),
                            product.getProductImageURL(),
                            product.getCategory().getName(),
                            product.getShop().getFirstName() + " " + product.getShop().getLastName());

                    result.setMessage("Create product successfully");
                    result.setData(response);
                } else {
                    result.setMessage("Discount is less than 100");
                    result.setStatus(ServiceResult.Status.FAILED);
                }
            } else {
                result.setMessage("Status is not existed");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage(ShopConst.EMPTY_FIELD);
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult updateProduct(ShopUpdateProductRequest request) {
        ServiceResult result = new ServiceResult();
        if (request.getName() != null && request.getStatus() != null) {
            boolean isStatusExist = Arrays.stream(StatusName.values()).anyMatch(t -> t.name().equals(request.getStatus()));
            if (isStatusExist) {
                CategoryEntity category = categoryRepository.findById(request.getCategoryID()).orElse(null);
                ProductEntity product = productRepository.findByIdAndDeleted(
                        request.getId(), false);
                if (isDiscountRight(request.getDiscount())) {
                    product.setName(request.getName());
                    product.setDescription(request.getDescription());
                    product.setOriginalPrice(request.getOriginalPrice());
                    product.setDiscount(request.getDiscount());
                    product.setStatus(statusRepository.findByName(StatusName.valueOf(request.getStatus())));
                    product.setQuantity(request.getQuantity());
                    if (request.getProductImageURL() == null)
                        product.setProductImageURL(ShopConst.DEFAULT_AVATAR);
                    else product.setProductImageURL(request.getProductImageURL());
                    product.setCategory(category);

                    productRepository.save(product);
                    ShopUpdateProductResponse response = new ShopUpdateProductResponse(product.getId(),
                            product.getName(),
                            product.getStatus().getName().name(),
                            product.getDescription(),
                            product.getQuantity(),
                            product.getOriginalPrice(),
                            product.getDiscount(),
                            product.getProductImageURL(),
                            product.getCategory().getName(),
                            product.getShop().getFirstName() + " " + product.getShop().getLastName());

                    result.setMessage("Create product successfully");
                    result.setData(response);
                } else {
                    result.setMessage("Discount is less than 100");
                    result.setStatus(ServiceResult.Status.FAILED);
                }
            } else {
                result.setMessage("Status is not existed");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage(ShopConst.EMPTY_FIELD);
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    private boolean isDiscountRight(int discount) {
        return discount >= 0 && discount < 100;
    }

    @Override
    public ServiceResult deleteProduct(int id) {
        ServiceResult result = new ServiceResult();
        ProductEntity product = productRepository.findByIdAndDeleted(id, false);
        if (product != null) {
            product.setDeleted(true);
            productRepository.delete(product);
            result.setMessage("Delete product successfully");
        } else {
            result.setMessage("Cannot delete this product");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult updateOrder(int id, String status) {
        ServiceResult result = new ServiceResult();
        OrderEntity order = orderRepository.findByIdAndDeleted(id, false);
        if (order != null) {
            boolean isStatusExist = Arrays.stream(StatusName.values()).anyMatch(t -> t.name().equals(status));
            if (isStatusExist) {
                order.setStatus(statusRepository.findByName(StatusName.valueOf(status)));
                result.setMessage("Update status successfully");
            } else {
                result.setMessage("Product not found");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage("Status not found");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult updateOrderDetail(int id, int quantity, int productID) {
        ServiceResult result = new ServiceResult();
        OrderDetailEntity orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail != null) {
            ProductEntity product = productRepository.findByIdAndDeletedAndStatusName(productID, false, StatusName.ACTIVE);
            if (product != null) {
                int count = product.getQuantity() + orderDetail.getQuantity() - quantity;
                if (count >= 0) {
                    orderDetail.setProduct(product);
                    orderDetail.setQuantity(quantity);
                    OrderEntity order = orderDetail.getOrder();
                    double preTotalPrice = order.getTotalPrice() - orderDetail.getPrice();
                    orderDetail.setPrice(calculatePrice(quantity, product.getOriginalPrice(), product.getDiscount()));
                    order.setTotalPrice(preTotalPrice + orderDetail.getPrice());
                    orderDetailRepository.save(orderDetail);
                    orderRepository.save(order);
                    result.setMessage("Update order detail successfully");
                } else {
                    result.setMessage("The quantity exceeded");
                    result.setStatus(ServiceResult.Status.FAILED);
                }
            } else {
                result.setMessage("Product not found");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        }
        return result;
    }

    @Override
    public ServiceResult paginateCampaign(int id, int page, int size) {
        ServiceResult result = new ServiceResult();
        Pageable info = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<CampaignEntity> campaignList = campaignPaginationRepository.findAllByShopId(info, id);
        boolean isCampaignListEmpty = campaignList.isEmpty();
        if (!isCampaignListEmpty) {
            int totalPages = campaignList.getTotalPages();
            List<AdminGetCampaignResponse> responses = new ArrayList<>();
            for (CampaignEntity entity : campaignList) {


                AdminGetCampaignResponse response = new AdminGetCampaignResponse(
                        entity.getId(),
                        entity.getShop().getId(),
                        entity.getName(),
                        entity.getStatus().getName().name(),
                        entity.getStartDate().toString().replaceAll("-", "/"),
                        entity.getEndDate().toString().replaceAll("-", "/"),
                        entity.getBudget());
                response.setBid(entity.getBid());
                response.setImageURL(entity.getImageURL());
                response.setTitle(entity.getTitle());
                response.setDescription(entity.getDescription());
                response.setProductURL(entity.getProductURL());
                responses.add(response);

            }
            AdminPaginateCampaignResponse response = new AdminPaginateCampaignResponse(totalPages, responses);
            result.setMessage("Campaigns are returned successfully");
            result.setData(response);
        } else {
            result.setMessage("Campaigns list is empty");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    private double calculatePrice(int quantity, double price, double discount) {
        return (price - price * discount / 100) * quantity;
    }
}
