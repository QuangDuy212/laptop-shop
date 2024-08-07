<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@page contentType="text/html" pageEncoding="UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Thông tin tài khoản</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <!-- Google Web Fonts -->
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link
                    href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                    rel="stylesheet">

                <!-- Icon Font Stylesheet -->
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                    rel="stylesheet">

                <!-- Libraries Stylesheet -->
                <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


                <!-- Customized Bootstrap Stylesheet -->
                <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                <!-- Template Stylesheet -->
                <link href="/client/css/style.css" rel="stylesheet">

                <meta name="_csrf" content="${_csrf.token}" />
                <!-- default header name is X-CSRF-TOKEN -->
                <meta name="_csrf_header" content="${_csrf.headerName}" />

                <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.css"
                    rel="stylesheet">



            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div class="container px-4 " style="margin-top: 100px;">
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"><a href="/">Trang chủ</a></li>
                        <li class="breadcrumb-item active">Quản lí tài khoản</li>
                    </ol>
                    <div class=" mt-5">
                        <div class="row">
                            <div class="col-md-6 col-12 mx-auto">
                                <h3>Thông tin tài khoản</h3>
                                <hr />
                                <form:form method="post" action="/account/update" modelAttribute="newUser" class="row"
                                    enctype="multipart/form-data">
                                    <div class="col-12 mb-3" style="display: flex; justify-content: center;">
                                        <img style="width: 200px; height: 200px; border-radius: 50%; display: none;"
                                            alt="product preview" id="avatarAccountPreview" />
                                        <div id="avatarEmpty"
                                            style="width: 200px; height: 200px; background-color: #ccc;border-radius: 50%; display: none;">
                                        </div>
                                    </div>
                                    <div class="mb-3" style="display: none;">
                                        <label class="form-label">ID:</label>
                                        <form:input type="text" class="form-control" path="id" />
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Email:</label>
                                        <form:input type="email" class="form-control" path="email" disabled="true" />
                                    </div>
                                    <div class="mb-3 col-12 col-md-6" style="display: none;">
                                        <label class="form-label">Password:</label>
                                        <form:input type="password" class="form-control" path="password" />
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Phone number:</label>
                                        <form:input type="text" class="form-control" path="phone" />
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Full Name:</label>
                                        <form:input type="text" class="form-control" path="fullName" />
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                        <label class="form-label">Address:</label>
                                        <form:input type="text" class="form-control" path="address" />
                                    </div>

                                    <div class="mb-3 col-12 col-md-6" style="display: none;">
                                        <label class="form-label">Role:</label>
                                        <form:select class="form-select" path="role.name">
                                            <form:option value="ADMIN">ADMIN</form:option>
                                            <form:option value="USER">USER</form:option>
                                        </form:select>
                                    </div>
                                    <div class="mb-3 col-12 col-md-6">
                                        <label for="avatarAccountFile" class="form-label">Image:</label>
                                        <input class="form-control" type="file" id="avatarAccountFile"
                                            accept=".png, .jpg, .jpeg" name="avatarAccountFile" />
                                    </div>
                                    <div class="col-12">

                                    </div>

                                    <div class="col-12 mb-5 col-md-4">
                                        <button type=" submit" class="btn btn-warning" style="width: 100%; ">
                                            Update</button>
                                    </div>
                                </form:form>

                            </div>

                        </div>

                    </div>
                </div>
                <jsp:include page="../layout/footer.jsp" />
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
                <!-- JavaScript Libraries -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/lib/easing/easing.min.js"></script>
                <script src="/client/lib/waypoints/waypoints.min.js"></script>
                <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                <!-- Template Javascript -->
                <script src="/client/js/main.js"></script>
                <script
                    src="https://cdnjs.cloudflare.com/ajax/libs/jquery-toast-plugin/1.3.2/jquery.toast.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarAccountFile = $("#avatarAccountFile");
                        const avatarUser = "${newUser.avatar}";
                        const avatarEmpty = $("#avatarEmpty");
                        if (avatarUser) {
                            const imgURL = "/images/avatar/" + avatarUser;
                            $("#avatarAccountPreview").attr("src", imgURL);
                            $("#avatarAccountPreview").css({ "display": "block" });
                            avatarEmpty.css({ "display": "none" })
                        } else {
                            $("#avatarAccountPreview").css({ "display": "none" });
                            avatarEmpty.css({ "display": "block" })

                        }
                        avatarAccountFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarAccountPreview").attr("src", imgURL);
                            $("#avatarAccountPreview").css({ "display": "block" });
                            avatarEmpty.css({ "display": "none" })
                        });
                    });
                </script>

            </body>

            </html>