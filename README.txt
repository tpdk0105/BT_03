             HƯỚNG DẪN CHẠY DỰ ÁN LoginSessionCookie (JPA / HIBERNATE)

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
   - Ghi chú: Hai bảng [categories] và [videos] sẽ được JPA/Hibernate tự động tạo
     (nhờ cấu hình hibernate.hbm2ddl.auto = update) khi chạy kiểm thử Test.java
     hoặc khi khởi chạy ứng dụng lần đầu.

3. CẤU HÌNH KẾT NỐI JPA (Nếu máy chấm dùng tài khoản/mật khẩu SQL khác):
   - Mở file: src/main/resources/META-INF/persistence.xml
   - Chỉnh sửa các thuộc tính trong persistence-unit "jpa-hibernate-mysql":
     + jakarta.persistence.jdbc.url      : jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;databaseName=test
     + jakarta.persistence.jdbc.user     : sa
     + jakarta.persistence.jdbc.password : Khoa010506@

4. IMPORT VÀ CHẠY DỰ ÁN TRÊN ECLIPSE / SPRING TOOL SUITE (STS):
   - Vào File -> Import... -> Maven -> Existing Maven Projects.
   - Chọn thư mục "LoginSessionCookie" -> Bấm Finish.
   - Chuột phải vào project -> Maven -> Update Project... (tích chọn Force Update) -> OK.
   - (Tuỳ chọn) Test cấu hình JPA:
     Mở file src/main/java/vn/iotstar/config/Test.java -> Chuột phải -> Run As -> Java Application
     để kiểm tra kết nối JPA, tự động sinh bảng [categories], [videos] và chèn dữ liệu mẫu.
   - Chạy ứng dụng Web:
     Chuột phải vào project -> Run As -> Run on Server -> Chọn Apache Tomcat v10 / v11.

5. THƯ MỤC LƯU ẢNH UPLOAD:
   - Hình ảnh upload từ chức năng CRUD Category / Video được lưu tại: C:\upload
     (Cấu hình trong src/main/java/vn/iotstar/util/Constant.java -> biến DIR;
     hệ thống sẽ tự động tạo thư mục nếu chưa tồn tại).

6. ĐƯỜNG DẪN TRUY CẬP CHÍNH:
   - Đăng nhập (Session / Cookie Remember Me): http://localhost:8080/LoginSessionCookie/login
   - Đăng ký tài khoản mới                   : http://localhost:8080/LoginSessionCookie/register
   - Quản lý Thể loại (Category CRUD - Admin): http://localhost:8080/LoginSessionCookie/admin/categories
   - Quản lý Video (Video CRUD - Admin)      : http://localhost:8080/LoginSessionCookie/admin/videos

7. DANH SÁCH TÀI KHOẢN MẪU:
   + Quản trị viên (Admin - Role 1):
     - Tài khoản: admin
     - Mật khẩu : 123456
     - Điều hướng: /admin/home (có nút truy cập Quản lý Thể loại & Quản lý Video)

   + Quản lý (Manager - Role 2):
     - Tài khoản: manager
     - Mật khẩu : 123456
     - Điều hướng: /manager/home

   + Thành viên (User - Role 3):
     - Tài khoản: khoa (Trần Phong Đăng Khoa)
     - Mật khẩu : 123456
     - Điều hướng: /home
