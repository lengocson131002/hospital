<%@ page import="com.hospital.booking.utils.ApplicationCore" %>
<%@ page import="com.hospital.booking.utils.ApplicationSettings" %>
<%@ page import="com.hospital.booking.constants.GoogleConstants" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medical Website</title>
    <jsp:include page="common/import.jsp"/>
    <link href="resources/css/common.css" rel="stylesheet">
    <script src="resources/js/common.js"></script>
</head>
<body>
<%--Header--%>
<jsp:include page="./common/header.jsp"/>
<div class="container">
    <!-- Pills navs -->
    <div class="row my-5">
        <section class="">
            <div class="container-fluid h-custom">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-md-9 col-lg-6 col-xl-5">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                             class="img-fluid" alt="Sample image">
                    </div>
                    <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                        <h3 class="mb-4">Đăng nhập</h3>

                        <form method="post" action="login">
                            <!-- Email input -->
                            <div class="form-group required mb-4">
                                <label class="form-label" for="email">Email</label>
                                <div class="input-group has-validation">
                                    <input value="${email}" name="email" type="email" id="email" class="form-control"
                                           required/>
                                    <div class="invalid-feedback">
                                        Email is required.
                                    </div>
                                </div>
                            </div>

                            <!-- Password input -->
                            <div class="form-group required mb-3">
                                <label class="form-label" for="password">Mật khẩu</label>
                                <div class="input-group has-validation">
                                    <input value="${password}" name="password" type="password" id="password"
                                           class="form-control" required/>
                                    <div class="invalid-feedback">
                                        Password is required.
                                    </div>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between align-items-center">
                                <!-- Checkbox -->
                                <div class="form-check mb-0">
                                    <input name="remember" class="form-check-input me-2" type="checkbox" value="y"
                                           id="remember"/>
                                    <label class="form-check-label" for="remember">
                                        Lưu đăng nhập
                                    </label>
                                </div>
                                <a href="forgot-password" class="text-body">Quên mật khẩu?</a>
                            </div>


                            <div class="text-center text-lg-start mt-4 pt-2">
                                <button type="submit" class="btn btn-primary"
                                        style="padding-left: 2.5rem; padding-right: 2.5rem;">Đăng nhập
                                </button>
                                <p class="small fw-bold mt-2 pt-1 mb-0">Bạn chưa có tài khoản? <a href="register"
                                                                                                class="link-danger">Đăng ký</a>
                                </p>
                            </div>

                            <div class="divider d-flex align-items-center justify-content-center my-4">
                                <p class="text-center fw-bold mx-3 mb-0">hoặc</p>
                            </div>

                            <div class="d-flex flex-row align-items-center justify-content-center">
                                <p class="lead fw-normal mb-0 me-3">Đăng nhập với google</p>
                                <a class="text-light" href="${loginGgUrl}">
                                    <button type="button" class="btn btn-primary btn-floating mx-1">
                                        <i class="fa-brands fa-google"></i>
                                    </button>
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>

<%--footer--%>
<jsp:include page="./common/footer.jsp"/>
</body>
</html>