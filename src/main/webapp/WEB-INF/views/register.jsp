<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SprintBurg - Registrar Usuario</title>
    <style>
        /* Estilo Base consistente */
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

        /* Barra Superior (Igual a las otras vistas) */
        .top-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 40px;
        }
        .user-info { color: #666; font-size: 0.9em; }
        .back-link { text-decoration: none; color: #555; font-weight: bold; padding: 8px 15px; background: #e0e0e0; border-radius: 5px; transition: background 0.2s; }
        .back-link:hover { background: #d0d0d0; }

        /* Contenedor Centrado */
        .wrapper {
            flex-grow: 1;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding-top: 20px;
        }

        /* Tarjeta de Registro */
        .register-card {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 500px;
            text-align: center;
        }

        h1 { margin-top: 0; color: #333; margin-bottom: 25px; font-size: 1.8em; }

        /* Alertas */
        .alert {
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 25px;
            text-align: left;
            font-size: 0.95em;
            display: flex;
            align-items: center;
            border: 1px solid transparent;
        }
        .alert-success { background-color: #d4edda; color: #155724; border-color: #c3e6cb; }
        .alert-error { background-color: #f8d7da; color: #721c24; border-color: #f5c6cb; }

        /* Grupos de Formulario */
        .form-group { text-align: left; margin-bottom: 20px; }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #555;
            font-size: 0.9em;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        /* Inputs y Selects */
        input[type="text"], input[type="password"], select {
            width: 100%;
            padding: 12px;
            box-sizing: border-box;
            font-size: 1.1em;
            border: 2px solid #ddd;
            border-radius: 6px;
            outline: none;
            transition: border-color 0.3s;
            background-color: #fff;
        }

        input:focus, select:focus { border-color: #007bff; }

        /* Botón Principal */
        button {
            width: 100%;
            padding: 15px;
            font-size: 1.2em;
            margin-top: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
            transition: background 0.3s, transform 0.1s;
        }
        button:hover {
            background-color: #218838;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        /* Enlace de ayuda */
        .help-text { font-size: 0.8em; color: #999; margin-top: 20px; }
    </style>
</head>
<body>

<div class="top-bar">
    <div>
        <h2 style="margin:0; font-size: 1.2em; color: #444;">👤 Administración de Usuarios</h2>
        <span class="user-info">Sesión: <b>${pageContext.request.userPrincipal.name}</b></span>
    </div>
    <div>
        <a href="/admin/users" class="back-link">Cancelar y Volver</a>
    </div>
</div>

<div class="wrapper">
    <div class="register-card">
        <h1>Nuevo Empleado</h1>

        <c:if test="${param.success != null}">
            <div class="alert alert-success">
                ✅ <strong>¡Éxito!</strong> El usuario ha sido registrado correctamente.
            </div>
        </c:if>
        <c:if test="${error != null}">
            <div class="alert alert-error">
                ⚠️ <strong>Error:</strong> ${error}
            </div>
        </c:if>

        <form method="POST" action="/admin/register">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <label>Nombre Completo</label>
                <input type="text" name="fullName" required placeholder="Ej. Juan Pérez" autocomplete="off">
            </div>

            <div class="form-group">
                <label>Usuario (Login)</label>
                <input type="text" name="username" required placeholder="Ej. jperez" autocomplete="off">
            </div>

            <div class="form-group">
                <label>Contraseña Temporal</label>
                <input type="password" name="password" required placeholder="••••••••">
            </div>

            <div class="form-group">
                <label>Rol / Permisos</label>
                <select name="roleName">
                    <option value="EMPLOYEE">🔵 Empleado (Cajero - Ventas)</option>
                    <option value="MANAGER">🔴 Gerente (Admin Total)</option>
                </select>
            </div>

            <button type="submit">Registrar Usuario</button>
        </form>

        <div class="help-text">
            El usuario podrá iniciar sesión inmediatamente después del registro.
        </div>
    </div>
</div>

</body>
</html>