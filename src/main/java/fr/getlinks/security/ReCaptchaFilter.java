package fr.getlinks.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class ReCaptchaFilter extends OncePerRequestFilter
{
	public static final ThreadLocal<String> reCaptchaChallenge = new ThreadLocal<String>();
	public static final ThreadLocal<String> reCaptchaResponse = new ThreadLocal<String>();
	public static final ThreadLocal<String> remoteAddress = new ThreadLocal<String>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,
			IOException
	{
		reCaptchaChallenge.set(request.getParameter("recaptcha_challenge_field"));
		reCaptchaResponse.set(request.getParameter("recaptcha_response_field"));
		remoteAddress.set(request.getRemoteAddr());

		System.out.println("reCaptchaChallenge = " + reCaptchaChallenge.get());
		System.out.println("reCaptchaResponse = " + reCaptchaResponse.get());
		filterChain.doFilter(request, response);
	}
}
