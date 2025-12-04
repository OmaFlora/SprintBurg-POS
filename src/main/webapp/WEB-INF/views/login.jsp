<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SprintBurg - Iniciar Sesión</title>
    <style>
        /* Estilo Base consistente */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* Tarjeta de Login (Estilo similar a payment.jsp) */
        .login-card {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 350px;
            text-align: center;
        }

        h1 {
            color: #333;
            margin-bottom: 30px;
            font-size: 1.8em;
            font-weight: bold;
        }

        /* Grupos de Formulario */
        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #555;
            font-size: 0.95em;
        }

        /* Inputs estilizados */
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 12px;
            box-sizing: border-box;
            font-size: 1em;
            border: 2px solid #ddd;
            border-radius: 6px;
            outline: none;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus, input[type="password"]:focus {
            border-color: #007bff; /* Azul SprintBurg */
        }

        /* Botón Principal */
        button {
            width: 100%;
            padding: 15px;
            background-color: #d9534f; /* Rojo Marca */
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 1.1em;
            font-weight: bold;
            transition: background 0.3s, transform 0.1s;
            margin-top: 10px;
        }

        button:hover {
            background-color: #c9302c;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        /* Alertas */
        .alert {
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 20px;
            font-size: 0.9em;
            text-align: left;
            display: flex;
            align-items: center;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
    </style>
</head>
<body>

<div class="login-card">
    <h1 style="margin-top: 0;">🍔 SprintBurg POS</h1>

    <c:if test="${param.error != null}">
        <div class="alert alert-error">
            ⚠️ Usuario o contraseña incorrectos.
        </div>
    </c:if>

    <c:if test="${param.logout != null}">
        <div class="alert alert-success">
            ✅ Has cerrado sesión exitosamente.
        </div>
    </c:if>

    <form method="POST" action="/authenticate">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="form-group">
            <label for="username">Usuario</label>
            <input type="text" id="username" name="username" placeholder="Ej. admin" required autofocus>
        </div>

        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password" placeholder="••••••••" required>
        </div>

        <button type="submit">Iniciar Sesión</button>
    </form>

    <div style="margin-top: 20px; font-size: 0.8em; color: #999;">
        Sistema de Punto de Venta v1.0
    </div>
</div>

</body>
</html>