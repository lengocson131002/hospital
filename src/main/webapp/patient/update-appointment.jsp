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
<div class="container pb-5">
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
                                    <span class="input-group-text d-inline-block h-100" style="color: #ffd43b">
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

    <form method="post" action="${pageContext.request.contextPath}/patient/update-appointment">
        <input type="hidden" name="id" value="${appointment.id}">
        <div class="row my-5 gx-5">
            <div class="col-6">
                <h4 class="mb-3">
                    <span class="d-inline-block me-2"><ion-icon name="person-outline"></ion-icon> </span>Thông tin khám
                    bệnh
                </h4>
                <div class="form-group required mb-3">
                    <label class="form-label" for="name">Họ và tên</label>
                    <input name="name" type="text" id="name" class="form-control" required
                           value="${appointment.patientName}"/>
                </div>

                <div class="form-group mb-3 required">
                    <label class="form-label" for="phoneNumber">Số điện thoại</label>
                    <input name="phoneNumber" id="phoneNumber" value="${appointment.patientPhoneNumber}"
                           class="form-control" required/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input name="email" type="email" id="email" class="form-control"
                           value="${appointment.patientEmail}"/>
                </div>


                <%
                    Appointment appointment = (Appointment) request.getAttribute("appointment");
                    Account doctor = appointment.getDoctor();
                    Shift shift = appointment.getShift();

                    pageContext.setAttribute("shiftId", shift.getId());
                    pageContext.setAttribute("doctorId", doctor.getId());
                    pageContext.setAttribute("doctor", doctor);
                %>
                <div class="form-group mb-3">
                    <label class="form-label" for="date">Ngày</label>
                    <input name="date" id="date" value="${appointment.shift.date}" type="date"
                           class="form-control"/>
                </div>

                <div class="form-group required mb-3">
                    <label class="form-label" for="department-filter">Phòng ban</label>
                    <select class="d-inline-block form-select me-2" name="departmentId" required id="department-filter">
                        <option value="" selected>Chọn phòng ban</option>
                        <c:forEach var="department" items="${departments}">
                            <option value="${department.id}" ${doctor.department.id == department.id ? 'selected' : ''}>${department.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group required mb-3">
                    <label class="form-label" for="doctor-filter">Bác sĩ</label>
                    <select class="d-inline-block form-select me-2" name="doctorId" id="doctor-filter" required>
                        <option value="" selected>Chọn bác sĩ</option>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="patientNote">Ghi chú</label>
                    <textarea name="note" id="patientNote" type="text"
                              class="form-control">${appointment.patientNote}</textarea>
                </div>

                <div>
                    <div class="text-center text-lg-start mt-4 pt-2">
                        <button id="booking-button" type="submit" class="btn btn-primary me-2">Cập nhật</button>
                        <a href="${pageContext.request.contextPath}/patient/appointment?id=${appointment.id}"
                           class="btn-outline-danger btn d-inline-block me-2">Trở lại</a>
                    </div>
                </div>

            </div>
            <div class="col-6">
                <h4 class="mb-5">
                    <span class="d-inline-block me-2"><ion-icon name="calendar-outline"></ion-icon> </span>Chọn lịch
                    khám
                </h4>
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="text-center" scope="col">Chọn</th>
                        <th scope="col">Bác sĩ</th>
                        <th scope="col">Ngày khám</th>
                        <th scope="col">Slot</th>
                        <th scope="col">Giờ khám</th>
                        <th scope="col">Trạng thái</th>
                    </tr>
                    </thead>
                    <tbody id="shift-table-body">
                    </tbody>
                </table>
                <span class="text-center d-block my-3" id="warning"></span>
            </div>
        </div>
    </form>

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
                            <label for="content" class="form-label">Lý do hủy lịch hẹn:</label>
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

<script type="text/javascript">
    $(document).ready(function () {
        let contextPath = '<%=request.getContextPath()%>';
        let doctorId = '<%= pageContext.getAttribute("doctorId")%>'
        let shiftId = '<%= pageContext.getAttribute("shiftId")%>'

        updateDoctors();
        updateShift();

        $("#department-filter").on('change', updateDoctors)
        $("#doctor-filter").on('change', updateShift)
        $("#date").on('change', updateShift)

        function updateDoctors() {
            let departmentId = $("#department-filter").val();

            $("#shift-table-body").html("");

            $.ajax({
                url: contextPath + '/patient/filter-doctors',
                type: 'get',
                data: {
                    departmentId: departmentId
                },
                success: function (response) {
                    $("#doctor-filter").html("");
                    $("#doctor-filter").append('<option value="">Chọn bác sĩ</option>')
                    response.forEach((doctor, index) => {
                        let selected = doctor.id == doctorId;
                        $("#doctor-filter").append('<option ' + (selected ? 'selected' : '') + ' value="' + doctor.id + '">' + doctor.lastName + ' ' + doctor.firstName + '</option>');
                    })
                    updateShift();
                },
                error: function (error) {

                }
            })
        }

        function updateShift() {
            let doctorId = $("#doctor-filter").val();
            let date = $('#date').val();

            if (!doctorId || !date || date.length === 0) {
                return;
            }
            $.ajax({
                url: contextPath + '/patient/filter-shifts',
                type: 'get',
                data: {
                    date: $("#date").val(),
                    doctorId: doctorId
                },
                success: function (response) {
                    $("#shift-table-body").html("");

                    if (response && response.length > 0) {
                        $("#booking-button").prop("disabled", false);
                    } else {
                        $("#booking-button").prop("disabled", true);
                        $("#shift-table-body").html('<div colspan="5" class="w-100 my-2 text-center">Không có ca làm việc nào!</div>');
                    }

                    response.forEach((shift, index) => {
                        let checked = shift.id == shiftId;
                        let booked = !checked && shift.booked;
                        let status = checked
                            ? '<span class="badge bg-warning">Đang chọn</span>'
                            : shift.booked
                                ? '<span class="badge bg-danger">Đã đặt</span>'
                                : '<span class="badge bg-success">Trống</span>'

                        $("#shift-table-body").append(
                            '<tr>' +
                            '   <td class="text-center"> ' +
                            '       <input ' + (booked ? 'disabled' : '') + ' ' + (checked ? 'checked' : '') + ' required type="radio" name="shiftId" value="' + shift.id + '"/>' +
                            '   </td>' +
                            '   <td>' + shift.doctor?.lastName + ' ' + shift.doctor?.firstName + '</td>' +
                            '   <td>' + shift.date.day + '/' + shift.date.month + '/' + shift.date.year + '</td>' +
                            '   <td>' + shift.slot + '</td>' +
                            '   <td>' + shift.slotInfo?.startTime + '-' + shift.slotInfo?.endTime + '</td>' +
                            '   <td>' + status + '</td>' +
                            '</tr>')
                    })
                },
                error: function (error) {

                }
            })
        }
    })
</script>
</body>
</html>
