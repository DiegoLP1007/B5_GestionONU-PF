<%@ page import="java.util.List" %>
<%@ page import="model.Usuario" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Gestión de Usuarios</title>
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
                padding: 0;
            }

            .navbar {
                background: white;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                position: sticky;
                top: 0;
                z-index: 999;
            }

            .nav-container {
                max-width: 1400px;
                margin: 0 auto;
                padding: 16px 40px;
                display: flex;
                align-items: center;
                justify-content: space-between;
                gap: 20px;
            }

            .nav-brand {
                display: flex;
                align-items: center;
                gap: 10px;
                color: #5B68C4;
                font-weight: 700;
                font-size: 20px;
            }

            .nav-brand i {
                font-size: 28px;
            }

            .nav-menu {
                display: flex;
                gap: 8px;
                flex: 1;
                justify-content: center;
            }

            .nav-item {
                display: flex;
                align-items: center;
                gap: 6px;
                padding: 10px 16px;
                border-radius: 6px;
                color: #666;
                text-decoration: none;
                font-weight: 500;
                font-size: 14px;
            }

            .nav-item:hover {
                background: #f0f2ff;
                color: #5B68C4;
            }

            .nav-item.active {
                background: #5B68C4;
                color: white;
            }

            .nav-item i {
                font-size: 20px;
            }

            .btn-logout {
                display: flex;
                align-items: center;
                gap: 6px;
                padding: 10px 16px;
                background: #F44336;
                color: white;
                border-radius: 6px;
                text-decoration: none;
                font-weight: 500;
                font-size: 14px;
            }

            .btn-logout:hover {
                background: #D32F2F;
            }

            .btn-logout i {
                font-size: 20px;
            }

            .container {
                max-width: 1400px;
                margin: 20px auto;
                background: white;
                border-radius: 8px;
                padding: 40px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            }
            .header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 30px;
                padding-bottom: 20px;
                border-bottom: 2px solid #e0e0e0;
            }
            h1 {
                color: #333;
                font-size: 28px;
                font-weight: 600;
            }
            .btn-group {
                display: flex;
                gap: 12px;
            }
            .btn {
                padding: 12px 24px;
                border: none;
                border-radius: 6px;
                font-size: 14px;
                font-weight: 500;
                cursor: pointer;
                display: inline-flex;
                align-items: center;
                gap: 8px;
                text-decoration: none;
            }
            .btn-primary {
                background: #5B68C4;
                color: white;
            }
            .btn-primary:hover {
                background: #4a56a8;
            }
            .table-wrapper {
                overflow-x: auto;
                border-radius: 8px;
                border: 1px solid #e0e0e0;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background: white;
            }
            thead {
                background: #5B68C4;
                color: white;
            }
            th {
                padding: 16px;
                text-align: left;
                font-weight: 600;
                font-size: 14px;
                text-transform: uppercase;
            }
            td {
                padding: 16px;
                border-bottom: 1px solid #e0e0e0;
                color: #555;
                font-size: 14px;
            }
            tbody tr:hover {
                background: #f9f9f9;
            }
            .btn-edit {
                background: #2196F3;
                color: white;
                padding: 8px 16px;
                font-size: 13px;
            }
            .btn-edit:hover {
                background: #1976D2;
            }
            .btn-delete {
                background: #F44336;
                color: white;
                padding: 8px 16px;
                font-size: 13px;
            }
            .btn-delete:hover {
                background: #D32F2F;
            }
            .action-buttons {
                display: flex;
                gap: 8px;
            }
            .modal {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.5);
                z-index: 1000;
                align-items: center;
                justify-content: center;
            }
            .modal.show {
                display: flex;
            }
            .modal-content {
                background: white;
                border-radius: 8px;
                padding: 40px;
                max-width: 600px;
                width: 90%;
                max-height: 90vh;
                overflow-y: auto;
                box-shadow: 0 4px 16px rgba(0, 0, 0, 0.2);
            }
            .modal-header {
                margin-bottom: 30px;
            }
            .modal-header h2 {
                color: #333;
                font-size: 24px;
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
            .form-control {
                width: 100%;
                padding: 12px;
                border: 2px solid #e0e0e0;
                border-radius: 6px;
                font-size: 14px;
            }
            .form-control:focus {
                outline: none;
                border-color: #5B68C4;
            }
            textarea.form-control {
                min-height: 100px;
                resize: vertical;
                font-family: 'Inter', sans-serif;
            }
            .modal-footer {
                display: flex;
                gap: 12px;
                justify-content: flex-end;
                margin-top: 30px;
            }
            .btn-secondary {
                background: #9E9E9E;
                color: white;
            }
            .btn-secondary:hover {
                background: #757575;
            }
            .empty-state {
                text-align: center;
                padding: 60px 20px;
                color: #999;
            }
            .empty-state i {
                font-size: 64px;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <nav class="navbar">
            <div class="nav-container">
                <div class="nav-brand">
                    <i class='bx bx-building-house'></i>
                    <span>Sistema ONG</span>
                </div>
                <div class="nav-menu">
                    <a href="${pageContext.request.contextPath}/ServletUsuario?accion=listar" class="nav-item active">
                        <i class='bx bx-user'></i>
                        <span>Usuarios</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/ServletProyecto?accion=listar" class="nav-item">
                        <i class='bx bx-briefcase'></i>
                        <span>Proyectos</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/ServletActividad?accion=listar" class="nav-item">
                        <i class='bx bx-calendar'></i>
                        <span>Actividades</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/ServletParticipacion?accion=listar" class="nav-item">
                        <i class='bx bx-group'></i>
                        <span>Participaciones</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/ServletImpacto?accion=listar" class="nav-item">
                        <i class='bx bx-line-chart'></i>
                        <span>Impactos</span>
                    </a>
                </div>
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout">
                    <i class='bx bx-log-out'></i>
                    <span>Cerrar Sesión</span>
                </a>
            </div>
        </nav>
        <div class="container">
            <div class="header">
                <h1><i class='bx bx-user-circle'></i> Gestión de Usuarios</h1>
                <c:if test="${rol != 'Voluntario'}">
                    <div class="btn-group">
                        <button class="btn btn-primary" onclick="prepararModalAgregar()">
                            <i class='bx bx-plus-circle'></i> Nuevo Usuario
                        </button>
                    </div>
                </c:if>
            </div>

            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre Completo</th>
                            <th>Correo</th>
                            <th>Teléfono</th>
                            <th>Rol</th>
                            <th>Fecha Registro</th>
                            <c:if test="${rol != 'Voluntario'}">
                                <th>Acciones</th>
                                </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usuario" items="${listarUsuarios}">
                            <tr>
                                <td><strong>#${usuario.idUsuario}</strong></td>
                                <td>${usuario.nombre} ${usuario.apellido}</td>
                                <td>${usuario.correo}</td>
                                <td>${usuario.telefono}</td>
                                <td>
                                    <span class="badge badge-${usuario.rol.name().toLowerCase()}">${usuario.rol}</span>
                                </td>
                                <td><fmt:formatDate value="${usuario.fechaRegistro}" pattern="dd/MM/yyyy"/></td>
                                <td>
                                    <c:if test="${rol != 'Voluntario'}">
                                        <div class="action-buttons">
                                            <c:if test="${rol == 'Administrador' || rol == 'Coordinador'}">
                                                <button class="btn btn-edit"
                                                        onclick="prepararModalEditar('${usuario.idUsuario}', '${usuario.nombre}', '${usuario.apellido}', '${usuario.telefono}', '${usuario.direccion}', '${usuario.correo}', '${usuario.rol}')">
                                                    <i class='bx bx-edit'></i> Editar
                                                </button>
                                            </c:if>
                                            <c:if test="${rol == 'Administrador'}">
                                                <a href="${pageContext.request.contextPath}/ServletUsuario?accion=eliminar&id=${usuario.idUsuario}"
                                                   class="btn btn-delete"
                                                   onclick="return confirm('¿Eliminar este usuario?')">
                                                    <i class='bx bx-trash'></i> Eliminar
                                                </a>
                                            </c:if>
                                        </div>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty listarUsuarios}">
                            <tr>
                                <td colspan="7">
                                    <div class="empty-state">
                                        <i class='bx bx-user-x'></i>
                                        <p>No hay usuarios registrados</p>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="modal" id="usuarioModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 id="usuarioModalLabel">Agregar Nuevo Usuario</h2>
                </div>
                <form action="${pageContext.request.contextPath}/ServletUsuario" method="post">
                    <input type="hidden" name="accion" id="formAccion" value="agregar">
                    <input type="hidden" name="idUsuario" id="formIdUsuario" value="">

                    <div class="form-group">
                        <label for="modalNombre">Nombre</label>
                        <input type="text" name="nombre" id="modalNombre" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="modalApellido">Apellido</label>
                        <input type="text" name="apellido" id="modalApellido" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="modalCorreo">Correo Electrónico</label>
                        <input type="email" name="correo" id="modalCorreo" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="modalTelefono">Teléfono</label>
                        <input type="text" name="telefono" id="modalTelefono" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="modalDireccion">Dirección</label>
                        <input type="text" name="direccion" id="modalDireccion" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="modalContrasena">Contraseña</label>
                        <input type="password" name="contrasena" id="modalContrasena" class="form-control" required>
                        <small style="color: #999;">Deja vacío para mantener la contraseña actual</small>
                    </div>

                    <div class="form-group">
                        <label for="modalRol">Rol</label>
                        <select name="rol" id="modalRol" class="form-control" required>
                            <option value="Voluntario">Voluntario</option>
                            <option value="Administrador">Administrador</option>
                            <option value="Coordinador">Coordinador</option>
                        </select>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" onclick="cerrarModal()">Cancelar</button>
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            function prepararModalAgregar() {
                document.getElementById('usuarioModalLabel').innerText = 'Agregar Nuevo Usuario';
                document.querySelector('form').reset();
                document.getElementById('formAccion').value = 'agregar';
                document.getElementById('formIdUsuario').value = '';
                document.getElementById('modalContrasena').setAttribute('required', 'required');
                document.getElementById('usuarioModal').classList.add('show');
            }

            function prepararModalEditar(id, nombre, apellido, telefono, direccion, correo, rol) {
                document.getElementById('usuarioModalLabel').innerText = 'Editar Usuario #' + id;
                document.getElementById('formAccion').value = 'actualizar';
                document.getElementById('formIdUsuario').value = id;
                document.getElementById('modalNombre').value = nombre;
                document.getElementById('modalApellido').value = apellido;
                document.getElementById('modalTelefono').value = telefono;
                document.getElementById('modalDireccion').value = direccion;
                document.getElementById('modalCorreo').value = correo;
                document.getElementById('modalContrasena').value = '';
                document.getElementById('modalContrasena').removeAttribute('required');
                document.getElementById('modalRol').value = rol;
                document.getElementById('usuarioModal').classList.add('show');
            }

            function cerrarModal() {
                document.getElementById('usuarioModal').classList.remove('show');
            }

            window.onclick = function (event) {
                const modal = document.getElementById('usuarioModal');
                if (event.target === modal) {
                    cerrarModal();
                }
            }
        </script>
    </body>
</html>