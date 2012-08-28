package fr.getlinks.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import me.prettyprint.cassandra.utils.TimeUUIDUtils;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.getlinks.domain.GeneratedQR;
import fr.getlinks.domain.NetworkLink;
import fr.getlinks.service.util.JavascriptConstantsBuilder;
import fr.getlinks.service.util.TimeUUIdReorder;
import fr.getlinks.web.view.ViewConstants;

/**
 * 
 * @author DuyHai DOAN
 */
@Controller
public class GetlinksController
{

	private final Logger log = LoggerFactory.getLogger(GetlinksController.class);
	private final Map<String, List<String>> links = new HashMap<String, List<String>>();
	private final Map<String, String> networkCodeImageMap = new HashMap<String, String>();
	private final Map<String, String> networkCodeLinkMap = new HashMap<String, String>();

	public GetlinksController() {
		this.networkCodeImageMap.put("FACEBOOK", "facebook.png");
		this.networkCodeImageMap.put("GOOGLE_PLUS", "google_plus.png");
		this.networkCodeImageMap.put("TWITTER", "twitter.png");
		this.networkCodeImageMap.put("LINKEDIN", "linkedin.png");
		this.networkCodeImageMap.put("WORDPRESS", "wordpress.png");

		this.networkCodeLinkMap.put("FACEBOOK", "http://www.facebook.com/getlinkfr.getlink");
		this.networkCodeLinkMap.put("GOOGLE_PLUS", "https://plus.google.com/104873722693929676548");
		this.networkCodeLinkMap.put("TWITTER", "https://twitter.com/getlinkfr");
		this.networkCodeLinkMap.put("LINKEDIN", "http://fr.linkedin.com/pub/getlinks-france/57/516/53a");
		this.networkCodeLinkMap.put("WORDPRESS", "http://getlinksfr.wordpress.com/");
	}

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
	public String home(Model model, HttpServletRequest servletRequest)
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

	@RequestMapping(value = "/js/constants.js", method = RequestMethod.GET)
	public HttpEntity<String> scriptConstants() throws IllegalArgumentException, IllegalAccessException
	{
		Map<String, Object> constantsMap = new HashMap<String, Object>();
		constantsMap.put("HTTP_GET", "GET");
		constantsMap.put("HTTP_POST", "POST");
		constantsMap.put("JSON_DATA", "json");
		constantsMap.put("JSON_CONTENT", "application/json; charset=UTF-8");
		constantsMap.put("HTML", "html; charset=UTF-8");

		String script = new JavascriptConstantsBuilder().add(constantsMap).build();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/javascript");
		HttpEntity<String> httpEntity = new HttpEntity<String>(script, responseHeaders);

		return httpEntity;

	}

	@RequestMapping(value = "/getQR/{uniqueTimeUUID}", produces = "image/png")
	@ResponseBody
	public byte[] generateQRCode(@PathVariable String uniqueTimeUUID, HttpServletRequest request)
	{
		String url = request.getRequestURL().toString().replaceAll("(.+)/getQR/.+", "$1") + "/share/" + uniqueTimeUUID;

		return QRCode.from(url).withSize(200, 200).to(ImageType.PNG).stream().toByteArray();
	}

	@RequestMapping(value = "/share/{uniqueTimeUUID}")
	public String shareLinks(@PathVariable String uniqueTimeUUID, Model model)
	{
		List<String> networkCodes = this.links.get(uniqueTimeUUID) == null ? new ArrayList<String>() : this.links.get(uniqueTimeUUID);

		List<NetworkLink> networkLinks = new ArrayList<NetworkLink>();
		for (String networkCode : networkCodes)
		{
			networkLinks.add(new NetworkLink(networkCode, this.networkCodeImageMap.get(networkCode), this.networkCodeLinkMap.get(networkCode)));
		}
		model.addAttribute("networks", networkLinks);
		return "/pages/share";
	}

	@RequestMapping(value = "/generateLinks", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public GeneratedQR generateLink(@RequestBody List<String> networks, HttpServletRequest request)
	{
		String uniqueTimeUUID = TimeUUIdReorder.reorderTimeUUId(TimeUUIDUtils.getUniqueTimeUUIDinMillis().toString());
		String url = request.getRequestURL().toString().replaceAll("(.+)/generateLinks", "$1") + "/share/" + uniqueTimeUUID;

		if (this.links.size() > 10)
		{
			List<String> uuidList = new ArrayList<String>();
			for (Entry<String, List<String>> entry : this.links.entrySet())
			{
				uuidList.add(entry.getKey());
			}

			Collections.sort(uuidList);
			this.links.remove(uuidList.get(0));
		}
		this.links.put(uniqueTimeUUID, networks);

		return new GeneratedQR(uniqueTimeUUID, networks, url);
	}
}
