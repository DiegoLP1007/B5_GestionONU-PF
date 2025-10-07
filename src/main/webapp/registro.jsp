<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Registro de Usuario</title>
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <style>
            /* Tu CSS existente */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            body {
                font-family: 'Inter', sans-serif;
                background: #f5f5f5;
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 20px;
            }
            .container {
                max-width: 500px;
                width: 100%;
                background: white;
                padding: 50px 40px;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            }
            .registro-header {
                text-align: center;
                margin-bottom: 40px;
            }
            .registro-header i {
                font-size: 64px;
                color: #5B68C4;
                margin-bottom: 20px;
            }
            .registro-header h1 {
                color: #333;
                font-size: 28px;
                font-weight: 600;
                margin-bottom: 8px;
            }
            .registro-header p {
                color: #666;
                font-size: 14px;
            }
            .form-group {
                margin-bottom: 20px;
            }
            .form-group label {
                display: block;
                margin-bottom: 8px;
                color: #555;
                font-weight: 600;
                font-size: 14px;
            }
            .input-wrapper {
                position: relative;
            }
            .input-wrapper i {
                position: absolute;
                left: 12px;
                top: 50%;
                transform: translateY(-50%);
                color: #999;
                font-size: 18px;
            }
            input {
                width: 100%;
                padding: 12px 12px 12px 40px;
                border: 2px solid #e0e0e0;
                border-radius: 6px;
                font-size: 14px;
                font-family: 'Inter', sans-serif;
            }
            input:focus {
                outline: none;
                border-color: #5B68C4;
            }
            button {
                width: 100%;
                padding: 14px;
                border: none;
                background: #5B68C4;
                color: white;
                border-radius: 6px;
                cursor: pointer;
                font-size: 16px;
                font-weight: 600;
                font-family: 'Inter', sans-serif;
                margin-top: 10px;
            }
            button:hover {
                background: #4a56a8;
            }
            .alert {
                padding: 12px 16px;
                border-radius: 6px;
                margin-bottom: 24px;
                font-size: 14px;
                display: flex;
                align-items: center;
                gap: 10px;
            }
            .alert i {
                font-size: 20px;
            }
            .alert-danger {
                background: #FFEBEE;
                color: #C62828;
                border: 1px solid #FFCDD2;
            }
            .footer-text {
                text-align: center;
                margin-top: 24px;
                color: #666;
                font-size: 14px;
            }
            .footer-text a {
                color: #5B68C4;
                text-decoration: none;
                font-weight: 600;
            }
            .footer-text a:hover {
                color: #4a56a8;
            }
            .form-row {
                display: grid;
                grid-template-columns: 1fr 1fr;
                gap: 15px;
            }
            @media (max-width: 600px) {
                .form-row {
                    grid-template-columns: 1fr;
                }
                .container {
                    padding: 40px 30px;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="registro-header">
                <i class='bx bx-user-plus'></i>
                <h1>Registro de Usuario</h1>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <i class='bx bx-error-circle'></i>
                    <span>${error}</span>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/guardarUsuario" method="post">
                <div class="form-row">
                    <div class="form-group">
                        <label for="nombreUsuario">Nombre</label>
                        <div class="input-wrapper">
                            <i class='bx bx-user'></i>
                            <input type="text" id="nombreUsuario" name="nombreUsuario" placeholder="Tu nombre" required
                                   value="${nombreUsuario != null ? nombreUsuario : ''}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="apellidoUsuario">Apellido</label>
                        <div class="input-wrapper">
                            <i class='bx bx-user'></i>
                            <input type="text" id="apellidoUsuario" name="apellidoUsuario" placeholder="Tu apellido" required
                                   value="${apellidoUsuario != null ? apellidoUsuario : ''}">
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label for="direccionUsuario">Dirección</label>
                    <div class="input-wrapper">
                        <i class='bx bx-home'></i>
                        <input type="text" id="direccionUsuario" name="direccionUsuario" placeholder="Tu dirección" required
                               value="${direccionUsuario != null ? direccionUsuario : ''}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="telefono">Teléfono</label>
                    <div class="input-wrapper">
                        <i class='bx bx-phone'></i>
                        <input type="text" id="telefono" name="telefono" placeholder="Tu teléfono" required
                               value="${telefono != null ? telefono : ''}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="email">Correo Electrónico</label>
                    <div class="input-wrapper">
                        <i class='bx bx-envelope'></i>
                        <input type="email" id="email" name="email" placeholder="ejemplo@correo.com" required
                               value="${email != null ? email : ''}">
                    </div>
                </div>

                <div class="form-group">
                    <label for="contrasena">Contraseña</label>
                    <div class="input-wrapper">
                        <i class='bx bx-lock-alt'></i>
                        <input type="password" id="contrasena" name="contrasena" placeholder="••••••••" required>
                    </div>
                </div>

                <button type="submit">
                    <i class='bx bx-check-circle'></i> Registrarse
                </button>
            </form>

            <div class="footer-text">
                ¿Ya tienes cuenta? <a href="login.jsp">Inicia sesión</a>
            </div>
        </div>
    </body>
</html>
