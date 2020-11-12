package com.spring.login.controller;

import java.io.IOException;
import java.util.ArrayList;



import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import com.spring.login.entity.User;
import com.spring.login.repository.UserRepository;


/* i Model (in italiano Modelli) si occupano di accedere ai dati necessari alla 
 * logica di business implementata nell’applicazione (nel caso di un’applicazione
 * per una biblioteca potrebbero essere le classi Libro, Autore, Scaffale);
 * 
 * i Model sono rappresentati dalle classi che a loro volta rappresentano 
 * gli oggetti gestiti e le classi di accesso al database; -> User.java
 */

/* le View (in italiano Viste) si occupano di creare l’interfaccia utilizzabile 
 * dall’utente e che espone i dati da esso richiesti (nel caso bibliotecario 
 * potrebbero essere le pagine HTML del catalogo, le form di ricerca oppure i 
 * PDF contenenti ricerche o appunti);
 * 
 * le View sono rappresentate dai vari file JSP (che vengono compilati in HTML)
 * e da eventuali classi per l’esportazione in formati diversi da HTML (PDF, XLS, CSV…);
 */

/* i Controller (in italiano Controllori) si occupano di implementare la vera
 *  logica di business dell’applicazione integrando le due componenti precedenti,
 *  ricevendo gli input dell’utente, gestendo i modelli per la ricerca dei dati
 *  e la creazione di viste da restituire all’utente (nel nostro caso potrebbero 
 *  essere il motore di ricerca interno oppure il sistema di login al sito 
 *  Internet della biblioteca).
 *  
 *  i Controller sono rappresentati da classi (chiamate appositamante Controller)
 *  che rimangono “in ascolto” su un determinato URL e, grazie ai Model e alle View, 
 *  si occupano di gestire la richiesta dell’utente.
 */

/* 
 * Il tipo di bean che si occupa del mapping tra url invocato dall’utente 
 * e metodo Java da invocare è l’handlerMapping
 * @controller => Da utilizzare a livello di classe per identificarla come controller
 * @requestMapping => Da utilizzare per evidenziare il metodo e l’url da mappare
 * 
 * Per recuperare i valori dei parametri inviati alla pagina possiamo utilizzare
 * l’annotation @RequestParam 
 */

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

// -------------------------------------------- /home --------------------------------------------------
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	// creo all'interno del controller un metodo home che risponde all’url /home o /
	public ModelAndView home(HttpServletResponse response, @ModelAttribute User user) throws IOException {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home"); // e' possibile utilizzare il metodo setViewName() 
								// per dire in quale pagina voglio andare
		
		return mv;
	}

// -------------------------------------------- /addNewUser --------------------------------------------------
	
	@RequestMapping(value = "/addNewUser", method = RequestMethod.GET)
	public ModelAndView displayNewUserForm() {
		
		ModelAndView mv = new ModelAndView("addUser");
		mv.addObject("headerMessage", "Sing up");
		mv.addObject("subHeader", "It’s quick and easy");
		mv.addObject("user", new User());
		return mv;
	}

	@RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
	public ModelAndView saveNewUser(@ModelAttribute User user) {
		
		ModelAndView mv = new ModelAndView("home");
		System.out.println("STAMPAMI NOME " + user.getName() + " E COGNOME " + user.getSurname());
		
		// con save salvo l'utente nel db se non c'è l'id
		// se c'è l'id è una update dell'utente nel db
		User userCheck = userRepository.save(user);
		
		if (userCheck != null) {
			mv.addObject("headerMessage", "New user added successfully");
			mv.addObject("message","Welcome " + userCheck.getName() + ", you can now loggin in:");
		} else {
			return new ModelAndView("error");
		}
		return mv;
	}
	
// -------------------------------------------- /login --------------------------------------------------

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginProfile(@ModelAttribute User user) {
		
		ModelAndView mv = new ModelAndView("profile");
		System.out.println(" ----------------> Login di " + user.getEmail() + " " + user.getPassword());
		User userCheck = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if (userCheck != null) {
			
			// ---------------------- SESSION -----------------------
			/*
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();
            HttpSession session = request.getSession(true);
            session.setAttribute("utenteOnline", userCheck);
            */
            // ---------------------- SESSION -----------------------
            
            //in questo modo gli ritorno l'utente trovato
			mv.addObject("userLogged", userCheck);
			mv.addObject("showThem", false);
			mv.addObject("inProfile",false);
			return mv;
		} else {
			return new ModelAndView("error loginProfile");
		}
	} 

//-------------------------------------------- /findAll --------------------------------------------------

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public ModelAndView findAllUsers() {
		
		ModelAndView mv = new ModelAndView("profile");
		ArrayList<User> usersList = (ArrayList<User>) userRepository.findAll();
		
		//in questo modo gli ritorno tutti gli utenti trovati
		mv.addObject("usersList", usersList);
		mv.addObject("showThem", true);
		
		// nell'elaborazione della tabella il model attribute memorizza per ogni utente il suo ID
		// (e lo passa al controller quando avviene richiesta delete/update
		mv.addObject("user", new User());
		
		return mv;
	} 

//-------------------------------------------- /delete --------------------------------------------------

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ModelAndView deleteUser(@PathVariable("id") int id) {
		
		userRepository.deleteById(id);

		ArrayList <User> usersList = (ArrayList<User>) userRepository.findAll();	
		 
		ModelAndView mv = new ModelAndView("profile");
		mv.addObject("usersList", usersList);
		mv.addObject("user", new User());
		mv.addObject("showThem", true);
		
		return mv;
	}
	
//-------------------------------------------- /update --------------------------------------------------
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public ModelAndView editUser (@PathVariable("id") int id) {
		
		ModelAndView mv = new ModelAndView("update");
		// qui l'user (model attribute ha solo l'ID)
		User userToUpdate = userRepository.findById(id).get();
		mv.addObject("userToUpdate", userToUpdate);
		return mv;
	}
	
	@RequestMapping(value = "/updateOldUser", method = RequestMethod.POST)
	public ModelAndView updateUser(@ModelAttribute User user) {
		System.out.println(" ----------------> Qui arrivos");
		ModelAndView mv = new ModelAndView("profile");
		User userCheck = userRepository.save(user);
		System.out.println(" ----------------> Qui arrivo");
		mv.addObject("userCheck", userCheck);
		mv.addObject("user", new User());
		mv.addObject("showThem", false);
		return mv;
	}
}


