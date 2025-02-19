package br.com.rodrigo.todolist.tarefa;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodrigo.todolist.usuario.ControllerUsuario;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class ControllerTarefa {

    @Autowired
    private RepositoryTarefa taskRepository;

    @Autowired
    private ControllerUsuario controllerUsuario;

    private void validarDatas(LocalDateTime inicio, LocalDateTime termino){
        var dataAtual = LocalDateTime.now();
        if (inicio.isBefore(dataAtual) || termino.isBefore(dataAtual)){
            throw new IllegalArgumentException("A data de início / término não pode ser anterior à data atual!");
        }
        if (inicio.isAfter(termino)){
            throw new IllegalArgumentException("A data de término deve ser posterior à data de início");
        }
    }

    private void verificarPermissaoUsuario(ModelTarefa tarefaArmazenada, HttpServletRequest request) {
        var idUsuarioEntrada = (UUID) request.getAttribute("idUsuario");
        var idUsuarioArmazenado = tarefaArmazenada.getIdUsuario();

        if (!idUsuarioEntrada.equals(idUsuarioArmazenado)) {
            var nomeUsuario = this.controllerUsuario.buscarUsuario(idUsuarioEntrada).getNickName();
            String mensagem = String.format("Usuário \"%s\" não tem permissão para acessar essa tarefa!", nomeUsuario);
            throw new SecurityException(mensagem);
        }
    }

    public ModelTarefa buscarTarefa(UUID idTarefa){
        var tarefaArmazenada = taskRepository.findById(idTarefa).orElse(null);
        if (tarefaArmazenada == null) {
            throw new IllegalArgumentException("Tarefa não encontrada!");
        }
        return tarefaArmazenada;
    }

    @GetMapping
    public ModelTarefa[] listarTarefas(HttpServletRequest request){
        var idUser = request.getAttribute("idUsuario");
        var tarefas = this.taskRepository.findByIdUsuario((UUID) idUser);
        return tarefas;
    }

    @PostMapping
    public ResponseEntity<String> criarTarefa(@RequestBody ModelTarefa dadosTarefa, HttpServletRequest request) {
        var idUser = request.getAttribute("idUsuario");
        dadosTarefa.setIdUsuario((UUID) idUser); // Define o id do usuário automaticamente
        validarDatas(dadosTarefa.getDataInicio(), dadosTarefa.getDataTermino());
        taskRepository.save(dadosTarefa);
        String mensagem = String.format("Tarefa \"%s\" cadastrada com sucesso!", dadosTarefa.getTitulo());
        return ResponseEntity.ok().body(mensagem);
    }

    @PatchMapping("/{idTarefa}")
    public ResponseEntity<String> atualizarTarefa(@RequestBody Map<String, Object> dadosTarefa, @PathVariable UUID idTarefa, HttpServletRequest request){
        var tarefaArmazenada = buscarTarefa(idTarefa);
        verificarPermissaoUsuario(tarefaArmazenada, request);
        BeanWrapper beanWrapper = new BeanWrapperImpl(tarefaArmazenada);
        dadosTarefa.forEach((key, value) -> {
            if (!beanWrapper.isWritableProperty(key)){
                var mensagem = String.format("Chave \"%s\" não cadastrada!", key);
                throw new IllegalArgumentException(mensagem);
            }
            beanWrapper.setPropertyValue(key, value);
        });
        taskRepository.save(tarefaArmazenada);
        return ResponseEntity.ok().body("Tarefa atualizada com sucesso!");
    }
    
    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<String> deletarTarefa(@PathVariable UUID idTarefa, HttpServletRequest request){
        var tarefaArmazenada = buscarTarefa(idTarefa);
        verificarPermissaoUsuario(tarefaArmazenada, request);
        this.taskRepository.delete(tarefaArmazenada);
        return ResponseEntity.ok().body("Tarefa deletada com sucesso!");
        
    }
}
