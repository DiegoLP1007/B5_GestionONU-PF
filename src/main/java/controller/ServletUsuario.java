package controller;

import dao.UsuarioDao;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import model.Usuario.RolUsuario;

@WebServlet(name = "ServletUsuario", urlPatterns = {"/ServletUsuario"})
public class ServletUsuario extends HttpServlet {

    private final UsuarioDao dao = new UsuarioDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null || accion.isEmpty()) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                doListarUsuarios(request, response);
                break;
            case "editar":
                doMostrarFormularioEdicion(request, response);
                break;
            case "eliminar":
                doEliminarUsuario(request, response);
                break;
            default:
                doListarUsuarios(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        switch (accion) {
            case "agregar":
                doAgregarUsuario(request, response);
                break;
            case "actualizar":
                doActualizarUsuario(request, response);
                break;
            default:
                doListarUsuarios(request, response);
        }
    }

    private void doListarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Usuario> listaUsuarios = dao.listarUsuarios();
        request.setAttribute("listarUsuarios", listaUsuarios);
        request.getRequestDispatcher("servicioUsuarios.jsp").forward(request, response);
    }

    private void doMostrarFormularioEdicion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idEditar = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuario = dao.buscarPorId(idEditar);
            request.setAttribute("usuarioEditar", usuario);
            request.getRequestDispatcher("servicioUsuarios.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            System.err.println("Error: El parámetro 'idUsuario' no es válido: " + request.getParameter("idUsuario"));
            doListarUsuarios(request, response);
        }
    }

    private void doAgregarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Usuario nuevo = new Usuario();
            nuevo.setNombre(request.getParameter("nombre"));
            nuevo.setApellido(request.getParameter("apellido"));
            nuevo.setTelefono(request.getParameter("telefono"));
            nuevo.setDireccion(request.getParameter("direccion"));
            nuevo.setCorreo(request.getParameter("correo"));
            nuevo.setContrasena(request.getParameter("contrasena"));

            String rolStr = request.getParameter("rol");
            if (rolStr != null && !rolStr.isEmpty()) {
                try {
                    Usuario.RolUsuario rol = Usuario.RolUsuario.valueOf(rolStr);
                    nuevo.setRol(rol);
                } catch (IllegalArgumentException e) {
                    System.err.println("Rol no válido: " + rolStr);
                }
            }

            dao.guardar(nuevo);

            response.sendRedirect("ServletUsuario?accion=listar");

        } catch (Exception e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("ServletUsuario?accion=listar");
        }
    }

    private void doActualizarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idActualizar = Integer.parseInt(request.getParameter("idUsuario"));
            Usuario usuarioActualizar = dao.buscarPorId(idActualizar);

            if (usuarioActualizar != null) {
                usuarioActualizar.setNombre(request.getParameter("nombre"));
                usuarioActualizar.setApellido(request.getParameter("apellido"));
                usuarioActualizar.setTelefono(request.getParameter("telefono"));
                usuarioActualizar.setDireccion(request.getParameter("direccion"));
                usuarioActualizar.setCorreo(request.getParameter("correo"));
                String nuevaContrasena = request.getParameter("contrasena");
                if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
                    usuarioActualizar.setContrasena(nuevaContrasena);
                }

                String rolStr = request.getParameter("rol");
                if (rolStr != null && !rolStr.isEmpty()) {
                    try {
                        RolUsuario rol = RolUsuario.valueOf(rolStr);
                        usuarioActualizar.setRol(rol);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error al parsear el rol: " + rolStr);
                    }
                }
                dao.actualizar(usuarioActualizar);
            }
            response.sendRedirect("ServletUsuario?accion=listar");
        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("ServletUsuario?accion=listar");
        }
    }

    private void doEliminarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
            response.sendRedirect("ServletUsuario?accion=listar");
        } catch (Exception e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            response.sendRedirect("ServletUsuario?accion=listar");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de usuarios";
    }
}
