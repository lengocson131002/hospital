<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div>
    <div id="navbar_top">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/home">Medical</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse ms-auto" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page"
                               href="${pageContext.request.contextPath}/home">Trang chủ</a>
                        </li>

                        <c:choose>
                            <c:when test="${sessionScope.account.role=='ADMIN'}">
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page"
                                       href="${pageContext.request.contextPath}/admin/accounts">Quản lý tài khoản</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page"
                                       href="${pageContext.request.contextPath}/admin/reviews">Quản lý đánh giá</a>
                                </li>
                            </c:when>
                            <c:when test="${sessionScope.account.role=='STAFF'}">
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page"
                                       href="${pageContext.request.contextPath}/staff/bills">Thanh toán</a>
                                </li>
                            </c:when>
                            <c:when test="${sessionScope.account.role=='DOCTOR'}">
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page"
                                       href="${pageContext.request.contextPath}/doctor/appointments">Lịch hẹn</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page"
                                       href="${pageContext.request.contextPath}/doctor/schedule">Lịch làm việc</a>
                                </li>
                            </c:when>
                            <c:when test="${sessionScope.account.role=='PATIENT'}">
                                <li class="nav-item">
                                    <a class="nav-link" aria-current="page"
                                       href="${pageContext.request.contextPath}/patient/appointments">Lịch hẹn</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <div class="ms-5">
                                    <a href="${pageContext.request.contextPath}/login"
                                       class="btn btn-outline-primary mx-2">Đăng nhập</a>
                                    <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Đăng
                                        ký</a>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        </li>
                        <c:if test="${sessionScope.account!=null}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                                   data-bs-toggle="dropdown" aria-expanded="false">
                                        ${sessionScope.account.email}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">Profile</a>
                                    </li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li>
                                        <form method="post" action="${pageContext.request.contextPath}/logout">
                                            <button class="dropdown-item text-danger" href="">Logout</button>
                                        </form>
                                    </li>
                                </ul>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="alerts">
            <c:if test="${message!=null}">
                <div class="">
                    <div class="alert alert-info alert-dismissible fade show" role="alert">
                            ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <c:remove var="message" scope="session" />
                </div>
            </c:if>

            <c:if test="${error!=null}">
                <div class="">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <c:remove var="error" scope="session" />
                </div>
            </c:if>
        </div>
    </div>
</div>