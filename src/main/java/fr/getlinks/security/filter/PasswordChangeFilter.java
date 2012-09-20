package fr.getlinks.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import fr.getlinks.domain.cassandra.User;

/**
 * 
 * @author DuyHai DOAN
 */
public class PasswordChangeFilter extends GenericFilterBean implements InitializingBean
{

	private static final Logger logger = LoggerFactory.getLogger(PasswordChangeFilter.class);
	private String changePasswordView;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user.isShouldChangePassword())
		{
			RequestDispatcher dispatcher = request.getRequestDispatcher(changePasswordView);
			dispatcher.forward(request, response);
		}
		else
		{
			try
			{
				chain.doFilter(request, response);
			}
			catch (IOException ex)
			{
				logger.error(ex.getMessage(), ex);
				throw ex;
			}

		}
	}

	public void setChangePasswordView(String changePasswordView)
	{
		this.changePasswordView = changePasswordView;
	}

	@Override
	public void afterPropertiesSet() throws ServletException
	{
		super.afterPropertiesSet();
		if (StringUtils.isBlank(this.changePasswordView))
		{
			throw new IllegalArgumentException("The 'changePasswordView' property should be set for the filter " + this.getClass());
		}
	}
}
