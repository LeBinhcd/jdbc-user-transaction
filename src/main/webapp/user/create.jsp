<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi"><head><meta charset="UTF-8"><title>Add User</title></head><body>
<h2>Add User</h2>
<form action="${pageContext.request.contextPath}/users?action=create" method="post">
<label>Name: <input name="name" required></label><br>
<label>Email: <input type="email" name="email" required></label><br>
<label>Country: <input name="country" required></label><br>
<div class="form-group"><label>Quyền hạn (Permissions):</label><div>
<label><input type="checkbox" name="permissions" value="1"> Thêm (Add)</label>
<label><input type="checkbox" name="permissions" value="2"> Sửa (Edit)</label>
<label><input type="checkbox" name="permissions" value="3"> Xóa (Delete)</label>
<label><input type="checkbox" name="permissions" value="4"> Xem (View)</label>
</div></div>
<button type="submit">Save</button>
</form>
<a href="${pageContext.request.contextPath}/users">Back</a>
</body></html>

