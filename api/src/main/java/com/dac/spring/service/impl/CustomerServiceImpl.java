package com.dac.spring.service.impl;

import com.dac.spring.constant.CustomerSignInConst;
import com.dac.spring.constant.CustomerSignUpConst;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
        if (category != null)
        response.setParentCategory(category.getName());
        else response.setParentCategory(null);

        return response;
    }

    private List<CustomerGetAllCategoryResponse> getAllCategoryResponseList(List<CategoryEntity> categoryList) {
        List<CustomerGetAllCategoryResponse> responseList = new ArrayList<>();
        for (CategoryEntity category : categoryList) {
            responseList.add(getAllCategoryResponse(category));
        }
        return responseList;
    }

    private JWTEntity saveJwt(String token){
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
    public ServiceResult signIn(String email, String password) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByEmail(email).orElse(null);
        if (customer != null) {
            if (!customer.isDeleted()){
                boolean isPasswordChecked = encoder.matches(password, customer.getPassword());
                if (isPasswordChecked) {
                    String jwt = authenticationWithJwt(email, password);
                    CustomerSignInSignUpResponse response = new CustomerSignInSignUpResponse(customer.getId(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getRole().getName().name(),
                            jwt);

                    saveJwt(jwt);
                    result.setMessage(CustomerSignInConst.SUCCESS);
                    result.setData(response);
                } else {
                    result.setStatus(ServiceResult.Status.FAILED);
                    result.setMessage(CustomerSignInConst.EMAIL_PASSWORD_WRONG_FORMAT);
                }
            }else {
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
    public ServiceResult signOut(HttpServletRequest request) {
        ServiceResult result = new ServiceResult();
        String token = request.getHeader("Authorization").split(" ")[1];
        JWTEntity jwt = jwtRepository.findByToken(token).orElse(null);
        if (jwt != null){
            jwtRepository.delete(jwt);
            result.setMessage("Sign out successfully");
        }else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Cannot sign out");
        }
        return result;
    }

    @Override
    public ServiceResult getInfoById(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_CUSTOMER).orElse(null);
        if (customer != null) {
            CustomerGetInfoResponse response = new CustomerGetInfoResponse(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getImageURL());
            result.setData(response);
            result.setMessage("Get info successfully");
        } else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Customer not found");
        }
        return result;
    }

    @Override
    public ServiceResult updateInfo(int id, String firstName, String lastName, String password, String imageURL) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByIdAndDeletedAndRoleName(id, false,
                RoleName.ROLE_CUSTOMER).orElse(null);
        if (customer != null) {
            if (firstName != null && lastName != null && password != null) {
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setPassword(encoder.encode(password));
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
            result.setMessage("Customer not found");
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
                if (category != null){
                    CustomerGetProductByCatResponse response = new CustomerGetProductByCatResponse(entity.getId(),
                            entity.getName(),
                            entity.getDescription(),
                            entity.getOriginalPrice(),
                            entity.getDiscount(),
                            entity.getProductImageURL(),
                            category.getName());
                    responses.add(response);
                }
            }
            CustomerPaginateProductByCatResponse response = new CustomerPaginateProductByCatResponse(totalPages, responses);
            result.setMessage("Products are returned successfully");
            result.setData(response);
        } else {
            result.setMessage("Product list is empty");
            result.setStatus(ServiceResult.Status.FAILED);
        }
        return result;
    }

    @Override
    public ServiceResult returnRole(HttpServletRequest request) {
        ServiceResult result = new ServiceResult();
        String authHeader = request.getHeader("Authorization").split(" ")[1];
        EmployeeEntity employee = employeeRepository.findByEmail(getUserNameFromJwtToken(authHeader)).orElse(null);
        if (employee != null)
        result.setData(employee.getRole().getName().name());
        else result.setStatus(ServiceResult.Status.FAILED);
        return result;
    }

//    @Override
//    public ServiceResult createOrder(int customerID, List<CustomerCreateOrderDetailRequest> orderDetailRequests) {
//        ServiceResult result = new ServiceResult();
//        OrderEntity createdOrder = createOrderEntity(customerID);
//        if (createdOrder != null) orderRepository.save(createdOrder);
//        orderDetailRepository.saveAll(createOrderDetailList(orderDetailRequests, createdOrder));
//
//        return result;
//    }

    @Override
    public ServiceResult createOrder(int customerID, List<CustomerCreateOrderDetailRequest> orderDetailRequests) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(customerID,
                false, StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);
        if (customer != null) {
            OrderEntity createdOrder = new OrderEntity(statusRepository.findByName(StatusName.ACTIVE), customer);
            orderRepository.save(createdOrder);
            List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
            double totalPrice =0;
            for (CustomerCreateOrderDetailRequest detailRequest : orderDetailRequests){
                ProductEntity product = productRepository.findByIdAndDeletedAndStatusName(detailRequest.getProductID(),
                        false, StatusName.ACTIVE);
                if (product != null){
                    if (product.getQuantity() >= detailRequest.getQuantity()){
                        OrderDetailEntity orderDetail = new OrderDetailEntity(
                                calculatePrice(detailRequest.getQuantity(),product.getOriginalPrice(),product.getDiscount()),
                                detailRequest.getQuantity(),
                                product,
                                createdOrder);
                        orderDetailEntityList.add(orderDetail);
                        product.setQuantity(product.getQuantity()-detailRequest.getQuantity());
                        totalPrice+= calculatePrice(detailRequest.getQuantity(),product.getOriginalPrice(),product.getDiscount());
                    }else {
                        result.setStatus(ServiceResult.Status.FAILED);
                        result.setMessage("The quantity of " + product.getName() +" is exceeded");
                        return result;
                    }
                }else {
                    result.setStatus(ServiceResult.Status.FAILED);
                    result.setMessage("Product not found");
                    return result;
                }
            }
            orderRepository.save(createdOrder);
            orderDetailRepository.saveAll(orderDetailEntityList);
            List<CustomerCreateOrderDetailResponse> orderDetailResponses = new ArrayList<>();
            for (OrderDetailEntity orderDetail : orderDetailEntityList){
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
        }else {
            result.setStatus(ServiceResult.Status.FAILED);
            result.setMessage("Customer not found");
        }
        return result;
    }

    private double calculatePrice(int quantity, double price, double discount){
        return (price - price*discount/100)*quantity;
    }

    private String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    private OrderDetailEntity createOrderDetail(CustomerCreateOrderDetailRequest request, OrderEntity order) {
        ProductEntity product = productRepository.findByIdAndDeletedAndStatusName(request.getProductID(),
                false, StatusName.ACTIVE);
        if (product != null && order != null) {
            return new OrderDetailEntity(product.getOriginalPrice()*product.getDiscount(),
                    request.getQuantity(),
                    product,
                    order);
        } else return null;
    }

    private List<OrderDetailEntity> createOrderDetailList(List<CustomerCreateOrderDetailRequest> requestList, OrderEntity order) {
        List<OrderDetailEntity> orderDetailList = new ArrayList<>();
        for (CustomerCreateOrderDetailRequest detailCreateRequest : requestList) {
            orderDetailList.add(createOrderDetail(detailCreateRequest, order));
        }
        return orderDetailList;
    }

    private OrderEntity createOrderEntity(int customerID) {
        EmployeeEntity customer = employeeRepository.findByIdAndDeletedAndRoleName(customerID,
                false, RoleName.ROLE_CUSTOMER).orElse(null);
        if (customer != null) return new OrderEntity(statusRepository.findByName(StatusName.ACTIVE), customer);
        else return null;
    }
}
