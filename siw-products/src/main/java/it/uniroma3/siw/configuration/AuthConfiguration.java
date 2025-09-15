package it.uniroma3.siw.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.uniroma3.siw.model.Credentials;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

	/**
	 * 
	 * la sorgente dati contenente le credenziali è iniettata automaticamente
	 * 
	 */

	@Autowired
	DataSource dataSource;

	/**
	 * 
	 * metodo che contiene le impostazioni di configurazione di autenticazione e di
	 * autorizzazione
	 * 
	 */

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
				// AUTORIZZAZIONE: qui definiamo chi può accedere a cosa
				.authorizeHttpRequests()
				// chiunque (autenticato o no) può accedere alle pagine index, login, register,
				// ai css e alle immagini
				.requestMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/css/**", "/images/**",
						"/product", "/product/**",
						"/searchProducts")
				.permitAll()
				// chiunque (autenticato o no) può mandare richieste POST al punto di accesso
				// per login e register
				.requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
				// solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con
				// path /admin/**
				.requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
				.requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
				// tutti gli utenti autenticati possono accere alle pagine rimanenti
				.anyRequest().authenticated().and().exceptionHandling().accessDeniedPage("/index")

				// LOGIN: qui definiamo come è gestita l'autenticazione
				// usiamo il protocollo formlogin
				.and().formLogin()
				// la pagina di login si trova a /login
				.loginPage("/login")
				// se il login ha successo, si viene rediretti al path /default
				.defaultSuccessUrl("/success", true)

				// LOGOUT: qui definiamo il logout
				.and().logout()
				// il logout è attivato con una richiesta GET a "/logout"
				.logoutUrl("/logout")
				// in caso di successo, si viene reindirizzati alla home
				.logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).clearAuthentication(true).permitAll();
		return http.build();
	}

	/**
	 * 
	 * metodo per definire query sql per ottenere username e password
	 * 
	 */

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource)
				// query per recuperare username e ruolo
				.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
				// query per recupero password
				.usersByUsernameQuery(
						"SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * 
	 * metodo per criptare e decriptare le password
	 * 
	 */

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
