<%@ page import="java.util.List" %>
<%@ page import="model.Proyecto" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Gestión de Proyectos</title>
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
                padding: 20px;
            }
            .container {
                max-width: 1400px;
                margin: 0 auto;
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
            .btn-report {
                background: #4CAF50;
                color: white;
            }
            .btn-report:hover {
                background: #45a049;
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
        <div class="container">
            <div class="header">
                <h1><i class='bx bx-briefcase'></i> Gestión de Proyectos</h1>
                <div class="btn-group">
                    <button class="btn btn-primary" onclick="prepararModalAgregar()">
                        <i class='bx bx-plus-circle'></i> Nuevo Proyecto
                    </button>
                </div>
            </div>

            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th>Fecha Inicio</th>
                            <th>Fecha Fin</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="proyecto" items="${listarProyectos}">
                            <tr>
                                <td><strong>#${proyecto.idProyecto}</strong></td>
                                <td>${proyecto.nombre}</td>
                                <td>${proyecto.descripcion}</td>
                                <td><fmt:formatDate value="${proyecto.fechaInicio}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${proyecto.fechaFin}" pattern="yyyy-MM-dd"/></td>
                                <td>${proyecto.estado}</td>
                                <td>
                                    <div class="action-buttons">
                                        <button class="btn btn-edit"
                                                onclick="prepararModalEditar('${proyecto.idProyecto}', '${proyecto.nombre}', '${proyecto.descripcion}', '${proyecto.fechaInicio}', '${proyecto.fechaFin}', '${proyecto.estado}')">
                                            <i class='bx bx-edit'></i> Editar
                                        </button>
                                        <a href="${pageContext.request.contextPath}/ServletProyecto?accion=eliminar&id=${proyecto.idProyecto}" 
                                           class="btn btn-delete" 
                                           onclick="return confirm('¿Eliminar este proyecto?')">
                                            <i class='bx bx-trash'></i> Eliminar
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty listarProyectos}">
                            <tr>
                                <td colspan="7">
                                    <div class="empty-state">
                                        <i class='bx bx-briefcase-alt-2'></i>
                                        <p>No hay proyectos registrados</p>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal" id="proyectoModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 id="proyectoModalLabel">Agregar Nuevo Proyecto</h2>
                </div>
                <form action="${pageContext.request.contextPath}/ServletProyecto" method="post">
                    <input type="hidden" name="accion" id="formAccion" value="agregar">
                    <input type="hidden" name="idProyecto" id="formIdProyecto" value="">

                    <div class="form-group">
                        <label for="modalNombre">Nombre</label>
                        <input type="text" name="nombre" id="modalNombre" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="modalDescripcion">Descripción</label>
                        <textarea name="descripcion" id="modalDescripcion" class="form-control" required></textarea>
                    </div>

                    <div class="form-group">
                        <label for="modalFechaInicio">Fecha Inicio</label>
                        <input type="date" name="fechaInicio" id="modalFechaInicio" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="modalFechaFin">Fecha Fin</label>
                        <input type="date" name="fechaFin" id="modalFechaFin" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label for="modalEstado">Estado</label>
                        <select name="estado" id="modalEstado" class="form-control" required>
                            <option value="Activo">Activo</option>
                            <option value="Finalizado">Finalizado</option>
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
                document.getElementById('proyectoModalLabel').innerText = 'Agregar Nuevo Proyecto';
                document.querySelector('form').reset();
                document.getElementById('formAccion').value = 'agregar';
                document.getElementById('formIdProyecto').value = '';
                document.getElementById('proyectoModal').classList.add('show');
            }

            function prepararModalEditar(id, nombre, descripcion, fechaInicio, fechaFin, estado) {
                document.getElementById('proyectoModalLabel').innerText = 'Editar Proyecto #' + id;
                document.getElementById('formAccion').value = 'actualizar';
                document.getElementById('formIdProyecto').value = id;
                document.getElementById('modalNombre').value = nombre;
                document.getElementById('modalDescripcion').value = descripcion;
                document.getElementById('modalFechaInicio').value = fechaInicio;
                document.getElementById('modalFechaFin').value = fechaFin;
                document.getElementById('modalEstado').value = estado;
                document.getElementById('proyectoModal').classList.add('show');
            }

            function cerrarModal() {
                document.getElementById('proyectoModal').classList.remove('show');
            }

            window.onclick = function (event) {
                const modal = document.getElementById('proyectoModal');
                if (event.target === modal) {
                    cerrarModal();
                }
            }
        </script>
    </body>
</html>
