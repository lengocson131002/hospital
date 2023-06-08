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
    <title>Medical Website</title>
    <jsp:include page="../common/import.jsp"/>
    <link href="../resources/css/common.css" rel="stylesheet">
    <script src="../resources/js/common.js"></script>
    <style>
        .form-check-input:checked[type=radio] + label {
            background-color: #0d6efd;
            color: white;
        }

        .form-check-input[type=radio] {
            display: none;
        }
    </style>
</head>
<body>
<%--Header--%>
<jsp:include page="../common/header.jsp"/>
<div class="container pb-5">
    <div class="my-5 d-flex justify-content-between align-content-center">
        <div>
            <h2>Lịch hẹn của tôi</h2>
        </div>
    </div>

    <div>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th class="text-center" scope="col">Ngày khám</th>
                <th scope="col">Slot</th>
                <th scope="col">Giờ khám</th>
                <th scope="col">Tên bệnh nhân</th>
                <th scope="col">Số điện thoại</th>
                <th scope="col">Email</th>
                <th scope="col">Ngày tạo</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Chi tiết</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${empty appointments}">
                <tr>
                    <p class="text-center my-5">Bạn chưa có lịch hẹn nào</p>
                </tr>
            </c:if>
                <c:forEach items="${appointments}" var="appointment" varStatus="loop">
                    <%
                        Appointment appointment = (Appointment) pageContext.getAttribute("appointment");
                        Shift shift = appointment.getShift();
                        Slot slot = SlotUtils.getSlot(shift.getSlot());
                        Account doctor = appointment.getDoctor();

                        pageContext.setAttribute("shift", shift);
                        pageContext.setAttribute("slot", slot);
                        pageContext.setAttribute("doctor", doctor);
                    %>
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td><%= DatetimeUtils.toString(shift.getDate(), DateTimeConstants.DATE_FORMAT) %></td>
                        <td>${shift.slot}</td>
                        <td><%= slot != null ? String.format("%s-%s", slot.getStartTime(), slot.getEndTime()) : "" %></td>
                        <td>${appointment.patientName}</td>
                        <td>${appointment.patientPhoneNumber}</td>
                        <td>${appointment.patientEmail}</td>
                        <td><%=  DatetimeUtils.toString(appointment.getCreatedAt(), DateTimeConstants.DATE_TIME_FORMAT) %></td>
                        <td class="text-center">
                            <c:if test="${appointment.status=='CREATED'}">
                                <span class="badge bg-primary">Mới khởi tạo</span>
                            </c:if>
                            <c:if test="${appointment.status=='FINISHED'}">
                                <span class="badge bg-warning">Chờ thanh toán</span>
                            </c:if>
                            <c:if test="${appointment.status=='COMPLETED'}">
                                <span class="badge bg-success">Đã hoàn thành</span>
                            </c:if>
                            <c:if test="${appointment.status=='CANCELED'}">
                                <span class="badge bg-danger">Đã hủy</span>
                            </c:if>
                        </td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/doctor/appointment?id=${appointment.id}">
                                <ion-icon name="eye-outline"></ion-icon>
                            </a>
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
