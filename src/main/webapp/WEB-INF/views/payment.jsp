<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>SprintBurg - Finalizar Pago</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
            height: 100vh;
            box-sizing: border-box;
            display: flex;
            flex-direction: column;
        }

        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 40px;
        }
        .user-info { color: #666; font-size: 0.9em; }
        .back-link { text-decoration: none; color: #555; font-weight: bold; padding: 8px 15px; background: #e0e0e0; border-radius: 5px; }
        .back-link:hover { background: #d0d0d0; }

        .wrapper {
            flex-grow: 1;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding-top: 20px;
        }

        .payment-card {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 450px;
            text-align: center;
        }

        h1 { margin-top: 0; color: #333; margin-bottom: 25px; font-size: 1.8em; }

        .receipt-list {
            text-align: left;
            margin-bottom: 20px;
            border-bottom: 2px dashed #eee;
            padding-bottom: 20px;
        }
        .receipt-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
            color: #555;
            font-size: 1.1em;
        }

        .total-box {
            background-color: #333;
            color: white;
            padding: 15px;
            border-radius: 8px;
            text-align: right;
            margin-bottom: 30px;
        }
        .total-label { font-size: 0.8em; color: #ccc; text-transform: uppercase; }
        .total-amount { font-size: 2em; font-weight: bold; }

        .btn { padding: 15px; font-size: 1.1em; border: none; border-radius: 8px; cursor: pointer; width: 100%; margin-bottom: 15px; font-weight: bold; transition: transform 0.1s, box-shadow 0.2s; display: flex; align-items: center; justify-content: center; gap: 10px; }
        .btn:hover { transform: translateY(-2px); box-shadow: 0 4px 8px rgba(0,0,0,0.1); }

        .btn-cash { background-color: #28a745; color: white; }
        .btn-card { background-color: #007bff; color: white; }

        #input-section {
            display: none;
            background: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            border: 1px solid #e0e0e0;
            animation: fadeIn 0.3s ease-out;
        }

        input[type="text"] {
            width: 100%;
            padding: 12px;
            margin: 15px 0;
            box-sizing: border-box;
            font-size: 1.2em;
            text-align: center;
            border: 2px solid #ddd;
            border-radius: 6px;
            outline: none;
        }
        input[type="text"]:focus { border-color: #007bff; }

        .btn-confirm { background-color: #17a2b8; color: white; }
        .btn-cancel { background-color: white; color: #666; border: 1px solid #ccc; font-size: 0.9em; padding: 10px; }
        .btn-cancel:hover { background-color: #eee; }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<div class="top-bar">
    <div>
        <span class="user-info">Cajero: <b>${pageContext.request.userPrincipal.name}</b></span>
    </div>
    <div>
        <a href="/orders" class="back-link">Cancelar y Volver</a>
    </div>
</div>

<div class="wrapper">
    <div class="payment-card">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>

        <h1>Confirmar Pago</h1>

        <div class="receipt-list">
            <div class="receipt-row">
                <span>Subtotal</span>
                <span><fmt:formatNumber value="${subtotal}" type="currency" currencyCode="MXN"/></span>
            </div>
            <div class="receipt-row">
                <span>IVA (16%)</span>
                <span><fmt:formatNumber value="${taxes}" type="currency" currencyCode="MXN"/></span>
            </div>
        </div>

        <div class="total-box">
            <div class="total-label">Total a Cobrar</div>
            <div class="total-amount">
                <fmt:formatNumber value="${total}" type="currency" currencyCode="MXN"/>
            </div>
        </div>

        <div id="selection-screen">
            <button class="btn btn-cash" onclick="setupInputScreen('CASH')">
                <span>💵</span> Cobrar en Efectivo
            </button>
            <button class="btn btn-card" onclick="setupInputScreen('CARD')">
                <span>💳</span> Cobrar con Tarjeta
            </button>
        </div>

        <div id="input-section">
            <h3 id="input-title" style="margin-top:0;">Datos del Pago</h3>
            <p id="input-label" style="color:#666; margin-bottom:5px;">Referencia:</p>

            <input type="text" id="ticketNumberInput" autocomplete="off">

            <button class="btn btn-confirm" onclick="submitPayment()">Confirmar Transacción</button>
            <button class="btn btn-cancel" onclick="cancelInput()">Volver a selección</button>
        </div>

    </div>
</div>

<script>
    let currentMethod = null;

    function setupInputScreen(method) {
        currentMethod = method;

        document.getElementById('selection-screen').style.display = 'none';
        document.getElementById('input-section').style.display = 'block';

        const titleEl = document.getElementById('input-title');
        const labelEl = document.getElementById('input-label');
        const inputEl = document.getElementById('ticketNumberInput');

        inputEl.value = '';

        if (method === 'CASH') {
            titleEl.innerText = "Pago en Efectivo";
            titleEl.style.color = "#28a745";
            labelEl.innerText = "Ingrese Folio / Billete recibido:";
            inputEl.placeholder = "Ej. EF-500";
        } else {
            titleEl.innerText = "Pago con Tarjeta";
            titleEl.style.color = "#007bff";
            labelEl.innerText = "Ingrese últimos 4 dígitos / Voucher:";
            inputEl.placeholder = "Ej. 4592";
        }

        setTimeout(() => inputEl.focus(), 100);
    }

    function cancelInput() {
        document.getElementById('input-section').style.display = 'none';
        document.getElementById('selection-screen').style.display = 'block';
        currentMethod = null;
    }

    function submitPayment() {
        const ticketVal = document.getElementById('ticketNumberInput').value.trim();

        if (!ticketVal) {
            alert("Por favor ingrese el número de referencia o folio.");
            document.getElementById('ticketNumberInput').focus();
            return;
        }

        const storedCart = localStorage.getItem('sprintBurgCart');
        if (!storedCart) {
            alert("Error crítico: El carrito se ha perdido. Volviendo al menú.");
            window.location.href = "/orders";
            return;
        }
        const cart = JSON.parse(storedCart);

        const orderRequestDTO = {
            items: cart.map(item => ({
                productId: item.productId,
                quantity: item.quantity,
                addIngredients: item.addIngredients,
                removeIngredients: item.removeIngredients
            })),
            employeeId: 1,
            ticketNumber: ticketVal,
            cashAmount: currentMethod === 'CASH' ? ${total} : 0,
            cardAmount: currentMethod === 'CARD' ? ${total} : 0
        };

        const btnConfirm = document.querySelector('.btn-confirm');
        const originalText = btnConfirm.innerText;
        btnConfirm.innerText = "Procesando...";
        btnConfirm.disabled = true;

        fetch('/api/orders/pay', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
            },
            body: JSON.stringify(orderRequestDTO)
        })
            .then(response => {
                if (!response.ok) throw new Error("Error en la transacción");
                return response.json();
            })
            .then(saleData => {
                alert("Venta registrada correctamente.\nID Venta: #" + saleData.id);
                localStorage.removeItem('sprintBurgCart');
                window.location.href = "/orders";
            })
            .catch(error => {
                console.error(error);
                alert("Error al procesar el pago. Intente nuevamente.");
                btnConfirm.innerText = originalText;
                btnConfirm.disabled = false;
            });
    }
</script>
</body>
</html>