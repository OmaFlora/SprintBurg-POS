<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>SprintBurg - Menú Principal</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4; text-align: center; padding: 50px; }
        h1 { color: #333; margin-bottom: 40px; }
        .menu-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; max-width: 800px; margin: 0 auto; }

        .menu-card {
            background: white; padding: 30px; border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1); text-decoration: none; color: #333;
            transition: transform 0.2s, box-shadow 0.2s; display: flex; flex-direction: column; align-items: center;
        }
        .menu-card:hover { transform: translateY(-5px); box-shadow: 0 8px 12px rgba(0,0,0,0.15); }

        .icon { font-size: 3em; margin-bottom: 15px; }
        .title { font-size: 1.5em; font-weight: bold; }
        .desc { color: #666; margin-top: 5px; }

        .card-order { border-top: 5px solid #28a745; }
        .card-history { border-top: 5px solid #007bff; }
        .card-admin { border-top: 5px solid #d9534f; }

        .logout-btn { margin-top: 50px; display: inline-block; padding: 10px 20px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>

<h1>Bienvenido a SprintBurg 🍔</h1>
<p>Usuario: <b><sec:authentication property="principal.username" /></b></p>

<div class="menu-grid">

    <a href="/orders" class="menu-card card-order">
        <div class="icon">🛒</div>
        <div class="title">Nueva Orden</div>
        <div class="desc">Tomar pedidos y cobrar</div>
    </a>

    <a href="/history" class="menu-card card-history"> <div class="icon">📜</div>
        <div class="title">Historial</div>
        <div class="desc">Ver ventas pasadas</div>
    </a>

    <sec:authorize access="hasRole('MANAGER')">
        <a href="/admin/products" class="menu-card card-admin"> <div class="icon">⚙️</div>
            <div class="title">Administración</div>
            <div class="desc">Inventario y Usuarios</div>
        </a>
    </sec:authorize>

</div>

<a href="/logout" class="logout-btn">Cerrar Sesión</a>

</body>
</html>