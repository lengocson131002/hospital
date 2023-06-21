<%@ page import="com.hospital.booking.utils.DatetimeUtils" %>
<%@ page import="com.hospital.booking.constants.DateTimeConstants" %>
<%@ page import="com.hospital.booking.models.Account" %>
<%@ page import="com.hospital.booking.models.Review" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý đánh giá</title>
    <jsp:include page="../common/import.jsp"/>
    <link href="../resources/css/common.css" rel="stylesheet">
    <script src="../resources/js/common.js"></script>
</head>
<body>
<%--Header--%>
<jsp:include page="../common/header.jsp"/>
<div class="container my-5">
    <div class="row">
        <div class="col-7">
            <div class="my-5">
                <div>
                    <h3>Đánh giá bệnh viện từ bệnh nhân (${reviews.size()})</h3>
                </div>
            </div>
            <div>
                <c:forEach varStatus="loop" items="${reviews}" var="review">
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
        <div class="col-5">
            <div class="px-3">
                <div class="my-5">
                    <h3>Top 5 bác sĩ</h3>
                </div>
                <div>
                    <table class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Ảnh</th>
                            <th scope="col">Tên</th>
                            <th scope="col">Phòng ban</th>
                            <th scope="col">Số lần khám</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${topDoctors}" var="doctor" varStatus="loop">
                            <tr>
                                <th>${loop.index + 1}</th>
                                <td>
                                    <c:if test="${doctor.avatar!=null}">
                                        <img id="frame" src="${doctor.avatar}" class="img-fluid mb-3 d-block"
                                             style="width: 50px; height: 50px; object-fit: cover; border-radius: 50%"/>
                                    </c:if>
                                    <c:if test="${doctor.avatar==null}">
                                        <img id="frame"
                                             src="${pageContext.request.contextPath}/resources/images/person.jpg"
                                             class="img-fluid mb-3 d-block"
                                             style="width: 50px; height: 50px; object-fit: cover; border-radius: 50%"/>
                                    </c:if>
                                </td>
                                <td>
                                    <a class="text-decoration-none" href="${pageContext.request.contextPath}/admin/doctor?id=${doctor.id}">
                                            ${doctor.lastName} ${doctor.firstName}
                                    </a>
                                </td>
                                <td>
                                        ${doctor.department.name}
                                </td>
                                <td>
                                    ${doctor.appointmentCount}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>

<%--footer--%>
<jsp:include page="../common/footer.jsp"/>

</body>
</html>
