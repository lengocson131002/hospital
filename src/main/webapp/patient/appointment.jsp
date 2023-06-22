<%@ page import="com.hospital.booking.models.Shift" %>
<%@ page import="com.hospital.booking.models.Slot" %>
<%@ page import="com.hospital.booking.utils.SlotUtils" %>
<%@ page import="com.hospital.booking.models.Appointment" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.hospital.booking.models.Account" %>
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
<div class="container pb-5 row">
    <div class="col-6 mx-auto">
        <div class="d-flex justify-content-between align-items-center">
            <h2 class="my-5">Thông tin lịch hẹn khám bệnh</h2>
            <c:if test="${appointmentReview != null}">
                <button id="rating-button" class="btn btn-primary me-2" data-bs-toggle="modal"
                        data-bs-target="#reviewModal">Đã đánh giá
                </button>
                <div class="modal fade" id="reviewModal" data-bs-backdrop="static" data-bs-keyboard="false"
                     tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <form>
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Đánh giá bác sĩ</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="input-group mb-3 d-flex align-items-center">
                                        <label class="d-inline-block me-3 form-label">Điểm: </label>
                                        <select class="form-control d-inline-block" class="form-select" name="score"
                                                disabled>
                                            <option value="1" ${appointmentReview.score == 1 ? 'selected' : 0}>1</option>
                                            <option value="2" ${appointmentReview.score == 2 ? 'selected' : 0}>2</option>
                                            <option value="3" ${appointmentReview.score == 3 ? 'selected' : 0}>3</option>
                                            <option value="4" ${appointmentReview.score == 4 ? 'selected' : 0}>4</option>
                                            <option value="5" ${appointmentReview.score == 5 ? 'selected' : 0}>5</option>
                                        </select>
                                        <span class="input-group-text d-inline-block h-100"
                                              style="color: #ffd43b">
                                                    <ion-icon name="star"></ion-icon>
                                                </span>
                                    </div>
                                    <div class="mb-4 form-group required">
                                        <label class="form-label">Nội dung đánh giá:</label>
                                        <textarea required class="form-control required"
                                                  readonly>${appointmentReview.content}</textarea>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </c:if>
            <c:if test="${appointment.status=='COMPLETED' && appointmentReview == null}">
                <button id="rating-button" class="btn btn-primary me-2" data-bs-toggle="modal"
                        data-bs-target="#rateModal">Đánh giá bác sĩ
                </button>
                <div class="modal fade" id="rateModal" data-bs-backdrop="static" data-bs-keyboard="false"
                     tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <form method="post"
                              action="${pageContext.request.contextPath}/patient/rating-doctor">
                            <input type="hidden" name="appointmentId" value="${appointment.id}"/>
                            <input type="hidden" name="doctorId" value="${appointment.doctor.id}"/>

                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="staticBackdropLabel">Đánh giá bác sĩ</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="input-group mb-3 d-flex align-items-center">
                                        <label class="d-inline-block me-3 form-label"
                                               for="score">Điểm: </label>
                                        <select class="form-control d-inline-block" id="score"
                                                class="form-select"
                                                name="score">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5" selected>5</option>
                                        </select>
                                        <span class="input-group-text d-inline-block h-100"
                                              style="color: #ffd43b">
                                                    <ion-icon name="star"></ion-icon>
                                                </span>
                                    </div>
                                    <div class="mb-4 form-group required">
                                        <label for="content" class="form-label">Nội dung đánh giá:</label>
                                        <textarea required class="form-control required" name="content"
                                                  id="content"></textarea>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary">Đánh giá</button>
                                    <button type="button" class="btn btn-outline-danger"
                                            data-bs-dismiss="modal">Hủy
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </c:if>
        </div>
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

        <div>
            <input type="hidden" name="id" value="${appointment.id}">
            <div class="my-5 gx-5">
                <div class="">
                    <h4 class="mb-3">
                        <span class="d-inline-block me-2"><ion-icon name="person-outline"></ion-icon> </span>Thông tin khám
                        bệnh
                    </h4>
                    <div class="form-group mb-3">
                        <label class="form-label" for="name">Họ và tên</label>
                        <input name="name" type="text" id="name" class="form-control" readonly value="${appointment.patientName}"/>
                    </div>

                    <div class="form-group mb-3">
                        <label class="form-label" for="phoneNumber">Số điện thoại</label>
                        <input name="phoneNumber" id="phoneNumber" value="${appointment.patientPhoneNumber}"  class="form-control" readonly/>
                    </div>

                    <div class="form-group mb-3">
                        <label class="form-label" for="email">Email</label>
                        <input name="email" type="email" id="email" class="form-control" readonly value="${appointment.patientEmail}"/>
                    </div>


                    <%
                        Appointment appointment = (Appointment) request.getAttribute("appointment");
                        Account doctor = appointment.getDoctor();
                        Shift shift = appointment.getShift();
                        Slot slot = SlotUtils.getSlot(shift.getSlot());

                        pageContext.setAttribute("slot", slot);
                        pageContext.setAttribute("doctor", doctor);
                        pageContext.setAttribute("department", doctor.getDepartment());
                    %>

                    <div class="form-group mb-3">
                        <label class="form-label" for="date">Ngày</label>
                        <input name="date" id="date" value="${appointment.shift.date}" readonly  class="form-control"/>
                    </div>

                    <div class="row">
                        <div class="form-group mb-3 col-6">
                            <label class="form-label" for="slot">Slot</label>
                            <input name="date" id="slot" value="${slot.number}" readonly  class="form-control"/>
                        </div>

                        <div class="form-group mb-3 col-6">
                            <label class="form-label" for="time">Thời gian</label>
                            <input name="date" id="time" value="${slot.startTime} - ${slot.endTime}" readonly  class="form-control"/>
                        </div>
                    </div>

                    <div class="form-group mb-3">
                        <label class="form-label">Phòng ban</label>
                        <input name="department" readonly value="${department.name}" class="form-control">
                    </div>

                    <div class="form-group mb-3">
                        <label class="form-label">Bác sĩ</label>
                        <input name="doctor" readonly value="${doctor.lastName} ${doctor.firstName}" class="form-control">
                    </div>



                    <div class="form-group mb-3">
                        <label class="form-label" for="patientNote">Ghi chú</label>
                        <textarea name="note" id="patientNote" type="text" readonly class="form-control">${appointment.patientNote}</textarea>
                    </div>

                    <c:if test="${not empty appointment.doctorNote}">
                        <div class="form-group mb-3">
                            <label class="form-label" for="doctorNote">Ghi chú của bác sĩ</label>
                            <textarea name="doctorNote" id="doctorNote" type="text" readonly class="form-control">${appointment.doctorNote}</textarea>
                        </div>
                    </c:if>

                    <div>
                        <div class="text-center text-lg-start mt-4 pt-2">
                            <c:if test="${appointment.status=='CREATED'}">
                                <a href="${pageContext.request.contextPath}/patient/update-appointment?id=${appointment.id}" id="booking-button" type="submit" class="btn btn-primary me-2">Cập nhật</a>
                                <button id="cancel-button" class="btn btn-outline-danger me-2" data-bs-toggle="modal"
                                        data-bs-target="#cancelModal">Hủy lịch hẹn
                                </button>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/patient/appointments"
                               class="btn-outline-danger btn d-inline-block me-2">Trở lại</a>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <div class="modal fade" id="cancelModal" data-bs-backdrop="static" data-bs-keyboard="false"
             tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form method="post"
                      action="${pageContext.request.contextPath}/patient/cancel-appointment">
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
</div>

<%--footer--%>
<jsp:include page="../common/footer.jsp"/>

</body>
</html>
