# User Management

Ứng dụng JSP/Servlet/JDBC/MySQL quản lý User. Có CRUD, tìm kiếm theo country và sắp xếp theo name.

Chạy `schema.sql` trong MySQL trước. Cấu hình mặc định: `localhost:3306/user_management`, user `root`, password rỗng. Build bằng `mvn clean package`, deploy WAR lên Tomcat 10.1+ và truy cập `/user-management/users`.
