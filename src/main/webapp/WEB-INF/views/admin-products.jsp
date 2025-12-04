<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin - Inventario</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #f4f4f4; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }

        .nav-admin { margin-bottom: 20px; border-bottom: 2px solid #eee; padding-bottom: 10px; }
        .nav-admin a { margin-right: 15px; text-decoration: none; font-weight: bold; color: #555; padding: 5px 10px; border-radius: 4px; }
        .nav-admin a.active { background-color: #d9534f; color: white; }
        .nav-admin a:hover:not(.active) { background-color: #ddd; }

        .form-box { background: #f9f9f9; padding: 15px; border-radius: 5px; margin-bottom: 20px; border: 1px solid #ddd; }
        .form-row { display: flex; gap: 10px; }
        input, select { padding: 8px; border: 1px solid #ccc; border-radius: 4px; flex: 1; }
        button { padding: 8px 15px; border: none; border-radius: 4px; cursor: pointer; color: white; font-weight: bold; }
        .btn-add { background-color: #28a745; }
        .btn-stock { background-color: #007bff; font-size: 0.8em; padding: 4px 8px; }

        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
        .low-stock { color: #d9534f; font-weight: bold; }
    </style>
</head>
<body>

<div class="container">
    <div class="nav-admin">
        <a href="/admin/products" class="active">📦 Inventario</a>
        <a href="/admin/users">👥 Personal</a>
        <a href="/home" style="float: right;">← Menú Principal</a>
    </div>

    <h1>📦 Gestión de Productos</h1>

    <div class="form-box">
        <h3>Nuevo Producto</h3>
        <form action="/admin/products/create" method="POST">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-row">
                <input type="text" name="name" placeholder="Nombre (ej. Hamburguesa BBQ)" required>
                <input type="number" name="price" step="0.50" placeholder="Precio Venta" required>
                <input type="number" name="cost" step="0.50" placeholder="Costo Insumos" required>
            </div>
            <div class="form-row" style="margin-top: 10px;">
                <input type="number" name="stock" placeholder="Stock Inicial" required>
                <select name="type">
                    <option value="BURGER_BASE">Hamburguesa Base</option>
                    <option value="TOPPING">Extra/Ingrediente</option>
                    <option value="DRINK">Bebida</option>
                    <option value="SIDE">Complemento</option>
                </select>
                <button type="submit" class="btn-add">➕ Agregar</button>
            </div>
        </form>
    </div>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Tipo</th>
            <th>Precio</th>
            <th>Stock</th>
            <th>Acciones Rápidas</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>${p.type}</td>
                <td><fmt:formatNumber value="${p.price}" type="currency" currencyCode="USD"/></td>
                <td class="${p.stock < 10 ? 'low-stock' : ''}">
                    <span id="stock-val-${p.id}">${p.stock}</span>
                </td>
                <td>
                    <button class="btn-stock" onclick="updateStock(${p.id}, 5)">+5</button>
                    <button class="btn-stock" onclick="updateStock(${p.id}, -1)">-1</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    function updateStock(id, amount) {
        fetch('/api/admin/products/' + id + '/stock', {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': '${_csrf.token}'
            },
            body: amount
        })
            .then(res => {
                if(res.ok) return res.json();
                throw new Error('Error al actualizar');
            })
            .then(product => {
                document.getElementById('stock-val-' + id).innerText = product.stock;
                document.getElementById('stock-val-' + id).style.backgroundColor = "#e8f0fe";
                setTimeout(() => document.getElementById('stock-val-' + id).style.backgroundColor = "transparent", 500);
            })
            .catch(err => alert("Error: " + err));
    }
</script>

</body>
</html>