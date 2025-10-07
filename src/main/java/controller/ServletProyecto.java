package controller;

import dao.ProyectoDao;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Proyecto;
import model.Proyecto.EstadoProyecto;

@WebServlet(name = "ServletProyecto", urlPatterns = {"/ServletProyecto"})
public class ServletProyecto extends HttpServlet {

    private final ProyectoDao dao = new ProyectoDao();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null || accion.isEmpty()) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                doListarProyectos(request, response);
                break;
            case "editar":
                doMostrarFormularioEdicion(request, response);
                break;
            case "eliminar":
                doEliminarProyecto(request, response);
                break;
            default:
                doListarProyectos(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion) {
            case "agregar":
                doAgregarProyecto(request, response);
                break;
            case "actualizar":
                doActualizarProyecto(request, response);
                break;
            default:
                doListarProyectos(request, response);
        }
    }

    private void doListarProyectos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Proyecto> listaProyectos = dao.listarTodos();
        request.setAttribute("listarProyectos", listaProyectos);
        request.getRequestDispatcher("servicioProyectos.jsp").forward(request, response);
    }

    private void doMostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idEditar = Integer.parseInt(request.getParameter("idProyecto"));
            Proyecto proyecto = dao.buscarPorId(idEditar);
            request.setAttribute("proyectoEditar", proyecto);
            request.getRequestDispatcher("servicioProyectos.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Error: El parámetro 'idProyecto' no es válido: " + request.getParameter("idProyecto"));
            doListarProyectos(request, response);
        }
    }

    private void doAgregarProyecto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Proyecto nuevo = new Proyecto();
            nuevo.setNombre(request.getParameter("nombre"));
            nuevo.setDescripcion(request.getParameter("descripcion"));
            nuevo.setFechaInicio(parseFecha(request.getParameter("fechaInicio")));
            nuevo.setFechaFin(parseFecha(request.getParameter("fechaFin")));

            String estadoStr = request.getParameter("estado");
            if (estadoStr != null && !estadoStr.isEmpty()) {
                try {
                    EstadoProyecto estado = EstadoProyecto.valueOf(estadoStr);
                    nuevo.setEstado(estado);
                } catch (IllegalArgumentException e) {
                    System.err.println("Estado no válido: " + estadoStr);
                }
            }

            dao.guardar(nuevo);
            response.sendRedirect("ServletProyecto?accion=listar");

        } catch (Exception e) {
            System.err.println("Error al agregar proyecto: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("ServletProyecto?accion=listar");
        }
    }

    private void doActualizarProyecto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idActualizar = Integer.parseInt(request.getParameter("idProyecto"));
            Proyecto proyectoActualizar = dao.buscarPorId(idActualizar);

            if (proyectoActualizar != null) {
                proyectoActualizar.setNombre(request.getParameter("nombre"));
                proyectoActualizar.setDescripcion(request.getParameter("descripcion"));
                proyectoActualizar.setFechaInicio(parseFecha(request.getParameter("fechaInicio")));
                proyectoActualizar.setFechaFin(parseFecha(request.getParameter("fechaFin")));

                String estadoStr = request.getParameter("estado");
                if (estadoStr != null && !estadoStr.isEmpty()) {
                    try {
                        EstadoProyecto estado = EstadoProyecto.valueOf(estadoStr);
                        proyectoActualizar.setEstado(estado);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Estado no válido: " + estadoStr);
                    }
                }

                dao.actualizarProyecto(proyectoActualizar);
            }

            response.sendRedirect("ServletProyecto?accion=listar");

        } catch (Exception e) {
            System.err.println("Error al actualizar proyecto: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("ServletProyecto?accion=listar");
        }
    }

    private void doEliminarProyecto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
            response.sendRedirect("ServletProyecto?accion=listar");
        } catch (Exception e) {
            System.err.println("Error al eliminar proyecto: " + e.getMessage());
            response.sendRedirect("ServletProyecto?accion=listar");
        }
    }

    private Date parseFecha(String fechaStr) {
        try {
            if (fechaStr != null && !fechaStr.isEmpty()) {
                return sdf.parse(fechaStr);
            }
        } catch (ParseException e) {
            System.err.println("Error al parsear fecha: " + fechaStr);
        }
        return null;
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de proyectos";
    }
}
