package fr.getlinks.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

public class ReCaptchaVerifier extends OncePerRequestFilter
{
	private ReCaptchaFilter reCaptchaFilter;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
			IOException
	{
		if (StringUtils.isEmpty(ReCaptchaFilter.reCaptchaResponse.get()))
		{
			System.out.println("Empty Recaptcha");
		}
		else
		{
			System.out.println("Not empty Recaptcha");
		}

		if (StringUtils.isEmpty(ReCaptchaFilter.reCaptchaChallenge.get()))
		{
			System.out.println("Empty Challenge");
		}
		else
		{
			System.out.println("Not empty Challenge");
		}
		filterChain.doFilter(request, response);
	}

	public void setReCaptchaFilter(ReCaptchaFilter reCaptchaFilter)
	{
		this.reCaptchaFilter = reCaptchaFilter;
	}

}
