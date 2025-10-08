package controller;
 
import dao.ImpactoDao;
import dao.ProyectoDao;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Impacto;
import model.Proyecto;
 
@WebServlet(name = "ServletImpacto", urlPatterns = {"/ServletImpacto"})
public class ServletImpacto extends HttpServlet {
 
    private final ImpactoDao impactoDao = new ImpactoDao();
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
                doListarImpactos(request, response);
                break;
            case "editar":
                doMostrarFormularioEdicion(request, response);
                break;
            case "eliminar":
                doEliminarImpacto(request, response);
                break;
            default:
                doListarImpactos(request, response);
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
 
        switch (accion) {
            case "agregar":
                doAgregarImpacto(request, response);
                break;
            case "actualizar":
                doActualizarImpacto(request, response);
                break;
            default:
                doListarImpactos(request, response);
        }
    }
 
    private void doListarImpactos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        List<Impacto> lista = impactoDao.listarImpactos();
        List<Proyecto> proyectos = proyectoDao.listarTodos();
 
        request.setAttribute("listarImpactos", lista);
        request.setAttribute("listarProyectos", proyectos);
 
        request.getRequestDispatcher("servicioImpactos.jsp").forward(request, response);
    }
 
    private void doMostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idEditar = Integer.parseInt(request.getParameter("idImpacto"));
            Impacto impacto = impactoDao.buscarPorId(idEditar);
            request.setAttribute("impactoEditar", impacto);
            doListarImpactos(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Error: El parámetro 'idImpacto' no es válido: " + request.getParameter("idImpacto"));
            doListarImpactos(request, response);
        }
    }
 
    private void doAgregarImpacto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error = null;
        try {
            int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
            Proyecto proyecto = proyectoDao.buscarPorId(idProyecto);
 
            String descripcion = request.getParameter("descripcion");
            int participantes = Integer.parseInt(request.getParameter("participantes"));
            Double horasTotales = Double.parseDouble(request.getParameter("horasTotales"));
            String resultados = request.getParameter("resultados");
            Date fecha = Date.valueOf(request.getParameter("fecha"));
 
            if (proyecto == null) {
                error = "Error: El proyecto seleccionado no existe.";
            } else {
                Impacto impacto = new Impacto();
                impacto.setProyecto(proyecto);
                impacto.setDescripcion(descripcion);
                impacto.setParticipantes(participantes);
                impacto.setHorasTotales(horasTotales);
                impacto.setResultados(resultados);
                impacto.setFecha(fecha);
 
                impactoDao.guardar(impacto);
                response.sendRedirect("ServletImpacto?accion=listar");
                return;
            }
 
        } catch (NumberFormatException e) {
            error = "Error de formato: verifique los campos numéricos.";
            System.err.println("Error de formato al agregar impacto: " + e.getMessage());
        } catch (Exception e) {
            error = "Error interno al guardar el impacto. Consulte los logs del servidor.";
            System.err.println("Error al agregar impacto: " + e.getMessage());
        }
 
        if (error != null) {
            request.setAttribute("errorAgregar", error);
        }
        doListarImpactos(request, response);
    }
 
    private void doActualizarImpacto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idActualizar = Integer.parseInt(request.getParameter("idImpacto"));
            Impacto impacto = impactoDao.buscarPorId(idActualizar);
 
            if (impacto != null) {
                int idProyecto = Integer.parseInt(request.getParameter("idProyecto"));
                Proyecto proyecto = proyectoDao.buscarPorId(idProyecto);
 
                String descripcion = request.getParameter("descripcion");
                int participantes = Integer.parseInt(request.getParameter("participantes"));
                Double horasTotales = Double.parseDouble(request.getParameter("horasTotales"));
                String resultados = request.getParameter("resultados");
                Date fecha = Date.valueOf(request.getParameter("fecha"));
 
                impacto.setProyecto(proyecto);
                impacto.setDescripcion(descripcion);
                impacto.setParticipantes(participantes);
                impacto.setHorasTotales(horasTotales);
                impacto.setResultados(resultados);
                impacto.setFecha(fecha);
 
                impactoDao.actualizar(impacto);
            }
            response.sendRedirect("ServletImpacto?accion=listar");
        } catch (Exception e) {
            System.err.println("Error al actualizar impacto: " + e.getMessage());
            response.sendRedirect("ServletImpacto?accion=listar");
        }
    }
 
    private void doEliminarImpacto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            impactoDao.eliminar(id);
            response.sendRedirect("ServletImpacto?accion=listar");
        } catch (Exception e) {
            System.err.println("Error al eliminar impacto: " + e.getMessage());
            response.sendRedirect("ServletImpacto?accion=listar");
        }
    }
 
    @Override
    public String getServletInfo() {
        return "Servlet para gestión de impactos";
    }
}