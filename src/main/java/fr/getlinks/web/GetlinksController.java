package fr.getlinks.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.getlinks.web.view.ViewConstants;

/**
 * 
 * @author DuyHai DOAN
 */
@Controller
public class GetlinksController
{

	private final Logger log = LoggerFactory.getLogger(GetlinksController.class);

	@RequestMapping(ViewConstants.URL_LOGIN)
	public String loginPage(@RequestParam(value = "error", required = false) Integer errorCode, Model model, HttpServletRequest servletRequest)
	{
		Device currentDevice = DeviceUtils.getCurrentDevice(servletRequest);

		log.info("Is current device mobile : " + currentDevice.isMobile());

		if (currentDevice.isMobile())
		{
			return ViewConstants.MOBILE_LOGIN;
		}
		else
		{

			if (errorCode != null)
			{
				if (errorCode.equals(1))
				{
					model.addAttribute("authenticationError", true);
				}
				else if (errorCode.equals(2))
				{
					String login = servletRequest.getParameter("j_username");
					model.addAttribute("reCaptchaError", true);
					model.addAttribute("login", login);
				}
			}

			return ViewConstants.PAGE_LOGIN;
		}

	}

	@RequestMapping(value =
	{
			ViewConstants.URL_ROOT,
			ViewConstants.URL_HOME
	})
	// @Value("#{'${ajax.session.timeout.http.code}'}") String ajaxSessionTimeoutCode
	public String tatami(Model model, HttpServletRequest servletRequest)
	{
		Device currentDevice = DeviceUtils.getCurrentDevice(servletRequest);

		if (currentDevice.isMobile())
		{
			return ViewConstants.MOBILE_HOME;
		}
		else
		{
			return ViewConstants.PAGE_HOME;
		}

	}

	@RequestMapping(ViewConstants.URL_ABOUT)
	public String about(HttpServletRequest servletRequest)
	{
		Device currentDevice = DeviceUtils.getCurrentDevice(servletRequest);

		if (currentDevice.isMobile())
		{
			return ViewConstants.MOBILE_ABOUT;
		}
		else
		{
			return ViewConstants.PAGE_ABOUT;
		}
	}

}
