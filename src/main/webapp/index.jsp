<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bienvenido - Sistema ONG</title>
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
            max-width: 600px;
            width: 100%;
            background: white;
            padding: 60px 50px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .logo {
            margin-bottom: 30px;
        }

        .logo i {
            font-size: 80px;
            color: #5B68C4;
        }

        h1 {
            color: #333;
            font-size: 32px;
            font-weight: 700;
            margin-bottom: 12px;
        }

        .subtitle {
            color: #666;
            font-size: 16px;
            margin-bottom: 50px;
            line-height: 1.6;
        }

        .buttons-container {
            display: flex;
            flex-direction: column;
            gap: 16px;
            margin-bottom: 40px;
        }

        .btn {
            padding: 16px 32px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            text-decoration: none;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            font-family: 'Inter', sans-serif;
        }

        .btn i {
            font-size: 22px;
        }

        .btn-primary {
            background: #5B68C4;
            color: white;
        }

        .btn-primary:hover {
            background: #4a56a8;
        }

        .btn-secondary {
            background: white;
            color: #5B68C4;
            border: 2px solid #5B68C4;
        }

        .btn-secondary:hover {
            background: #f0f2ff;
        }

        @media (max-width: 600px) {
            .container {
                padding: 50px 30px;
            }

            h1 {
                font-size: 28px;
            }

            .features {
                grid-template-columns: 1fr;
                gap: 30px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="logo">
            <i class='bx bx-building-house'></i>
        </div>

        <h1>Sistema de Gestión ONG</h1>
        <p class="subtitle">
            Bienvenido a nuestra plataforma de gestión.<br>
            Inicia sesión o registrate para comenzar.
        </p>

        <div class="buttons-container">
            <a href="login.jsp" class="btn btn-primary">
                <i class='bx bx-log-in'></i>
                Iniciar Sesión
            </a>
            <a href="registro.jsp" class="btn btn-secondary">
                <i class='bx bx-user-plus'></i>
                Registrarse
            </a>
        </div>

    </div>
</body>
</html>