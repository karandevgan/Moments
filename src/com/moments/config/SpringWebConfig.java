package com.moments.config;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.moments.DaoImpl.AlbumDaoImpl;
import com.moments.DaoImpl.PhotoDaoImpl;
import com.moments.DaoImpl.UserDaoImpl;
import com.moments.model.Album;
import com.moments.model.Photo;
import com.moments.model.User;
import com.moments.service.Service;

@EnableWebMvc
// mvc:annotation-driven
@Configuration
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
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/momentsdb");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}

	@Bean
	public AnnotationSessionFactoryBean sessionFactory() {
		AnnotationSessionFactoryBean sf = new AnnotationSessionFactoryBean();
		sf.setDataSource(dataSource());
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		sf.setHibernateProperties(hibernateProperties);
		sf.setAnnotatedClasses(User.class, Album.class, Photo.class);
		return sf;
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
	public Service service() {
		return new Service();
	}
}