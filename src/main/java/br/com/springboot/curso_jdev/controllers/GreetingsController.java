package br.com.springboot.curso_jdev.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev.model.Usuario;
import br.com.springboot.curso_jdev.repository.UsuarioRepository;

@RestController
public class GreetingsController {
	
	@Autowired /*IC/CD ou CDI - Injenção de dependência*/
	private UsuarioRepository usuarioRepository;
	
    @RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring boot API : " + name + "!";
    }
    
    @RequestMapping(value = "/testespring/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaTeste(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	usuarioRepository.save(usuario);
    	
    	return " Teste do springBoot " + nome;
    }
    
    
    @GetMapping(value = "listatodos") /* Primeiro método de API*/
    @ResponseBody /*Retorna os dados para o corpo da resposta*/
    public ResponseEntity<List<Usuario>> listUsuario(){
    	
    List<Usuario> usuarios = usuarioRepository.findAll(); /*Executa a consulta no banco de dados */
    
    return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /*Retorna a lista em JSON*/
    	
    }
    
    
    @PostMapping(value = "salvar") /*Mapeia a url */
    @ResponseBody /*Descrição da reposta*/
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ /*Recebe os dados para salvar*/
    	
     Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user,HttpStatus.CREATED);
    }
    
    @PutMapping(value = "atualizar") /*Mapeia a url */
    @ResponseBody /*Descrição da reposta*/
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ /*Recebe os dados para salvar*/
    	
		if (usuario.getId() == null) {

			return new ResponseEntity<String>("Id não foi informado para atualização !!!", HttpStatus.OK);

		}

		Usuario user = usuarioRepository.saveAndFlush(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "delete") /*Mapeia a url */
    @ResponseBody /*Descrição da reposta*/
    public ResponseEntity<String> delete(@RequestParam Long iduser){ /*Recebe os dados para delete*/
    	
         usuarioRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>("User deletado com sucesso",HttpStatus.OK);
    }
    
    @GetMapping(value = "buscaruserid") /*Mapeia a url */
    @ResponseBody /*Descrição da reposta*/
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name ="iduser" ) Long idUser){ /*Recebe os dados para salvar*/
    	
        Usuario usuario =  usuarioRepository.findById(idUser).get();
    	
    	return new ResponseEntity<Usuario>(usuario,HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarPorNome") /*Mapeia a url */
    @ResponseBody /*Descrição da reposta*/
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name ="name" ) String name){ /*Recebe os dados para salvar*/
    	
        List<Usuario> usuario =  usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Usuario>>(usuario,HttpStatus.OK);
    }
}
