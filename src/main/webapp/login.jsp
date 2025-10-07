<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login - ONG</title>
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
        <style>
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
                max-width: 450px;
                width: 100%;
                background: white;
                padding: 50px 40px;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }

            .login-header {
                text-align: center;
                margin-bottom: 40px;
            }

            .login-header i {
                font-size: 64px;
                color: #5B68C4;
                margin-bottom: 20px;
            }

            .login-header h2 {
                color: #333;
                font-size: 28px;
                font-weight: 600;
                margin-bottom: 8px;
            }

            .login-header p {
                color: #666;
                font-size: 14px;
            }

            .form-group {
                margin-bottom: 24px;
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

            .alert-error {
                background: #FFEBEE;
                color: #C62828;
                border: 1px solid #FFCDD2;
            }

            .alert-success {
                background: #E8F5E9;
                color: #2E7D32;
                border: 1px solid #C8E6C9;
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
        </style>
    </head>
    <body>
        <div class="container">
            <div class="login-header">
                <i class='bx bx-user-circle'></i>
                <h2>Iniciar Sesión</h2>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <i class='bx bx-error-circle'></i>
                    <span>${error}</span>
                </div>
            </c:if>

            <c:if test="${param.mensaje == 'logout'}">
                <div class="alert alert-success">
                    <i class='bx bx-check-circle'></i>
                    <span>Has cerrado sesión correctamente</span>
                </div>
            </c:if>

            <form action="loginController" method="post">
                <div class="form-group">
                    <label for="correo">Correo Electrónico</label>
                    <div class="input-wrapper">
                        <i class='bx bx-envelope'></i>
                        <input type="email" id="correo" name="correo" placeholder="ejemplo@correo.com" required>
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
                    <i class='bx bx-log-in'></i> Ingresar
                </button>
            </form>

            <div class="footer-text">
                ¿No tienes cuenta? <a href="registro.jsp">Registrate</a>
            </div>
        </div>
    </body>
</html>