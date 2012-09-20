package fr.getlinks.web.view;

/**
 * @author DuyHai DOAN
 */
public class ViewConstants
{
	/*
	 * URL constants
	 */
	public static final String URL_LOGIN = "/login";
	public static final String URL_ROOT = "/";
	public static final String URL_HOME = "/home";
	public static final String URL_ABOUT = "/about";
	public static final String URL_ACTIVATE = "/activate/{randomToken}";
	public static final String URL_CHANGE_PASSWORD = "/changePassword";

	/*
	 * View constants
	 */
	public static final String VIEW_LOGIN = "pages/login";
	public static final String VIEW_HOME = "pages/home";
	public static final String VIEW_ABOUT = "pages/about";

	public static final String VIEW_USER_ACTIVATION = "pages/registration/activation";
	public static final String VIEW_USER_ACTIVATION_INVALID = "pages/registration/notFound";
	public static final String VIEW_CHANGE_PASSWORD = "pages/registration/changePassword";

	/*
	 * Mobile URLS & views
	 */
	public static final String MOBILE_LOGIN = "pages/mobile/login";

	public static final String MOBILE_HOME = "pages/mobile/home";

	public static final String MOBILE_ABOUT = "pages/mobile/about";

}
