<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tạo nhân viên</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../common/import.jsp"/>
    <link href="../resources/css/common.css" rel="stylesheet">
    <script src="../resources/js/common.js"></script>
</head>
<body>
<%--Header--%>
<jsp:include page="../common/header.jsp"/>
<div class="container">
    <div class="col-lg-6 col-md-8 col-10 mx-auto my-5 row gx-5">
        <h3 class="mb-5">Tạo mới nhân viên</h3>
        <div>
            <form method="post" action="${pageContext.request.contextPath}/admin/add-staff" class="row">
                <div class="row mb-3">
                    <div class="form-group required col-6">
                        <label for="lastName" class="form-label">Last name</label>
                        <input name="lastName" class="form-control" id="lastName" required value="${acc.lastName}">
                    </div>
                    <div class="form-group required col-6">
                        <label for="firstName" class="form-label">First name</label>
                        <input name="firstName" class="form-control" id="firstName" required
                               value="${acc.firstName}">
                    </div>
                </div>

                <!-- Email input -->
                <div class="form-group required mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input name="email" type="email" id="email" required class="form-control" value="${acc.email}"/>
                </div>

                <div class="form-group required mb-3">
                    <label class="form-label">Gender</label>
                    <div class="d-flex">
                        <div class="form-check me-4">
                            <input required class="form-check-input" type="radio" name="gender" id="male"
                                   value="MALE" ${acc.gender=='MALE'?'checked': ''}>
                            <label class="form-check-label" for="male">
                                Male
                            </label>
                        </div>

                        <div class="form-check me-5">
                            <input required class="form-check-input" type="radio" name="gender" id="female"
                                   value="FEMALE" ${acc.gender=='FEMALE'?'checked': ''}>
                            <label class="form-check-label" for="female">
                                Female
                            </label>
                        </div>

                        <div class="form-check me-5">
                            <input required class="form-check-input" type="radio" name="gender" id="other"
                                   value="OTHER" ${acc.gender=='OTHER'?'checked': ''}>
                            <label class="form-check-label" for="other">
                                Other
                            </label>
                        </div>
                    </div>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="phoneNumber">Phone Number</label>
                    <input name="phoneNumber" id="phoneNumber" value="${acc.phoneNumber}" class="form-control"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="dob">Birthday</label>
                    <input name="dob" type="date" id="dob" class="form-control" value="${acc.DOB}"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="address">Address</label>
                    <input name="address" id="address" class="form-control" value="${acc.address}"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="description">Description</label>
                    <textarea name="description" id="description" class="form-control">${acc.description}</textarea>
                </div>

                <div class="form-group required mb-3">
                    <label class="form-label" for="password">Mật khẩu</label>
                    <input value="${password}" name="password" type="password" id="password" class="form-control"
                           required/>
                </div>
                <div class="form-group required mb-3">
                    <label class="form-label" for="confirm-password">Nhập lại mật khẩu</label>
                    <input value="${confirmPassword}" name="confirmPassword" type="password" id="confirm-password"
                           class="form-control"
                           required/>
                </div>

                <div class="text-center text-lg-start mt-4 pt-2">
                    <button type="submit" class="btn btn-primary px-3">Tạo
                    </button>
                    <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/admin/accounts">Trở lại
                        danh sách</a>
                </div>
            </form>
        </div>
    </div>
</div>
<%--Header--%>
<jsp:include page="../common/footer.jsp"/>
</script>

</body>
</html>
