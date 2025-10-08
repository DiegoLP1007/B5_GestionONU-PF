package controller;

import dao.ActividadDao;
import dao.ParticipacionDao;
import dao.ProyectoDao;
import dao.UsuarioDao;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Actividad;
import model.Participacion;
import model.Proyecto;
import model.Usuario;

@WebServlet(name = "ServletParticipacion", urlPatterns = {"/ServletParticipacion"})
public class ServletParticipacion extends HttpServlet {

    private final ParticipacionDao dao = new ParticipacionDao();
    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final ActividadDao actividadDao = new ActividadDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null || accion.isEmpty()) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                doListarParticipaciones(request, response);
                break;
            case "editar":
                doMostrarFormularioEdicion(request, response);
                break;
            case "eliminar":
                doEliminarParticipacion(request, response);
                break;
            default:
                doListarParticipaciones(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        switch (accion) {
            case "agregar":
                doAgregarParticipacion(request, response);
                break;
            case "actualizar":
                doActualizarParticipacion(request, response);
                break;
            default:
                doListarParticipaciones(request, response);
        }
    }

    private void doListarParticipaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Participacion> lista = dao.listarTodos();
        List<Usuario> usuarios = usuarioDao.listarUsuarios();
        List<Actividad> actividades = actividadDao.listarTodos();

        System.out.println("Usuarios encontrados: " + usuarios.size());
        System.out.println("Proyectos encontrados: " + actividades.size());

        request.setAttribute("listarParticipaciones", lista);
        request.setAttribute("listarUsuarios", usuarios);
        request.setAttribute("listarActividades", actividades);

        request.getRequestDispatcher("servicioParticipaciones.jsp").forward(request, response);
    }

    private void doMostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idEditar = Integer.parseInt(request.getParameter("idParticipacion"));
            Participacion participacion = dao.buscarPorId(idEditar);
            request.setAttribute("participacionEditar", participacion);
            doListarParticipaciones(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Error: El parámetro 'idParticipacion' no es válido: " + request.getParameter("idParticipacion"));
            doListarParticipaciones(request, response);
        }
    }

    private void doAgregarParticipacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String error = null;
        try {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            int idActividad = Integer.parseInt(request.getParameter("idActividad"));
            Double horas = Double.parseDouble(request.getParameter("horasTrabajadas"));

            Usuario usuario = usuarioDao.buscarPorId(idUsuario);
            Actividad actividad = actividadDao.buscarPorId(idActividad);

            if (usuario == null) {
                error = "Error: El usuario seleccionado no existe en la base de datos.";
            } else if (actividad == null) {
                error = "Error: El proyecto seleccionado no existe en la base de datos.";
            } else if (actividad.getCapacidad() <= 0) {
                error = "Error: No hay capacidad disponible para esta actividad.";
            } else {
                Participacion participacion = new Participacion();
                participacion.setUsuario(usuario);
                participacion.setActividad(actividad);
                participacion.setHorasTrabajadas(horas);
                participacion.setFechaRegistro(new Timestamp(System.currentTimeMillis()));

                actividad.setCapacidad(actividad.getCapacidad() - 1);
                actividadDao.actualizar(actividad); 

                dao.guardar(participacion);
                response.sendRedirect("ServletParticipacion?accion=listar");
                return;
            }

        } catch (NumberFormatException e) {
            error = "Error de formato: Asegúrese de que las horas trabajadas sean un número válido.";
            System.err.println("Error de formato al agregar participación: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            error = "Error interno al guardar la participación. Consulte los logs del servidor.";
            System.err.println("Error al agregar participación: " + e.getMessage());
            e.printStackTrace();
        }
        if (error != null) {
            request.setAttribute("errorAgregar", error);
        }
        doListarParticipaciones(request, response);
    }

    private void doActualizarParticipacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idActualizar = Integer.parseInt(request.getParameter("idParticipacion"));
            Participacion participacion = dao.buscarPorId(idActualizar);

            if (participacion != null) {
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                int idActividad = Integer.parseInt(request.getParameter("idActividad"));
                Double horas = Double.parseDouble(request.getParameter("horasTrabajadas"));

                Usuario usuario = usuarioDao.buscarPorId(idUsuario);
                Actividad actividadNueva = actividadDao.buscarPorId(idActividad);
                Actividad actividadAnterior = participacion.getActividad();

                if (actividadNueva != null) {
                    if (!actividadAnterior.equals(actividadNueva)) {
                        actividadAnterior.setCapacidad(actividadAnterior.getCapacidad() + 1);
                        actividadDao.actualizar(actividadAnterior);

                        if (actividadNueva.getCapacidad() > 0) {
                            actividadNueva.setCapacidad(actividadNueva.getCapacidad() - 1);
                            actividadDao.actualizar(actividadNueva); 
                        } else {
                            request.setAttribute("errorAgregar", "No hay capacidad disponible para la nueva actividad.");
                            doListarParticipaciones(request, response);
                            return;
                        }
                    }

                    participacion.setUsuario(usuario);
                    participacion.setActividad(actividadNueva);
                    participacion.setHorasTrabajadas(horas);

                    dao.actualizar(participacion);
                }

                response.sendRedirect("ServletParticipacion?accion=listar");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar participación: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("ServletParticipacion?accion=listar");
        }
    }

    private void doEliminarParticipacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Participacion participacion = dao.buscarPorId(id);

            if (participacion != null) {
                Actividad actividad = participacion.getActividad();
                actividad.setCapacidad(actividad.getCapacidad() + 1);
                actividadDao.actualizar(actividad); 
                dao.eliminar(id);  
            }

            response.sendRedirect("ServletParticipacion?accion=listar");
        } catch (Exception e) {
            System.err.println("Error al eliminar participación: " + e.getMessage());
            response.sendRedirect("ServletParticipacion?accion=listar");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de participaciones";
    }
}
