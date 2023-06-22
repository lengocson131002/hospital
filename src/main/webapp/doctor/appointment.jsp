<%@ page import="com.hospital.booking.utils.ApplicationCore" %>
<%@ page import="com.hospital.booking.utils.ApplicationSettings" %>
<%@ page import="com.hospital.booking.constants.GoogleConstants" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.hospital.booking.utils.DatetimeUtils" %>
<%@ page import="com.hospital.booking.models.Shift" %>
<%@ page import="com.hospital.booking.models.Slot" %>
<%@ page import="com.hospital.booking.utils.SlotUtils" %>
<%@ page import="com.hospital.booking.constants.DateTimeConstants" %>
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
</head>
<body>
<%--Header--%>
<jsp:include page="../common/header.jsp"/>
<div class="container pb-5">
    <h2 class="my-5 text-center">Thông tin lịch hẹn khám bệnh</h2>

    <form method="post" action="${pageContext.request.contextPath}/doctor/complete-appointment">
        <input type="hidden" name="id" value="${appointment.id}">
        <div class="row my-5 gx-5">
            <div class="col-lg-6 col-md-8 col-10 mx-auto">
                <h4 class="mb-3">
                    <span class="d-inline-block me-2"><ion-icon name="person-outline"></ion-icon> </span>Thông tin khám
                    bệnh
                </h4>

                <p class="my-3">
                    Trạng thái:
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
                </p>

                <div class="form-group mb-3">
                    <label class="form-label" for="name">Họ và tên</label>
                    <input disabled name="name" type="text" id="name" class="form-control"
                           value="${appointment.patientName}"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="phoneNumber">Số điện thoại</label>
                    <input disabled name="phoneNumber" id="phoneNumber" value="${appointment.patientPhoneNumber}"
                           class="form-control"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input disabled name="email" type="email" id="email" class="form-control"
                           value="${appointment.patientEmail}"/>
                </div>
                <%
                    Appointment appointment = (Appointment) request.getAttribute("appointment");
                    Shift shift = appointment.getShift();
                    Slot slot = SlotUtils.getSlot(shift.getSlot());

                    pageContext.setAttribute("slot", slot);
                %>

                <div class="form-group mb-3">
                    <label class="form-label" for="date">Ngày</label>
                    <input disabled name="date" id="date" value="${appointment.shift.date}" type="date"
                           class="form-control"/>
                </div>

                <div class="row">
                    <div class="form-group mb-3 col-6">
                        <label class="form-label" for="slot">Slot</label>
                        <input name="slot" id="slot" value="${slot.number}" disabled class="form-control"/>
                    </div>

                    <div class="form-group mb-3 col-6">
                        <label class="form-label" for="time">Thời gian</label>
                        <input name="time" id="time" value="${slot.startTime} - ${slot.endTime}" disabled
                               class="form-control"/>
                    </div>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="patientNote">Ghi chú của bệnh nhân</label>
                    <textarea disabled name="note" id="patientNote" type="text"
                              class="form-control">${appointment.patientNote}</textarea>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="doctorNote">Ghi chú của bác sĩ</label>
                    <textarea name="doctorNote" id="doctorNote" type="text"
                              class="form-control">${appointment.doctorNote}</textarea>
                </div>

                <div class="text-center text-lg-start mt-4 pt-2">
                    <c:if test="${appointment.status=='CREATED'}">
                        <button id="booking-button" type="submit" class="btn btn-primary me-2">Hoàn thành</button>
                        <button id="cancel-button" type="button" class="btn btn-outline-danger me-2" data-bs-toggle="modal"
                                data-bs-target="#cancelModal">Hủy lịch hẹn
                        </button>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/doctor/appointments" class="btn btn-outline-danger">Trở
                        lại </a>
                </div>
            </div>
        </div>
    </form>

    <div class="modal fade" id="cancelModal" data-bs-backdrop="static" data-bs-keyboard="false"
         tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog">
            <form method="post"
                  action="${pageContext.request.contextPath}/doctor/cancel-appointment">
                <input type="hidden" name="id" value="${appointment.id}"/>
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="">Hủy lịch hẹn</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-4 form-group required">
                            <label for="reason" class="form-label">Lý do hủy lịch hẹn:</label>
                            <textarea required class="form-control required" name="cancelReason"
                                      id="reason"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Hủy lịch hẹn</button>
                        <button type="button" class="btn btn-outline-danger"
                                data-bs-dismiss="modal">Thoát
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<%--footer--%>
<jsp:include page="../common/footer.jsp"/>

</body>
</html>
