<%@ page import="com.hospital.booking.utils.ApplicationCore" %>
<%@ page import="com.hospital.booking.utils.ApplicationSettings" %>
<%@ page import="com.hospital.booking.constants.GoogleConstants" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.hospital.booking.utils.DatetimeUtils" %>
<%@ page import="com.hospital.booking.models.Shift" %>
<%@ page import="com.hospital.booking.models.Slot" %>
<%@ page import="com.hospital.booking.utils.SlotUtils" %>
<%@ page import="com.hospital.booking.constants.DateTimeConstants" %>
<%@ page import="java.util.Calendar" %>
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
    <h2 class="my-5">Tạo lịch hẹn khám bệnh</h2>
    <form method="post" action="${pageContext.request.contextPath}/patient/create-appointment">
        <div class="row my-5 gx-5">
            <div class="col-6">
                <h4 class="mb-3">
                    <span class="d-inline-block me-2"><ion-icon name="person-outline"></ion-icon> </span>Thông tin khám
                    bệnh
                </h4>
                <div class="form-group required mb-3">
                    <label class="form-label" for="name">Full Name</label>
                    <input name="name" type="text" id="name" class="form-control" required
                           value="${name}"/>
                </div>

                <div class="form-group mb-3 required">
                    <label class="form-label" for="phoneNumber">Phone Number</label>
                    <input name="phoneNumber" id="phoneNumber" value="${phoneNumber}" class="form-control" required/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input name="email" type="email" id="email" class="form-control" value="${email}"/>
                </div>

                <%
                    // Get the current date
                    Calendar calendar = Calendar.getInstance();

                    // Add one day to the current date
                    calendar.add(Calendar.DAY_OF_YEAR, 1);

                    // Get the year, month, and day values
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    // Format the date values as a string in the required format (YYYY-MM-DD)
                    String tomorrow = String.format("%04d-%02d-%02d", year, month, day);
                %>

                <div class="form-group mb-3">
                    <label class="form-label" for="date">Date</label>
                    <input name="date" id="date" min="<%= tomorrow %>" value="${date}" type="date" class="form-control"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="note">Note</label>
                    <textarea name="note" id="note" type="text" class="form-control">${note}</textarea>
                </div>

                <div class="text-center text-lg-start mt-4 pt-2">
                    <button id="booking-button" type="submit" class="btn btn-primary" disabled>Đặt lịch</button>
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
                    </tr>
                    </thead>
                    <tbody id="shift-table-body">
                    </tbody>
                </table>
                <span class="text-center d-block my-3" id="warning"></span>
            </div>
        </div>
    </form>
</div>

<%--footer--%>
<jsp:include page="../common/footer.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {
        $("#date").on('change', function updateShift() {
            let contextPath = '<%=request.getContextPath()%>';
            let date = $(this).val();

            $.ajax({
                url: contextPath + '/patient/shifts-by-date',
                type: 'get',
                data: {
                    date: date
                },
                success: function (response) {
                    if (response && response.length > 0) {
                        $("#booking-button").prop("disabled", false)
                    } else {
                        $("#booking-button").prop("disabled", true)
                    }

                    $("#shift-table-body").html("");
                    response.forEach((shift, index) => {
                        $("#shift-table-body").append(
                            '<tr>' +
                            '   <td class="text-center"> ' +
                            '       <input required type="radio" name="shiftId" value="' + shift.id + '"/>' +
                            '   </td>' +
                            '   <td>' + shift.doctor?.lastName + ' ' + shift.doctor?.firstName + '</td>' +
                            '   <td>' + shift.date.day + '/' + shift.date.month + '/' + shift.date.year + '</td>' +
                            '   <td>' + shift.slot + '</td>' +
                            '   <td>' + shift.slotInfo?.startTime + '-' + shift.slotInfo?.endTime + '</td>' +
                            '</tr>')
                    })
                },
                error: function (error) {

                }
            })
        })
    })
</script>
</body>
</html>
