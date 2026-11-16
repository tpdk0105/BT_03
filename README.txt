             HƯỚNG DẪN CHẠY DỰ ÁN Login - Hệ Thống ABC (JPA / HIBERNATE)

1. YÊU CẦU MÔI TRƯỜNG:
   - JDK 11 trở lên
   - Apache Tomcat v10.0 hoặc v11.0 (Jakarta EE)
   - Microsoft SQL Server (port 1433)
   - Spring Tool Suite (STS) hoặc Eclipse IDE

2. CẤU HÌNH CƠ SỞ DỮ LIỆU (SQL Server):
   - Mở file script SQL tại đường dẫn:
     database/test_User.sql
   - Chạy (Execute / F5) script trên SQL Server Management Studio (SSMS).
   - Script sẽ tạo cơ sở dữ liệu [test], bảng [User] và chèn sẵn 3 tài khoản mẫu.
   - Ghi chú: Các cột mới của [User] (status, code) và các bảng [categories], [videos]
     (kèm khóa ngoại CategoryId và userId 1-N với [User]) sẽ được JPA/Hibernate
     tự động tạo/cập nhật (nhờ cấu hình hibernate.hbm2ddl.auto = update)
     khi chạy ứng dụng lần đầu hoặc chạy Test.java.

3. CẤU HÌNH KẾT NỐI JPA & EMAIL SMTP:
   a) Kết nối SQL Server (nếu máy chấm dùng tài khoản/mật khẩu SQL khác):
      - Mở file: src/main/resources/META-INF/persistence.xml
      - Chỉnh sửa các thuộc tính trong persistence-unit "jpa-hibernate-mysql":
        + jakarta.persistence.jdbc.url      : jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=test
        + jakarta.persistence.jdbc.user     : sa
        + jakarta.persistence.jdbc.password : Khoa010506@

   b) Gửi OTP qua Email (Jakarta Mail - SMTP Gmail):
      - Đã cấu hình sẵn tại src/main/java/vn/iotstar/util/Constant.java
        (EMAIL_FROM, EMAIL_PASSWORD dùng Gmail App Password).

4. IMPORT VÀ CHẠY DỰ ÁN TRÊN ECLIPSE / SPRING TOOL SUITE (STS):
   - Vào File -> Import... -> Maven -> Existing Maven Projects.
   - Chọn thư mục dự án "Login" (chứa pom.xml) -> Bấm Finish.
   - Chuột phải vào project -> Maven -> Update Project... (tích chọn Force Update) -> OK.
   - Kiểm tra Context Root (nếu cần): Chuột phải project -> Properties -> Web Project Settings
     -> đảm bảo Context root là "Login".
   - Chạy ứng dụng Web:
     Chuột phải vào project -> Run As -> Run on Server -> Chọn Apache Tomcat v10 / v11.

5. THƯ MỤC LƯU FILE UPLOAD (MULTIPART):
   - Ảnh Poster và Video (.mp4) upload bằng @MultipartConfig được lưu tại: C:\upload
     (Cấu hình trong src/main/java/vn/iotstar/util/Constant.java -> biến DIR;
     hệ thống sẽ tự động tạo thư mục nếu chưa tồn tại).

6. CÁC CHỨC NĂNG & ĐƯỜNG DẪN TRUY CẬP CHÍNH:
   - Đăng ký & Kích hoạt OTP Gmail : http://localhost:8080/Login/register  -> /verify-otp
     (Bắt buộc nhập đúng định dạng @gmail.com; mã OTP 6 số gửi qua email)
   - Đăng nhập (BCrypt + Cookie)   : http://localhost:8080/Login/login
     (Mật khẩu được mã hóa bằng BCrypt; chặn tài khoản chưa kích hoạt OTP)
   - Quên mật khẩu (OTP qua Gmail) : http://localhost:8080/Login/forgot-password -> /reset-password
   - Trang chủ (/home)             : http://localhost:8080/Login/home
     (Hiển thị Top 10 video mới nhất, có nút chuyển xem tất cả Video)
   - Danh sách / Quản lý Video     : http://localhost:8080/Login/video
     (Phân trang 6 video/trang. Manager/Admin quản lý CRUD video của mình
      và bắt buộc có Poster + Link/File Video; User chỉ xem & Play)
   - Chi tiết & Play 01 Video      : http://localhost:8080/Login/video/detail?id=<VideoId>
     (Bấm vào tiêu đề video trên /home hoặc /video để xem chi tiết & phát)
   - Quản trị Admin:
     + Quản lý Thể loại (CRUD)     : http://localhost:8080/Login/admin/categories
     + Quản lý Video (Admin CRUD)  : http://localhost:8080/Login/admin/videos
     + Phân quyền Manager          : http://localhost:8080/Login/admin/users

7. DANH SÁCH TÀI KHOẢN MẪU:
   + Quản trị viên (Admin - Role 1):
     - Tài khoản: admin  |  Mật khẩu: 123456
     - Điều hướng: /admin/home (có Quản lý Thể loại, Quản lý Video, Phân quyền Manager)

   + Quản lý (Manager - Role 2):
     - Tài khoản: manager |  Mật khẩu: 123456
     - Tài khoản: testmail1 | Mật khẩu: 654321
     - Điều hướng: /manager/home (có quyền đăng/sửa/xóa Video tại /video)

   + Thành viên (User - Role 3):
     - Tài khoản: khoa   |  Mật khẩu: 123456
     - Điều hướng: /home (xem Top 10 mới nhất, xem tất cả tại /video và Play video)
