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
    <div class="my-5 d-flex justify-content-between align-content-center">
        <div>
            <h2>Quản lý hóa đơn</h2>
        </div>
    </div>

    <div class="filter row my-5">
        <form class="d-flex col-md-4 align-items-end justify-content-between" method="get"
              action="${pageContext.request.contextPath}/staff/bills">
            <div class="form-group me-2" style="min-width: 150px">
                <label for="status" class="mb-2">Trạng thái:</label>
                <select class="form-select" id="status" name="status">
                    <option value="" selected>Chọn trạng thái</option>
                    <option value="CREATED" ${status=='CREATED' ? 'selected' : ''}>Mới khởi tạo</option>
                    <option value="COMPLETED" ${status=='COMPLETED' ? 'selected' : ''}>Đã hoàn thành</option>
                </select>
            </div>

            <div class="form-group me-2">
                <label for="from" class="mb-2">Từ ngày:</label>
                <input name="from" id="from" value="${from}" type="date" class="form-control" placeholder="Từ ngày"/>
            </div>

            <div class="form-group me-2">
                <label for="to" class="mb-2">Đến ngày:</label>
                <input name="to" id="to" value="${to}" type="date" class="form-control" placeholder="Đến ngày"/>
            </div>

            <button type="submit" class="btn btn-outline-primary">Lọc</button>
        </form>
    </div>

    <div>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th class="text-center" scope="col">STT</th>
                <th scope="col">Mã hóa đơn</th>
                <th scope="col">Tên bệnh nhân</th>
                <th scope="col">Số điện thoại</th>
                <th scope="col">Email</th>
                <th scope="col">Giá</th>
                <th scope="col">Thời gian tạo</th>
                <th scope="col">Thời gian thanh toán</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Chi tiết</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${empty bills}">
                <tr>
                    <p class="text-center my-5">Không có hóa đơn nào được tạo</p>
                </tr>
            </c:if>
            <c:forEach items="${bills}" var="bill" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${bill.id}</td>
                    <td>${bill.patientName}</td>
                    <td>${bill.patientPhoneNumber}</td>
                    <td>${bill.patientEmail}</td>
                    <%
                        Bill bill = (Bill) pageContext.getAttribute("bill");
                    %>
                    <td><%= NumberFormat.formatNumber(bill.getPrice())%> VND</td>
                    <td><%= bill.getCreatedAt() != null
                            ? DatetimeUtils.toString(bill.getCreatedAt(), DateTimeConstants.DATE_TIME_FORMAT)
                            : "" %>
                    </td>
                    <td><%= bill.getCheckoutAt() != null
                            ? DatetimeUtils.toString(bill.getCheckoutAt(), DateTimeConstants.DATE_TIME_FORMAT)
                            : "" %>
                    </td>

                    <td class="text-center">
                        <c:if test="${bill.status=='CREATED'}">
                            <span class="badge bg-primary">Mới khởi tạo</span>
                        </c:if>
                        <c:if test="${bill.status=='COMPLETED'}">
                            <span class="badge bg-success">Đã hoàn thành</span>
                        </c:if>
                    </td>
                    <td class="text-center">
                        <a href="${pageContext.request.contextPath}/staff/bill?id=${bill.id}">
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
