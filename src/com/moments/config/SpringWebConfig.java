package com.moments.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.moments.DaoImpl.AlbumDaoImpl;
import com.moments.DaoImpl.PhotoDaoImpl;
import com.moments.DaoImpl.TokenDaoImpl;
import com.moments.DaoImpl.UserDaoImpl;
import com.moments.model.Album;
import com.moments.model.Photo;
import com.moments.model.Token;
import com.moments.model.User;
import com.moments.service.Service;


@EnableWebMvc
// mvc:annotation-driven
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.moments.web.controller" })
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
		registry.addResourceHandler("/pages/**")
				.addResourceLocations("/pages/");
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getResolver() throws IOException {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();

		// Set the maximum allowed size (in bytes) for each individual file.
		resolver.setMaxUploadSize(5242880);// 5MB

		// You may also set other available properties.

		return resolver;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/jsp/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl(System.getenv().get("JDBC_DATABASE_URL")+"?sslmode=require");
		ds.setUsername(System.getenv().get("DATABASE_USER"));
		ds.setPassword(System.getenv().get("DATABASE_PASSWORD"));
		return ds;
	}
	
	
	@Bean
	public AnnotationSessionFactoryBean sessionFactory() {
		AnnotationSessionFactoryBean sf = new AnnotationSessionFactoryBean();
		sf.setDataSource(dataSource());
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.PostgreSQLDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		sf.setHibernateProperties(hibernateProperties);
		sf.setAnnotatedClasses(User.class, Album.class, Photo.class, Token.class);
		return sf;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory().getObject());
		return tx;
	}
	
	@Bean
	public UserDaoImpl userDao() {
		UserDaoImpl user = new UserDaoImpl();
		user.setSessionFactory(sessionFactory().getObject());
		return user;
	}

	@Bean
	public AlbumDaoImpl albumDao() {
		AlbumDaoImpl album = new AlbumDaoImpl();
		album.setSessionFactory(sessionFactory().getObject());
		return album;
	}

	@Bean
	public PhotoDaoImpl photoDao() {
		PhotoDaoImpl photo = new PhotoDaoImpl();
		photo.setSessionFactory(sessionFactory().getObject());
		return photo;
	}
	
	@Bean
	public TokenDaoImpl tokenDao() {
		TokenDaoImpl token = new TokenDaoImpl();
		token.setSessionFactory(sessionFactory().getObject());
		return token;
	}
	
	@Bean
	public Service service() {
		return new Service();
	}
}