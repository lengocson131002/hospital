<%@ page import="com.hospital.booking.utils.ApplicationCore" %>
<%@ page import="com.hospital.booking.utils.ApplicationSettings" %>
<%@ page import="com.hospital.booking.constants.GoogleConstants" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.hospital.booking.utils.DatetimeUtils" %>
<%@ page import="com.hospital.booking.models.Shift" %>
<%@ page import="com.hospital.booking.models.Slot" %>
<%@ page import="com.hospital.booking.utils.SlotUtils" %>
<%@ page import="com.hospital.booking.constants.DateTimeConstants" %>
<%@ page import="com.hospital.booking.models.Account" %>
<%@ page import="com.hospital.booking.models.Appointment" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý tài khoản</title>
    <jsp:include page="../common/import.jsp"/>
    <link href="../resources/css/common.css" rel="stylesheet">
    <script src="../resources/js/common.js"></script>
</head>
<body>
<%--Header--%>
<jsp:include page="../common/header.jsp"/>
<div class="container pb-5">
    <div class="my-5 d-flex justify-content-between align-content-center">
        <div>
            <h2>Quản lý tài khoản</h2>
        </div>
    </div>
    <div class="mb-5 d-flex justify-content-between align-items-center">
        <form class="d-flex align-items-center justify-content-between" method="get" action="${pageContext.request.contextPath}/admin/accounts">
            <select class="d-inline-block form-select me-2" name="role" aria-label="Lọc theo role">
                <option value="DOCTOR" ${role=='DOCTOR' ? 'selected' : ''}>Bác sĩ</option>
                <option value="STAFF" ${role=='STAFF' ? 'selected' : ''}>Nhân viên</option>
                <option value="PATIENT" ${role=='PATIENT' ? 'selected' : ''}>Bệnh nhân</option>
            </select>
            <button type="submit" class="btn btn-outline-primary">Lọc</button>
        </form>
        <div class="d-flex align-items-center">
            <a href="${pageContext.request.contextPath}/admin/add-doctor" class="align-middle btn btn-outline-primary d-flex align-items-center me-2">
                <ion-icon class="me-1" name="add-circle-outline"></ion-icon>
                <span class="d-inline-block">Tạo bác sĩ</span>
            </a>
            <a href="${pageContext.request.contextPath}/admin/add-staff" class="align-middle btn btn-outline-primary d-flex align-items-center">
                <ion-icon class="me-1" name="add-circle-outline"></ion-icon>
                <span class="d-inline-block">Tạo nhân viên</span>
            </a>
        </div>
    </div>
    <div class=" table-responsive">
        <table class="table table-bordered table-hover">
            <thead>
            <tr class="align-middle">
                <th scope="col">Avatar</th>
                <th class="text-center" scope="col">ID</th>
                <th scope="col">Họ và Tên</th>
                <c:if test="${role=='DOCTOR'}">
                    <th scope="col">Phòng ban</th>
                </c:if>
                <th scope="col">Giới tính </th>
                <th scope="col">Số điện thoại</th>
                <th scope="col">Email</th>
                <th scope="col">Ngày sinh</th>
                <th scope="col">Ngày tạo</th>
                <th scope="col">Chỉnh sửa lần cuối</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Chi tiết</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${empty accounts}">
                <tr>
                    <p class="text-center my-5">Không có tài khoản nào</p>
                </tr>
            </c:if>
            <c:forEach items="${accounts}" var="acc" varStatus="loop">
                <tr>
                    <td>
                        <c:if test="${acc.avatar!=null}">
                            <img id="frame" src="${acc.avatar}" class="img-fluid mb-3 d-block"
                                 style="width: 50px; height: 50px; object-fit: cover; border-radius: 5px"/>
                        </c:if>
                        <c:if test="${acc.avatar==null}">
                            <img  id="frame" src="${pageContext.request.contextPath}/resources/images/person.jpg"
                                  class="img-fluid mb-3 d-block"
                                  style="width: 50px; height: 50px; object-fit: cover; border-radius: 5px"/>
                        </c:if>
                    </td>

                    <td>${acc.id}</td>
                    <td>${acc.lastName != null ? acc.lastName : ''} ${acc.firstName != null ? acc.firstName : ''}</td>
                    <c:if test="${role=='DOCTOR'}">
                        <td>
                            ${acc.department.name}
                        </td>
                    </c:if>
                    <td>${acc.gender=='MALE' ? 'Nam' : acc.gender=='FEMALE' ? 'Nữ' : 'Khác' }</td>
                    <td>${acc.phoneNumber}</td>
                    <td>${acc.email}</td>
                    <%
                        Account acc = (Account) pageContext.getAttribute("acc");
                    %>
                    <td class="text-center"><%= DatetimeUtils.toString(acc.getDOB(), DateTimeConstants.DATE_FORMAT) %></td>
                    <td class="text-center"><%= DatetimeUtils.toString(acc.getCreatedAt(), DateTimeConstants.DATE_TIME_FORMAT) %></td>
                    <td class="text-center"><%= DatetimeUtils.toString(acc.getUpdatedAt(), DateTimeConstants.DATE_TIME_FORMAT) %></td>
                    <td class="text-center">
                        <c:if test="${acc.active==true}">
                            <span class="badge bg-success">Đang hoạt động</span>
                        </c:if>
                        <c:if test="${acc.active==false}">
                            <span class="badge bg-danger">Ngừng hoạt động</span>
                        </c:if>
                    </td>
                    <td class="text-center">
                        <c:choose>
                            <c:when test="${acc.role == 'DOCTOR'}">
                                <a href="${pageContext.request.contextPath}/admin/doctor?id=${acc.id}">
                                    <ion-icon name="eye-outline"></ion-icon>
                                </a>
                            </c:when>
                            <c:when test="${acc.role == 'STAFF'}">
                                <a href="${pageContext.request.contextPath}/admin/staff?id=${acc.id}">
                                    <ion-icon name="eye-outline"></ion-icon>
                                </a>
                            </c:when>
                            <c:when test="${acc.role == 'PATIENT'}">
                                <a href="${pageContext.request.contextPath}/admin/patient?id=${acc.id}">
                                    <ion-icon name="eye-outline"></ion-icon>
                                </a>
                            </c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%--footer--%>
<jsp:include page="../common/footer.jsp"/>

</body>
</html>
