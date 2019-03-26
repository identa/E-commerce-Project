package com.dac.spring.service.impl;

import com.dac.spring.constant.CustomerConst;
import com.dac.spring.constant.CustomerSignInConst;
import com.dac.spring.constant.CustomerSignUpConst;
import com.dac.spring.constant.ShopConst;
import com.dac.spring.entity.*;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;
import com.dac.spring.model.req.CustomerCreateOrderDetailRequest;
import com.dac.spring.model.resp.*;
import com.dac.spring.repository.*;
import com.dac.spring.service.CustomerService;
import com.dac.spring.utils.jwt.JwtProvider;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductPaginationRepository productPaginationRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    JWTRepository jwtRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder encoder;

    @Value("${jwtSecretKey}")
    private String jwtSecret;

    @Value("${jwtExpireTime}")
    private int jwtExpiration;

    private String authenticationWithJwt(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateJwtToken(authentication);
    }

    private CustomerGetCategoryTreeResponse getCategoryTreeResponse(CategoryEntity categoryEntity) {
        CustomerGetCategoryTreeResponse response = new CustomerGetCategoryTreeResponse();
        response.setId(categoryEntity.getId());
        response.setName(categoryEntity.getName());
        response.setSubCategory(getCategoryTreeResponseList(categoryRepository.findAllByParentID(categoryEntity.getId())));
        if (response.getSubCategory().isEmpty()) response.setSubCategory(null);

        return response;
    }

    private List<CustomerGetCategoryTreeResponse> getCategoryTreeResponseList(List<CategoryEntity> categoryList) {
        List<CustomerGetCategoryTreeResponse> responseList = new ArrayList<>();
        for (CategoryEntity category : categoryList) {
            responseList.add(getCategoryTreeResponse(category));
        }
        return responseList;
    }

    private CustomerGetAllCategoryResponse getAllCategoryResponse(CategoryEntity categoryEntity) {
        CustomerGetAllCategoryResponse response = new CustomerGetAllCategoryResponse();
        response.setId(categoryEntity.getId());
        response.setName(categoryEntity.getName());
        CategoryEntity category = categoryRepository.findById(categoryEntity.getParentID()).orElse(null);
        if (category != null) {
            response.setParentCategory(category.getName());
        } else response.setParentCategory(null);

        return response;
    }

    private List<CustomerGetAllCategoryResponse> getAllCategoryResponseList(List<CategoryEntity> categoryList) {
        List<CustomerGetAllCategoryResponse> responseList = new ArrayList<>();
        for (CategoryEntity category : categoryList) {
            responseList.add(getAllCategoryResponse(category));
        }
        return responseList;
    }

    private JWTEntity saveJwt(String token) {
        JWTEntity jwtEntity = new JWTEntity();
        jwtEntity.setToken(token);
        return jwtRepository.save(jwtEntity);
    }

    @Override
    public ServiceResult signUpCustomer(String email, String password, String firstName, String lastName) {
        ServiceResult result = new ServiceResult();
        StatusEntity activeStatus = statusRepository.findByName(StatusName.ACTIVE);
        boolean isEmailExist = employeeRepository.existsByEmail(email);
        if (isEmailExist) {
            if (employeeRepository.existsByEmailAndDeleted(email, true)) {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage("This email cannot be used to sign up");
            } else {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage(CustomerSignUpConst.EMAIL_EXIST);
            }
        } else {
            if (firstName != null && lastName != null && email != null && password != null && activeStatus != null) {
                EmployeeEntity employee = new EmployeeEntity(firstName, lastName, email,
                        encoder.encode(password),
                        activeStatus);
                employee.setRole(roleRepository.findByName(RoleName.ROLE_CUSTOMER));
                employee.setImageURL(ShopConst.DEFAULT_AVATAR);
                employeeRepository.save(employee);
                String jwt = authenticationWithJwt(email, password);
                CustomerSignInSignUpResponse response = new CustomerSignInSignUpResponse(employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getRole().getName().name(),
                        employee.getImageURL(),
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
    public ServiceResult signIn(String email, String password) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByEmail(email).orElse(null);
        if (customer != null) {
            if (!customer.isDeleted()) {
                boolean isPasswordChecked = encoder.matches(password, customer.getPassword());
                if (isPasswordChecked) {
                    String jwt = authenticationWithJwt(email, password);
                    CustomerSignInSignUpResponse response = new CustomerSignInSignUpResponse(customer.getId(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getRole().getName().name(),
                            customer.getImageURL(),
                            jwt);

                    saveJwt(jwt);
                    result.setMessage(CustomerSignInConst.SUCCESS);
                    result.setData(response);
                } else {
                    result.setStatus(ServiceResult.Status.FAILED);
                    result.setMessage(CustomerSignInConst.EMAIL_PASSWORD_WRONG_FORMAT);
                }
            } else {
                result.setStatus(ServiceResult.Status.FAILED);
                result.setMessage("Cannot sign in with this account");
            }


        } else {
            result.setMessage(CustomerSignInConst.EMAIL_NOT_FOUND);
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult signOut(String token) {
        ServiceResult result = new ServiceResult();
        String authHeader = token.replace("Bearer ", "");
        JWTEntity jwt = jwtRepository.findByToken(authHeader).orElse(null);
        if (jwt != null) {
            jwtRepository.delete(jwt);
            result.setMessage("Sign out successfully");
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Cannot sign out");
        }
        return result;
    }

    @Override
    public ServiceResult getInfo(String token) {
        ServiceResult result = new ServiceResult();
        String authHeader = token.replace("Bearer ", "");
        EmployeeEntity customer = employeeRepository.findByEmail(getUserNameFromJwtToken(authHeader)).orElse(null);
        if (customer != null) {
            CustomerGetInfoResponse response = new CustomerGetInfoResponse(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getImageURL());
            result.setData(response);
            result.setMessage("Get info successfully");
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(CustomerConst.CUSTOMER_NOT_FOUND);
        }
        return result;
    }

    @Override
    public ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(id, false,
                StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);
        if (customer != null) {
            if (firstName != null && lastName != null && password != null) {
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                if (!password.equals("")) {
                    customer.setPassword(encoder.encode(password));
                }
                if (imageURL.equals("")) {
                    customer.setImageURL(ShopConst.DEFAULT_AVATAR);
                }
                customer.setImageURL(imageURL);
                employeeRepository.save(customer);
                CustomerUpdateInfoResponse response = new CustomerUpdateInfoResponse(customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getImageURL());
                result.setMessage("Update info successfully");
                result.setData(response);
            } else {
                result.setMessage("Fields cannot be empty");
                result.setStatus(ServiceResult.Status.FAILED);
            }
        } else {
            result.setMessage(CustomerConst.CUSTOMER_NOT_FOUND);
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult getCategoryTree() {
        ServiceResult result = new ServiceResult();
        List<CustomerGetCategoryTreeResponse> categoryResponses = getCategoryTreeResponseList(categoryRepository.findAllByParentID(0));
        result.setMessage("Get category tree successfully");
        result.setData(categoryResponses);
        return result;
    }

    @Override
    public ServiceResult getAllCategory() {
        ServiceResult result = new ServiceResult();
        List<CustomerGetAllCategoryResponse> categoryResponses = getAllCategoryResponseList(categoryRepository.findAll());
        result.setMessage("Get category successfully");
        result.setData(categoryResponses);
        return result;
    }

    @Override
    public ServiceResult paginateProductByCat(int id, int page, int size) {
        ServiceResult result = new ServiceResult();
        Pageable info = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<ProductEntity> productList = productPaginationRepository.
                findAllByDeletedAndStatusNameAndCategoryIdAndQuantityGreaterThan(
                        info, false, StatusName.ACTIVE, id, 0);
        boolean isProductListEmpty = productList.isEmpty();
        if (!isProductListEmpty) {
            int totalPages = productList.getTotalPages();
            List<CustomerGetProductByCatResponse> responses = new ArrayList<>();
            for (ProductEntity entity : productList) {
                CategoryEntity category = categoryRepository.findById(id).orElse(null);
                if (category != null) {
                    CustomerGetProductByCatResponse response = new CustomerGetProductByCatResponse(entity.getId(),
                            entity.getName(),
                            entity.getDescription(),
                            entity.getOriginalPrice(),
                            entity.getDiscount(),
                            entity.getProductImageURL(),
                            category.getName(),
                            category.getId());
                    responses.add(response);
                }
            }
            CustomerPaginateProductByCatResponse response = new CustomerPaginateProductByCatResponse(totalPages, responses);
            result.setMessage(CustomerConst.PRODUCT_SUCCESS);
            result.setData(response);
        } else {
            result.setMessage("Product list is empty");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult getProduct(int id) {
        ServiceResult result = new ServiceResult();
        ProductEntity product = productRepository.findByIdAndDeletedAndStatusName(id, false, StatusName.ACTIVE);
        if (product!= null) {
                CategoryEntity category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
                if (category != null) {
                    CustomerGetProductByCatResponse response = new CustomerGetProductByCatResponse(product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getOriginalPrice(),
                            product.getDiscount(),
                            product.getProductImageURL(),
                            category.getName(),
                            category.getId());
                    result.setMessage(CustomerConst.PRODUCT_SUCCESS);
                    result.setData(response);
                }else {
                    result.setMessage("Category not found");
                    result.setStatus(ServiceResult.Status.FAILED);
                }
        } else {
            result.setMessage("Product not found");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult returnRole(String token) {
        ServiceResult result = new ServiceResult();
        String authHeader = token.replace("Bearer ", "");
        EmployeeEntity employee = employeeRepository.findByEmail(getUserNameFromJwtToken(authHeader)).orElse(null);
        if (employee != null) {
            result.setData(employee.getRole().getName().name());
        } else result.setStatus(ServiceResult.Status.FAILED);
        return result;
    }

    @Override
    public ServiceResult createOrder(int customerID, List<CustomerCreateOrderDetailRequest> orderDetailRequests) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(customerID,
                false, StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);
        if (customer != null) {
            OrderEntity createdOrder = new OrderEntity(statusRepository.findByName(StatusName.PAUSE), customer);
            orderRepository.save(createdOrder);
            List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
            double totalPrice = 0;
            for (CustomerCreateOrderDetailRequest detailRequest : orderDetailRequests) {
                ProductEntity product = productRepository.findByIdAndDeletedAndStatusName(detailRequest.getProductID(),
                        false, StatusName.ACTIVE);
                if (product != null) {
                    if (product.getQuantity() >= detailRequest.getQuantity()) {
                        OrderDetailEntity orderDetail = new OrderDetailEntity(
                                calculatePrice(detailRequest.getQuantity(), product.getOriginalPrice(), product.getDiscount()),
                                detailRequest.getQuantity(),
                                product,
                                createdOrder);
                        orderDetailEntityList.add(orderDetail);
                        product.setQuantity(product.getQuantity() - detailRequest.getQuantity());
                        totalPrice += calculatePrice(detailRequest.getQuantity(), product.getOriginalPrice(), product.getDiscount());
                    } else {
                        result.setStatus(ServiceResult.Status.FAILED);
                        result.setMessage("The quantity of " + product.getName() + " is exceeded");
                        return result;
                    }
                } else {
                    result.setStatus(ServiceResult.Status.FAILED);
                    result.setMessage("Product not found");
                    return result;
                }
            }
            orderRepository.save(createdOrder);
            orderDetailRepository.saveAll(orderDetailEntityList);
            List<CustomerCreateOrderDetailResponse> orderDetailResponses = new ArrayList<>();
            for (OrderDetailEntity orderDetail : orderDetailEntityList) {
                CustomerCreateOrderDetailResponse response = new CustomerCreateOrderDetailResponse(orderDetail.getId(),
                        orderDetail.getPrice(),
                        orderDetail.getQuantity(),
                        orderDetail.getProduct().getName());
                orderDetailResponses.add(response);
            }
            CustomerCreateOrderResponse orderResponse = new CustomerCreateOrderResponse(createdOrder.getId(),
                    totalPrice,
                    customerID,
                    orderDetailResponses);
            result.setMessage("You ordered successfully");
            result.setData(orderResponse);
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage(CustomerConst.CUSTOMER_NOT_FOUND);
        }
        return result;
    }

    @Override
    public ServiceResult deleteOrder(int id) {
        ServiceResult result = new ServiceResult();
        OrderEntity order = orderRepository.findByIdAndDeletedAndStatusName(id, false, StatusName.ACTIVE);
        if (order != null) {
            orderRepository.delete(order);
            result.setMessage("Delete order successfully");
        } else {
            result.setMessage("Cannot delete this order");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult searchProduct(String name, int page, int size) {
        ServiceResult result = new ServiceResult();

        PageRequest pageRequest = new PageRequest(page - 1, size);
        List<ProductEntity> list = productRepository.findAll();
        List<ProductEntity> useList = new ArrayList<>();
        for (ProductEntity productEntity : list) {
            String subName = productEntity.getName().replaceAll("\\s+", "");
            if (StringUtils.containsIgnoreCase(subName, name.replaceAll("\\s+", ""))) {
                useList.add(productEntity);
            }
        }
        int start = (int) pageRequest.getOffset();
        int end = (start + size) > useList.size() ? useList.size() : (start + size);
        if (start < end) {
            Page<ProductEntity> productList = new PageImpl<>(useList.subList(start, end), pageRequest, useList.size());
            List<CustomerGetProductByCatResponse> responses = new ArrayList<>();
            for (ProductEntity product : productList) {
                CustomerGetProductByCatResponse response = new CustomerGetProductByCatResponse(product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getOriginalPrice(),
                        product.getDiscount(),
                        product.getProductImageURL(),
                        product.getCategory().getName(),
                        product.getCategory().getId());
                responses.add(response);
            }
            result.setData(responses);
            result.setMessage(CustomerConst.PRODUCT_SUCCESS);
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("This page is empty");
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
            List<CustomerPaginateProductResponse> responses = new ArrayList<>();
            for (ProductEntity entity : productList) {
                CustomerPaginateProductResponse response = new CustomerPaginateProductResponse(entity.getId(),
                        entity.getName(),
                        entity.getOriginalPrice(),
                        entity.getDiscount(),
                        entity.getProductImageURL());
                if (entity.getCategory().getLimited() < entity.getQuantity())response.setLimit(entity.getCategory().getLimited());
                else response.setLimit(entity.getQuantity());
                responses.add(response);
            }
            CustomerPaginateProductListResponse response = new CustomerPaginateProductListResponse(totalPages, responses);
            result.setMessage(CustomerConst.PRODUCT_SUCCESS);
            result.setData(response);
        } else {
            result.setMessage("Product list is empty");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult getCampaign() {
        ServiceResult result = new ServiceResult();
        Date currentDate = new Date();
        Random random = new Random();
        List<CampaignEntity> list = getCampaignList(currentDate, currentDate);
        List<CampaignEntity> validList = new ArrayList<>();
        List<CampaignEntity> activeList = new ArrayList<>();
        List<CustomerGetCampaignResponse> responses = new ArrayList<>();

        for (CampaignEntity campaignEntity : list) {
            if (campaignEntity.getBudget() >= campaignEntity.getBid())
                validList.add(campaignEntity);
        }
        if (validList.size() < CustomerConst.CAMPAIGN_AMOUNT) {
            for (CampaignEntity campaign : validList){
                activeList.add(campaign);
            }
            for (int j = 1; j <= CustomerConst.CAMPAIGN_AMOUNT - validList.size(); j++) {
                activeList.add(campaignRepository.findByNameContaining("Default campaign " + j));
            }
        } else {
            for (int k = 1; k <= CustomerConst.CAMPAIGN_AMOUNT; k++) {
                activeList.add(validList.get(random.nextInt(validList.size())));
            }
        }
        for (CampaignEntity campaign : activeList) {
            campaign.setSpend(campaign.getSpend() + campaign.getBid());

            campaignRepository.save(campaign);

            CustomerGetCampaignResponse response = new CustomerGetCampaignResponse(campaign.getTitle(),
                    campaign.getImageURL(),
                    campaign.getProductURL());
            responses.add(response);
        }
        result.setMessage("Get campaign successfully");
        result.setData(responses);
        return result;
    }

    private double calculatePrice(int quantity, double price, double discount) {
        return (price - price * discount / 100) * quantity;
    }

    private String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    private List<CampaignEntity> getCampaignList(Date startDate, Date endDate) {
        return campaignRepository.findAllByStartDateLessThanEqualAndEndDateGreaterThanAndStatusNameOrderByBidDesc(startDate, endDate, StatusName.ACTIVE);
    }
}
