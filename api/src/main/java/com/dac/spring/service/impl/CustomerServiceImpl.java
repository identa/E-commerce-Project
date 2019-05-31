package com.dac.spring.service.impl;

import com.dac.spring.constant.CustomerConst;
import com.dac.spring.constant.CustomerSignInConst;
import com.dac.spring.constant.CustomerSignUpConst;
import com.dac.spring.constant.ShopConst;
import com.dac.spring.entity.*;
import com.dac.spring.model.ServiceResult;
import com.dac.spring.model.enums.RoleName;
import com.dac.spring.model.enums.StatusName;
import com.dac.spring.model.req.AddOrderRequest;
import com.dac.spring.model.req.AddressRequest;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    CartRepository cartRepository;

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    EmployeeInfoRepository employeeInfoRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JavaMailSender javaMailSender;

    @PersistenceContext
    EntityManager entityManager;

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
                String input = firstName + lastName + password;
                String hashtext;
                try {

                    // Static getInstance method is called with hashing MD5
                    MessageDigest md = MessageDigest.getInstance("MD5");

                    // digest() method is called to calculate message digest
                    //  of an input digest() return array of byte
                    byte[] messageDigest = md.digest(input.getBytes());

                    // Convert byte array into signum representation
                    BigInteger no = new BigInteger(1, messageDigest);

                    // Convert message digest into hex value
                    hashtext = no.toString(16);
                    while (hashtext.length() < 32) {
                        hashtext = "0" + hashtext;
                    }
                }

                // For specifying wrong message digest algorithms
                catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                EmployeeEntity employee = new EmployeeEntity(firstName, lastName, email,
                        encoder.encode(password),
                        activeStatus);
                employee.setRole(roleRepository.findByName(RoleName.ROLE_CUSTOMER));
                employee.setImageURL(ShopConst.DEFAULT_AVATAR);
                employee.setConfirmCode(hashtext);
                employeeRepository.save(employee);
                String jwt = authenticationWithJwt(email, password);
                CustomerSignInSignUpResponse response = new CustomerSignInSignUpResponse(employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getRole().getName().name(),
                        employee.getImageURL(),
                        jwt);

                saveJwt(jwt);

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(email);
                mailMessage.setSubject("Email confirmation");
                mailMessage.setText("To confirm your account, please click here: " + CustomerSignUpConst.CONFIRM_URL + "?email=" + email + "&code=" + employee.getConfirmCode());

                javaMailSender.send(mailMessage);

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
                    if (customer.getConfirmCode() == null) {
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
                    }else {
                        result.setStatus(ServiceResult.Status.FAILED);
                        result.setMessage("This account is not verified yet. Please check your inbox");
                    }
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
    public ServiceResult signInCustomer(String email, String password) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity customer = employeeRepository.findByEmail(email).orElse(null);
        if (customer != null) {
            if (!customer.isDeleted()) {
                boolean isPasswordChecked = encoder.matches(password, customer.getPassword());
                if (isPasswordChecked) {
                    String jwt = authenticationWithJwt(email, password);
                    boolean isAddAddress = employeeInfoRepository.existsByEmployee(customer);
                    SignUpResponse response = new SignUpResponse(customer.getId(),
                            customer.getFirstName(),
                            customer.getLastName(),
                            customer.getRole().getName().name(),
                            customer.getImageURL(),
                            jwt,
                            isAddAddress);

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
    public ServiceResult getAddress(int id) {
        ServiceResult result = new ServiceResult();
        EmployeeInfoEntity entity = employeeInfoRepository.findByEmployeeId(id);
//        GetAddressResp resp = new GetAddressResp();
//        GetAddressResponse response;
        if (entity == null){
//            response = new GetAddressResponse();
        }else {
            GetAddressResponse response = new GetAddressResponse(entity.getRecipientName(),
                    entity.getCity(),
                    entity.getAddress(),
                    entity.getState(),
                    entity.getPostalCode());
//            resp = new GetAddressResp(response);
            result.setMessage("Get address successfully");
            result.setData(response);
        }
//        result.setData(resp);
        return result;
    }

    @Override
    public ServiceResult addAddress(int id, AddressRequest request) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity entity = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(id, false, StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);

        if (entity == null){
//            response = new GetAddressResponse();
        }else {
            EmployeeInfoEntity employeeInfoEntity = new EmployeeInfoEntity(request.getRecipientName(),
                    request.getCity(),
                    request.getAddress(),
                    request.getState(),
                    request.getPostalCode(),
                    entity);
            employeeInfoRepository.save(employeeInfoEntity);
            GetAddressResponse response = new GetAddressResponse(employeeInfoEntity.getRecipientName(),
                    employeeInfoEntity.getCity(),
                    employeeInfoEntity.getAddress(),
                    employeeInfoEntity.getState(),
                    employeeInfoEntity.getPostalCode());
//            resp = new GetAddressResp(response);
            result.setMessage("Add address successfully");
            result.setData(response);
        }
//        result.setData(resp);
        return result;
    }

    @Override
    public ServiceResult editAddress(int id, AddressRequest request) {
        ServiceResult result = new ServiceResult();
        EmployeeInfoEntity entity = employeeInfoRepository.findByEmployeeId(id);

        if (entity == null){
//            response = new GetAddressResponse();
        }else {
            entity.setAddress(request.getAddress());
            entity.setCity(request.getCity());
            entity.setRecipientName(request.getRecipientName());
            entity.setState(request.getState());
            entity.setPostalCode(request.getPostalCode());

            employeeInfoRepository.save(entity);
            GetAddressResponse response = new GetAddressResponse(entity.getRecipientName(),
                    entity.getCity(),
                    entity.getAddress(),
                    entity.getState(),
                    entity.getPostalCode());
//            resp = new GetAddressResp(response);
            result.setMessage("Edit address successfully");
            result.setData(response);
        }
//        result.setData(resp);
        return result;
    }

    @Override
    public ServiceResult getOrderDetails(int id) {
        ServiceResult result = new ServiceResult();

        List<OrderDetailEntity> list = orderDetailRepository.findByOrderId(id);

        List<OrderDetailsResponse> responses = new ArrayList<>();
        for (OrderDetailEntity entity : list){
            OrderDetailsResponse response = new OrderDetailsResponse(entity.getId(),
                    entity.getProduct().getName(),
                    entity.getProduct().getProductImageURL(),
                    entity.getPrice(),
                    entity.getQuantity());
            responses.add(response);
        }
        result.setMessage("Get order detail sccessfully");
        result.setData(responses);
        return result;
    }

    @Override
    public ServiceResult sendMail() {
        ServiceResult result = new ServiceResult();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("tatrunganh14t1@gmail.com");
        mailMessage.setSubject("Order receipt");
        mailMessage.setText("test");

        javaMailSender.send(mailMessage);
        result.setMessage("Send order receipt successfully");
        return result;
    }

    @Override
    public ServiceResult verify(String email, String code) {
        ServiceResult result = new ServiceResult();

        EmployeeEntity customer = employeeRepository.findByEmail(email).orElse(null);
        if (customer != null){
            if (customer.getConfirmCode().equals(code.trim())){
                customer.setConfirmCode(null);
                employeeRepository.save(customer);
                result.setMessage("Verify account successfully");
            }
        }

        return result;
    }

    @Override
    public ServiceResult getSorted(int min, int max, List<String> names, int orderBy) {
        ServiceResult result = new ServiceResult();
        List<ProductEntity> entityList = new ArrayList<>();
        if (names == null){
            names = new ArrayList<>();
            for (CategoryEntity categoryEntity : categoryRepository.findAll()){
                names.add(categoryEntity.getName());
            }
        }
        if (orderBy == 0){
            entityList = productRepository.getSortedProduct(min, max, names);
        } else if (orderBy == 1){
            entityList = productRepository.getSortedProduct2(min, max, names);
        } else if (orderBy == 2){
            entityList = productRepository.getSortedProduct3(min, max, names);
        }else if (orderBy == 3){
            entityList = productRepository.getSortedProduct4(min, max, names);
        } else if (orderBy == 4){
            entityList = productRepository.getSortedProduct5(min, max, names);
        }
        
        List<ProductEntity> test = new ArrayList<>();
        for (ProductEntity entity : entityList){
            if (names.contains(entity.getCategory().getName())){
                test.add(entity);
            }
        }

//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);
//        Root<ProductEntity> productEntityRoot = criteriaQuery.from(ProductEntity.class);
////        Join<ProductEntity, CategoryEntity> join = productEntityRoot.join("catName");
//        Predicate predicate = criteriaBuilder.greaterThanOrEqualTo()
//        criteriaQuery.select(productEntityRoot);
//        criteriaQuery.where(productEntityRoot.get(""))
//        Query query = entityManager.createQuery(criteriaQuery);
//
//        query.getResultList();

        List<CustomerGetMOProductAllResponse> responses = new ArrayList<>();
        for (ProductEntity entity : test){
            CustomerGetMOProductAllResponse response = new CustomerGetMOProductAllResponse(entity.getId(),
                    entity.getProductImageURL(),
                    entity.getName(),
                    4.5,
                    20,
                    entity.getCurrentPrice(),
                    entity.getOriginalPrice());
            responses.add(response);
        }

        result.setMessage("sort successfully");
        result.setData(responses);
        return result;
    }

    @Override
    public ServiceResult getRating(int pid, int uid) {
        ServiceResult result = new ServiceResult();
        RatingEntity ratingEntity = ratingRepository.findByProductIdAndUserId(pid, uid);
        int total1 = 0;
        int total2 = 0;
        int total3 = 0;
        int total4 = 0;
        int total5 = 0;
        int total = 0;
        GetRatingResp resp;
        Float avg = ratingRepository.avg(pid);
        for (RatingEntity entity : ratingRepository.findAllByProductId(pid)){
            if (entity.getRate() == 1){
                total1 += entity.getRate();
            }
            if (entity.getRate() == 2){
                total2 += entity.getRate();
            }
            if (entity.getRate() == 3){
                total3 += entity.getRate();
            }
            if (entity.getRate() == 4){
                total4 += entity.getRate();
            }
            if (entity.getRate() == 5){
                total5 += entity.getRate();
            }
            total++;
        }

        Integer rating = null;
        if (ratingEntity != null) rating = ratingEntity.getRate();

        resp = new GetRatingResp(total1, total2, total3, total4, total5, total, avg, rating);

        result.setMessage("Get rating successfully");
        result.setData(resp);
        return result;
    }

    @Override
    public ServiceResult editInfo(int id, String fn, String ln, String photo) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(id, false, StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);
        employeeEntity.setFirstName(fn);
        employeeEntity.setLastName(ln);
        employeeEntity.setImageURL(photo);

        employeeRepository.save(employeeEntity);
        EditInfoResp resp = new EditInfoResp(employeeEntity.getFirstName(),
                employeeEntity.getLastName(),
                employeeEntity.getImageURL());

        result.setData(resp);
        result.setMessage("Edit info successfully");
        return result;
    }

    @Override
    public ServiceResult editPassword(int id, String oldPass, String newPass) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(id, false, StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);
        if (encoder.matches(oldPass, employeeEntity.getPassword())){
            employeeEntity.setPassword(encoder.encode(newPass));
        }
        employeeRepository.save(employeeEntity);

        result.setMessage("Edit password successfully");
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

            if (campaignEntity.getBudget() > campaignEntity.getSpend())
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

    @Override
    public ServiceResult getMostViewedProduct() {
        ServiceResult result = new ServiceResult();
        List<ProductEntity> productEntityList = productRepository.findTop8ByDeletedAndStatusNameOrderByViewDesc(false, StatusName.ACTIVE);

        List<CustomerGetMostViewedProductResponse> responses = new ArrayList<>();
        for (ProductEntity entity : productEntityList){
            CustomerGetMostViewedProductResponse response = new CustomerGetMostViewedProductResponse(entity.getId(),
                    entity.getName(),
                    entity.getCategory().getName(),
                    entity.getCurrentPrice(),
                    entity.getProductImageURL());
            responses.add(response);
        }

        result.setMessage("Get most viewed products successfully");
        result.setData(responses);
        return result;
    }

    @Override
    public ServiceResult getMostOrderedProduct() {
        ServiceResult result = new ServiceResult();
        List<ProductEntity> productEntityList = productRepository.findTop4ByDeletedAndStatusNameOrderByOrderedDesc(false, StatusName.ACTIVE);

        List<CustomerGetMostViewedProductResponse> responses = new ArrayList<>();
        for (ProductEntity entity : productEntityList){
            CustomerGetMostViewedProductResponse response = new CustomerGetMostViewedProductResponse(entity.getId(),
                    entity.getName(),
                    entity.getCategory().getName(),
                    entity.getCurrentPrice(),
                    entity.getProductImageURL());
            responses.add(response);
        }

        result.setMessage("Get most ordered products successfully");
        result.setData(responses);
        return result;
    }

    @Override
    public ServiceResult getMostViewedProductAll() {
        ServiceResult result = new ServiceResult();
        List<ProductEntity> productEntityList = productRepository.findAllByDeletedAndStatusNameOrderByViewDesc(false, StatusName.ACTIVE);

        List<CustomerGetMOProductAllResponse> responses = new ArrayList<>();
        for (ProductEntity entity : productEntityList){
            CustomerGetMOProductAllResponse response = new CustomerGetMOProductAllResponse(entity.getId(),
                    entity.getProductImageURL(),
                    entity.getName(),
                    4.5,
                    20,
                    entity.getCurrentPrice(),
                    entity.getOriginalPrice());
            responses.add(response);
        }

        result.setMessage("Get most viewed products successfully");
        result.setData(responses);
        return result;
    }

    @Override
    public ServiceResult getMostOrderedProductAll() {
        ServiceResult result = new ServiceResult();
        List<ProductEntity> productEntityList = productRepository.findAllByDeletedAndStatusNameOrderByOrderedDesc(false, StatusName.ACTIVE);

        List<CustomerGetMostViewedProductResponse> responses = new ArrayList<>();
        for (ProductEntity entity : productEntityList){
            CustomerGetMostViewedProductResponse response = new CustomerGetMostViewedProductResponse(entity.getId(),
                    entity.getName(),
                    entity.getCategory().getName(),
                    entity.getCurrentPrice(),
                    entity.getProductImageURL());
            responses.add(response);
        }

        result.setMessage("Get most ordered products successfully");
        result.setData(responses);
        return result;
    }

    @Override
    public ServiceResult getProductDetail(int pid, int uid) {
        ServiceResult result = new ServiceResult();
        ProductEntity productEntity = productRepository.findByIdAndDeletedAndStatusName(pid, false, StatusName.ACTIVE);
        boolean isInCart = cartRepository.existsByEmployeeIdAndProductId(uid, pid);
        boolean isInWishlist = wishlistRepository.existsByProductIdAndEmployeeId(pid, uid);

        productEntity.setView(productEntity.getView() + 1);
        productRepository.save(productEntity);

        List<ImageResponse> responseList = new ArrayList<>();
        for (ImageEntity imageEntity : productEntity.getImageEntityList()){
            responseList.add(new ImageResponse(imageEntity.getImageURL()));
        }

        ProductDetailResponse response = new ProductDetailResponse(productEntity.getId(),
                productEntity.getName(),
                productEntity.getOriginalPrice(),
                productEntity.getCurrentPrice(),
                productEntity.getDescription(),
                productEntity.getCategory().getLimited(),
                isInCart,
                isInWishlist,
                productEntity.getQuantity(),
                responseList);

        result.setMessage("Get product detail successfully");
        result.setData(response);
        return result;
    }

    @Override
    public ServiceResult addToCart(int pid, int uid) {
        ServiceResult result = new ServiceResult();
        ProductEntity productEntity = productRepository.findByIdAndDeletedAndStatusName(pid, false, StatusName.ACTIVE);
        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(uid, false, StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);
        CartEntity cartEntity = new CartEntity();
        cartEntity.setEmployee(employeeEntity);
        cartEntity.setProduct(productEntity);
        cartEntity.setQuantity(1);
        cartEntity.setPrice(productEntity.getOriginalPrice());
        cartEntity.setCurrentPrice(productEntity.getCurrentPrice());

        productEntity.setQuantity(productEntity.getQuantity() - cartEntity.getQuantity());
        productRepository.save(productEntity);
        cartRepository.save(cartEntity);

        AddToCartResponse response = new AddToCartResponse(cartEntity.getId(),
                employeeEntity.getId(),
                productEntity.getId(),
                cartEntity.getQuantity(),
                cartEntity.getPrice(),
                cartEntity.getCurrentPrice());

        result.setMessage("Add to cart successfully");
        result.setData(response);
        return result;
    }

    @Override
    public ServiceResult getCart(int id) {
        ServiceResult result = new ServiceResult();
        double totalPrice = 0;
        int totalItem = 0;
        int itemAmount;

        List<CartEntity> cartEntityList = cartRepository.findAllByEmployeeId(id);
        itemAmount = cartEntityList.size();

        List<GetCartData> cartData = new ArrayList<>();

        for (CartEntity entity : cartEntityList){
            GetCartData response = new GetCartData(entity.getId(),
                    entity.getProduct().getName(),
                    entity.getCurrentPrice(),
                    entity.getPrice(),
                    entity.getQuantity(),
                    entity.getProduct().getProductImageURL());
            if (entity.getQuantity() + entity.getProduct().getQuantity() < entity.getProduct().getCategory().getLimited()){
                response.setLimit(entity.getQuantity() + entity.getProduct().getQuantity());
            }else {
                response.setLimit(entity.getProduct().getCategory().getLimited());
            }

            cartData.add(response);
            totalPrice +=  entity.getQuantity()*entity.getCurrentPrice();
            totalItem += entity.getQuantity();
        }

        GetCartResponse response = new GetCartResponse(totalPrice, totalItem, itemAmount, cartData);
        result.setMessage("Get cart successfully");
        result.setData(response);
        return result;
    }

    @Override
    public ServiceResult deleteCart(int id) {
        ServiceResult result = new ServiceResult();

        CartEntity entity = cartRepository.findById(id).orElse(null);
        ProductEntity productEntity = entity.getProduct();
        productEntity.setQuantity(productEntity.getQuantity() + entity.getQuantity());
        productRepository.save(productEntity);

        cartRepository.delete(entity);
        result.setMessage("Delete item from cart successfully");
        return result;
    }

    @Override
    public ServiceResult editCart(int id, int qty) {
        ServiceResult result = new ServiceResult();
        CartEntity entity = cartRepository.findById(id).orElse(null);
        ProductEntity productEntity = entity.getProduct();
        productEntity.setQuantity(productEntity.getQuantity() + entity.getQuantity() - qty);
        productRepository.save(productEntity);
        entity.setQuantity(qty);

        cartRepository.save(entity);

        EditCartResponse response = new EditCartResponse(id,
                entity.getCurrentPrice(),
                entity.getPrice(),
                entity.getQuantity());

        result.setData(response);
        result.setMessage("Edit cart successfully");
        return result;
    }

    @Override
    public ServiceResult getWishlist(int id) {
        ServiceResult result = new ServiceResult();
        List<WishlistEntity> wishlistEntityList = wishlistRepository.findAllByEmployeeId(id);

        List<WishlistData> response = new ArrayList<>();

        for (WishlistEntity wishlistEntity : wishlistEntityList){
            WishlistData data = new WishlistData(wishlistEntity.getProduct().getId(),
                    wishlistEntity.getProduct().getProductImageURL(),
                    wishlistEntity.getProduct().getName(),
                    4.5,
                    20,
                    wishlistEntity.getProduct().getCurrentPrice(),
                    wishlistEntity.getProduct().getOriginalPrice());
            response.add(data);
        }

        result.setData(response);
        result.setMessage("Get wishlist successfully");
        return result;
    }

    @Override
    public ServiceResult addToWishlist(int pid, int uid) {
        ServiceResult result = new ServiceResult();
        ProductEntity productEntity = productRepository.findByIdAndDeletedAndStatusName(pid, false, StatusName.ACTIVE);
        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(uid, false, StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);

        WishlistEntity entity = new WishlistEntity();
        entity.setEmployee(employeeEntity);
        entity.setProduct(productEntity);

        wishlistRepository.save(entity);

        AddToWishlistResponse response = new AddToWishlistResponse(entity.getId(),
                entity.getProduct().getId(),
                entity.getEmployee().getId());

        result.setData(response);
        result.setMessage("Add to cart successfully");
        return result;
    }

    @Override
    public ServiceResult deleteWishlist(int pid, int uid) {
        ServiceResult result = new ServiceResult();

        WishlistEntity entity = wishlistRepository.findByProductIdAndEmployeeId(pid, uid);
        wishlistRepository.delete(entity);
        
        result.setMessage("Delete item from wishlist successfully");
        return result;
    }

    @Override
    public ServiceResult getOrder(int id) {
        ServiceResult result = new ServiceResult();
        List<OrderEntity> orderEntityList = orderRepository.findAllByEmployeeIdAndDeleted(id, false);

        List<GetOrderResponse> responseList = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntityList){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            GetOrderResponse response = new GetOrderResponse(orderEntity.getId(),
                    orderEntity.getEmployee().getId(),
                    formatter.format(orderEntity.getCreateAt()));
            responseList.add(response);
        }

        result.setData(responseList);
        result.setMessage("Get order successfully");
        return result;
    }

    @Override
    public ServiceResult addOrder(int id, List<AddOrderRequest> request) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(id, false, StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(statusRepository.findByName(StatusName.ACTIVE));
        orderEntity.setTotalPrice(0);
        orderEntity.setDeleted(false);
        orderEntity.setEmployee(employeeEntity);
        orderEntity.setCreateAt(new Date());
        orderRepository.save(orderEntity);
        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        for (AddOrderRequest orderRequest : request){
            ProductEntity productEntity = cartRepository.findById(orderRequest.getId()).orElse(null).getProduct();
            productEntity.setOrdered(productEntity.getOrdered() + orderRequest.getQuantity());
            productRepository.save(productEntity);

            OrderDetailEntity orderDetailEntity = new OrderDetailEntity(orderRequest.getPrice(),
                    orderRequest.getQuantity(),
                    productEntity,
                    orderRepository.findById(orderEntity.getId()).orElse(null));
            orderDetailEntityList.add(orderDetailEntity);
        }
        orderDetailRepository.saveAll(orderDetailEntityList);

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "utf-8");
            helper.setTo(employeeEntity.getEmail());
            helper.setSubject("Order receipt");
            String orderInfo = "";
            int totalItems = 0;
            double totalPrice = 0;
            for (OrderDetailEntity entity : orderDetailEntityList) {
//                orderInfo += entity.getProduct().getName() + ": \n" + "     Quantity:" + entity.getQuantity() + "\n" + "     Price:" + entity.getPrice() + "\n";
                orderInfo += "<h3>" + entity.getProduct().getName() + ":</h3> <br>" +
                        "<h4>Quantity:" + entity.getQuantity() + "</h4> <br>" + "<h4>Price:$" + entity.getPrice() + "</h4> <br>";

                totalItems += entity.getQuantity();
                totalPrice += entity.getPrice();
            }
            orderInfo += "<h2>Total items:" + totalItems + "</h2><br>" + "<h2>Total price:" + totalPrice + "</h2>";

            mailMessage.setContent("<h2>Here is your order:</h2><br>" + orderInfo, "text/html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mailMessage);

        cartRepository.deleteAll(cartRepository.findAllByEmployeeId(id));

        AddOrderResponse response = new AddOrderResponse(orderEntity.getId());
        result.setMessage("Check out successfully");
        result.setData(response);
        return result;
    }

    @Override
    public ServiceResult buyNow(int id, List<AddOrderRequest> request) {
        ServiceResult result = new ServiceResult();
        EmployeeEntity employeeEntity = employeeRepository.findByIdAndDeletedAndStatusNameAndRoleName(id, false, StatusName.ACTIVE, RoleName.ROLE_CUSTOMER);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(statusRepository.findByName(StatusName.ACTIVE));
        orderEntity.setTotalPrice(0);
        orderEntity.setDeleted(false);
        orderEntity.setEmployee(employeeEntity);
        orderEntity.setCreateAt(new Date());
        orderRepository.save(orderEntity);
        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        for (AddOrderRequest orderRequest : request){
            ProductEntity productEntity = productRepository.findByIdAndDeletedAndStatusName(orderRequest.getId(), false, StatusName.ACTIVE);
            productEntity.setQuantity(productEntity.getQuantity() - orderRequest.getQuantity());
            productEntity.setOrdered(productEntity.getOrdered() + orderRequest.getQuantity());
            productRepository.save(productEntity);

            OrderDetailEntity orderDetailEntity = new OrderDetailEntity(orderRequest.getPrice(),
                    orderRequest.getQuantity(),
                    productEntity,
                    orderRepository.findById(orderEntity.getId()).orElse(null));
            orderDetailEntityList.add(orderDetailEntity);
        }
        orderDetailRepository.saveAll(orderDetailEntityList);

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, false, "utf-8");
            helper.setTo(employeeEntity.getEmail());
            helper.setSubject("Order receipt");
            String orderInfo = "";
            int totalItems = 0;
            double totalPrice = 0;
            for (OrderDetailEntity entity : orderDetailEntityList) {
//                orderInfo += entity.getProduct().getName() + ": \n" + "     Quantity:" + entity.getQuantity() + "\n" + "     Price:" + entity.getPrice() + "\n";
                orderInfo += "<h3>" + entity.getProduct().getName() + ":</h3> <br>" +
                        "<h4>Quantity:" + entity.getQuantity() + "</h4> <br>" + "<h4>Price:$" + entity.getPrice() + "</h4> <br>";

                totalItems += entity.getQuantity();
                totalPrice += entity.getPrice();
            }
            orderInfo += "<h2>Total items:" + totalItems + "</h2><br>" + "<h2>Total price:" + totalPrice + "</h2>";

            mailMessage.setContent("<h2>Here is your order:</h2><br>" + orderInfo, "text/html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mailMessage);

        AddOrderResponse response = new AddOrderResponse(orderEntity.getId());
        result.setMessage("Check out successfully");
        result.setData(response);
        return result;
    }

    @Override
    public ServiceResult search(String query) {
        ServiceResult result = new ServiceResult();
        List<ProductEntity> productEntityList = productRepository.findAllByDeletedAndStatusNameAndNameContaining(false, StatusName.ACTIVE, query);

        List<CustomerGetMOProductAllResponse> responses = new ArrayList<>();
        for (ProductEntity entity : productEntityList){
            CustomerGetMOProductAllResponse response = new CustomerGetMOProductAllResponse(entity.getId(),
                    entity.getProductImageURL(),
                    entity.getName(),
                    4.5,
                    20,
                    entity.getCurrentPrice(),
                    entity.getOriginalPrice());
            responses.add(response);
        }

        result.setMessage("Get most viewed products successfully");
        result.setData(responses);
        return result;
    }

    @Override
    public ServiceResult getCat() {
        ServiceResult result = new ServiceResult();
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<GetCatResponse> responses = new ArrayList<>();
        for (CategoryEntity entity : categoryEntityList){
            GetCatResponse response = new GetCatResponse(entity.getId(),
                    entity.getImageURL(),
                    entity.getName());
            responses.add(response);
        }

        result.setMessage("Get category successfully");
        result.setData(responses);
        return result;
    }

    @Override
    public ServiceResult showCat(int id) {
        ServiceResult result = new ServiceResult();
        List<ProductEntity> productEntityList = productRepository.findByCategoryIdAndDeletedAndStatusName(id, false, StatusName.ACTIVE);

        List<CustomerGetMOProductAllResponse> responses = new ArrayList<>();
        for (ProductEntity entity : productEntityList){
            CustomerGetMOProductAllResponse response = new CustomerGetMOProductAllResponse(entity.getId(),
                    entity.getProductImageURL(),
                    entity.getName(),
                    4.5,
                    20,
                    entity.getCurrentPrice(),
                    entity.getOriginalPrice());
            responses.add(response);
        }

        result.setMessage("Get category products successfully");
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
