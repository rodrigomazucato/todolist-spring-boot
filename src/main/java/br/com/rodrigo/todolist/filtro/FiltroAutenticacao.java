package br.com.rodrigo.todolist.filtro;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rodrigo.todolist.usuario.ControllerUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroAutenticacao extends OncePerRequestFilter {

    @Autowired
    private ControllerUsuario controllerUsuario;

    private String[] obterCredenciais(HttpServletRequest request) {
        var dadosCodificados = request.getHeader("Authorization").replace("Basic ", "");
        var dadosDecodificados = Base64.getDecoder().decode(dadosCodificados);
        var credenciais = new String(dadosDecodificados).split(":");
        return credenciais;
    }
    
    private void validarSenha(String senhaEntrada, String senhaArmazenada, HttpServletResponse response) throws IOException{
        var resultado = BCrypt.verifyer().verify(senhaEntrada.toCharArray(), senhaArmazenada);
        if (!resultado.verified){
            response.sendError(401, "Senha incorreta");
        }
    }

    private void autenticarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var credenciais = obterCredenciais(request);
        var nickName = credenciais[0];
        var senhaEntrada = credenciais[1];
        var dadosUsuario = controllerUsuario.buscarUsuario(nickName);

        if (dadosUsuario == null) {
            response.sendError(401, "Usuário não cadastrado");
            return;
        }

        validarSenha(senhaEntrada, dadosUsuario.getSenha(), response);
        request.setAttribute("idUsuario", dadosUsuario.getId());       
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getServletPath().startsWith("/tasks")){
            autenticarUsuario(request, response);
        }
        filterChain.doFilter(request, response);
    }
}