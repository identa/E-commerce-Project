package com.dac.spring.service.impl;

import com.dac.spring.constant.AdminConst;
import com.dac.spring.constant.AdminUserCreateConst;
import com.dac.spring.constant.CustomerSignUpConst;
import com.dac.spring.constant.ShopConst;
import com.dac.spring.entity.*;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;
import com.dac.spring.model.req.AdminCreateCampaignRequest;
import com.dac.spring.model.req.AdminCreateProductRequest;
import com.dac.spring.model.req.AdminUpdateCampaignRequest;
import com.dac.spring.model.resp.AdminGetOrderResponse;
import com.dac.spring.model.req.ShopUpdateProductRequest;
import com.dac.spring.model.resp.AdminPaginateCategoryResponse;
import com.dac.spring.model.resp.*;
import com.dac.spring.repository.*;
import com.dac.spring.service.AdminService;
import com.dac.spring.utils.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    UserPaginationRepository userPaginationRepository;

    @Autowired
    CategoryPagingRepository categoryPagingRepository;

    @Autowired
    ProductPaginationRepository productPaginationRepository;

    @Autowired
    OrderPaginationRepository orderPaginationRepository;

    @Autowired
    OrderDetailPaginationRepository orderDetailPaginationRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    CampaignPaginationRepository campaignPaginationRepository;

    @Autowired
    JWTRepository jwtRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder encoder;

    private String authenticationWithJwt(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateJwtToken(authentication);
    }

    private AdminGetAllCustomerResponse createGetAllCustomerResponse(EmployeeEntity entity) {
        return new AdminGetAllCustomerResponse(entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getStatus().getName().name());

    }

    private List<AdminGetAllCustomerResponse> createCustomerGetResponseList() {
        List<EmployeeEntity> customerList = employeeRepository.findByDeletedAndRoleName(false,
                RoleName.ROLE_CUSTOMER);
        List<AdminGetAllCustomerResponse> responseList = new ArrayList<>();
        for (EmployeeEntity entity : customerList) {
            responseList.add(createGetAllCustomerResponse(entity));
        }
        return responseList;
    }

    private boolean isStatusAndRoleExisted(String statusName, String roleName) {
        boolean isStatusExist = Arrays.stream(StatusName.values()).anyMatch(t -> t.name().equals(statusName));
        boolean isRoleExist = Arrays.stream(RoleName.values()).anyMatch(t -> t.name().equals(roleName));
        return isStatusExist && isRoleExist;
    }

    private JWTEntity saveJwt(String token) {
        JWTEntity jwtEntity = new JWTEntity();
        jwtEntity.setToken(token);
        return jwtRepository.save(jwtEntity);
    }

    @Override
    public ServiceResult getAllCustomer() {
        ServiceResult result = new ServiceResult();
        List<AdminGetAllCustomerResponse> employeeEntityList = createCustomerGetResponseList();
        result.setMessage("Get all customers successfully");
        result.setData(employeeEntityList);
        return result;
    }

    @Override
    public ServiceResult getCustomerById(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            AdminGetCustomerByIdResponse response = new AdminGetCustomerByIdResponse(employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getStatus().getName().name());
            result.setData(response);
            result.setMessage("Get customer successfully");
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(AdminConst.CUSTOMER_NOT_FOUND);
        }
        return result;
    }

    @Override
    public ServiceResult updateUser(int id, String firstName, String lastName, String password,
                                    String imageURL, String statusName, String roleName) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employee = employeeRepository.findByIdAndDeletedAndRoleNameOrIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_CUSTOMER, id, false, RoleName.ROLE_SHOP).orElse(null);
        if (employee != null) {
            if (firstName != null && lastName != null &&
                    statusName != null && roleName != null) {
                if (isStatusAndRoleExisted(statusName, roleName)) {
                    employee.setFirstName(firstName);
                    employee.setLastName(lastName);
                    if (!password.equals("")) {
                        employee.setPassword(encoder.encode(password));
                    } else employee.setPassword(employee.getPassword());
                    employee.setStatus(statusRepository.findByName(StatusName.valueOf(statusName)));
                    employee.setRole(roleRepository.findByName(RoleName.valueOf(roleName)));
                    employee.setImageURL(imageURL);

                    employeeRepository.save(employee);
                    AdminUpdateUserResponse response = new AdminUpdateUserResponse(employee.getId(),
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getEmail(),
                            employee.getStatus().getName().name(),
                            employee.getRole().getName().name());
                    result.setMessage("Update customer successfully");
                    result.setData(response);
                } else {
                    result.setMessage(AdminUserCreateConst.STATUS_ROLE_NOT_EXIST);
                    result.setStatus(ServiceResult.Status.FAILED);
                }

            } else {
                result.setMessage("Fields cannot be empty");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage("Customer not found");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult deleteUserById(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employee = employeeRepository.findByIdAndDeletedAndRoleNameOrIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_CUSTOMER, id, false, RoleName.ROLE_SHOP).orElse(null);
        if (employee != null) {
            employee.setDeleted(true);
            employeeRepository.delete(employee);
            result.setMessage("Delete customer successfully");
        } else {
            result.setMessage("Customer not found");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult createEmployee(String firstName, String lastName, String email, String password,
                                        String imageURL, String statusName, String roleName) {
        ServiceResult result = new ServiceResult();
        if (firstName != null && lastName != null && email != null && password != null &&
                statusName != null && roleName != null) {

            if (isStatusAndRoleExisted(statusName, roleName)) {
                boolean isEmailExisted = employeeRepository.existsByEmail(email);
                if (!isEmailExisted) {
                    EmployeeEntity employee = new EmployeeEntity(firstName, lastName, email,
                            encoder.encode(password),
                            imageURL,
                            statusRepository.findByName(StatusName.valueOf(statusName)),
                            roleRepository.findByName(RoleName.valueOf(roleName)));
                    employeeRepository.save(employee);
                    AdminCreateUserResponse response = new AdminCreateUserResponse(employee.getId(),
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getEmail(),
                            employee.getImageURL(),
                            employee.getStatus().getName().name(),
                            employee.getRole().getName().name());

                    result.setMessage(AdminUserCreateConst.SUCCESS);
                    result.setData(response);
                } else {
                    result.setStatus(ServiceResult.Status.FAILED);
                    result.setMessage(AdminUserCreateConst.EMAIL_EXIST);
                }

            } else {
                result.setMessage(AdminUserCreateConst.STATUS_ROLE_NOT_EXIST);
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage(AdminUserCreateConst.NULL_DATA);
            result.setStatus(ServiceResult.Status.FAILED);
        }

        return result;
    }

    @Override
    public ServiceResult signupAdmin(String firstName, String lastName, String email, String password) {
        ServiceResult result = new ServiceResult();
        StatusEntity activeStatus = statusRepository.findByName(StatusName.ACTIVE);
        boolean isEmailExisted = employeeRepository.existsByEmail(email);
        if (isEmailExisted) {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(CustomerSignUpConst.EMAIL_EXIST);
        } else {
            if (firstName != null && lastName != null && email != null && password != null && activeStatus != null) {
                EmployeeEntity employee = new EmployeeEntity(firstName, lastName, email,
                        encoder.encode(password),
                        activeStatus);
                employee.setRole(roleRepository.findByName(RoleName.ROLE_ADMIN));
                employeeRepository.save(employee);
                String jwt = authenticationWithJwt(email, password);
                CustomerSignInSignUpResponse response = new CustomerSignInSignUpResponse(employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getRole().getName().name(),
                        jwt);
                saveJwt(jwt);
                result.setMessage(CustomerSignUpConst.SUCCESS);
                result.setData(response);
            } else {
                result.setMessage(CustomerSignUpConst.NULL_DATA);
                result.setStatus(ServiceResult.Status.FAILED);
            }
        }
        return result;
    }

    @Override
    public ServiceResult paginateUser(int page, int size) {
        ServiceResult result = new ServiceResult();
        Pageable info = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<EmployeeEntity> employeeList = userPaginationRepository.findAllByDeletedAndRoleNameOrDeletedAndRoleName(info,
                false, RoleName.ROLE_CUSTOMER, false, RoleName.ROLE_SHOP);
        boolean isUserListEmpty = employeeList.isEmpty();
        if (!isUserListEmpty) {
            int totalPages = employeeList.getTotalPages();
            List<UserResponse> responses = new ArrayList<>();
            for (EmployeeEntity entity : employeeList) {
                UserResponse userResponse = new UserResponse(entity.getId(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getEmail(),
                        entity.getImageURL(),
                        entity.getStatus().getName().name(),
                        entity.getRole().getName().name());
                responses.add(userResponse);
            }
            AdminPaginateUserResponse response = new AdminPaginateUserResponse(totalPages, responses);
            result.setMessage("Users are returned successfully");
            result.setData(response);
        } else {
            result.setMessage("User list is empty");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult createCategory(String name, int parentID, int limit) {
        ServiceResult result = new ServiceResult();
        if (name != null) {
            boolean isCategoryExist = categoryRepository.existsByName(name);
            if (!isCategoryExist) {
                CategoryEntity parentCategory = categoryRepository.findById(parentID).orElse(null);
                if (parentCategory != null) {
                    CategoryEntity category = new CategoryEntity();
                    category.setName(name);
                    category.setParentID(parentID);
                    category.setLimited(limit);
                    categoryRepository.save(category);
                    AdminCreateCategoryResponse response = new AdminCreateCategoryResponse(category.getId(),
                            category.getName(),
                            parentCategory.getName());

                    result.setMessage(AdminUserCreateConst.SUCCESS);
                    result.setData(response);
                } else {
                    CategoryEntity category = new CategoryEntity();
                    category.setName(name);
                    category.setParentID(0);
                    categoryRepository.save(category);
                    AdminCreateCategoryResponse response = new AdminCreateCategoryResponse(category.getId(),
                            category.getName(),
                            null);
                    result.setMessage(AdminUserCreateConst.SUCCESS);
                    result.setData(response);
                }
            } else {
                result.setMessage("This category is existed");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage(AdminUserCreateConst.NULL_DATA);
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult updateCategory(int id, String name, String parentName) {
        ServiceResult result = new ServiceResult();
        CategoryEntity category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            boolean isCategoryExist = categoryRepository.existsByNameAndIdNot(name, id);
            if (!isCategoryExist) {
                if (parentName != null) {
                    CategoryEntity parentCategory = categoryRepository.findByName(parentName).orElse(null);
                    if (parentCategory != null) {
                        category.setName(name);
                        category.setParentID(parentCategory.getId());
                        categoryRepository.save(category);
                        AdminCreateCategoryResponse response = new AdminCreateCategoryResponse(category.getId(),
                                category.getName(),
                                parentName);

                        result.setMessage(AdminUserCreateConst.SUCCESS);
                        result.setData(response);
                    } else {
                        result.setStatus(ServiceResult.Status.FAILED);
                        result.setMessage("This category is not existed");
                    }
                } else {
                    category.setName(name);
                    category.setParentID(0);
                    categoryRepository.save(category);
                    AdminCreateCategoryResponse response = new AdminCreateCategoryResponse(category.getId(),
                            category.getName(),
                            null);

                    result.setMessage(AdminUserCreateConst.SUCCESS);
                    result.setData(response);
                }
            } else {
                result.setMessage("This name is already used");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage("This category is not exist");
            result.setStatus(ServiceResult.Status.FAILED);
        }

        return result;
    }

    @Override
    public ServiceResult paginateCategory(int page, int size) {
        ServiceResult result = new ServiceResult();
        Pageable info = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<CategoryEntity> categoryList = categoryPagingRepository.findAll(info);
        boolean isCategoryListEmpty = categoryList.isEmpty();
        if (!isCategoryListEmpty) {
            int totalPages = categoryList.getTotalPages();
            List<CustomerGetAllCategoryResponse> responses = new ArrayList<>();
            for (CategoryEntity entity : categoryList) {
                CustomerGetAllCategoryResponse response = new CustomerGetAllCategoryResponse();
                response.setId(entity.getId());
                response.setName(entity.getName());
                if (entity.getParentID() != 0) {
                    CategoryEntity category = categoryRepository.findById(entity.getParentID()).orElse(null);
                    if (category != null) response.setParentCategory(category.getName());
                }
                responses.add(response);
            }
            AdminPaginateCategoryResponse response = new AdminPaginateCategoryResponse(totalPages, responses);
            result.setMessage("Categories are returned successfully");
            result.setData(response);
        } else {
            result.setMessage("Category list is empty");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult deleteCategory(int id) {
        ServiceResult result = new ServiceResult();
        CategoryEntity category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            categoryRepository.delete(category);
            for (ProductEntity product : productRepository.findByCategoryId(id)) {
                product.setDeleted(true);
                productRepository.save(product);
            }
            result.setMessage("Delete category successfully");
        } else {
            result.setMessage(AdminConst.CATEGORY_NOT_FOUND);
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult paginateProduct(int page, int size) {
        ServiceResult result = new ServiceResult();

        Pageable info = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<ProductEntity> productList = productPaginationRepository.
                findAllByDeleted(info, false);
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
        return result;
    }

    @Override
    public ServiceResult createProduct(AdminCreateProductRequest request) {
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
                    AdminCreateProductResponse response = new AdminCreateProductResponse(product.getId(),
                            product.getName(),
                            product.getStatus().getName().name(),
                            product.getDescription(),
                            product.getQuantity(),
                            product.getOriginalPrice(),
                            product.getDiscount(),
                            product.getProductImageURL(),
                            product.getCategory().getName(),
                            product.getShop().getFirstName());
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
                if (category != null) {
                    ProductEntity product = productRepository.findByIdAndDeleted(request.getId(), false);
                    product.setName(request.getName());
                    product.setDescription(request.getDescription());
                    product.setOriginalPrice(request.getOriginalPrice());
                    product.setDiscount(request.getDiscount());
                    product.setStatus(statusRepository.findByName(StatusName.valueOf(request.getStatus())));
                    product.setQuantity(request.getQuantity());
                    if (request.getProductImageURL() == null) product.setProductImageURL(ShopConst.DEFAULT_AVATAR);
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
    public ServiceResult deleteOrderDetail(int id) {
        ServiceResult result = new ServiceResult();
        OrderDetailEntity orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail != null) {
            OrderEntity order = orderDetail.getOrder();
            order.setTotalPrice(order.getTotalPrice() - orderDetail.getPrice());
            orderDetailRepository.delete(orderDetail);
            orderRepository.save(order);
            result.setMessage("Delete order detail successfully");
        } else {
            result.setMessage("Cannot delete this order detail");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult deleteOrder(int id) {
        ServiceResult result = new ServiceResult();
        OrderEntity order = orderRepository.findByIdAndDeleted(id, false);
        if (order != null) {
            orderDetailRepository.deleteAll(orderDetailRepository.findByOrderId(id));
            orderRepository.delete(order);
            result.setMessage("Delete order successfully");
        } else {
            result.setMessage("Cannot delete this order");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult paginateOrder(int page, int size) {
        ServiceResult result = new ServiceResult();

        Pageable info = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<OrderEntity> orderList = orderPaginationRepository.findAll(info);
        boolean isOrderListEmpty = orderList.isEmpty();
        if (!isOrderListEmpty) {
            int totalPages = orderList.getTotalPages();
            List<AdminGetOrderResponse> responses = new ArrayList<>();
            for (OrderEntity entity : orderList) {

                AdminGetOrderResponse response = new AdminGetOrderResponse(entity.getId(),
                        entity.getStatus().getName().name(),
                        entity.getTotalPrice(),
                        entity.getEmployee().getId());
                responses.add(response);

            }
            AdminPaginateOrderResponse response = new AdminPaginateOrderResponse(totalPages, responses);
            result.setMessage("Orders are returned successfully");
            result.setData(response);
        } else {
            result.setMessage("Orders list is empty");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult paginateOrderDetail(int id, int page, int size) {
        ServiceResult result = new ServiceResult();

        Pageable info = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<OrderDetailEntity> orderDetailList = orderDetailPaginationRepository.findAllByOrderId(info, id);
        boolean isOrderDetailList = orderDetailList.isEmpty();
        if (!isOrderDetailList) {
            int totalPages = orderDetailList.getTotalPages();
            List<AdminGetOrderDetailResponse> responses = new ArrayList<>();
            for (OrderDetailEntity entity : orderDetailList) {

                AdminGetOrderDetailResponse response = new AdminGetOrderDetailResponse(entity.getId(),
                        entity.getPrice(),
                        entity.getQuantity(),
                        entity.getProduct().getId());
                responses.add(response);

            }
            AdminPaginateOrderDetailResponse response = new AdminPaginateOrderDetailResponse(totalPages, responses);
            result.setMessage("Order details are returned successfully");
            result.setData(response);
        } else {
            result.setMessage("Order details list is empty");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult createOrderDetail(int id, int quantity, int productID, int orderID) {
        ServiceResult result = new ServiceResult();
        OrderDetailEntity orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail != null) {
            ProductEntity product = productRepository.findByIdAndDeletedAndStatusName(productID, false, StatusName.ACTIVE);
            if (product != null) {
                OrderEntity order = orderRepository.findByIdAndDeleted(orderID, false);
                if (order != null) {
                    int count = product.getQuantity() + orderDetail.getQuantity() - quantity;
                    if (count >= 0) {
                        orderDetail.setProduct(product);
                        orderDetail.setQuantity(quantity);
                        double preTotalPrice = order.getTotalPrice() - orderDetail.getPrice();
                        orderDetail.setPrice(calculatePrice(quantity, product.getOriginalPrice(), product.getDiscount()));
                        order.setTotalPrice(preTotalPrice + orderDetail.getPrice());
                        orderDetailRepository.save(orderDetail);
                        orderRepository.save(order);
                        result.setMessage("Create order detail successfully");
                    } else {
                        result.setMessage("The quantity exceeded");
                        result.setStatus(ServiceResult.Status.FAILED);
                    }
                } else {
                    result.setStatus(ServiceResult.Status.FAILED);
                    result.setMessage("Order not found");
                }

            } else {
                result.setMessage("Product not found");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        }
        return result;
    }

    @Override
    public ServiceResult getAllShop() {
        ServiceResult result = new ServiceResult();
        List<EmployeeEntity> shopList = employeeRepository.findByDeletedAndRoleName(false, RoleName.ROLE_SHOP);
        List<AdminGetAllShopResponse> responses = new ArrayList<>();
        for (EmployeeEntity entity : shopList) {
            AdminGetAllShopResponse response = new AdminGetAllShopResponse(entity.getId(),
                    entity.getFirstName() + " " + entity.getLastName());
            responses.add(response);
        }
        result.setData(responses);
        result.setMessage("Get shops successfully");
        return result;
    }

    @Override
    public ServiceResult paginateCampaign(int page, int size) {
        ServiceResult result = new ServiceResult();

        Pageable info = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<CampaignEntity> campaignList = campaignPaginationRepository.findAll(info);
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
                        entity.getStartDate().toString(),
                        entity.getEndDate().toString(),
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

    @Override
    public ServiceResult createCampaign(AdminCreateCampaignRequest request) {
        ServiceResult result = new ServiceResult();
        if (!request.getProductURL().equals("") && !request.getTitle().equals("")
                && !request.getStatus().equals("") && !request.getName().equals("")) {
            boolean isStatusExist = Arrays.stream(StatusName.values()).anyMatch(t -> t.name().equals(request.getStatus()));
            if (isStatusExist) {
                if (request.getBid() < request.getBudget()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date startDate = formatter.parse(request.getStartDate());
                        Date endDate = formatter.parse(request.getEndDate());
                        if (startDate.before(endDate)) {
                            CampaignEntity campaignEntity = new CampaignEntity(request.getName(),
                                    statusRepository.findByName(StatusName.valueOf(request.getStatus())),
                                    startDate,
                                    endDate,
                                    request.getBudget(),
                                    request.getBid());
                            if (request.getImageURL().equals("")) {
                                campaignEntity.setImageURL(ShopConst.DEFAULT_AVATAR);
                            } else campaignEntity.setImageURL(request.getImageURL());
                            campaignEntity.setShop(employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(request.getShopID(),
                                    false,
                                    StatusName.ACTIVE,
                                    RoleName.ROLE_SHOP));
                            campaignEntity.setTitle(request.getTitle());
                            campaignEntity.setDescription(request.getDescription());
                            campaignEntity.setProductURL(request.getProductURL());
                            campaignEntity.setShop(employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(request.getShopID(),
                                    false,
                                    StatusName.ACTIVE,
                                    RoleName.ROLE_SHOP));
                            campaignRepository.save(campaignEntity);

                            AdminGetCampaignResponse response = new AdminGetCampaignResponse(
                                    campaignEntity.getId(),
                                    campaignEntity.getShop().getId(),
                                    campaignEntity.getName(),
                                    campaignEntity.getStatus().getName().name(),
                                    formatter.format(campaignEntity.getStartDate()),
                                    formatter.format(campaignEntity.getEndDate()),
                                    campaignEntity.getBudget());
                            response.setBid(campaignEntity.getBid());
                            response.setImageURL(campaignEntity.getImageURL());
                            response.setTitle(campaignEntity.getTitle());
                            response.setDescription(campaignEntity.getDescription());
                            response.setProductURL(campaignEntity.getProductURL());

                            result.setMessage("Successfully");
                            result.setData(response);
                        } else {
                            result.setMessage("Start date must less than end date");
                            result.setStatus(ServiceResult.Status.FAILED);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    result.setMessage("Bid must less than budget");
                    result.setStatus(ServiceResult.Status.FAILED);
                }
            } else {
                result.setMessage("Status not found");
                result.setStatus(ServiceResult.Status.FAILED);
            }

        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Fields cannot be empty");
        }

        return result;
    }

    @Override
    public ServiceResult updateCampaign(AdminUpdateCampaignRequest request) {
        ServiceResult result = new ServiceResult();
        CampaignEntity campaign = campaignRepository.findById(request.getId()).orElse(null);
        if (!request.getProductURL().equals("") && !request.getTitle().equals("")
                && !request.getStatus().equals("") && !request.getName().equals("")) {
            boolean isStatusExist = Arrays.stream(StatusName.values()).anyMatch(t -> t.name().equals(request.getStatus()));
            if (isStatusExist) {
                if (request.getBid() < request.getBudget()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date startDate = formatter.parse(request.getStartDate());
                        Date endDate = formatter.parse(request.getEndDate());
                        if (startDate.compareTo(endDate) < 0) {
                            campaign.setBid(request.getBid());
                            campaign.setBudget(request.getBudget());
                            campaign.setName(request.getName());
                            campaign.setStartDate(startDate);
                            campaign.setEndDate(endDate);
                            campaign.setDescription(request.getDescription());
                            campaign.setStatus(statusRepository.findByName(StatusName.valueOf(request.getStatus())));
                            campaign.setImageURL(request.getImageURL());
                            campaign.setProductURL(request.getProductURL());
                            campaignRepository.save(campaign);

                            AdminGetCampaignResponse response = new AdminGetCampaignResponse(campaign.getId(),
                                    campaign.getShop().getId(),
                                    campaign.getName(),
                                    campaign.getStatus().getName().name(),
                                    formatter.format(campaign.getStartDate()),
                                    formatter.format(campaign.getEndDate()),
                                    campaign.getBudget());
                            response.setBid(campaign.getBid());
                            response.setImageURL(campaign.getImageURL());
                            response.setTitle(campaign.getTitle());
                            response.setDescription(campaign.getDescription());
                            response.setProductURL(campaign.getProductURL());

                            result.setMessage("Update campaign successfully");
                            result.setData(response);
                        } else {
                            result.setMessage("Start date must less than end date");
                            result.setStatus(ServiceResult.Status.FAILED);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    result.setMessage("Bid must less than budget");
                    result.setStatus(ServiceResult.Status.FAILED);
                }
            } else {
                result.setMessage("Status not found");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Fields cannot be empty");
        }

        return result;
    }

    @Override
    public ServiceResult deleteCampaign(int id) {
        ServiceResult result = new ServiceResult();
        CampaignEntity campaign = campaignRepository.findById(id).orElse(null);
        if (campaign != null) {
            campaignRepository.delete(campaign);

            result.setMessage("Delete campaign successfully");
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Campaign not found");
        }
        return result;
    }

    private double calculatePrice(int quantity, double price, double discount) {
        return (price - price * discount / 100) * quantity;
    }

    private boolean isDiscountRight(int discount) {
        return discount >= 0 && discount < 100;
    }
}
