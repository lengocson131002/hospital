<%@ page import="com.hospital.booking.utils.DatetimeUtils" %>
<%@ page import="com.hospital.booking.models.Review" %>
<%@ page import="com.hospital.booking.constants.DateTimeConstants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thông tin bác sĩ</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="../common/import.jsp"/>
    <link href="../resources/css/common.css" rel="stylesheet">
    <script src="../resources/js/common.js"></script>
</head>
<body>
<%--Header--%>
<jsp:include page="../common/header.jsp"/>
<div class="container">
    <div class="row my-5">
        <div class="col-lg-6 col-md-8 col-12 mx-auto">
            <h3 class="mb-5">Thông tin bác sĩ</h3>
            <c:if test="${acc != null}">
                <div>
                    <div class="mb-5">
                        <c:if test="${acc.avatar!=null}">
                            <img id="frame" src="${acc.avatar}" class="img-fluid mb-3 d-block"
                                 style="width: 200px; height: 200px; object-fit: cover; border-radius: 50%"/>
                        </c:if>
                        <c:if test="${acc.avatar==null}">
                            <img id="frame" src="${pageContext.request.contextPath}/resources/images/person.jpg"
                                 class="img-fluid mb-3 d-block"
                                 style="width: 200px; height: 200px; object-fit: cover; border-radius: 50%"/>
                        </c:if>
                    </div>

                    <form method="post" action="${pageContext.request.contextPath}/admin/doctor" class="row">
                        <input type="hidden" name="id" value="${acc.id}">
                        <div class="form-group required mb-3">
                            <label class="form-label">Trạng thái</label>
                            <div class="d-flex">
                                <div class="form-check me-4">
                                    <input class="form-check-input" type="radio" name="active" id="active"
                                           value="true" ${acc.active==true?'checked': ''}>
                                    <label class="form-check-label" for="active">
                                        <span class="badge bg-success">Đang hoạt động</span>
                                    </label>
                                </div>

                                <div class="form-check me-5">
                                    <input class="form-check-input" type="radio" name="active" id="inactive"
                                           value="false" ${acc.active==false?'checked': ''}>
                                    <label class="form-check-label" for="inactive">
                                        <span class="badge bg-danger">Ngừng hoạt động</span>
                                    </label>
                                </div>

                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="form-group required col-6">
                                <label for="lastName" class="form-label">Họ</label>
                                <input name="lastName" class="form-control" id="lastName" required
                                       value="${acc.lastName}">
                            </div>
                            <div class="form-group required col-6">
                                <label for="firstName" class="form-label">Tên</label>
                                <input name="firstName" class="form-control" id="firstName" required
                                       value="${acc.firstName}">
                            </div>
                        </div>

                        <!-- Email input -->
                        <div class="form-group required mb-3">
                            <label class="form-label" for="email">Email</label>
                            <input name="email" type="email" id="email" required class="form-control"
                                   value="${acc.email}"/>
                        </div>

                        <div class="form-group required mb-3">
                            <label class="form-label">Giới tính</label>
                            <div class="d-flex">
                                <div class="form-check me-4">
                                    <input class="form-check-input" type="radio" name="gender" id="male"
                                           value="MALE" ${acc.gender=='MALE'?'checked': ''}>
                                    <label class="form-check-label" for="male">
                                        Nam
                                    </label>
                                </div>

                                <div class="form-check me-5">
                                    <input class="form-check-input" type="radio" name="gender" id="female"
                                           value="FEMALE" ${acc.gender=='FEMALE'?'checked': ''}>
                                    <label class="form-check-label" for="female">
                                        Nữ
                                    </label>
                                </div>

                                <div class="form-check me-5">
                                    <input class="form-check-input" type="radio" name="gender" id="other"
                                           value="OTHER" ${acc.gender=='OTHER'?'checked': ''}>
                                    <label class="form-check-label" for="other">
                                        Khác
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group mb-3">
                            <label class="form-label" for="phoneNumber">Số điện thoại</label>
                            <input name="phoneNumber" id="phoneNumber" value="${acc.phoneNumber}" class="form-control"/>
                        </div>

                        <div class="form-group mb-3">
                            <label class="form-label" for="dob">Ngày sinh</label>
                            <input name="dob" type="date" id="dob" class="form-control" value="${acc.DOB}"/>
                        </div>

                        <div class="form-group mb-3">
                            <label class="form-label" for="address">Địa chỉ</label>
                            <input name="address" id="address" class="form-control" value="${acc.address}"/>
                        </div>

                        <div class="form-group mb-3 required">
                            <label class="form-label" for="department">Phòng ban</label>
                            <select name="departmentId" class="form-select" aria-label="" required id="department">
                                <c:forEach items="${departments}" var="department">
                                    <option value="${department.id}" ${department.id==acc.department.id?'selected': ''}>${department.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group mb-3">
                            <label class="form-label" for="description">Description</label>
                            <textarea name="description" id="description"
                                      class="form-control">${acc.description}</textarea>
                        </div>

                        <div class="text-center text-lg-start mt-4 pt-2">
                            <button type="submit" class="btn btn-primary px-3">Cập nhật</button>
                            <a class="btn btn-outline-danger"
                               href="${pageContext.request.contextPath}/admin/accounts?role=DOCTOR">Trở lại danh
                                sách</a>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
        <div class="col-lg-6 col-md-4 col-12">
            <h3 class="mb-5">Thông tin đánh giá</h3>
            <c:if test="${empty doctorReviews}">
                <div class="text-start">Chưa có đánh giá nào</div>
            </c:if>
            <c:forEach varStatus="loop" items="${doctorReviews}" var="review">
                <div class="card mb-3" style="max-height: 200px; overflow: hidden;">
                    <div class="row g-0">
                        <div class="col-md-3">
                            <c:if test="${review.reviewer.avatar!=null}">
                                <img id="frame" src="${review.reviewer.avatar}" class="img-fluid d-block"
                                     style="width: 100%; height: 100%; object-fit: cover;"/>
                            </c:if>
                            <c:if test="${review.reviewer.avatar==null}">
                                <img id="frame" src="${pageContext.request.contextPath}/resources/images/person.jpg"
                                     class="img-fluid d-block"
                                     style="width:  100%; height: 100%; object-fit: cover;"/>
                            </c:if>
                        </div>
                        <div class="col-md-9">
                            <div class="card-body">
                                <h5 class="card-title">
                                    <a class="text-decoration-none"
                                       href="${pageContext.request.contextPath}/admin/patient?id=${review.reviewer.id}">${review.reviewer.lastName} ${review.reviewer.firstName}</a>
                                </h5>
                                <p class="card-text d-flex align-items-center">
                                    <span class="d-block me-1">${review.score}</span>
                                    <span class="d-block" style="color: #ffd43b"><ion-icon
                                            name="star"></ion-icon></span>
                                </p>
                                <p class="card-text">${review.content}</p>
                                <p class="card-text"><small class="text-muted">
                                    <%= DatetimeUtils.toString(((Review) pageContext.getAttribute("review")).getCreatedAt(), DateTimeConstants.DATE_TIME_FORMAT) %>
                                </small></p>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%--Header--%>
<jsp:include page="../common/footer.jsp"/>
</script>

</body>
</html>
