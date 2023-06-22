<%@ page import="com.hospital.booking.models.Account" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Medical Website</title>
    <jsp:include page="common/import.jsp"/>
    <link rel="stylesheet" href="resources/css/header.css"/>
    <script src="resources/js/common.js"></script>
    <style>
        .form-group.required .form-label:after {
            content: "*";
            color: red;
        }
    </style>
</head>
<body>
<%--Header--%>
<jsp:include page="common/header.jsp"/>

<!-- ======= Hero Section ======= -->
<section id="hero" class="d-flex align-items-center">
    <div class="container" style="min-height: max-content">
        <h1>Welcome to Medilab</h1>
        <h5>Mỗi ngày bận rộn trôi qua, chúng ta lại vô tình lãng quên tài sản quý<br> giá nhất của mình: Sức khỏe. Hãy
            để Medilab trở thành một cánh<br> tay đắc lực chăm sóc và gìn giữ tài sản đó cho bạn và cả gia đình.</h5>
        <a href="#about.html" class="btn-get-started scrollto">Bắt Đầu</a>
    </div>
</section>

<div class="container">

    <main id="main">
        <!-- ======= Why Us Section ======= -->
        <section id="why-us" class="why-us">
            <div class="container">

                <div class="row">
                    <div class="col-lg-4 d-flex align-items-stretch">
                        <div class="content">
                            <h3>Tại sao nên lựa chọn Medilab?</h3>
                            <p>
                                Medilab là một phòng khám hiện đại, thân thiện với đội ngũ chuyên gia được đào tạo tại
                                Mỹ, tận tâm cung cấp cho gia đình bạn mọi dịch vụ khám chữa bệnh cần thiết để chăm sóc
                                toàn diện. Nhân viên Medilab tự tin rằng bạn sẽ thấy phòng khám của chúng tôi
                                luôn thân thiện và chuyên nghiệp mỗi khi bạn ghé thăm. Thành công của chúng tôi được đo
                                bằng từng bệnh nhân và từng nụ cười.

                            </p>
                            <div class="text-center">
                                <a href="#" class="more-btn">Xem thêm <i class="bx bx-chevron-right"></i></a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-8 d-flex align-items-stretch">
                        <div class="icon-boxes d-flex flex-column justify-content-center">
                            <div class="row">
                                <div class="col-xl-4 d-flex align-items-stretch">
                                    <div class="icon-box mt-4 mt-xl-0">
                                        <i class="fa-solid fa-hospital"></i>
                                        <h4>Phòng Khám Chất Lượng</h4>
                                        <p>Bác sĩ học tập và làm việc tại Mỹ nhiều năm kinh nghiệm. Trang thiết bị, máy
                                            chụp x-quang hiện đại chẩn đoán thích hợp</p>
                                    </div>
                                </div>
                                <div class="col-xl-4 d-flex align-items-stretch">
                                    <div class="icon-box mt-4 mt-xl-0">
                                        <i class="fa-solid fa-briefcase-medical"></i>
                                        <h4>Dễ Tìm Hiểu</h4>
                                        <p>Nhận tất cả thông tin về Medilab trên trang web của chúng tôi</p>
                                    </div>
                                </div>
                                <div class="col-xl-4 d-flex align-items-stretch">
                                    <div class="icon-box mt-4 mt-xl-0">
                                        <i class="fa-solid fa-capsules"></i>
                                        <h4>Lịch Trình Tài Chính Thuận Tiện</h4>
                                        <p>
                                            Báo giá nhanh, chính xác qua email hoặc điện thoại</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- End .content-->
                    </div>
                </div>

            </div>
        </section>
        <!-- End Why Us Section -->
        <!-- ======= About Section ======= -->
        <section id="about" class="about">
            <div class="container-fluid">

                <div class="row">
                    <div class="col-xl-5 col-lg-6 video-box d-flex justify-content-center align-items-stretch position-relative">
                        <a href="https://www.youtube.com/watch?v=wQ2TN_gI3sE" class="glightbox play-btn mb-4"></a>
                    </div>

                    <div class="col-xl-7 col-lg-6 icon-boxes d-flex flex-column align-items-stretch justify-content-center py-5 px-lg-5">
                        <h3>Phòng khám chăm sóc sức khỏe tư nhân hàng đầu tại Việt Nam.</h3>
                        <p>Medilab cam kết hướng tới sự xuất sắc trong hoạt động thăm khám lâm sàng, đào tạo và nghiên
                            cứu nhằm cung cấp dịch vụ chăm sóc tốt nhất. Medilab đã tạo dựng được uy tín vững chắc trong
                            việc cung cấp dịch vụ chăm sóc sức khỏe lấy
                            bệnh nhân làm trung tâm, với chi phí hợp lý và chất lượng cao. Chúng tôi tự hào trong việc
                            nỗ lực cải thiện chất lượng dịch vụ chăm sóc sức khỏe ở Việt Nam, góp phần vào sự phồn vinh
                            và an sinh của đất nước. </p>

                        <div class="icon-box">
                            <div class="icon"><i class="fa-solid fa-fingerprint"></i></div>
                            <h4 class="title"><a href="">Mục đích</a></h4>
                            <p class="description">Vì mục tiêu nâng cao sức khỏe và hạnh phúc của mọi người dân Việt
                                Nam</p>
                        </div>

                        <div class="icon-box">
                            <div class="icon"><i class="fa-solid fa-street-view"></i></div>
                            <h4 class="title"><a href="">Tầm nhìn</a></h4>
                            <p class="description">Trở thành đơn vị dẫn đầu toàn quốc về lĩnh vực chăm sóc sức khỏe và
                                là thương hiệu chăm sóc sức khỏe đáng tin cậy nhất ở Việt Nam.</p>
                        </div>

                        <div class="icon-box">
                            <div class="icon"><i class="fa-solid fa-flask"></i></div>
                            <h4 class="title"><a href="">Sứ mệnh</a></h4>
                            <p class="description">Sứ mệnh của chúng tôi là nâng cao sức khỏe và đời sống của người dân
                                Việt Nam, thông qua một hệ thống bao gồm các cơ sở chăm sóc sức khỏe tại các thành phố
                                lớn với phương châm sáng tạo, tích hợp và lấy bệnh nhân làm trung tâm.</p>
                        </div>

                    </div>
                </div>

            </div>
        </section>
        <!-- End About Section -->
        <!-- ======= Services Section ======= -->
        <section id="services" class="services">
            <div class="container">

                <div class="section-title">
                    <h2>Giá trị của chúng tôi</h2>
                    <p>Công việc của chúng tôi được định hình chủ yếu bởi các giá trị cốt lõi của chúng tôi
                    </p>
                </div>

                <div class="row">
                    <div class="col-lg-4 col-md-6 d-flex align-items-stretch">
                        <div class="icon-box">
                            <div class="icon"><i class="fas fa-heartbeat"></i></div>
                            <h4><a href="">Lòng Trắc Ẩn</a></h4>
                            <p>Chúng tôi mong muốn thấu hiểu và quan tâm đến những lo lắng và nhu cầu của bệnh nhân bằng
                                cách lắng nghe chăm chú và đặt mình vào vị trí của họ.
                            </p>
                        </div>
                    </div>

                    <div class="col-lg-4 col-md-6 d-flex align-items-stretch mt-4 mt-md-0">
                        <div class="icon-box">
                            <div class="icon"><i class="fas fa-pills"></i></div>
                            <h4><a href="">Phát Triển</a></h4>
                            <p>
                                Chúng tôi cố gắng thích nghi với các tình huống khác nhau, với mong muốn học hỏi và phấn
                                đấu để trở nên tốt hơn. Học hỏi các xu hướng và công nghệ mới trong ngành.</p>
                        </div>
                    </div>

                    <div class="col-lg-4 col-md-6 d-flex align-items-stretch mt-4 mt-lg-0">
                        <div class="icon-box">
                            <div class="icon"><i class="fas fa-hospital-user"></i></div>
                            <h4><a href="">Sự Tôn Trọng</a></h4>
                            <p>Tôn trọng mọi người chúng ta gặp và đối xử với họ như cách chúng ta muốn được đối xử. Cho
                                dù đó là đồng nghiệp hay bệnh nhân, sự hiểu biết và tôn trọng đều quan trọng.</p>
                        </div>
                    </div>
                </div>

            </div>
        </section>
        <!-- End Services Section -->
        <!-- ======= Appointment Section ======= -->

        <!-- End Appointment Section -->

        <!-- ======= Doctors Section ======= -->
        <section id="doctors" class="doctors">
            <div class="container">

                <div class="section-title">
                    <h2>Bác Sĩ</h2>
                    <p>Chúng tôi làm việc không chỉ bằng trí óc mà còn bằng trái tim. </p>
                </div>

                <div class="row">
                    <c:forEach items="${doctors}" var="doctor">
                        <div class="col-lg-6 mb-3">
                            <div class="member d-flex align-items-start">
                                <div class="pic" style="width: 120px; height: 120px;">
                                    <c:if test="${doctor.avatar!=null}">
                                        <img style="object-fit: cover; height: 100%;" id="frame" src="${doctor.avatar}"
                                             class="img-fluid"/>
                                    </c:if>
                                    <c:if test="${doctor.avatar==null}">
                                        <img id="frame"

                                             src="${pageContext.request.contextPath}/resources/images/person.jpg"
                                             class="img-fluid"/>
                                    </c:if>
                                </div>
                                <div class="member-info">
                                    <h4 class="d-inline-block me-2">${doctor.lastName} ${doctor.firstName}</h4>

                                    <span>${doctor.department.name}</span>
                                    <p>${doctor.description}</p>
                                    <div class="social">
                                        <c:if test="${doctor.phoneNumber != null}">
                                            <a href="tel:${doctor.phoneNumber}">
                                                <ion-icon name="call-outline"></ion-icon>
                                            </a>
                                        </c:if>
                                        <c:if test="${doctor.email != null}">
                                            <a href="mailto:${doctor.email}">
                                                <ion-icon name="mail-outline"></ion-icon>
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

            </div>
        </section>
        <!-- End Doctors Section -->

        <!-- ======= Testimonials Section ======= -->
        <section id="testimonials" class="testimonials">
            <div class="section-title">
                <h2>Lời Chứng Thực</h2>
                <p>Khách hàng của chúng tôi nói gì.</p>
            </div>
            <div class="container">

                <div class="testimonials-slider swiper" data-aos="fade-up" data-aos-delay="100">
                    <div class="swiper-wrapper">

                        <c:forEach items="${reviews}" var="review">
                            <div class="swiper-slide">
                                <div class="testimonial-wrap">
                                    <div class="testimonial-item">
                                        <c:if test="${review.reviewer.avatar!=null}">
                                            <img id="frame" src="${review.reviewer.avatar}" class="testimonial-img"/>
                                        </c:if>
                                        <c:if test="${review.reviewer.avatar==null}">
                                            <img id="frame"
                                                 src="${pageContext.request.contextPath}/resources/images/person.jpg"
                                                 class="testimonial-img"/>
                                        </c:if>
                                        <h3>${review.reviewer.lastName} ${review.reviewer.firstName} </h3>
                                        <h4>
                                            <p class="d-flex align-items-center">
                                                <span class="d-block me-1">${review.score}</span>
                                                <span class="d-block" style="color: #ffd43b"><ion-icon
                                                        name="star"></ion-icon></span>
                                            </p>
                                        </h4>
                                        <p>
                                            <i class="bx bxs-quote-alt-left quote-icon-left"></i>
                                                ${review.content}
                                            <i class="bx bxs-quote-alt-right quote-icon-right"></i>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <!-- Button trigger modal -->
                    <!-- Modal -->
                    <c:if test="${account != null && account.role == 'PATIENT' && review == null}">
                        <div class="d-flex justify-content-center my-5">
                            <button type="button" class="btn btn-outline-primary mx-auto" data-bs-toggle="modal"
                                    data-bs-target="#rateModal">
                                Gửi đánh giá
                            </button>
                        </div>

                        <div class="modal fade" id="rateModal" data-bs-backdrop="static" data-bs-keyboard="false"
                             tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <form method="post" action="${pageContext.request.contextPath}/patient/rating-hospital">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="staticBackdropLabel">Đánh giá chúng tôi</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="input-group mb-3 d-flex align-items-center">
                                                <label class="d-inline-block me-3 form-label" for="score">Điểm: </label>
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
                    <div class="swiper-pagination"></div>
                </div>

            </div>
        </section>
        <!-- End Testimonials Section -->
        <!-- ======= Gallery Section ======= -->
        <section id="gallery" class="gallery">
            <div class="container" style="min-height: max-content">

                <div class="section-title">
                    <h2>Hình Ảnh</h2>
                    <p>Thành công không bao giờ là của riêng một ai. Nó là sản phẩm của cả một tập thể làm việc, phối
                        hợp thông qua sự chia sẻ, tin tưởng và tôn trọng lẫn nhau.</p>
                </div>
            </div>

            <div class="container-fluid">
                <div class="row g-0">

                    <div class="col-lg-3 col-md-4">
                        <div class="gallery-item">
                            <a href="${pageContext.request.contextPath}/resources/images/gallery/gallery-1.jpg"
                               class="galelry-lightbox">
                                <img src="${pageContext.request.contextPath}/resources/images/gallery/gallery-1.jpg"
                                     alt="" class="img-fluid">
                            </a>
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-4">
                        <div class="gallery-item">
                            <a href="${pageContext.request.contextPath}/resources/images/gallery/gallery-2.jpg"
                               class="galelry-lightbox">
                                <img src="${pageContext.request.contextPath}/resources/images/gallery/gallery-2.jpg"
                                     alt="" class="img-fluid">
                            </a>
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-4">
                        <div class="gallery-item">
                            <a href="${pageContext.request.contextPath}/resources/images/gallery/gallery-3.jpg"
                               class="galelry-lightbox">
                                <img src="${pageContext.request.contextPath}/resources/images/gallery/gallery-3.jpg"
                                     alt="" class="img-fluid">
                            </a>
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-4">
                        <div class="gallery-item">
                            <a href="${pageContext.request.contextPath}/resources/images/gallery/gallery-4.jpg"
                               class="galelry-lightbox">
                                <img src="${pageContext.request.contextPath}/resources/images/gallery/gallery-4.jpg"
                                     alt="" class="img-fluid">
                            </a>
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-4">
                        <div class="gallery-item">
                            <a href="${pageContext.request.contextPath}/resources/images/gallery/gallery-5.jpg"
                               class="galelry-lightbox">
                                <img src="${pageContext.request.contextPath}/resources/images/gallery/gallery-5.jpg"
                                     alt="" class="img-fluid">
                            </a>
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-4">
                        <div class="gallery-item">
                            <a href="${pageContext.request.contextPath}/resources/images/gallery/gallery-6.jpg"
                               class="galelry-lightbox">
                                <img src="${pageContext.request.contextPath}/resources/images/gallery/gallery-6.jpg"
                                     alt="" class="img-fluid">
                            </a>
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-4">
                        <div class="gallery-item">
                            <a href="${pageContext.request.contextPath}/resources/images/gallery/gallery-7.jpg"
                               class="galelry-lightbox">
                                <img src="${pageContext.request.contextPath}/resources/images/gallery/gallery-7.jpg"
                                     alt="" class="img-fluid">
                            </a>
                        </div>
                    </div>

                    <div class="col-lg-3 col-md-4">
                        <div class="gallery-item">
                            <a href="${pageContext.request.contextPath}/resources/images/gallery/gallery-8.jpg"
                               class="galelry-lightbox">
                                <img src="${pageContext.request.contextPath}/resources/images/gallery/gallery-8.jpg"
                                     alt="" class="img-fluid">
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- End Gallery Section -->
    </main>
</div>

<%--footer--%>
<jsp:include page="./common/footer.jsp"/>

</body>
</html>