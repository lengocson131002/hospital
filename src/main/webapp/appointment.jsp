<%@ page import="com.hospital.booking.utils.ApplicationCore" %>
<%@ page import="com.hospital.booking.utils.ApplicationSettings" %>
<%@ page import="com.hospital.booking.constants.GoogleConstants" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medical Website</title>
    <jsp:include page="common/import.jsp"/>
    <link href="resources/css/common.css" rel="stylesheet">
    <script src="resources/js/common.js"></script>
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
<jsp:include page="./common/header.jsp"/>
<div class="container">
    <!-- Pills navs -->
    <div class="row my-5">
        <div class="col-6">
            <form method="post" action="appointment">
                <div class="form-group required mb-3">
                    <label class="form-label" for="name">Full Name</label>
                    <input name="name" type="text" id="name" class="form-control" required value="${account.name}"/>
                </div>

                <!-- Email input -->
                <div class="form-group required mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input name="email" type="email" id="email" class="form-control" required value="${account.email}"/>
                </div>
                <div class="form-group mb-3">
                    <label class="form-label" for="phoneNumber">Phone Number</label>
                    <input name="phoneNumber" id="phoneNumber" value="${account.phoneNumber}" class="form-control"/>
                </div>
                <div class="form-group mb-3">
                    <label class="form-label" for="note">Note</label>
                    <input name="note" id="note" type="text" class="form-control"/>
                </div>
                <div class="form-group mb-3">
                    <label class="form-label" for="date">Date</label>
                    <input name="date" id="date" value="${LocalDateTime.now()}" type="date" class="form-control"/>
                </div>

                <div class="text-center text-lg-start mt-4 pt-2">
                    <button type="submit" class="btn btn-primary"
                            style="padding-left: 2.5rem; padding-right: 2.5rem;">Send
                    </button>
                </div>
            </form>
        </div>
        <div class="col-6">
            <div class="row">
                <c:forEach var="i" items="${[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]}">
                    <div class="time-slot col-4">
                        <input class="form-check-input" type="radio" name="slot" id="${i}" value="${i}">
                        <label class="form-check-label btn btn-outline-primary" for="${i}"
                               style="padding-left: 2.5rem; padding-right: 2.5rem;">
                                ${i}:00
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<%--footer--%>
<jsp:include page="./common/footer.jsp"/>
</body>
</html>
