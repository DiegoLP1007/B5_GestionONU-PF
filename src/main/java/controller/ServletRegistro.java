package controller;

import dao.UsuarioDao;
import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;

@WebServlet("/guardarUsuario")
public class ServletRegistro extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String nombre = request.getParameter("nombreUsuario");
        String apellido = request.getParameter("apellidoUsuario");
        String direccion = request.getParameter("direccionUsuario");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        request.setAttribute("nombreUsuario", nombre);
        request.setAttribute("apellidoUsuario", apellido);
        request.setAttribute("direccionUsuario", direccion);
        request.setAttribute("telefono", telefono);
        request.setAttribute("email", correo);

        if (!contrasenaValida(contrasena)) {
            request.setAttribute("error", "Contraseña débil: debe tener al menos 8 caracteres, "
                    + "una letra mayúscula, un número y un carácter especial.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        UsuarioDao dao = new UsuarioDao();

        if (dao.erorEmail(correo)) {
            request.setAttribute("error", "El correo electrónico ya está registrado.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setDireccion(direccion);
        usuario.setTelefono(telefono);
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);
        usuario.setRol(Usuario.RolUsuario.Voluntario);
        usuario.setFechaRegistro(new Timestamp(System.currentTimeMillis()));

        try {
            dao.guardar(usuario);
            request.setAttribute("confirma", "Usuario registrado correctamente. Ahora puedes iniciar sesión.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al registrar el usuario.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private boolean contrasenaValida(String contrasena) {
        String patron = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.,_\\-])[A-Za-z\\d@$!%*?&.,_\\-]{8,}$";
        return contrasena != null && contrasena.matches(patron);
    }
}
