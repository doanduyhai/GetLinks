package fr.getlinks.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * @author DuyHai DOAN
 */
@Component(value = "getlinksAuthenticationSuccessHandler")
public class GetlinksAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException
	{
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
