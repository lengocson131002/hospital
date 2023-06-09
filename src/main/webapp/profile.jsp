<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <jsp:include page="common/import.jsp"/>
    <link href="resources/css/common.css" rel="stylesheet">
    <script src="resources/js/common.js"></script>
</head>
<body>
<%--Header--%>
<jsp:include page="./common/header.jsp"/>
<div class="container">
    <div class="col-md-10 col-lg-8 col-xl-8 offset-xl-1 mx-auto my-5 row gx-5">
        <h3 class="mb-4">Profile</h3>
        <div class="col-5">
            <div class="mb-5">
                <c:if test="${account.avatar!=null}">
                    <img id="frame" src="${account.avatar}" class="img-fluid mb-3 d-block"
                         style="width: 200px; height: 200px; object-fit: cover; border-radius: 50%"/>
                </c:if>
                <c:if test="${account.avatar==null}">
                    <img  id="frame" src="${pageContext.request.contextPath}/resources/images/person.jpg"
                         class="img-fluid mb-3 d-block"
                         style="width: 200px; height: 200px; object-fit: cover; border-radius: 50%"/>
                </c:if>

               <form method="post" action="${pageContext.request.contextPath}/upload-avatar" enctype="multipart/form-data">
                   <div class="row d-flex justify-content-between align-items-center">
                       <div class="col-8"><input name="avatar" class=" form-control"
                                                 required
                                                 type="file"
                                                 onchange="preview()"
                                                 placeholder="Choose avatar"></div>
                       <button type="submit" class="col-4 btn btn-primary">Upload</button>
                   </div>
               </form>
            </div>
            <form method="post" action="change-password">
                <div class="form-group required mb-3">
                    <label class="form-label" for="password">New password</label>
                    <input value="${password}" name="password" type="password" id="password" class="form-control"
                           required/>
                </div>
                <div class="form-group required mb-3">
                    <label class="form-label" for="confirm-password">Confirm Password</label>
                    <input value="${confirmPassword}" name="confirmPassword" type="password" id="confirm-password"
                           class="form-control"
                           required/>
                </div>
                <button type="submit" class="btn btn-primary">Change password</button>
            </form>
        </div>
        <div class="col-7">
            <form method="post" action="profile" class="row">
                <div class="row mb-3 gx-2">
                    <div class="form-group required col-6">
                        <label for="lastName" class="form-label">Last name</label>
                        <input name="lastName" class="form-control" id="lastName" required value="${account.lastName}">
                    </div>
                    <div class="form-group required col-6">
                        <label for="firstName" class="form-label">First name</label>
                        <input name="firstName" class="form-control" id="firstName" required
                               value="${account.firstName}">
                    </div>
                </div>

                <!-- Email input -->
                <div class="form-group required mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input name="email" type="email" id="email" disabled class="form-control" required
                           value="${account.email}"/>
                </div>

                <div class="form-group required mb-3">
                    <label class="form-label">Gender</label>
                    <div class="d-flex">
                        <div class="form-check me-4">
                            <input class="form-check-input" type="radio" name="gender" id="male"
                                   value="MALE" ${account.gender=='MALE'?'checked': ''}>
                            <label class="form-check-label" for="male">
                                Male
                            </label>
                        </div>

                        <div class="form-check me-5">
                            <input class="form-check-input" type="radio" name="gender" id="female"
                                   value="FEMALE" ${account.gender=='FEMALE'?'checked': ''}>
                            <label class="form-check-label" for="female">
                                Female
                            </label>
                        </div>

                        <div class="form-check me-5">
                            <input class="form-check-input" type="radio" name="gender" id="other"
                                   value="OTHER" ${account.gender=='OTHER'?'checked': ''}>
                            <label class="form-check-label" for="other">
                                Other
                            </label>
                        </div>
                    </div>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="phoneNumber">Phone Number</label>
                    <input name="phoneNumber" id="phoneNumber" value="${account.phoneNumber}" class="form-control"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="dob">Birthday</label>
                    <input name="dob" type="date" id="dob" class="form-control" value="${account.DOB}"/>
                </div>

                <div class="form-group mb-3">
                    <label class="form-label" for="address">Address</label>
                    <input name="address" id="address" class="form-control" value="${account.address}"/>
                </div>

                <c:if test="${account.role=='DOCTOR'}">
                <div class="form-group mb-3 required">
                    <label class="form-label" for="department">Department</label>
                    <select name="departmentId" class="form-select" aria-label="" required id="department">
                        <c:forEach items="${departments}" var="department">
                            <option value="${department.id}" ${account.department.id==department.id?'selected': ''}>${department.name}</option>
                        </c:forEach>
                    </select>
                </div>
                </c:if>

                <div class="form-group mb-3">
                    <label class="form-label" for="description">Description</label>
                    <textarea name="description" id="description" class="form-control">${account.description}</textarea>
                </div>

                <div class="text-center text-lg-start mt-4 pt-2">
                    <button type="submit" class="btn btn-primary px-3">Update
                    </button>
                </div>
        </div>
        </form>
    </div>
</div>
<%--Header--%>
<jsp:include page="./common/footer.jsp"/>

<script>
    function preview() {
        frame.src = URL.createObjectURL(event.target.files[0]);
    }

    function clearImage() {
        document.getElementById('formFile').value = null;
        frame.src = "";
    }
</script>

</body>
</html>
