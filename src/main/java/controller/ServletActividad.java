package controller;
 
import dao.ActividadDao;
import dao.ProyectoDao;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Actividad;
import model.Proyecto;
 
@WebServlet(name = "ServletActividad", urlPatterns = {"/ServletActividad"})
public class ServletActividad extends HttpServlet {
 
    private final ActividadDao actividadDao = new ActividadDao();
    private final ProyectoDao proyectoDao = new ProyectoDao();
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null || accion.isEmpty()) {
            accion = "listar";
        }
 
        switch (accion) {
            case "listar":
                doListarActividades(request, response);
                break;
            case "editar":
                doMostrarFormularioEdicion(request, response);
                break;
            case "eliminar":
                doEliminarActividad(request, response);
                break;
            default:
                doListarActividades(request, response);
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
 
        switch (accion) {
            case "agregar":
                doAgregarActividad(request, response);
                break;
            case "actualizar":
                doActualizarActividad(request, response);
                break;
            default:
                doListarActividades(request, response);
        }
    }
 
    private void doListarActividades(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        List<Actividad> lista = actividadDao.listarTodos();
        List<Proyecto> proyectos = proyectoDao.listarTodos();
 
        request.setAttribute("listarActividades", lista);
        request.setAttribute("listarProyectos", proyectos);
 
        request.getRequestDispatcher("servicioActividades.jsp").forward(request, response);
    }
 
    private void doMostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idEditar = Integer.parseInt(request.getParameter("idActividad"));
            Actividad actividad = actividadDao.buscarPorId(idEditar);
            request.setAttribute("actividadEditar", actividad);
            doListarActividades(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Error: El parámetro 'idActividad' no es válido: " + request.getParameter("idActividad"));
            doListarActividades(request, response);
        }
    }
 
    private void doAgregarActividad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error = null;
        try {
            int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
            Proyecto proyecto = proyectoDao.buscarPorId(idProyecto);
 
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String lugar = request.getParameter("lugar");
            Date fecha = Date.valueOf(request.getParameter("fecha"));
            int capacidad = Integer.parseInt(request.getParameter("capacidad"));
 
            if (proyecto == null) {
                error = "Error: El proyecto seleccionado no existe.";
            } else {
                Actividad actividad = new Actividad();
                actividad.setProyecto(proyecto);
                actividad.setNombre(nombre);
                actividad.setDescripcion(descripcion);
                actividad.setLugar(lugar);
                actividad.setFecha(fecha);
                actividad.setCapacidad(capacidad);
 
                actividadDao.guardar(actividad);
                response.sendRedirect("ServletActividad?accion=listar");
                return;
            }
 
        } catch (NumberFormatException e) {
            error = "Error de formato: verifique los campos numéricos.";
            System.err.println("Error de formato al agregar actividad: " + e.getMessage());
        } catch (Exception e) {
            error = "Error interno al guardar la actividad. Consulte los logs del servidor.";
            System.err.println("Error al agregar actividad: " + e.getMessage());
        }
 
        if (error != null) {
            request.setAttribute("errorAgregar", error);
        }
        doListarActividades(request, response);
    }
 
    private void doActualizarActividad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idActualizar = Integer.parseInt(request.getParameter("idActividad"));
            Actividad actividad = actividadDao.buscarPorId(idActualizar);
 
            if (actividad != null) {
                int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                Proyecto proyecto = proyectoDao.buscarPorId(idProyecto);
 
                String nombre = request.getParameter("nombre");
                String descripcion = request.getParameter("descripcion");
                String lugar = request.getParameter("lugar");
                Date fecha = Date.valueOf(request.getParameter("fecha"));
                int capacidad = Integer.parseInt(request.getParameter("capacidad"));
 
                actividad.setProyecto(proyecto);
                actividad.setNombre(nombre);
                actividad.setDescripcion(descripcion);
                actividad.setLugar(lugar);
                actividad.setFecha(fecha);
                actividad.setCapacidad(capacidad);
 
                actividadDao.actualizar(actividad);
            }
            response.sendRedirect("ServletActividad?accion=listar");
        } catch (Exception e) {
            System.err.println("Error al actualizar actividad: " + e.getMessage());
            response.sendRedirect("ServletActividad?accion=listar");
        }
    }
 
    private void doEliminarActividad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            actividadDao.eliminar(id);
            response.sendRedirect("ServletActividad?accion=listar");
        } catch (Exception e) {
            System.err.println("Error al eliminar actividad: " + e.getMessage());
            response.sendRedirect("ServletActividad?accion=listar");
        }
    }
 
    @Override
    public String getServletInfo() {
        return "Servlet para gestión de actividades";
    }
}