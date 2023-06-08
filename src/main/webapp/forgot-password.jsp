<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medical Website</title>
    <jsp:include page="common/import.jsp"/>
</head>
<body>
<%--Header--%>
<jsp:include page="./common/header.jsp"/>

<div class="container">
    <!-- Pills navs -->
    <div class="row mt-5">
        <section class="vh-100">
            <div class="container-fluid h-custom">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-md-9 col-lg-6 col-xl-5">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                             class="img-fluid" alt="Sample image">
                    </div>
                    <div class="col-md-8 col-lg-6 col-xl-4 offset-xl-1">
                        <h3 class="mb-4">Quên mật khẩu</h3>
                        <form method="post" action="forgot-password">
                            <!-- Email input -->
                            <div class="form-group required mb-4">
                                <label class="form-label" for="email">Email</label>
                                <input required value="${email}" name="email" type="email" id="email" class="form-control" />
                            </div>

                            <div class="text-center text-lg-start mt-4 pt-2">
                                <button type="submit" class="btn btn-primary"
                                        style="padding-left: 2.5rem; padding-right: 2.5rem;">Gửi yêu cầu</button>
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