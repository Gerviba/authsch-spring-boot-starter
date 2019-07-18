package hu.bme.sch.auth.authsch.login;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hu.bme.sch.auth.authsch.exception.AuthschAuthenticationFailedException;
import hu.bme.sch.auth.authsch.exception.AuthschInvalidStateException;
import hu.bme.sch.auth.authsch.exception.AuthschResponseException;

@Controller
@ConditionalOnProperty(
		name = "hu.bme.sch.auth.default-controller.enabled", 
		matchIfMissing = true)
public class AuthschDefaultController {

	@Autowired
	private AuthschLoginService login;
	
	@Autowired
	private AuthschLoginConfig config;
	
	Logger logger = LoggerFactory.getLogger(AuthschDefaultController.class);
	
	@PostConstruct
	public void init() {
		logger.info("Enabled default login controller");
	}
	
	@GetMapping("${hu.bme.sch.auth.default-controller.webhook-endpoint:/redirect}")
	public String redirectWebhook(@RequestParam String code, @RequestParam String state, HttpServletRequest request) {
		try {
			if (login.handleLoginHook(code, state, request)) {
				return "redirect:" + config.getLoginOk();
			} else {
				return "redirect:" + config.getLoginFailed();
			}
		} catch (AuthschResponseException | AuthschInvalidStateException e) {
			return "redirect:" + login.generateLoginRedirectUrl(request);
		} catch (AuthschAuthenticationFailedException e) {
			return "redirect:" + config.getUnexpectedError();
		}
	}
	
	@GetMapping("${hu.bme.sch.auth.default-controller.login-endpoint:/login}")
	public String login(HttpServletRequest request) {
		return "redirect:" + login.generateLoginRedirectUrl(request);
	}
	
	@GetMapping("${hu.bme.sch.auth.default-controller.logout-endpoint:/logout}")
	public String logout(HttpServletRequest request) {
		login.logout(request);
		return "redirect:" + config.getLoggedOut();
	}
	
}
