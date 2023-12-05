package space.techsmart.mqttprovider;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import space.techsmart.mqttprovider.backend.storage.StorageProperties;
import space.techsmart.mqttprovider.backend.storage.StorageService;
import space.techsmart.mqttprovider.ui.LoginView;

@SpringBootApplication
@Push
@EnableAsync
@EnableConfigurationProperties(StorageProperties.class)
public class MqttproviderApplication extends VaadinWebSecurity implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(MqttproviderApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Delegating the responsibility of general configurations
		// of http security to the super class. It's configuring
		// the followings: Vaadin's CSRF protection by ignoring
		// framework's internal requests, default request cache,
		// ignoring public views annotated with @AnonymousAllowed,
		// restricting access to other views/endpoints, and enabling
		// ViewAccessChecker authorization.
		// You can add any possible extra configurations of your own
		// here (the following is just an example):

		// http.rememberMe().alwaysRemember(false);

		// Configure your static resources with public access before calling
		// super.configure(HttpSecurity) as it adds final anyRequest matcher
		http.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/public/**"))
				.permitAll());

		super.configure(http);

		// This is important to register your login view to the
		// view access checker mechanism:
		setLoginView(http, LoginView.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Customize your WebSecurity configuration.
		super.configure(web);
	}

	/**
	 * Demo UserDetailsManager which only provides two hardcoded
	 * in memory users and their roles.
	 * NOTE: This shouldn't be used in real world applications.
	 */
	@Bean
	public UserDetailsManager userDetailsService() {
		UserDetails user =
				User.withUsername("user")
						.password("{noop}user")
						.roles("USER")
						.build();
		UserDetails admin =
				User.withUsername("admin")
						.password("{noop}admin")
						.roles("ADMIN")
						.build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//storageService.deleteAll();
			storageService.init();
		};
	}

}
