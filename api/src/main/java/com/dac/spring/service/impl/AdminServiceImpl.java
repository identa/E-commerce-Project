package com.dac.spring.service.impl;

import com.dac.spring.constant.AdminConst;
import com.dac.spring.constant.AdminUserCreateConst;
import com.dac.spring.constant.CustomerSignUpConst;
import com.dac.spring.constant.ShopConst;
import com.dac.spring.entity.*;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;
import com.dac.spring.model.req.AdminCreateProductRequest;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                    if (password != null){
                        employee.setPassword(encoder.encode(password));
                    }else employee.setPassword(employee.getPassword());
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
            employeeRepository.save(employee);
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
    public ServiceResult createCategory(String name, String parentName) {
        ServiceResult result = new ServiceResult();
        if (name != null) {
            boolean isCategoryExist = categoryRepository.existsByName(name);
            if (!isCategoryExist) {
                if (parentName != null) {
                    CategoryEntity parentCategory = categoryRepository.findByName(parentName).orElse(null);
                    if (parentCategory != null) {
                        CategoryEntity category = new CategoryEntity();
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
                    findAllByDeletedAndStatusName(info, false, StatusName.ACTIVE);
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
                                entity.getCategory().getName());
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
                CategoryEntity category = categoryRepository.findById(request.getCategoryID()).orElse(null);
                if (category != null){
                        if (isDiscountRight(request.getDiscount())){
                            ProductEntity product = new ProductEntity(request.getName(),
                                    statusRepository.findByName(StatusName.valueOf(request.getStatus())),
                                    request.getDescription(),
                                    request.getQuantity(),
                                    request.getOriginalPrice(),
                                    request.getDiscount(),
                                    request.getProductImageURL(),
                                    categoryRepository.findById(request.getCategoryID()).orElse(null),
                                    employeeRepository.findById(0).orElse(null));
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
                        }else {
                            result.setMessage("Discount is less than 100");
                            result.setStatus(ServiceResult.Status.FAILED);
                        }
                }else {
                    result.setMessage(AdminConst.CATEGORY_NOT_FOUND);
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
                if (category != null){
                    ProductEntity product = productRepository.findByIdAndDeletedAndStatusName(
                            request.getId(), false, StatusName.ACTIVE);
                    if (product != null){
                        if (isDiscountRight(request.getDiscount())) {
                            product.setName(request.getName());
                            product.setDescription(request.getDescription());
                            product.setOriginalPrice(request.getOriginalPrice());
                            product.setDiscount(request.getDiscount());
                            product.setStatus(statusRepository.findByName(StatusName.valueOf(request.getStatus())));
                            product.setQuantity(request.getQuantity());
                            product.setProductImageURL(request.getProductImageURL());
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
                        }
                        else {
                            result.setMessage("Discount is less than 100");
                            result.setStatus(ServiceResult.Status.FAILED);
                        }
                    }else {
                        result.setMessage(ShopConst.SHOP_NOT_FOUND);
                        result.setStatus(ServiceResult.Status.FAILED);
                    }
                }else {
                    result.setMessage(AdminConst.CATEGORY_NOT_FOUND);
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
        if (product != null){
            product.setDeleted(true);
            result.setMessage("Delete product successfully");
        }else {
            result.setMessage("Cannot delete this product");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    private boolean isDiscountRight(int discount){
        return discount >= 0 && discount < 100;
    }
}
