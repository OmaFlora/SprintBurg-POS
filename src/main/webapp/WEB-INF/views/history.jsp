<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>SprintBurg - Historial</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .user-info { color: #666; font-size: 0.9em; }
        .back-link { text-decoration: none; color: #555; font-weight: bold; padding: 8px 15px; background: #e0e0e0; border-radius: 5px; transition: background 0.2s; }
        .back-link:hover { background: #d0d0d0; }

        .main-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
            padding: 25px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }

        h1 { margin-top: 0; color: #333; font-size: 1.5em; margin-bottom: 20px; border-bottom: 2px solid #f4f4f4; padding-bottom: 10px; }

        .filter-bar {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 8px;
            display: flex;
            gap: 15px;
            align-items: center;
            margin-bottom: 20px;
            border: 1px solid #eee;
            flex-wrap: wrap;
        }

        .filter-group { display: flex; align-items: center; gap: 8px; }
        label { font-weight: 600; color: #555; font-size: 0.9em; }

        input[type="date"] {
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-family: inherit;
            outline: none;
        }
        input[type="date"]:focus { border-color: #007bff; }

        .btn-search {
            background-color: #007bff; color: white; border: none; padding: 9px 20px;
            border-radius: 5px; cursor: pointer; font-weight: bold; transition: background 0.2s;
        }
        .btn-search:hover { background-color: #0069d9; }

        .summary-box {
            margin-left: auto;
            background-color: #333;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            font-size: 1.1em;
        }

        .table-container { overflow-x: auto; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }

        th { text-align: left; padding: 15px; background-color: #f8f9fa; color: #444; font-weight: 600; border-bottom: 2px solid #ddd; }
        td { padding: 15px; border-bottom: 1px solid #eee; vertical-align: middle; color: #333; }
        tr:hover { background-color: #f1f8ff; }

        .badge { padding: 5px 10px; border-radius: 15px; font-size: 0.85em; font-weight: bold; display: inline-block; }
        .badge-cash { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; } /* Verde */
        .badge-card { background-color: #cce5ff; color: #004085; border: 1px solid #b8daff; } /* Azul */

        .btn-details {
            background-color: #17a2b8; color: white; border: none; padding: 6px 12px;
            border-radius: 4px; cursor: pointer; font-size: 0.85em; transition: opacity 0.2s;
        }
        .btn-details:hover { opacity: 0.85; }

        .modal { display: none; position: fixed; z-index: 100; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); backdrop-filter: blur(2px); }

        .modal-content {
            background-color: #fff; margin: 5% auto; padding: 0;
            border-radius: 8px; width: 90%; max-width: 600px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
            animation: slideDown 0.3s;
        }

        .modal-header { padding: 15px 20px; border-bottom: 1px solid #eee; display: flex; justify-content: space-between; align-items: center; }
        .modal-header h2 { margin: 0; font-size: 1.2em; color: #333; }
        .close { color: #aaa; font-size: 24px; font-weight: bold; cursor: pointer; transition: color 0.2s; }
        .close:hover { color: #333; }

        .modal-body { padding: 20px; max-height: 60vh; overflow-y: auto; }

        .modal-footer { padding: 15px 20px; background-color: #f9f9f9; border-top: 1px solid #eee; text-align: right; border-radius: 0 0 8px 8px; }

        .item-row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px dashed #eee; }
        .item-name { font-weight: 500; }
        .item-sub { font-size: 0.85em; color: #666; margin-top: 2px; }

        @keyframes slideDown { from { transform: translateY(-20px); opacity: 0; } to { transform: translateY(0); opacity: 1; } }
    </style>
</head>
<body>

<div class="top-bar">
    <div>
        <span class="user-info">Usuario: <b>${pageContext.request.userPrincipal.name}</b></span>
    </div>
    <div>
        <a href="/home" class="back-link">← Menú Principal</a>
    </div>
</div>

<div class="main-card">
    <h1>Historial de Ventas</h1>

    <form action="/history" method="GET" class="filter-bar">
        <div class="filter-group">
            <label>Desde:</label>
            <input type="date" name="startDate" value="${startDate}">
        </div>
        <div class="filter-group">
            <label>Hasta:</label>
            <input type="date" name="endDate" value="${endDate}">
        </div>
        <button type="submit" class="btn-search">Filtrar Resultados</button>

        <div class="summary-box">
            Total: <fmt:formatNumber value="${totalRevenue}" type="currency" currencyCode="MXN"/>
        </div>
    </form>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>ID Venta</th>
                <th>Fecha / Hora</th>
                <th>Ref. Ticket</th>
                <th>Método Pago</th>
                <th>Monto Total</th>
                <th>Detalles</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="sale" items="${sales}">
                <tr>
                    <td style="color:#888;">#${sale.id}</td>
                    <td>
                        <b>${sale.saleDate.toLocalDate()}</b> <span style="color:#666; font-size:0.9em;">${sale.saleDate.toLocalTime().toString().substring(0,5)}</span>
                    </td>
                    <td style="font-family: monospace; font-size: 1.1em;">${sale.ticketNumber}</td>
                    <td>
                        <c:choose>
                            <c:when test="${sale.paymentMethod == 'CASH'}">
                                <span class="badge badge-cash">💵 Efectivo</span>
                            </c:when>
                            <c:when test="${sale.paymentMethod == 'CARD'}">
                                <span class="badge badge-card">💳 Tarjeta</span>
                            </c:when>
                            <c:otherwise>${sale.paymentMethod}</c:otherwise>
                        </c:choose>
                    </td>
                    <td><strong><fmt:formatNumber value="${sale.total}" type="currency" currencyCode="MXN"/></strong></td>
                    <td>
                        <button class="btn-details" onclick="showDetails(${sale.id})">Ver Ítems</button>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty sales}">
                <tr>
                    <td colspan="6" style="text-align:center; padding: 40px; color: #999;">
                        No se encontraron ventas en el rango seleccionado.
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<div id="detailModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Detalle de Venta #<span id="modal-sale-id"></span></h2>
            <span class="close" onclick="closeModal()">&times;</span>
        </div>

        <div class="modal-body">
            <p style="margin-top:0; color:#666;">Atendido por Empleado ID: <strong id="modal-employee"></strong></p>
            <hr style="border:0; border-top:1px solid #eee; margin: 15px 0;">

            <div id="modal-items-list">
                Cargando...
            </div>
        </div>

        <div class="modal-footer">
            <span style="font-size: 1.2em; font-weight: bold; margin-right: 10px;">Total Pagado:</span>
            <span id="modal-total" style="font-size: 1.4em; color: #28a745; font-weight: bold;"></span>
        </div>
    </div>
</div>

<script>
    function showDetails(saleId) {
        const modal = document.getElementById("detailModal");
        const list = document.getElementById("modal-items-list");

        modal.style.display = "block";
        list.innerHTML = "<div style='text-align:center; padding:20px; color:#666;'>Cargando datos...</div>";

        fetch('/api/sales/' + saleId)
            .then(response => response.json())
            .then(data => {
                document.getElementById("modal-sale-id").innerText = data.id;
                document.getElementById("modal-employee").innerText = data.employeeId;

                const totalFormatted = new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN' }).format(data.total);
                document.getElementById("modal-total").innerText = totalFormatted;

                list.innerHTML = "";
                if(data.items && data.items.length > 0) {
                    data.items.forEach(item => {
                        const priceFormatted = new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN' }).format(item.unitPriceAtSale);

                        const div = document.createElement("div");
                        div.className = "item-row";

                        let extrasHtml = "";
                        if(item.customizationDetails && item.customizationDetails.length > 5) {
                            extrasHtml = '<div class="item-sub">' + item.customizationDetails + '</div>';
                        }
                        div.innerHTML =
                            '<div>' +
                            '<div class="item-name">Producto ID: ' + item.productId + ' <span style="color:#888;">(x' + item.quantity + ')</span></div>' +
                            extrasHtml +
                            '</div>' +
                            '<div style="font-weight:bold;">' + priceFormatted + '</div>';

                        list.appendChild(div);
                    });
                } else {
                    list.innerHTML = "<p>No hay ítems registrados.</p>";
                }
            })
            .catch(err => {
                console.error(err);
                list.innerHTML = "<p style='color:red'>Error al cargar detalles. Revise la consola.</p>";
            });
    }

    function closeModal() {
        document.getElementById("detailModal").style.display = "none";
    }

    window.onclick = function(event) {
        const modal = document.getElementById("detailModal");
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
</script>

</body>
</html>