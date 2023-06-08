<%@ page import="com.hospital.booking.utils.DatetimeUtils" %>
<%@ page import="com.hospital.booking.utils.SlotUtils" %>
<%@ page import="com.hospital.booking.constants.DateTimeConstants" %>
<%@ page import="com.hospital.booking.models.*" %>
<%@ page import="com.hospital.booking.utils.NumberFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý hóa đơn</title>
    <jsp:include page="../common/import.jsp"/>
    <link href="../resources/css/common.css" rel="stylesheet">
    <script src="../resources/js/common.js"></script>
</head>
<body>
<%--Header--%>
<jsp:include page="../common/header.jsp"/>
<div class="container pb-5">
    <div class="my-5 text-center">
        <h2>Thông tin hóa đơn</h2>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/staff/complete-bill">
        <input type="hidden" name="id" value="${bill.id}">
        <div class="row my-5 gx-5">
            <div class="col-lg-6 col-md-8 col-10 mx-auto">

                <div>
                    <p>Trạng thái:
                        <c:if test="${bill.status=='CREATED'}">
                            <span class="badge bg-primary">Mới khởi tạo</span>
                        </c:if>
                        <c:if test="${bill.status=='COMPLETED'}">
                            <span class="badge bg-success">Đã hoàn thành</span>
                        </c:if></p>
                </div>

                <div class="form-group required mb-3">
                    <label class="form-label" for="id">Mã hóa đơn</label>
                    <input disabled name="id" type="text" id="id" class="form-control"
                           value="${bill.id}"/>
                </div>

                <div class="form-group mb-3 required">
                    <label class="form-label" for="name">Tên bệnh nhân</label>
                    <input disabled name="name" id="name" value="${bill.patientName}"
                           class="form-control"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="phoneNumber">Số điệnt thoại</label>
                    <input disabled name="phoneNumber" id="phoneNumber" class="form-control"
                           value="${bill.patientPhoneNumber}"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input disabled name="email" type="email" id="email" class="form-control"
                           value="${bill.patientEmail}"/>
                </div>
                <%
                    Bill bill = (Bill) request.getAttribute("bill");
                %>

                <div class="form-group mb-3">
                    <label class="form-label" for="price">Giá</label>
                    <input name="price" id="price" value="<%= NumberFormat.formatNumber(bill.getPrice())%>" disabled
                           class="form-control"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="createdAt">Thời gian tạo</label>
                    <input name="createdAt" id="createdAt"
                           value="<%= bill.getCreatedAt() != null
                            ? DatetimeUtils.toString(bill.getCreatedAt(), DateTimeConstants.DATE_TIME_FORMAT)
                            : "" %>"
                           disabled class="form-control"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="checkoutAt">Thời gian thanh thanh toán</label>
                    <input name="createdAt" id="checkoutAt"
                           value="<%= bill.getCheckoutAt() != null
                            ? DatetimeUtils.toString(bill.getCheckoutAt(), DateTimeConstants.DATE_TIME_FORMAT)
                            : "" %>"
                           disabled class="form-control"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="patientNote">Ghi chú</label>
                    <textarea name="note" id="patientNote" type="text" class="form-control">${bill.note}</textarea>
                </div>

                <div class="text-center text-lg-start mt-4 pt-2">
                    <c:if test="${bill.status=='CREATED'}">
                        <button type="submit" class="btn btn-primary me-2">Xác nhận đã thanh toán</button>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/staff/bills" class="btn btn-outline-danger">Trở
                        lại danh sách</a>
                </div>
            </div>
        </div>
    </form>
</div>

<%--footer--%>
<jsp:include page="../common/footer.jsp"/>

</body>
</html>
