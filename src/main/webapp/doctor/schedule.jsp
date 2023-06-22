<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hospital.booking.utils.ApplicationCore" %>
<%@ page import="com.hospital.booking.utils.ApplicationSettings" %>
<%@ page import="com.hospital.booking.constants.GoogleConstants" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medical Website</title>
    <jsp:include page="../common/import.jsp"/>
    <link href="${pageContext.request.contextPath}/resources/css/common.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/schedule.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
</head>
<body>
<%--Header--%>
<jsp:include page="../common/header.jsp"/>
<div class="px-5">
    <!-- Pills navs -->
    <div class="row">
        <div class="my-5">
            <h2>Cập nhật lịch khám tuần</h2>
            <h4>Từ ${from} đến ${to}</h4>
        </div>
        <div>
            <div class="d-flex">
                <div class="me-3 d-flex justify-content-center align-content-center">
                    <div class="slot-status"></div>
                    <p>Chưa chọn</p>
                </div>
                <div class="me-3 d-flex justify-content-center align-content-center">
                    <div class="slot-status bg-primary"></div>
                    <p>Đã chọn</p>
                </div>
                <div class="me-3 d-flex justify-content-center align-content-center">
                    <div class="slot-status bg-success"></div>
                    <p>Đã tạo lịch hẹn</p>
                </div>
            </div>
        </div>
        <div class="response-alert">
        </div>
        <form method="post" action="schedule">
            <table class="table table-bordered table-inverse">
                <thead>
                <tr class="text-center">
                    <th scope="col">Ngày</th>
                    <th scope="col">Slots</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="today" value="<%= java.time.LocalDate.now()%>"/>
                <c:forEach items="${days}" var="d">
                    <tr>
                        <th class="text-center" style="min-width: 120px;" scope="row">${d.key} ${d.key == today ? '(Hôm nay)' : ''}</th>
                        <td>
                            <c:forEach items="${d.value}" var="slot">
                                <div class="form-check">
                                    <input name="${d.key}" class="form-check-input slot-check" type="checkbox"
                                           value="${slot.number}" id="${d.key}-${slot.number}"
                                        ${slot.status=='BOOKED' || d.key <= today ? 'disabled' : ''}
                                        ${slot.status=='SHIFTED' ? 'checked' : ''}
                                    >
                                    <label class="form-check-label ${slot.status=='BOOKED' ? 'bg-success' : ''}" for="${d.key}-${slot.number}">
                                        Slot ${slot.number} (${slot.startTime} - ${slot.endTime})
                                    </label>
                                </div>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="my-5">
                <div class="d-flex">
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-danger my-auto"
                       type="submit">Trang chủ</a>
                </div>
            </div>
        </form>
    </div>
</div>

<%--footer--%>
<jsp:include page="../common/footer.jsp"/>

<script type="text/javascript">
    $(document).ready(function () {
        $(".slot-check").on('change', function updateShift() {
            let slotCheck = $(this);
            let contextPath = '<%=request.getContextPath()%>';
            let date = $(this).attr('name');
            let selected = $(this).prop('checked')
            let slot = $(this).val();

            $.ajax({
                url: contextPath + '/doctor/schedule',
                type: 'post',
                data: {
                    date: date,
                    slot: slot,
                    selected: selected
                },
                success: function (response) {
                    let alert = $(".response-alert");
                    if (response.success) {
                        alert.html("<div class='alert alert-success alert-dismissible' role='alert' id='successAlert'>" +
                            "<span class='message'>Cập nhật lịch khám thành công</span>" +
                            " <button type='button' class='btn-close' data-bs-dismiss='alert'  aria-label='Close'>" +
                            "  <span aria-hidden='true'>&times;</span>" +
                            " </button>" +
                            "</div>");
                    } else {
                        alert.html("<div class='alert alert-danger alert-dismissible' role='alert' id='successAlert'>" +
                            "<span class='message'>Cập nhật lịch khám thất bại</span>" +
                            " <button type='button' class='btn-close' data-bs-dismiss='alert'  aria-label='Close'>" +
                            "  <span aria-hidden='true'>&times;</span>" +
                            " </button>" +
                            "</div>");
                        slotCheck.prop('checked', !selected);
                    }
                },
                error: function (error) {

                }
            })
        })
    })
</script>
</body>
</html>