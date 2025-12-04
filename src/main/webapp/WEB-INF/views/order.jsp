<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SprintBurg POS - Pedidos</title>
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
            margin-bottom: 20px;
        }
        .user-info { color: #666; font-size: 0.9em; }
        .back-link { text-decoration: none; color: #555; font-weight: bold; padding: 8px 15px; background: #e0e0e0; border-radius: 5px; }
        .back-link:hover { background: #d0d0d0; }

        .main-layout {
            display: flex;
            gap: 20px;
            height: 100%;
            overflow: hidden;
        }

        .product-panel {
            flex: 3;
            overflow-y: auto;
            padding-right: 10px;
        }

        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 15px;
        }

        .product-card {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.05);
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
            border-left: 5px solid transparent;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 120px;
        }

        .type-BURGER_BASE { border-left-color: #d9534f; } /* Rojo */
        .type-SIDE { border-left-color: #f0ad4e; }        /* Naranja */
        .type-DRINK { border-left-color: #0275d8; }       /* Azul */
        .type-TOPPING { border-left-color: #5bc0de; }     /* Celeste */

        .product-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        .product-name { font-weight: bold; font-size: 1.1em; margin-bottom: 5px; }
        .product-price { color: #28a745; font-weight: bold; font-size: 1.2em; }
        .product-tag { font-size: 0.8em; color: #999; text-transform: uppercase; }

        .cart-panel {
            flex: 1;
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
            height: fit-content;
            max-height: 90%;
        }

        .cart-header { border-bottom: 2px solid #f4f4f4; padding-bottom: 10px; margin-bottom: 10px; }

        #cart-list {
            flex-grow: 1;
            overflow-y: auto;
            min-height: 200px;
        }

        .cart-item {
            background: #f9f9f9;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 8px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .cart-item-details { flex-grow: 1; }
        .cart-item-name { font-weight: bold; display: block; }
        .cart-item-extra { font-size: 0.8em; color: #666; }

        .btn-remove {
            background: #ffcccc; color: #cc0000; border: none;
            border-radius: 4px; padding: 5px 10px; cursor: pointer; font-size: 0.8em;
        }
        .btn-remove:hover { background: #ffaaaa; }

        .total-box {
            margin-top: 20px;
            padding: 20px;
            background-color: #333;
            color: white;
            border-radius: 8px;
            text-align: right;
            font-size: 1.5em;
        }

        .btn-checkout {
            width: 100%;
            padding: 15px;
            font-size: 1.2em;
            margin-top: 15px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
            transition: background 0.3s;
        }
        .btn-checkout:hover { background-color: #218838; }

    </style>
</head>
<body>

<div class="top-bar">
    <div>
        <h2 style="margin:0;">🛒 Nueva Orden</h2>
        <span class="user-info">Cajero: ${pageContext.request.userPrincipal.name}</span>
    </div>
    <div>
        <a href="/home" class="back-link">← Menú Principal</a>
        <a href="/logout" class="back-link" style="background:#ffdddd; color:#d9534f;">Salir</a>
    </div>
</div>

<div class="main-layout">

    <div class="product-panel">
        <div class="product-grid">
            <c:forEach var="product" items="${products}">
                <div class="product-card type-${product.type}"
                     onclick="addToCart(${product.id}, '${product.name}', ${product.price})">
                    <div>
                        <div class="product-name">${product.name}</div>
                    </div>
                    <div class="product-price">
                        <fmt:formatNumber value="${product.price}" type="currency" currencyCode="MXN"/>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="cart-panel">
        <div class="cart-header">
            <h3>Pedido Actual</h3>
        </div>

        <div id="cart-list">
            <p style="text-align: center; color: #999; margin-top: 50px;">El pedido está vacío.</p>
        </div>

        <div class="total-box">
            <div style="font-size: 0.5em; color: #ccc;">TOTAL A PAGAR</div>
            <span id="cart-total">$0.00</span>
        </div>

        <button class="btn-checkout" onclick="checkout()">Pagar</button>

        <form id="checkout-form" style="display: none;">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>

<script>
    let cart = [];
    let currentTotal = 0;

    const EXTRA_BACON_ID = 101;

    function addToCart(productId, name, price, type) {
        const item = {
            productId: productId,
            name: name,
            price: price,
            quantity: 1,
            addIngredients: type === 'BURGER_BASE' ? [EXTRA_BACON_ID] : [],
            removeIngredients: []
        };

        cart.push(item);
        updateCartDisplay();
    }

    function updateCartDisplay() {
        const listElement = document.getElementById('cart-list');
        listElement.innerHTML = '';
        currentTotal = 0;

        if (cart.length === 0) {
            listElement.innerHTML = '<p style="text-align: center; color: #999; margin-top: 50px;">El pedido está vacío.</p>';
        }

        cart.forEach((item, index) => {
            let itemPrice = item.price;
            if (item.addIngredients && item.addIngredients.length > 0) {
                itemPrice += (item.addIngredients.length * 0.50);
            }
            currentTotal += (itemPrice * item.quantity);

            const div = document.createElement('div');
            div.className = 'cart-item';

            div.innerHTML =
                '<div class="cart-item-details">' +
                '<span class="cart-item-name">' + item.name + '</span>' +
                '</div>' +
                '<button class="btn-remove" onclick="removeItem(' + index + ')">X</button>';

            listElement.appendChild(div);
        });

        document.getElementById('cart-total').innerText =
            new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN' }).format(currentTotal);
    }

    function removeItem(index) {
        cart.splice(index, 1);
        updateCartDisplay();
    }

    function checkout() {
        if (cart.length === 0) {
            alert("El pedido está vacío.");
            return;
        }

        localStorage.setItem('sprintBurgCart', JSON.stringify(cart));

        const orderRequestDTO = {
            items: cart.map(item => ({
                productId: item.productId,
                quantity: item.quantity,
                addIngredients: item.addIngredients,
                removeIngredients: item.removeIngredients
            })),
            employeeId: 1
        };

        fetch('/api/orders/calculate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
            },
            body: JSON.stringify(orderRequestDTO)
        })
            .then(response => response.json())
            .then(data => {
                const url = '/payment?total=' + data.total +
                    '&subtotal=' + data.finalSubtotal +
                    '&taxes=' + data.taxes;
                window.location.href = url;
            })
            .catch(error => {
                console.error(error);
                alert('Hubo un error al procesar el pedido.');
            });
    }
</script>

</body>
</html>