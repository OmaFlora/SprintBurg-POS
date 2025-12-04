<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin - Personal</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f4f4; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }

        .nav-admin { margin-bottom: 20px; border-bottom: 2px solid #eee; padding-bottom: 10px; }
        .nav-admin a { margin-right: 15px; text-decoration: none; font-weight: bold; color: #555; padding: 5px 10px; border-radius: 4px; }
        .nav-admin a.active { background-color: #d9534f; color: white; }
        .nav-admin a:hover:not(.active) { background-color: #ddd; }

        .header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .btn-new { background-color: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold; }

        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        .role-badge { padding: 4px 8px; border-radius: 10px; font-size: 0.8em; color: white; }
        .badge-MANAGER { background-color: #d9534f; }
        .badge-EMPLOYEE { background-color: #17a2b8; }
    </style>
</head>
<body>

<div class="container">
    <div class="nav-admin">
        <a href="/admin/products">📦 Inventario</a>
        <a href="/admin/users" class="active">👥 Personal</a>
        <a href="/home" style="float: right;">← Menú Principal</a>
    </div>

    <div class="header-row">
        <h1>👥 Gestión de Personal</h1>
        <a href="/admin/register" class="btn-new">+ Nuevo Empleado</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Usuario</th>
            <th>Nombre Completo</th>
            <th>Rol</th>
            <th>Estado</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.id}</td>
                <td><b>${u.username}</b></td>
                <td>${u.fullName}</td>
                <td>
                    <span class="role-badge badge-${u.role}">${u.role}</span>
                </td>
                <td>
                    <c:if test="${u.active}">🟢 Activo</c:if>
                    <c:if test="${!u.active}">🔴 Inactivo</c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>