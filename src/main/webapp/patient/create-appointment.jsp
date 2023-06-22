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
                    <label class="form-label" for="name">Họ và tên</label>
                    <input name="name" type="text" id="name" class="form-control" required
                           value="${name}"/>
                </div>

                <div class="form-group mb-3 required">
                    <label class="form-label" for="phoneNumber">Số điện thoại</label>
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

                <div class="form-group required mb-3">
                    <label class="form-label" for="date">Ngày khám</label>
                    <input required name="date" id="date" min="<%= tomorrow %>" value="${date}" type="date"
                           class="form-control"/>
                </div>

                <div class="form-group required mb-3">
                    <label class="form-label" for="department-filter">Phòng ban</label>
                    <select class="d-inline-block form-select me-2" name="departmentId" required id="department-filter">
                        <option value="" selected>Chọn phòng ban</option>
                        <c:forEach var="department" items="${departments}">
                            <option value="${department.id}" ${departmentId == department.id ? 'selected' : ''}>${department.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group required mb-3">
                    <label class="form-label" for="doctor-filter">Bác sĩ</label>
                    <select class="d-inline-block form-select me-2" name="doctorId" id="doctor-filter" required>
                        <option value="" selected>Chọn bác sĩ</option>
                        <c:forEach var="doctor" items="${doctors}">
                            <option value="${doctor.id}" ${doctorId == department.id ? 'selected' : ''}>${doctor.firstName} ${doctor.lastName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="note">Note</label>
                    <textarea name="note" id="note" type="text" class="form-control">${note}</textarea>
                </div>

                <div class="text-center text-lg-start mt-4 pt-2">
                    <button id="booking-button" type="submit" class="btn btn-primary" disabled>Đặt lịch</button>
                    <a href="${pageContext.request.contextPath}/patient/appointments" class="btn-outline-danger btn">Trở
                        lại</a>
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
</div>

<%--footer--%>
<jsp:include page="../common/footer.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {
        $("#department-filter").on('change', function updateDoctors() {
            let contextPath = '<%=request.getContextPath()%>';
            let departmentId = $(this).val();

            $("#shift-table-body").html("");

            $.ajax({
                url: contextPath + '/patient/filter-doctors',
                type: 'get',
                data: {
                    departmentId: departmentId
                },
                success: function (response) {
                    console.log(response)
                    $("#doctor-filter").html("");
                    $("#doctor-filter").append('<option value="">Chọn bác sĩ</option>')
                    response.forEach((doctor, index) => {
                        $("#doctor-filter").append('<option value="' + doctor.id + '">' + doctor.lastName + ' ' + doctor.firstName + '</option>');
                    })
                },
                error: function (error) {

                }
            })
        })

        $("#doctor-filter").on('change', updateShift)
        $("#date").on('change', updateShift)

        function updateShift() {
            let contextPath = '<%=request.getContextPath()%>';
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
                        let booked = shift.booked;

                        $("#shift-table-body").append(
                            '<tr>' +
                            '   <td class="text-center"> ' +
                            '       <input' + (booked ? 'disabled' : '') + ' required type="radio" name="shiftId" value="' + shift.id + '"/>' +
                            '   </td>' +
                            '   <td>' + shift.doctor?.lastName + ' ' + shift.doctor?.firstName + '</td>' +
                            '   <td>' + shift.date.day + '/' + shift.date.month + '/' + shift.date.year + '</td>' +
                            '   <td>' + shift.slot + '</td>' +
                            '   <td>' + shift.slotInfo?.startTime + '-' + shift.slotInfo?.endTime + '</td>' +
                            '   <td>' + (booked ? '<span class="badge bg-danger">Đã đặt</span>' : '<span class="badge bg-success">Chưa đặt</span>') + '</td>' +
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
