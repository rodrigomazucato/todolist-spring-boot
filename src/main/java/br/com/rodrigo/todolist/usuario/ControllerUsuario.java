package br.com.rodrigo.todolist.usuario;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class ControllerUsuario {

    @Autowired
    private RepositoryUsuario iUserRepository;
    
    public ModelUsuario buscarUsuario(String nickName){
        var resultadoConsulta = this.iUserRepository.findByNickName(nickName).orElse(null);
        return resultadoConsulta;
    }

    public ModelUsuario buscarUsuario(UUID id){
        var usuarioArmazenado = this.iUserRepository.findById(id).orElse(null);
        return usuarioArmazenado;
    }

    private String criptografarSenha(String senha){
        var senhaCriptografada = BCrypt.withDefaults().hashToString(12, senha.toCharArray());
        return senhaCriptografada.toString();
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody ModelUsuario dadosUsuario){
        if (buscarUsuario(dadosUsuario.getNickName()) != null){
            throw new IllegalStateException("Usuário já existe");
        }
        var senhaCriptografada = criptografarSenha(dadosUsuario.getSenha());
        dadosUsuario.setSenha(senhaCriptografada);
        this.iUserRepository.save(dadosUsuario);
        String mensagem = String.format("Usuário \"%s\" registrado com sucesso!", dadosUsuario.getNickName());
        return ResponseEntity.ok().body(mensagem);        
    } 
    
}
